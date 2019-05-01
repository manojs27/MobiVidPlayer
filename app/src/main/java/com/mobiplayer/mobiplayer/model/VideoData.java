package com.mobiplayer.mobiplayer.model;

public class VideoData {
    private String id;
    private String description;
    private String title;
    private String thumb;
    private String url;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getThumb() {
        return thumb;
    }

    public String getUrl() {
        return url;
    }

    public VideoData(String id, String description, String title, String thumb, String url) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.thumb = thumb;
        this.url = url;
    }

}
