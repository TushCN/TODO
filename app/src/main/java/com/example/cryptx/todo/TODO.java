package com.example.cryptx.todo;

import java.io.Serializable;

/**
 * Created by CryptX on 09-09-2017.
 */

public class TODO implements Serializable{
    private String title;
    private int id;

    public TODO(String title, int id) {
        this.title = title;
        this.id=id;
            }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id=id;
    }
}
