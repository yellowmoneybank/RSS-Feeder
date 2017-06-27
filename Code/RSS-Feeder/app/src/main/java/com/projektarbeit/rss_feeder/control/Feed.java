package com.projektarbeit.rss_feeder.control;


import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import java.util.Date;

public class Feed {
    private String title;
    private String shortDescription;
    private String description;
    private String url;
    private Date publicationDate;
    private Date lastBuildTime;
    private Date receiveDate;
    private boolean isRead;
    private String feedAsXML;
    private String domainName;
    private int uniqueKey;

    //Konstruktor für das Erstellen eines Feeds
    public Feed(String title, String shortDescription, String description, String url, Date publicationDate,
                Date lastBuildTime, String feedAsXML, String domainName) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.url = url;
        this.publicationDate = publicationDate;
        this.lastBuildTime = lastBuildTime;
        this.feedAsXML = feedAsXML;
        this.domainName = domainName;
        this.isRead = false;
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/Berlin"));
        this.receiveDate = calendar.getTime();
    }


    public Feed(String title, String shortDescription, String description, String url, Date publicationDate,
                Date lastBuildTime, String feedAsXML, String domainName, int uniqueKey) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.description = description;
        this.url = url;
        this.publicationDate = publicationDate;
        this.lastBuildTime = lastBuildTime;
        this.feedAsXML = feedAsXML;
        this.domainName = domainName;
        this.uniqueKey = uniqueKey;
        this.isRead = false;
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/Berlin"));
        this.receiveDate = calendar.getTime();
    }

    //Getter für die internen Variablen
    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public Date getLastBuildTime() {
        return lastBuildTime;
    }

    public Date getReceiveDate() {
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
