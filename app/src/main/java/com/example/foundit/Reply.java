package com.example.foundit;

/**
 * Model class representing a reply to an item.
 */
public class Reply {
    private String userName;    // Name of the user who replied
    private String replyText;   // Text content of the reply
    private String timestamp;   // Timestamp when the reply was made (optional)

    /**
     * Empty constructor required for Firebase or serialization frameworks.
     */
    public Reply() {
        // No-arg constructor
    }

    /**
     * Constructor with all fields.
     *
     * @param userName  Name of the user who replied.
     * @param replyText The content of the reply.
     * @param timestamp Timestamp when the reply was posted.
     */
    public Reply(String userName, String replyText, String timestamp) {
        this.userName = userName;
        this.replyText = replyText;
        this.timestamp = timestamp;
    }

    // --- Getters ---

    public String getUserName() {
        return userName;
    }

    public String getReplyText() {
        return replyText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // --- Setters ---

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
