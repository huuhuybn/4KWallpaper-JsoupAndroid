package com.dotplays.slide8.model;

import java.io.Serializable;

public class Photo implements Serializable {
    String linkDetail;
    String urlThumb;

    public String getLinkDetail() {
        return linkDetail;
    }

    public void setLinkDetail(String linkDetail) {
        this.linkDetail = linkDetail;
    }

    public String getUrlThumb() {
        return urlThumb;
    }

    public void setUrlThumb(String urlThumb) {
        this.urlThumb = urlThumb;
    }
}
