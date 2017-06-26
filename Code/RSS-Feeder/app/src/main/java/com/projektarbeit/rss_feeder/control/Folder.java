package com.projektarbeit.rss_feeder.control;

public class Folder {

    private String folderName;
    private ArrayList<Feed> content;
    private Uri resource;
    private LocalDateTime lastRequestTime;

    public Folder() {

    }

    public List<Feed> getUnreadFeeds() {

    }

    public void refreshFolder(LocalDateTime lastRequest) {

    }

    public void refreshFolder() {

    }

    public void deleteFeed() {

    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public ArrayList<Feed> getContent() {
        return content;
    }

    public void setContent(HashMap<Integer, Feed> content) {
        this.content = content;
    }

    public Uri getResource() {
        return resource;
    }

    public void setResource(Uri resource) {
        this.resource = resource;
    }

    public LocalDateTime getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(LocalDateTime lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }
}

    public String getFolderName() {
        return folderName;
    }
}