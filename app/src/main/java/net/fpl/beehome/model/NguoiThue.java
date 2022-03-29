package net.fpl.beehome.model;

public class NguoiThue {
    String ID_thanhvien;
    String ID_phong;
    String HoTen;
    String SDT;
    String Password;
    String email;
    String CCCD;

    public static final String TB_NGUOITHUE = "tb_Nguoithue";
    public static final String COL_ID_THANHVIEN = "ID_thanhvien";
    public static final String COL_ID_PHONG = "id_phong";
    public static final String COL_HOTEN = "HoTen";
    public static final String COL_SDT = "SDT";
    public static final String COL_PASS = "Password";
    public static final String COL_EMAIL = "email";
    public static final String COL_CCCD = "CCCD";

    public NguoiThue() {
    }

    public NguoiThue(String ID_thanhvien, String ID_phong, String hoTen, String SDT, String password, String email, String CCCD) {
        this.ID_thanhvien = ID_thanhvien;
        this.ID_phong = ID_phong;
        HoTen = hoTen;
        this.SDT = SDT;
        Password = password;
        this.email = email;
        this.CCCD = CCCD;
    }

    public String getID_thanhvien() {
        return ID_thanhvien;
    }

    public void setID_thanhvien(String ID_thanhvien) {
        this.ID_thanhvien = ID_thanhvien;
    }

    public String getID_phong() {
        return ID_phong;
    }

    public void setID_phong(String ID_phong) {
        this.ID_phong = ID_phong;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    @Override
    public String toString() {
        return "NguoiThue{" +
                "ID_thanhvien='" + ID_thanhvien + '\'' +
                ", ID_phong='" + ID_phong + '\'' +
                ", HoTen='" + HoTen + '\'' +
                ", SDT='" + SDT + '\'' +
                ", Password='" + Password + '\'' +
                ", email='" + email + '\'' +
                ", CCCD='" + CCCD + '\'' +
                '}';
    }
}
