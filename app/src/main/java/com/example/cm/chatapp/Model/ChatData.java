package com.example.cm.chatapp.Model;

import android.net.Uri;

/**
 * Created by cm on 28/01/2018.
 */

public class ChatData {

    String username , message;
    String uriimg,urivideo;

    public  ChatData()
    {

    }

    public ChatData(String username , String message, String uriimg , String urivideo ) {

        this.username = username;
        this.message = message;
        this.uriimg = uriimg;
        this.urivideo = urivideo;
    }

    public String getMessage() {
        return message;
    }

    public String getUriimg() {
        return uriimg;
    }

    public String getUrivideo() {
        return urivideo;
    }


    public String getUsername() {
        return username;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUriimg(String uriimg) {
        this.uriimg = uriimg;
    }

    public void setUrivideo(String urivideo) {
        this.urivideo = urivideo;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
