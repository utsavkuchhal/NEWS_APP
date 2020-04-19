package com.utsav.sqliteexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source {

    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
