package org.hybridai;

public class Reply implements java.io.Serializable {
    private final String reply;

    public Reply(String message) {
        this.reply = message;
    }

    public String getReply() {
        return reply;
    }
}