package com.projektarbeit.rss_feeder.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.projektarbeit.rss_feeder.R;
import com.projektarbeit.rss_feeder.control.Folder;
import com.projektarbeit.rss_feeder.model.DBModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by HoenigDa on 14.06.2017.
 */

public class DomainChooser extends Fragment {
    public static final String TAG = "DomainChooser";

    private View view;
    private RadioGroup radioGroup;
    private RadioButton rbtnSuggestionDomain;
    private RadioButton rbtnCustomDomain;
    private Button btnAddDomains;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.domainchooser_layout, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.rbtngDomainChoose);
        rbtnSuggestionDomain = (RadioButton) view.findViewById(R.id.rbtnSuggestionDomain);
        rbtnCustomDomain = (RadioButton) view.findViewById(R.id.rbtnCustomDomain);
        btnAddDomains = (Button) view.findViewById(R.id.btnAddDomains);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButtonSelected = (RadioButton) radioGroup.findViewById(checkedId);
                Fragment currentFragment;

                if(radioButtonSelected.equals(rbtnSuggestionDomain)) {
                    currentFragment = createNewFragmentClass(SuggestionDomainFragment.class);

                } else if(radioButtonSelected.equals(rbtnCustomDomain)) {
                    currentFragment = createNewFragmentClass(CustomDomainFragment.class);
                }
            }
        });

        btnAddDomains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.content_frame_domain);

                DBModel dbModel = DBModel.getInstance(getActivity());
                if(currentFragment instanceof SuggestionDomainFragment) {
                    HashMap<Integer, Folder> checkedFolderMap = ((SuggestionDomainFragment) currentFragment).getCheckedFolderMap();

                    if(!checkedFolderMap.isEmpty()) {
                        for(Folder folder: checkedFolderMap.values()) {
                            dbModel.saveSingleFolder(folder);
                        }
                        Toast.makeText(getActivity(), R.string.successfullImport, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), R.string.noFoldersSelected, Toast.LENGTH_SHORT).show();
                    }
                } else if(currentFragment instanceof CustomDomainFragment) {
                    EditText txfFolderName = (EditText) currentFragment.getView().findViewById(R.id.txfCustomDomainFolderName);
                    EditText txfResourceURL = (EditText) currentFragment.getView().findViewById(R.id.txfCustomDomainURL);
                    String folderName = txfFolderName.getText().toString();
                    String resourceURL = txfResourceURL.getText().toString();

                    if(!folderName.matches("") &&
                            ((CustomDomainFragment) currentFragment).isValidUrl(resourceURL)) {
                        Date date = new Date();
                        date.setTime(0);

                        Folder folder = new Folder(folderName, null, resourceURL, date);
                        dbModel.saveSingleFolder(folder);
                        Toast.makeText(getActivity(), R.string.successfullImport, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), R.string.invalidFields, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        createNewFragmentClass(SuggestionDomainFragment.class);
    }

    private Fragment createNewFragmentClass(Class fragmentClass) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame_domain, fragment)
                    .commit();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return fragment;
    }
}
