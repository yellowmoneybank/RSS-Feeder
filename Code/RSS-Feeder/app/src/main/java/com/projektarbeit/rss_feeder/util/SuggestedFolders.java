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
        getSuggestedFolders().add(heise);

        Folder golem = new Folder("golem", null,"https://rss.golem.de/rss.php?feed=RSS2.0", invalidDate);
        getSuggestedFolders().add(golem);

        Folder tagesschau = new Folder("tagesschau", null, "http://www.tagesschau.de/xml/rss2", invalidDate);
        getSuggestedFolders().add(tagesschau);

        Folder chip = new Folder("chip", null, "http://www.chip.de/rss/rss_downloads.xml", invalidDate);
        suggestedFolders.add(chip);
    }


    public ArrayList<Folder> getSuggestedFolders() {
        return suggestedFolders;
    }
}
