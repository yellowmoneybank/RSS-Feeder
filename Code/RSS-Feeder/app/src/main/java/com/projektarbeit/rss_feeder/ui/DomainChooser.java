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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.projektarbeit.rss_feeder.R;

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

                if(radioButtonSelected.equals(rbtnSuggestionDomain)) {
                    createNewFragmentClass(SuggestionDomainFragment.class);

                } else if(radioButtonSelected.equals(rbtnCustomDomain)) {
                    createNewFragmentClass(CustomDomainFragment.class);
                }
            }
        });

        btnAddDomains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Insert into DB folders (Beachte: Die selbstdefinierten müssen in Tabelle bestehen bleiben! evtl. durch zusätzliches Feld als Merkmal für vorgeschlagen oder benutzerdefiniert feststellen)
                Toast.makeText(getActivity(), "ToDo", Toast.LENGTH_SHORT).show();
                //ToDo beachte: unterschiedliche Funktionalität (je nach aktivem Fragment)
            }
        });

        createNewFragmentClass(SuggestionDomainFragment.class);
    }

    private void createNewFragmentClass(Class fragmentClass) {
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
    }
}
