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
import android.widget.ListView;
import android.widget.Toast;

import com.projektarbeit.rss_feeder.R;
import com.projektarbeit.rss_feeder.control.Feed;
import com.projektarbeit.rss_feeder.control.FeedContainer;
import com.projektarbeit.rss_feeder.control.Folder;
import com.projektarbeit.rss_feeder.model.DBModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HoenigDa on 12.06.2017.
 */

public class FolderOverviewFragment extends Fragment {
    public static final String TAG = "FolderOverviewFragment";
    private final int MENUID_DELETEFOLDER = 0;

    private SwipeRefreshLayout swipeContainer;
    private ListView listView;
    private List<Folder> folderList;
    private FolderAdapter folderAdapter;
    private FeedContainer feedContainer;
    private Folder selectedFolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        folderList = new ArrayList<Folder>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.folder_list_view, container, false);
        listView = (ListView) view.findViewById(R.id.folderListView);
        folderAdapter = new FolderAdapter(getActivity(), folderList);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerFolder);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                updateDataSet();
                swipeContainer.setRefreshing(false);
                Toast.makeText(getActivity(), R.string.updatedFolders, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        listView.setAdapter(folderAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem == 0) {
                    swipeContainer.setEnabled(true);
                } else {
                    swipeContainer.setEnabled(false);
                }
            }
        });

        feedContainer = FeedContainer.getInstance(DBModel.getInstance(getActivity()));
        updateDataSet();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFolder = (Folder) parent.getAdapter().getItem(position);

                //FeedOverviewFragment erzeugen und als Bundle einen Ordner-Key mitgeben
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new FeedOverviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString(FeedOverviewFragment.ARG_FOLDERKEY, selectedFolder.getFolderName());
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(FeedOverviewFragment.TAG)
                        .commit();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFolder = (Folder) parent.getAdapter().getItem(position);

                registerForContextMenu(parent);
                getActivity().openContextMenu(parent);
                return true;
            }
        });

    }

    private void updateDataSet() {
        folderList.clear();
        folderList.addAll(feedContainer.getAllFolders());
        folderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, MENUID_DELETEFOLDER, Menu.NONE, R.string.deleteFolder);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.feed_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case(MENUID_DELETEFOLDER):
                DBModel.getInstance(getActivity()).deleteFolder(selectedFolder.getFolderID());
                updateDataSet();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
