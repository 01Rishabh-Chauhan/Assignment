package com.example.wiredviewer;

public class ThumbnailItem {
    private String imgUrl;
    private String title;
    private String detailsUrl;

    public ThumbnailItem() {
    }

    public ThumbnailItem(String imgUrl, String title,String detailsUrl) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.detailsUrl=detailsUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailsUrl() {
        return this.detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
}
