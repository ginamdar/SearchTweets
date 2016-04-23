package com.example.guru_i.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by guru_i on 2016-04-22.
 */
public class ParcelableTweet implements Parcelable {
    private long id;
    private String text;
    private String profileUrl;
    private ArrayList<String> mediaUrl;
    public ParcelableTweet(){
    }
    protected ParcelableTweet(Parcel in) {
        id = in.readLong();
        text = in.readString();
        profileUrl = in.readString();
        mediaUrl = (ArrayList<String>)in.readSerializable();
    }

    public void setId(long id){
        this.id = id;
    }
    public void setText(String text){
        this.text = text;
    }
    public void setProfileUrl(String url){ this.profileUrl = url;}
    public void setMediaUrl(ArrayList<String> mediaUrl){this.mediaUrl= mediaUrl;}
    public String getText(){
        return this.text;
    }
    public String getProfileUrl() { return  this.profileUrl;}
    public long getId(){ return this.id;}
    public ArrayList<String> getMediaUrl(){return this.mediaUrl;}

    public static final Creator<ParcelableTweet> CREATOR = new Creator<ParcelableTweet>() {
        @Override
        public ParcelableTweet createFromParcel(Parcel in) {
            return new ParcelableTweet(in);
        }

        @Override
        public ParcelableTweet[] newArray(int size) {
            return new ParcelableTweet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(text);
        dest.writeString(profileUrl);
        dest.writeSerializable(mediaUrl);
    }
}
