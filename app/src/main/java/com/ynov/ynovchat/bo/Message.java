package com.ynov.ynovchat.bo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quentin for YnovChat on 10/12/2021.
 */
public class Message implements Parcelable {
    String content;
    User author;
    String created_at;

    public Message(String content, User author, String created_at) {
        this.content = content;
        this.author = author;
        this.created_at = created_at;
    }

    protected Message(Parcel in) {
        content = in.readString();
        author = in.readParcelable(User.class.getClassLoader());
        created_at = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeParcelable(author, flags);
        dest.writeString(created_at);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

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

