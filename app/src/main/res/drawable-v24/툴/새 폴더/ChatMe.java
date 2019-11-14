package com.example.wanna_bet70;

public class ChatMe {
    String chatText;
    String chatTime;
    int viewtype;
    //나와 상대를 구별하는것
    //생성자를 어떻게 만들어야 하지


    public ChatMe(String chatText, String chatTime, int viewtype) {
        this.chatText = chatText;
        this.chatTime = chatTime;
        this.viewtype = viewtype;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public int getViewtype() {
        return viewtype;
    }

    public void setViewtype(int viewtype) {
        this.viewtype = viewtype;
    }
}
