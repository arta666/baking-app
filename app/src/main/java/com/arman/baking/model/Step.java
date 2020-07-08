package com.arman.baking.model;

public class Step {
    private int id;
    private String shortDescription;
    private String videoURL;
    private String thumbnailURL;

    public Step() {
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
