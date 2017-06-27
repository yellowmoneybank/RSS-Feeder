package com.projektarbeit.rss_feeder.model;

// 01.06.2017 | AE | Klasse erstellt

public class FeedOBJ {

    private int id;
    private String title;
    private String shortDescription;
    private String descritpion;
    private String link;
    private String lastBuildDate;
    private String publicationDate;
    private String receiveDate;
    private int isRead;
    private String feedAsXML;
    private String domainName;
    private int folder;

    public FeedOBJ(int id, String title, String descritpion, String shortDescription, String link, String lastBuildDate, String publicationDate, String receiveDate, int isRead, String feedAsXML, String domainName, int folder) {
        this.id = id;
        this.title = title;
        this.descritpion = descritpion;
        this.shortDescription = shortDescription;
        this.link = link;
        this.lastBuildDate = lastBuildDate;
        this.publicationDate = publicationDate;
        this.receiveDate = receiveDate;
        this.isRead = isRead;
        this.feedAsXML = feedAsXML;
        this.domainName = domainName;
        this.folder = folder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getFeedAsXML() {
        return feedAsXML;
    }

    public void setFeedAsXML(String feedAsXML) {
        this.feedAsXML = feedAsXML;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public int getFolder() {
        return folder;
    }

    public void setFolder(int folder) {
        this.folder = folder;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
