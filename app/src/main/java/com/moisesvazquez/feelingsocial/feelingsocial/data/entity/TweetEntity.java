package com.moisesvazquez.feelingsocial.feelingsocial.data.entity;

/**
 * Created by moises on 14/10/16.
 */

public class TweetEntity {

    long id;
    String title;
    String description;

    /**
     * Default constructor
     * @param id the id tweet
     */
    public TweetEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
