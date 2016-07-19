package com.hzq.redis;


import java.util.Date;

/**
 * Post
 * Created by hzq on 16/6/9.
 */
public class Post {
    private String title;
    private String user;
    private String link;
    private Long time;
    private Integer votes;

    public Post() {
    }

    public Post(String user, String title, String link, Long time, Integer votes) {
        this.user = user;
        this.title = title;
        this.link = link;
        this.time = time;
        this.votes = votes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", user='" + user + '\'' +
                ", link='" + link + '\'' +
                ", time=" + time +
                ", votes=" + votes +
                '}';
    }
}
