package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Status implements Serializable {


    public void setPost(String post) {
        this.post = post;
    }

    private long timeStamp;

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Text for the status.
     */
    public String post;
    /**
     * User who sent the status.
     */
    public User user;

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    /**
     * String representation of the date/time at which the status was sent.
     */
    public String datetime;
    /**
     * URLs contained in the post text.
     */
    public List<String> urls;
    /**
     * User mentions contained in the post text.
     */
    public List<String> mentions;

    public Status() { }

    public Status(String post, User user, String datetime, List<String> urls, List<String> mentions, long timeStamp) {
        this.post = post;
        this.user = user;
        this.datetime = datetime;
        this.urls = urls;
        this.mentions = mentions;
        this.timeStamp = timeStamp;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getDate() {
        return datetime;
    }

    public String getPost() {
        return post;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }

    public List<String> getMentions() {
        return mentions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(post, status.post) &&
                Objects.equals(user, status.user) &&
                Objects.equals(datetime, status.datetime) &&
                Objects.equals(mentions, status.mentions) &&
                Objects.equals(urls, status.urls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, user, datetime, mentions, urls);
    }

    @Override
    public String toString() {
        return "Status{" +
                "post='" + post + '\'' +
                ", user=" + user +
                ", datetime=" + datetime +
                ", mentions=" + mentions +
                ", urls=" + urls +
                ", timeStamp=" + timeStamp +
                '}';
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }
}