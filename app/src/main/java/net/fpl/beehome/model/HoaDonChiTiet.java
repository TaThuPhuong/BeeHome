package net.fpl.beehome.model;

public class HoaDonChiTiet {
    public static final String TB_NAME = "tb_hoaDonCT";
    String IDHoaDonCT, IDHoaDon, tenDichVu;
    int soLuong, thanhTien;

    public HoaDonChiTiet(String IDHoaDonCT, String IDHoaDon, String tenDichVu, int soLuong, int thanhTien) {
        this.IDHoaDonCT = IDHoaDonCT;
        this.IDHoaDon = IDHoaDon;
        this.tenDichVu = tenDichVu;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public HoaDonChiTiet() {
    }

    @Override
    public String toString() {
        return "HoaDonChiTiet{" +
                "IDHoaDonCT='" + IDHoaDonCT + '\'' +
                ", IDHoaDon='" + IDHoaDon + '\'' +
                ", tenDichVu='" + tenDichVu + '\'' +
                ", soLuong=" + soLuong +
                ", thanhTien=" + thanhTien +
                '}';
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
