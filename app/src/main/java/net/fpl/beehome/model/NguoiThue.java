package net.fpl.beehome.model;

import java.io.Serializable;

public class NguoiThue implements Serializable {
    String id_thanhvien;
    String id_phong;
    String hoTen;
    String sdt;
    String email;
    String cccd;

    public static final String TB_NGUOITHUE = "tb_nguoiThue";
    public static final String COL_ID_THANHVIEN = "id_thanhvien";
    public static final String COL_ID_PHONG = "id_phong";
    public static final String COL_HOTEN = "hoTen";
    public static final String COL_SDT = "sdt";
    public static final String COL_EMAIL = "email";
    public static final String COL_CCCD = "cccd";

    public NguoiThue() {
    }

    public NguoiThue(String id_thanhvien, String id_phong, String hoTen, String sdt, String email, String cccd) {
        this.id_thanhvien = id_thanhvien;
        this.id_phong = id_phong;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.email = email;
        this.cccd = cccd;
    }

    public String getId_thanhvien() {
        return id_thanhvien;
    }

    public void setId_thanhvien(String id_thanhvien) {
        this.id_thanhvien = id_thanhvien;
    }

    public String getId_phong() {
        return id_phong;
    }

    public void setId_phong(String id_phong) {
        this.id_phong = id_phong;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    @Override
    public String toString() {
        return "NguoiThue{" +
                "id_thanhvien='" + id_thanhvien + '\'' +
                ", id_phong='" + id_phong + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", sdt='" + sdt + '\'' +
                ", email='" + email + '\'' +
                ", cccd='" + cccd + '\'' +
                '}';
    }
}
