package net.conciencia.mensajeandroid.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stefanandonian on 12/8/16.
 */

public class CasoDeLaSemana implements Parcelable {

    public static final Creator<CasoDeLaSemana> CREATOR = new Parcelable.Creator<CasoDeLaSemana>(){

        @Override
        public CasoDeLaSemana createFromParcel(Parcel source) {
            return new CasoDeLaSemana(source);
        }

        @Override
        public CasoDeLaSemana[] newArray(int size) {
            return new CasoDeLaSemana[size];
        }
    };

    private String id;
    private Date date;
    private String title;
    private String text;
    private String topic;

    public CasoDeLaSemana(Parcel source) {
        this.id = source.readString();
        try {
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String unparsedDate = source.readString();
            this.date = dateParser.parse(unparsedDate);
        } catch (ParseException pE) {
            Log.e("ParseException", "CasoDeLaSemana(Parcel source) : Potentially parcel source packaged it wrong?");
        }
        this.title = source.readString();
        this.text = source.readString();
        this.topic = source.readString();
    }

    public CasoDeLaSemana(String id, Date date, String title, String text, String topic) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.text = text;
        this.topic = topic;
    }

    public String getId() {
        return this.id;
    }

    public Date getDate() {
        return this.date;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public String getTopic() {
        return this.topic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(date.toString());
        parcel.writeString(title);
        parcel.writeString(text);
        parcel.writeString(topic);
    }

    public void print() {
        System.out.println(title + " : " + id);
        System.out.println(topic);
        System.out.println(date.toString());
        System.out.println(text);
    }
}
