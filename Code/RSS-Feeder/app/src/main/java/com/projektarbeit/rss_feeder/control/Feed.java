package com.projektarbeit.rss_feeder.control;

import android.content.Intent;
import android.net.Uri;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class Feed {
    private String title;
    private String description;
    private Uri link;
    private LocalDateTime publicationDate;
    private LocalDateTime lastBuildTime;
    private LocalDateTime receiveDate;
    private boolean isRead;
    private String feedAsXML;
    private String domainName;
    private int uniqueKey;

    //Konstruktor für das Erstellen eines Feeds
    public Feed(String title, String description, Uri link, LocalDate publicationDate, LocalDateTime lastBuildTime,
                String feedAsXML, String domainName, int uniqueKey) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.publicationDate = publicationDate;
        this.lastBuildTime = lastBuildTime;
        this.feedAsXML = feedAsXML;
        this.domainName = domainName;
        this.uniqueKey = uniqueKey;
        this.isRead = false;

        //Aktuelle Zeit mit Hilfe der Library "Joda-Time" ermitteln
        LocalTime localTime = new LocalTime();
        this.receiveDate = localTime;
    }

    //Getter für die internen Variablen
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Uri getLink() {
        return link;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public LocalDateTime getLastBuildTime() {
        return lastBuildTime;
    }

    public LocalDateTime getReceiveDate() {
        return receiveDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getFeedAsXML() {
        return feedAsXML;
    }

    public String getDomainName() {
        return domainName;
    }

    public int getUniqueKey() {
        return uniqueKey;
    }

    //Setter für die interne Variablen
    public void setRead(boolean read) {
        if(this.isRead != read)
            isRead = read;
    }
}
