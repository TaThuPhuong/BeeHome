package net.fpl.beehome.model;

public class Message {
    private String mess;

    public static final String TB_NAME = "tb_message";
    public static final String COL_MESS = "message";

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public Message() {
    }

    public Message(String mess) {
        this.mess = mess;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mess='" + mess + '\'' +
                '}';
    }
}
