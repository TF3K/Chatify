package com.glsi.chatify;

public class Message {
    private String content;
    private boolean isIncoming;

    public Message(String content, boolean isIncoming) {
        this.content = content;
        this.isIncoming = isIncoming;
    }

    public String getContent() {
        return content;
    }

    public boolean isIncoming() {
        return isIncoming;
    }
}

