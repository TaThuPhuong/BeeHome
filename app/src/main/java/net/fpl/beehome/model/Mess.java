package net.fpl.beehome.model;

public class Mess {
    private String mess, sdt;

    public Mess() {
    }

    public Mess(String mess, String sdt) {
        this.mess = mess;
        this.sdt = sdt;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
