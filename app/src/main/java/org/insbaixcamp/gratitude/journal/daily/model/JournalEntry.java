package org.insbaixcamp.gratitude.journal.daily.model;

public class JournalEntry {
    private String date;
    private String title;
    private String content;
    private int iconResource;

    public JournalEntry() {
    }

    public JournalEntry(String date, String title, String content, int iconResource) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.iconResource = iconResource;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getIconResource() {
        return iconResource;
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", iconResource=" + iconResource +
                '}';
    }
}

