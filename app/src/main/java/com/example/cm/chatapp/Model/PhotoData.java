package com.example.cm.chatapp.Model;

import android.net.Uri;

/**
 * Created by cm on 24/01/2018.
 */

public class PhotoData {

    String describtion,name;
    String uri;


    public PhotoData(String describtion, String uri , String name) {
        this.describtion = describtion;
        this.uri = uri;
        this.name = name;
    }

    public String getDescribtion() {
        return describtion;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }
}
