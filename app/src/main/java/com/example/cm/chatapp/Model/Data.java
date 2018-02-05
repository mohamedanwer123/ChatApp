package com.example.cm.chatapp.Model;

/**
 * Created by cm on 23/01/2018.
 */

public class Data {

    String user_name;
    String post;

    public Data(String user_name, String post) {
        this.user_name = user_name;
        this.post = post;

    }

    public String getUser_name() {
        return user_name;
    }

    public String getPost() {
        return post;
    }

}
