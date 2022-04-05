package net.fpl.beehome.Adapter.Message;

public class MessageList {
    private String name, lastMessage;

    public MessageList(String name) {
        this.name = name;
    }

    public MessageList(String name, String lastMessage) {
        this.name = name;
        this.lastMessage = lastMessage;
    }
}
