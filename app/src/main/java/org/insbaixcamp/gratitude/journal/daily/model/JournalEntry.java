package org.insbaixcamp.gratitude.journal.daily.model;

import android.widget.ImageButton;

public class JournalEntry {
    private String date;
    private String phrase;
    private String autor;
    private String titleSun;
    private String messageSun;
    private String titleMoon;
    private String messageMoon;
    private int feeling;

    public JournalEntry(String date, String titleSun, String messageSun, int feeling) {
        this.date = date;
        this.titleSun = titleSun;
        this.messageSun = messageSun;
        this.feeling = feeling;
    }

    public JournalEntry() {
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
}

