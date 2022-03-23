package net.fpl.beehome.model;

public class NguoiThue {
    int ID_thanhvien;
    int ID_phong;
    String HoTen;
    String NgaySinh;
    String Username;
    String Password;
    String SDT;
    String CCCD;

    public static final String TB_NGUOITHUE = "tb_Nguoithue";
    public static final String COL_ID_THANHVIEN = "id_Thanhvien";
    public static final String COL_ID_PHONG = "id_Phong";
    public static final String COL_HOTEN = "hoTen";
    public static final String COL_NGAYSINH = "ngaySinh";
    public static final String COL_USERNAME = "userName";
    public static final String COL_PASSWORD = "passWord";
    public static final String COL_SDT = "SDT";
    public static final String COL_CCCD = "CCCD";

    public NguoiThue(int ID_thanhvien, int ID_phong, String hoTen, String ngaySinh, String username, String password, String SDT, String CCCD) {
        this.ID_thanhvien = ID_thanhvien;
        this.ID_phong = ID_phong;
        HoTen = hoTen;
        NgaySinh = ngaySinh;
        Username = username;
        Password = password;
        this.SDT = SDT;
        this.CCCD = CCCD;
    }

    public int getID_thanhvien() {
        return ID_thanhvien;
    }

    public void setID_thanhvien(int ID_thanhvien) {
        this.ID_thanhvien = ID_thanhvien;
    }

    public int getID_phong() {
        return ID_phong;
    }

    public void setID_phong(int ID_phong) {
        this.ID_phong = ID_phong;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }
}
