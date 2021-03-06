package net.conciencia.mensajeandroid.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {

    private String title;
    private String link;
    private String guid;
    private String pubDate;
    private String author;
    private String text;
    private String audioURL;
    private String videoURL;
    private String duration;

    public Message(String title, String link, String guid, String pubDate, String author, String text, String audioURL, String videoURL, String duration) {
        this.title = title;
        this.link = link;
        this.guid = guid;
        this.pubDate = pubDate;
        this.author = author;
        this.text = text;
        this.audioURL = audioURL;
        this.videoURL = videoURL;
        this.duration = duration;
    }

    public Message(Parcel source) {
        title = source.readString();
        link = source.readString();
        guid = source.readString();
        pubDate = source.readString();
        author = source.readString();
        text = source.readString();
        audioURL = source.readString();
        videoURL = source.readString();
        duration = source.readString();
    }

    public String getTitle() {
        return this.title;
    }

    public String getPubDate() {
        return this.pubDate;
    }

    public String getText() {
        return this.text;
    }

    public String getAudioURL() {
        return this.audioURL;
    }

    public String getVideoURL() {
        return this.videoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(guid);
        dest.writeString(pubDate);
        dest.writeString(author);
        dest.writeString(text);
        dest.writeString(audioURL);
        dest.writeString(videoURL);
        dest.writeString(duration);
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>(){

        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}

