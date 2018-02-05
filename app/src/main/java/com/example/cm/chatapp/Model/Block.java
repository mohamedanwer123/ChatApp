package com.example.cm.chatapp.Model;

/**
 * Created by cm on 04/02/2018.
 */

public class Block {

    int state;
    String name;

    public  Block()
    {

    }
    public Block(int state , String name) {
        this.state = state;
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
