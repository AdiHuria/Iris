package com.app.iris.model;

public class Profile {
    String title;

    public String getProfileSlug() {
        return profileSlug;
    }

    public void setProfileSlug(String profileSlug) {
        this.profileSlug = profileSlug;
    }

    String profileSlug;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    String subTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
