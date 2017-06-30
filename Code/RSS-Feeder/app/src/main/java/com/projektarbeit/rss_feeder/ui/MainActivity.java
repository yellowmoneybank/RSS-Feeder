package com.projektarbeit.rss_feeder.ui;

/**
 * Created by HoenigDa on 12.06.2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.projektarbeit.rss_feeder.R;
import com.projektarbeit.rss_feeder.control.FeedContainer;
import com.projektarbeit.rss_feeder.model.DBModel;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {
    private String[] drawerTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence itemTitle;
    private CharSequence drawerTitle;

    private boolean backButtonPressedOnce = false;

    private Context contextOfApplication;
    private static DBModel dbModel;
    private static FeedContainer feedContainer;

    //"Konstruktor" der Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = this;

        //Layout des Drawers
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //ListView, welche alle Items darstellt
        drawerList = (ListView) findViewById(R.id.left_drawer);

        //ItemTitels, mit denen die ListView gefüllt wird
        drawerTitles = getResources().getStringArray(R.array.itemTitleStrings);

        //Adapter der drawerListe für die einzelnen Items
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerTitles));

        //Aktuell angezeigter Titel im Head der Applikation
        itemTitle = drawerTitle = getTitle();

        //OnItemClickListener setzen, um auf Itemklicks reagieren zu können
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemTitle = ((TextView)view).getText().toString();
                ArrayList<String> tmpList = new ArrayList<String>(Arrays.asList(drawerTitles));

                if(itemTitle.equals(tmpList.get(0))) { //1. Item: RSS-Homepages
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new DomainChooser();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(DomainChooser.TAG)
                            .commit();
                } else if(itemTitle.equals(tmpList.get(1))) { //2. Item: RSS-Feed-Übersicht bzw. RSS-Folder-Übersicht
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new FolderOverviewFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(FolderOverviewFragment.TAG)
                            .commit();
                } else if(itemTitle.equals(tmpList.get(2))) { //3. Item: Einstellungen
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new SettingsFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(SettingsFragment.TAG)
                            .commit();
                }

                //Aktives Item hervorheben, Titel in Abhängigkeit des gewählen Items setzen
                //NavigationDrawer abschließend schließen
                drawerList.setItemChecked(position, true);
                setTitle(drawerTitles[position]);
                drawerLayout.closeDrawer(drawerList);
            }
        });

        //drawerToggle für das Ein- und Ausblenden des NavigationDrawers auf der linken Seite
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActionBar().setTitle(itemTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu();
            }
        };

        //DrawerListener setzen
        drawerLayout.setDrawerListener(drawerToggle);

        //Homebutton aktivieren, um auch so auf den NavigationDrawer zugreifen zu können
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //Funktionen, Ort evtl. noch ändern
        createWelcomeFragment();

        //TODO: am Anfang das erste Mal die Tabelle mit den Feeds updaten und dann nur noch wenn man einen Folder auswählt
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbModel = DBModel.getInstance(contextOfApplication);
        feedContainer = FeedContainer.getInstance(dbModel);
        int zahl = 5; //Todo: remove
    }

    private void createWelcomeFragment() {
        Fragment fragment = new WelcomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() >= 1) {
            if (getFragmentManager().getBackStackEntryCount() == 1) {
                setTitle(drawerTitle);
            } else  {
                setTitle(itemTitle);
            }
            getFragmentManager().popBackStack();
        } else if (!backButtonPressedOnce){
            backButtonPressedOnce = true;
            drawerLayout.openDrawer(drawerList);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState(); //Indikator setzen, ob NavigationDrawer geöffnet oder geschlossen ist
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        itemTitle = title;
        getActionBar().setTitle(itemTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        dbModel.closeDatabase();
    }
}