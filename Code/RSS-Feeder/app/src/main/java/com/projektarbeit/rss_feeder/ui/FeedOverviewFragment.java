package com.projektarbeit.rss_feeder.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.projektarbeit.rss_feeder.R;
import com.projektarbeit.rss_feeder.control.Feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HoenigDa on 12.06.2017.
 */

public class FeedOverviewFragment extends Fragment {
    public static final String ARG_FOLDERKEY = "FolderKey";
    public static final String TAG = "FeedOverviewFragment";
    private final int MENUID_MARKFEEDASREAD = 0;
    private final int MENUID_MARKFEEDASUNREAD = 1;

    private ListView listView;
    private SwipeRefreshLayout swipeContainer;
    private List<Feed> arrayOfFeeds;
    private List<Feed> relevantFeedList;
    private FeedAdapter feedAdapter;
    private String folderKey;
    private boolean feedHasRead;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayOfFeeds = new ArrayList<Feed>();
        relevantFeedList = new ArrayList<Feed>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_list_view, container, false);

        Bundle bundle = getArguments();
        folderKey = bundle.getString(ARG_FOLDERKEY);

        listView = (ListView) view.findViewById(R.id.feedListView);
        feedAdapter = new FeedAdapter(getActivity(), relevantFeedList);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerFeed);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                updateDataSet(folderKey);
                swipeContainer.setRefreshing(false);
                Toast.makeText(getActivity(), R.string.updatedFeeds, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Date date = new Date();

        arrayOfFeeds.clear();
        //ToDo: später aus DB lesen und in Liste schreiben, die dann nach X Minuten aktualisieren
        Feed feed1 = new Feed("Testfeed1", "Short1", "Description1", "www.golem.de", date, date, "XMLFeed", "Golem.de", 1, 2);
        arrayOfFeeds.add(feed1); //ToDo: remove after testing
        Feed feed2 = new Feed("Testfeed2", "Short2", "Description2", "www.heise.de", date, date, "XMLFeed", "Heise.de", 2, 1);
        arrayOfFeeds.add(feed2);
        Feed feed3 = new Feed("Testfeed3", "Short3", "Description3", "www.heise.de", date, date, "XMLFeed", "Heise.de", 2, 1);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);
        arrayOfFeeds.add(feed3);

        //Relevante Feeds erstmalig herausfinden
        updateDataSet(folderKey);

        listView.setAdapter(feedAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem == 0) { //notwendig, da da Scrollen in der ListView sonst blockiert wird
                    swipeContainer.setEnabled(true);
                } else {
                    swipeContainer.setEnabled(false);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Feed feed = (Feed) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putString(FeedFragment.ARG_FEEDTITLE, feed.getTitle());
                bundle.putString(FeedFragment.ARG_FEEDSHORTDESCRIPTION, feed.getShortDescription());
                bundle.putString(FeedFragment.ARG_FEEDDESCRIPTION, feed.getDescription());
                bundle.putString(FeedFragment.ARG_URL, feed.getUrl());

                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new FeedFragment();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .addToBackStack(FeedFragment.TAG)
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Feed feed = (Feed) parent.getItemAtPosition(position);
                feedHasRead = feed.isRead();
                registerForContextMenu(parent);
                getActivity().openContextMenu(parent);
                return true;
            }
        });

        Button btnGo2FolderOverview = (Button) getActivity().findViewById(R.id.btnGo2FolderOverview);
        btnGo2FolderOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        Feed feed4 = new Feed("Testfeed4", "Short4", "Description4", "Heise.de", date, date, "XMLFeed", "Heise.de", 4, 1); //ToDo: remove after testing
        arrayOfFeeds.add(feed4);
    }

    private void updateDataSet(String folderKey) {
        relevantFeedList.clear();
        for(Feed feed: arrayOfFeeds) {
            if(feed.getDomainName().equals(folderKey)) {
                relevantFeedList.add(feed); //ToDo: nur noch eine Liste mit Feeds für den gewählten Ordner; nach Ordnerwahl neu aus DB lesen
            }
        }
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(feedHasRead)
            menu.add(0, MENUID_MARKFEEDASUNREAD, Menu.NONE, R.string.markFeedAsUnread);
        else
            menu.add(0, MENUID_MARKFEEDASREAD, Menu.NONE, R.string.markFeedAsRead);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.feed_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case (MENUID_MARKFEEDASREAD):
                Toast.makeText(getActivity(), "mark as read selected", Toast.LENGTH_SHORT).show(); //ToDo: Funktionalitäten implementieren
                return true;
            case (MENUID_MARKFEEDASUNREAD):
                Toast.makeText(getActivity(), "mark as unread selected", Toast.LENGTH_SHORT).show(); //ToDo: Funktionalitäten implementieren
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
