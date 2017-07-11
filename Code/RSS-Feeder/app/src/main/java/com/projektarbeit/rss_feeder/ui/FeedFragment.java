package com.projektarbeit.rss_feeder.ui;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.projektarbeit.rss_feeder.R;

/**
 * Created by HoenigDa on 22.06.2017.
 */

public class FeedFragment extends Fragment {
    public static final String TAG = "FeedFragment";
    public static final String ARG_FEEDTITLE = "FeedTitle";
    public static final String ARG_FEEDSHORTDESCRIPTION = "FeedShortDescription";
    public static final String ARG_FEEDDESCRIPTION = "FeedDescription";
    public static final String ARG_URL = "FeedURL";

    private TextView tvFeedTitle;
    private TextView tvFeedShortDescription;
    private TextView tvFeedDescription;
    private Button btnGo2FeedOverview;
    private Button btnGo2Website;

    private String feedTitle;
    private String feedShortDescription;
    private String feedDescription;
    private String feedUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_layout, container, false);

        tvFeedTitle = (TextView) view.findViewById(R.id.tvFeedTitle);
        tvFeedShortDescription = (TextView) view.findViewById(R.id.tvFeedShortDescription);
        tvFeedDescription = (TextView) view.findViewById(R.id.tvFeedDescription);
        btnGo2FeedOverview = (Button) view.findViewById(R.id.btnGo2FeedOverview);
        btnGo2Website = (Button) view.findViewById(R.id.btnGo2Website);

        feedTitle = getArguments().getString(ARG_FEEDTITLE);
        feedShortDescription = getArguments().getString(ARG_FEEDSHORTDESCRIPTION);
        feedDescription = getArguments().getString(ARG_FEEDDESCRIPTION);
        feedUrl = getArguments().getString(ARG_URL);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        tvFeedTitle.setText(feedTitle);
        tvFeedShortDescription.setText(feedShortDescription);
        tvFeedDescription.setText(feedDescription);

        btnGo2FeedOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        btnGo2Website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!feedUrl.startsWith("http://") && !feedUrl.startsWith("https://"))
                    feedUrl = "http://" + feedUrl;
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(feedUrl));
                    startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), R.string.errorOnOpeningWebsite, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
