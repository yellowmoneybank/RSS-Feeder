package com.projektarbeit.rss_feeder.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.projektarbeit.rss_feeder.R;
import com.projektarbeit.rss_feeder.control.Feed;
import com.projektarbeit.rss_feeder.control.FeedContainer;
import com.projektarbeit.rss_feeder.model.DBModel;

import java.util.ArrayList;
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
    private List<Feed> listOfFeeds;
    private FeedAdapter feedAdapter;
    private String folderKey;
    private FeedContainer feedContainer;
    private Feed selectedFeed;
    private TextView selectedFeedView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listOfFeeds = new ArrayList<Feed>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_list_view, container, false);

        Bundle bundle = getArguments();
        folderKey = bundle.getString(ARG_FOLDERKEY);

        listView = (ListView) view.findViewById(R.id.feedListView);
        feedAdapter = new FeedAdapter(getActivity(), listOfFeeds);
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

        feedContainer = FeedContainer.getInstance(DBModel.getInstance(getActivity()));

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
                selectedFeed = (Feed) parent.getAdapter().getItem(position);
                selectedFeedView = (TextView) view.findViewById(R.id.tvFeedItemTitle);
                if(!selectedFeed.isRead()) {
                    selectedFeed.setRead(true);
                    DBModel.getInstance(getActivity()).updateFeed(selectedFeed.getId(), true);
                }
                //ToDo: updateFeed in Database -> Wie die ID bekommen? Fragen!
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
    }

    private void updateDataSet(String folderKey) {
        listOfFeeds.clear();
        //if(feedContainer.getFolderByName(folderKey).getContent().isEmpty()) {
            feedContainer.getFolderByName(folderKey).refreshFolder(); //ToDo: richtig hier? noch zu entfernen!
        //}
        //for(Feed feed: feedContainer.getFolderByName(folderKey).getContent()) {
            listOfFeeds.addAll(feedContainer.getFolderByName(folderKey).getContent());
        //}
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(selectedFeed.isRead())
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
                Toast.makeText(getActivity(), "Als gelesen markieren ausgewählt", Toast.LENGTH_SHORT).show(); //ToDo: Funktionalitäten implementieren + Toast entfernen!
                selectedFeed.setRead(true);
                selectedFeedView.setTypeface(Typeface.DEFAULT);
                DBModel.getInstance(getActivity()).updateFeed(selectedFeed.getId(), true);
                return true;
            case (MENUID_MARKFEEDASUNREAD):
                Toast.makeText(getActivity(), "Als ungelesen markieren ausgewählt", Toast.LENGTH_SHORT).show(); //ToDo: Funktionalitäten implementieren
                selectedFeed.setRead(false);
                selectedFeedView.setTypeface(Typeface.DEFAULT_BOLD);
                DBModel.getInstance(getActivity()).updateFeed(selectedFeed.getId(), false);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
