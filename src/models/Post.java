package models;

import exceptions.CustomDateTimeParseException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import utils.GlobalUtil;

public class Post {
    private String id;
    private String content;
    private String author;
    private Integer likes;
    private Integer shares;
    private String dateTime;

    private final BooleanProperty selected = new SimpleBooleanProperty(false);

    public Post(String id, String content, String author, Integer likes, Integer shares, String dateTime)
            throws CustomDateTimeParseException {
        this.id = id;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
        this.dateTime = dateTime;

        GlobalUtil.validateDateControl(dateTime);
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

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public Boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
}
