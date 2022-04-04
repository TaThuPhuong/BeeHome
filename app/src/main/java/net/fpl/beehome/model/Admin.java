package net.fpl.beehome.model;

import java.io.Serializable;

public class Admin implements Serializable {
    private String hoTen,email;
    public final static String TB_NAME = "tb_admin";
    public final static String COL_HO_TEN = "hoTen";
    public final static String COL_EMAIL = "email";

    public Admin() {
    }

    public Admin(String hoTen, String email) {
        this.hoTen = hoTen;
        this.email = email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "hoTen='" + hoTen + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
