package org.insbaixcamp.gratitude.journal.daily.model;

import android.os.Parcel;
import android.os.Parcelable;

public class JournalEntry implements Parcelable {
    private String date;
    private String phrase;
    private String autor;
    private String titleSun;
    private String messageSun;
    private String titleMoon;
    private String messageMoon;
    private int feeling;

    public JournalEntry() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTitleSun(String titleSun) {
        this.titleSun = titleSun;
    }

    public void setMessageSun(String messageSun) {
        this.messageSun = messageSun;
    }

    public void setTitleMoon(String titleMoon) {
        this.titleMoon = titleMoon;
    }

    public void setMessageMoon(String messageMoon) {
        this.messageMoon = messageMoon;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public JournalEntry(String date, String titleSun, String messageSun, int feeling) {
        this.date = date;
        this.titleSun = titleSun;
        this.messageSun = messageSun;
        this.feeling = feeling;
    }

    public JournalEntry(String date, String phrase, String autor, String titleSun, String messageSun, String titleMoon, String messageMoon, int feeling) {
        this.date = date;
        this.phrase = phrase;
        this.autor = autor;
        this.titleSun = titleSun;
        this.messageSun = messageSun;
        this.titleMoon = titleMoon;
        this.messageMoon = messageMoon;
        this.feeling = feeling;
    }

    protected JournalEntry(Parcel in) {
        date = in.readString();
        phrase = in.readString();
        autor = in.readString();
        titleSun = in.readString();
        messageSun = in.readString();
        titleMoon = in.readString();
        messageMoon = in.readString();
        feeling = in.readInt();
    }

    public static final Creator<JournalEntry> CREATOR = new Creator<JournalEntry>() {
        @Override
        public JournalEntry createFromParcel(Parcel in) {
            return new JournalEntry(in);
        }

        @Override
        public JournalEntry[] newArray(int size) {
            return new JournalEntry[size];
        }
    };

    public String getDate() {
        return date;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitleSun() {
        return titleSun;
    }

    public String getMessageSun() {
        return messageSun;
    }

    public String getTitleMoon() {
        return titleMoon;
    }

    public String getMessageMoon() {
        return messageMoon;
    }

    public int getFeeling() {
        return feeling;
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "date='" + date + '\'' +
                ", phrase='" + phrase + '\'' +
                ", autor='" + autor + '\'' +
                ", titleSun='" + titleSun + '\'' +
                ", messageSun='" + messageSun + '\'' +
                ", titleMoon='" + titleMoon + '\'' +
                ", messageMoon='" + messageMoon + '\'' +
                ", feeling=" + feeling +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(phrase);
        dest.writeString(autor);
        dest.writeString(titleSun);
        dest.writeString(messageSun);
        dest.writeString(titleMoon);
        dest.writeString(messageMoon);
        dest.writeInt(feeling);
    }
}
