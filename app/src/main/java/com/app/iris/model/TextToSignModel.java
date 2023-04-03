package com.app.iris.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class TextToSignModel implements Serializable {
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getSlug_name() {
        return slug_name;
    }

    public void setSlug_name(String slug_name) {
        this.slug_name = slug_name;
    }

    String slug_name;

    public Drawable getIcons() {
        return icons;
    }

    public void setIcons(Drawable icons) {
        this.icons = icons;
    }

    Drawable icons;

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    int drawableId;

    public boolean equals(Object obj){

        if(!(obj instanceof TextToSignModel)){
            return false; //objects cant be equal
        }

        TextToSignModel employeeDataSubModel = (TextToSignModel) obj;

        return this.title.equals(employeeDataSubModel.title);

    }

    // The below method is not required to be overridden if you are not using HashSet, HashMap or Hashtable,
// but for consistency and extensibility let us do this
    public int hashCode(){
        return this.title.hashCode();
    }
}
