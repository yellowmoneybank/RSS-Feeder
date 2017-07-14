package com.projektarbeit.rss_feeder.util;

import com.projektarbeit.rss_feeder.control.Folder;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexander on 29.06.2017.
 */

// 29.06.2017 | AE | Klasse erstellt

public class SuggestedFolders {

    private ArrayList<Folder> suggestedFolders;

    private Date invalidDate = new Date();

    public SuggestedFolders() {

        invalidDate.setTime(0);

        suggestedFolders = new ArrayList<Folder>();

        Folder heise = new Folder("heise", null, "https://www.heise.de/newsticker/heise.rdf", invalidDate );
        suggestedFolders.add(heise);

        Folder tagesschau = new Folder("tagesschau", null, "http://www.tagesschau.de/xml/rss2", invalidDate);
        suggestedFolders.add(tagesschau);

        Folder chip = new Folder("chip", null, "http://www.chip.de/rss/rss_downloads.xml", invalidDate);
        suggestedFolders.add(chip);

        Folder spiegel = new Folder("spiegel", null, "http://www.spiegel.de/schlagzeilen/index.rss", invalidDate);
        suggestedFolders.add(spiegel);

        Folder faz = new Folder("faz", null, "http://www.faz.net/rss/aktuell/", invalidDate);
        suggestedFolders.add(faz);

        Folder computerbild = new Folder("computerbild", null, "http://www.computerbild.de/rssfeed_2261.html?node=10", invalidDate);
        suggestedFolders.add(computerbild);

        Folder welt = new Folder("welt", null, "https://www.welt.de/feeds/latest.rss", invalidDate);
        suggestedFolders.add(welt);

        Folder onlinezeitung = new Folder("onlinezeitung", null, "http://online-zeitung.de/feed/", invalidDate);
        suggestedFolders.add(onlinezeitung);

        Folder zeitonline = new Folder("zeitonline", null, "http://newsfeed.zeit.de/index", invalidDate);
        suggestedFolders.add(zeitonline);
    }


    public ArrayList<Folder> getSuggestedFolders() {
        return suggestedFolders;
    }
}
