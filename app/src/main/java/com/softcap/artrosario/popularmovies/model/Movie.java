package com.softcap.artrosario.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/****
Sample JSON
     "vote_count": 1491,
      "id": 19404,
      "video": false,
      "vote_average": 9.2,
      "title": "Dilwale Dulhania Le Jayenge",
      "popularity": 13.964362,
      "poster_path": "\/uC6TTUhPpQCmgldGyYveKRAu8JN.jpg",
      "original_language": "hi",
      "original_title": "दिलवाले दुल्हनिया ले जायेंगे",
      "genre_ids": [
        35,
        18,
        10749
      ],
      "backdrop_path": "\/nl79FQ8xWZkhL3rDr1v2RFFR6J0.jpg",
      "adult": false,
      "overview": "Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.",
      "release_date": "1995-10-20"
    }
****/
@Entity(tableName = "Favorites")
public class Movie implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;
    @SerializedName("video")
    private Boolean video;


    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    //@SerializedName("genre_ids")
   // private List<Integer> genreIds;
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @SerializedName("adult")
    private Boolean adult;
    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    private boolean isFavorite = false;



    public Movie(String id, Double voteAverage, String title, String posterPath, String backdropPath, String overview, String releaseDate, boolean isFavorite) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.isFavorite = isFavorite;
    }

    @Ignore
    public Movie(String id, Double voteAverage, String title, String posterPath, String backdropPath, String overview, String releaseDate, Date updatedAt, boolean isFavorite) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.updatedAt = updatedAt;
    }


    public Movie(Parcel in){
        this.id = in.readString();
        this.voteAverage = in.readDouble();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
/*
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }
*/
    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }




}

