package com.example.deliver;

public class ChatVO {
    private String chattingRoomName;
    private String id;
    private String name;
    private String chatContents;

    public ChatVO(String chattingRoomName, String id) {
        this.chattingRoomName = chattingRoomName;
        this.id = id;
        this.name = name;
        this.chatContents = chatContents;
    }

    public String getChattingRoomName() {
        return chattingRoomName;
    }

    public void setChattingRoomName(String chattingRoomName) {
        this.chattingRoomName = chattingRoomName;
    }

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

    public String getChatContents() {
        return chatContents;
    }

    public void setChatContents(String chatContents) {
        this.chatContents = chatContents;
    }
}
