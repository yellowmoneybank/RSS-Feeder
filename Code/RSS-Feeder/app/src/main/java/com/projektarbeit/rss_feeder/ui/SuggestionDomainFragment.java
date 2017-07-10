package com.projektarbeit.rss_feeder.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.projektarbeit.rss_feeder.R;
import com.projektarbeit.rss_feeder.control.Folder;
import com.projektarbeit.rss_feeder.util.SuggestedFolders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HoenigDa on 14.06.2017.
 */

public class SuggestionDomainFragment extends Fragment {
    private View view;
    private List<String> folderNameList;
    private HashMap<Integer, Folder> checkedFolderMap;
    private List<Folder> suggestedFolderList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.suggestiondomain_layout, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ListView listView = (ListView) view.findViewById(R.id.suggestionDomainListView);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        TextView header = (TextView) inflater.inflate(R.layout.list_view_header, null);
        header.setText(R.string.listViewHeaderSuggestionDomain);
        listView.addHeaderView(header, "Head", false);

        checkedFolderMap = new HashMap<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Folder folder = (Folder) parent.getItemAtPosition(position); //ToDO: nicht als Folder casten sondern Folder mit Daten in der Klasse suggestedFOlders erstellen
                Folder folder = suggestedFolderList.get(position-1);
                CheckBox cb = (CheckBox) view.findViewById(R.id.cbSuggestionDomainItemTitle);

                if(!checkedFolderMap.containsKey(position)) {
                    checkedFolderMap.put(position, folder);
                    cb.setChecked(true);

                } else {
                    checkedFolderMap.remove(position);
                    cb.setChecked(false);
                    //ToDo: Checkbox checken bzw. unchecken, je nach Wahl
                }
            }
        });

        //Alle vorgeschlagenen Ordner durchgehen und im UI darstellen
        folderNameList = new ArrayList<>();
        SuggestedFolders suggestedFolders = new SuggestedFolders();
        suggestedFolderList = suggestedFolders.getSuggestedFolders();
        for(Folder folder: suggestedFolderList) {
            folderNameList.add(folder.getFolderName());
        }

        SuggestionDomainAdapter adapter = new SuggestionDomainAdapter(getActivity(), folderNameList);
        listView.setAdapter(adapter);
    }

    public HashMap<Integer, Folder> getCheckedFolderMap() {
        return checkedFolderMap;
    }
}
