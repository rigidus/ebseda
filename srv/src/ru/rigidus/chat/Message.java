package ru.rigidus.chat;

public class Message {
    public String from;
    public String text;

    public Message(String str, String from)
    {
        this.text = str;
        this.from = from;
    }
}
