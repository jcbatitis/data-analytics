import java.sql.Date;

public class Posts {
    private String id;
    private String content;
    private String author;
    private Integer likes;
    private Integer shares;
    private Date date;

    public Posts(String id, String content, String author, Integer likes, Integer shares, Date date) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.likes = likes;
        this.shares = shares;
        this.date = date;

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

    public Date getDate() {
        return date;
    }
}
