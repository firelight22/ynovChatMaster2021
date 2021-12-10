package com.ynov.ynovchat.bo;

/**
 * Created by quentin for YnovChat on 10/12/2021.
 */
public class Message {
    String content;
    User author;
    String created_at;

    public Message(String content, User author, String created_at) {
        this.content = content;
        this.author = author;
        this.created_at = created_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

