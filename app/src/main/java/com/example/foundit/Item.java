package com.example.foundit;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String id;              // Unique ID from Firebase
    private String name;
    private String description;
    private String userName;
    private String userContact;
    private List<Reply> replies;

    public Item() {
        // Default constructor for Firebase
    }

    public Item(String id, String name, String description, String userName, String userContact) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userName = userName;
        this.userContact = userContact;
        this.replies = new ArrayList<>();
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public void addReply(Reply reply) {
        if (replies == null) {
            replies = new ArrayList<>();
        }
        replies.add(reply);
    }
}
