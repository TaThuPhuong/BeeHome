package net.fpl.beehome.model;

public class HoaDonChiTiet {
    String IDHoaDonCT,IDHoaDon,tenDichVu;
    int soLuong, thanhTien;

    public static final String TB_NAME = "tb_hoaDonCT";

    public HoaDonChiTiet(String IDHoaDonCT, String IDHoaDon, String tenDichVu, int soLuong, int thanhTien) {
        this.IDHoaDonCT = IDHoaDonCT;
        this.IDHoaDon = IDHoaDon;
        this.tenDichVu = tenDichVu;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public HoaDonChiTiet() {
    }

    public String getIDHoaDonCT() {
        return IDHoaDonCT;
    }

    public void setIDHoaDonCT(String IDHoaDonCT) {
        this.IDHoaDonCT = IDHoaDonCT;
    }

    public String getIDHoaDon() {
        return IDHoaDon;
    }

    public void setIDHoaDon(String IDHoaDon) {
        this.IDHoaDon = IDHoaDon;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }
}
