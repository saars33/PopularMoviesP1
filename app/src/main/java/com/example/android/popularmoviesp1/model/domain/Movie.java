package com.example.android.popularmoviesp1.model.domain;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ARVIND on 7/17/2016.
 */
public class Movie implements Parcelable {

    private int mId;
    private String mOriginalTitle;
    private String mPlotOverview;
    private double mVoteAverage;
    private String mReleaseDate;
    private double mPopularity;
    private String mPosterPath;

    public Movie() {

    }

    public Movie(String posterPath, int id, String originalTitle, String plotOverview, double popularity, String releaseDate, double voteAverage) {
        mPosterPath = posterPath;
        mId = id;
        mOriginalTitle = originalTitle;
        mPlotOverview = plotOverview;
        mPopularity = popularity;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getPlotOverview() {
        return mPlotOverview;
    }

    public void setPlotOverview(String plotOverview) {
        mPlotOverview = plotOverview;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(double popularity) {
        mPopularity = popularity;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mId=" + mId +
                ", mOriginalTitle='" + mOriginalTitle + '\'' +
                ", mPlotOverview='" + mPlotOverview + '\'' +
                ", mVoteAverage=" + mVoteAverage +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mPopularity=" + mPopularity +
                ", mPosterPath='" + mPosterPath + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mOriginalTitle);
        dest.writeString(this.mPlotOverview);
        dest.writeDouble(this.mVoteAverage);
        dest.writeString(this.mReleaseDate);
        dest.writeDouble(this.mPopularity);
        dest.writeString(this.mPosterPath);
    }

    protected Movie(Parcel in) {
        this.mId = in.readInt();
        this.mOriginalTitle = in.readString();
        this.mPlotOverview = in.readString();
        this.mVoteAverage = in.readDouble();
        this.mReleaseDate = in.readString();
        this.mPopularity = in.readDouble();
        this.mPosterPath = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
