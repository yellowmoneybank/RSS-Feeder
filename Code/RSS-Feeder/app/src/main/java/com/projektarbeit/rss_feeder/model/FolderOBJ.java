package com.projektarbeit.rss_feeder.model;

// 23.06.2017 | AE | Klasse erstellt

public class FolderOBJ {

    private int id;
    private String name;
    private String lastRequestTime;
    private String resource;

    public FolderOBJ (int id, String name, String lastRequestTime, String resource) {
        this.setId(id);
        this.setName(name);
        this.setLastRequestTime(lastRequestTime);
        this.setResource(resource);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(String lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
