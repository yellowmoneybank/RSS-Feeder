package com.projektarbeit.rss_feeder.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.projektarbeit.rss_feeder.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HoenigDa on 14.06.2017.
 */

public class CustomDomainFragment extends Fragment {
    private EditText txfCustomWebsite;
    private Button btnValidateURL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customdomain_layout, container, false);

        txfCustomWebsite = (EditText) view.findViewById(R.id.txfCustomWebsite);
        btnValidateURL = (Button) view.findViewById(R.id.btnValidateURL);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        btnValidateURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidUrl(txfCustomWebsite.getText().toString()))
                    Toast.makeText(getActivity(), R.string.validURL, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), R.string.invalidURL, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidUrl(String url) {
        Pattern pattern = Patterns.WEB_URL;
        Matcher matcher = pattern.matcher(url.toLowerCase());
        return matcher.matches();
    }
}
