package com.arman.baking.model;

import com.google.gson.annotations.Expose;

public class Step {
    @Expose
    private int id;
    @Expose
    private String shortDescription;
    @Expose
    private String videoURL;
    @Expose
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
