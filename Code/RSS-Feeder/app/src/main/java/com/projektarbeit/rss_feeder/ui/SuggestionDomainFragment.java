package com.projektarbeit.rss_feeder.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.projektarbeit.rss_feeder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HoenigDa on 14.06.2017.
 */

public class SuggestionDomainFragment extends Fragment {
    private View view;

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

        List<String> teststrings = new ArrayList<String>(); //TODO: später aus Tabelle  lesen
        teststrings.add("Domain1");
        teststrings.add("Domain2");
        teststrings.add("Domain2");
        teststrings.add("Domain2");
        teststrings.add("Domain2");

        SuggestionDomainAdapter adapter = new SuggestionDomainAdapter(getActivity(), teststrings);
        listView.setAdapter(adapter);
    }
}
