package com.softcap.artrosario.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Trailer {
/****
  "results": [
    {
      "id": "5581bd68c3a3685df70000c6",
      "iso_639_1": "en",
      "iso_3166_1": "US",
      "key": "c25GKl5VNeY",
      "name": "Dilwale Dulhania Le Jayenge | Official Trailer | Shah Rukh Khan | Kajol",
      "site": "YouTube",
      "size": 720,
      "type": "Trailer"
    }
 ****/


    @SerializedName("results")
    private ArrayList<Trailer> results;
    @SerializedName("id")
    private String id;
    @SerializedName("iso_639_1")
    private String iso639;
    @SerializedName("iso_3166_1")
    private String iso3166;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private String size;
    @SerializedName("type")
    private String type;

    public Trailer(String id, String key, String name, String site, String size, String type) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso639() {
        return iso639;
    }

    public void setIso639(String iso639) {
        this.iso639 = iso639;
    }

    public String getIso3166() {
        return iso3166;
    }

    public void setIso3166(String iso3166) {
        this.iso3166 = iso3166;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
