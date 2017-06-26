package com.projektarbeit.rss_feeder.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.projektarbeit.rss_feeder.R;
import com.projektarbeit.rss_feeder.control.Folder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HoenigDa on 12.06.2017.
 */

public class FolderOverviewFragment extends Fragment {
    public static final String TAG = "FolderOverviewFragment";

    private SwipeRefreshLayout swipeContainer;
    private ListView listView;
    private List<Folder> folderList;
    private FolderAdapter folderAdapter;

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
                Toast.makeText(getActivity(), "Ordner wurden aktualisiert!", Toast.LENGTH_SHORT).show();
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

        updateDataSet();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Folder folder = (Folder) parent.getItemAtPosition(position);

                //FeedOverviewFragment erzeugen und als Bundle einen Ordner-Key mitgeben
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new FeedOverviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString(FeedOverviewFragment.ARG_FOLDERKEY, folder.getFolderName());
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(FeedOverviewFragment.TAG)
                        .commit();
            }
        });
    }

    private void updateDataSet() {
        folderList.clear();

        Folder folder1 = new Folder("Heise.de"); //ToDo: nur temporär, später entfernen -> Folders werden später aus DB gelesen (-> Bei jedem Aufruf neu alle Folders aus DB laden, um neue zu ermitteln)
        Folder folder2 = new Folder("Golem.de");
        Folder folder3 = new Folder("Focus.de");
        folderList.add(folder1);
        folderList.add(folder2);
        folderList.add(folder3);

        folderAdapter.notifyDataSetChanged();
    }
}
