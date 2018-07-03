package com.softcap.artrosario.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Review {
/****
 SAMPLE JSON
  "results": [
    {
      "author": "Reno",
      "content": "Well, one ofll the Chris Nolan fans,ng show ..."
       "id": "5463856c0e0a267815002598",
      "url": "https://www.themoviedb.org/review/5463856c0e0a267815002598
 ****/
    @SerializedName("results")
    private ArrayList<Review> results;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;

    public Review( String author, String content, String id, String url) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.url = url;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

    public void setResults(ArrayList<Review> results) {
        this.results = results;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
