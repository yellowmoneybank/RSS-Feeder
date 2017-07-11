package com.projektarbeit.rss_feeder.ui;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.projektarbeit.rss_feeder.R;

/**
 * Created by HoenigDa on 12.06.2017.
 */

public class SettingsFragment extends Fragment {
    public static final String TAG = "SettingsFragment";
    public static final String PREFERENCENAME = "SettingsPreferences";

    private EditText txfUsername;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_layout, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        txfUsername = (EditText) getActivity().findViewById(R.id.txfUsername);
        SharedPreferences settings = getActivity().getSharedPreferences(PREFERENCENAME, 0);
        if(!settings.getString("Username", "").equals("")) {
            txfUsername.setText(settings.getString("Username", ""));
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences settings = getActivity().getSharedPreferences(PREFERENCENAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        String test = txfUsername.getText().toString();
        editor.putString("Username", txfUsername.getText().toString());
        editor.apply();
    }
}
