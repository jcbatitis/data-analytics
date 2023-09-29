package models;

public class Post {
    private String id;
    private String content;
    private String author;
    private Integer likes;
    private Integer shares;
    private String dateTime;

    public Post(String id, String content, String author, Integer likes, Integer shares, String dateTime) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
        this.dateTime = dateTime;
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
}
