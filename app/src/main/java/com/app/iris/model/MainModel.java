package com.app.iris.model;

import android.graphics.drawable.Drawable;

public class MainModel {
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    String subTitle;

    public String getSlug_name() {
        return slug_name;
    }

    public void setSlug_name(String slug_name) {
        this.slug_name = slug_name;
    }

    String slug_name;

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }

    int colors;
    int colorsText;

    public int getColorsText() {
        return colorsText;
    }

    public void setColorsText(int colorsText) {
        this.colorsText = colorsText;
    }

    public Drawable getIcons() {
        return icons;
    }

    public void setIcons(Drawable icons) {
        this.icons = icons;
    }

    Drawable icons;
}
