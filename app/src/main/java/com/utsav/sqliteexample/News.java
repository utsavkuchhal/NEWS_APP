package com.utsav.sqliteexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResults")
    @Expose
    private String totalResults;

    @SerializedName("articles")
    @Expose
    private List<Article> articles;

    public String getStatus() {
        return status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }



}
