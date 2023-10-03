package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Post {
    private String id;
    private String content;
    private String author;
    private Integer likes;
    private Integer shares;
    private String dateTime;
    private String userId;

    private final BooleanProperty selected = new SimpleBooleanProperty(false);


    public Post(String id, String content, String author, Integer likes, Integer shares, String dateTime,
            String userId) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
        this.dateTime = dateTime;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getLikes() {
        return likes;
    }

    public Integer getShares() {
        return shares;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getUserId() {
        return userId;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public Boolean getSelected() {
        return selected.get();
    }
}
