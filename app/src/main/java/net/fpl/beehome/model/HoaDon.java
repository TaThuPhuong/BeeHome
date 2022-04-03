package net.fpl.beehome.model;

import java.util.Date;

public class HoaDon {
    private Date NgayGD;
    private String  IDHoaDon,IDPhong,GhiChu;
    private int TongHD,SoDienCuoi,SoNuocCuoi,TrangThaiHD,giamGia;



    public static final String TB_NAME = "tb_hoaDon";

    public HoaDon() {
    }

    public HoaDon(String IDHoaDon, String IDPhong, Date ngayGD, String ghiChu, int tongHD, int soDienCuoi, int soNuocCuoi, int trangThaiHD) {
        this.IDHoaDon = IDHoaDon;
        this.IDPhong = IDPhong;
        NgayGD = ngayGD;
        GhiChu = ghiChu;
        TongHD = tongHD;
        SoDienCuoi = soDienCuoi;
        SoNuocCuoi = soNuocCuoi;
        TrangThaiHD = trangThaiHD;
    }

    public String getIDHoaDon() {
        return IDHoaDon;
    }

    public void setIDHoaDon(String IDHoaDon) {
        this.IDHoaDon = IDHoaDon;
    }

    public String getIDPhong() {
        return IDPhong;
    }

    public void setIDPhong(String IDPhong) {
        this.IDPhong = IDPhong;
    }

    public Date getNgayGD() {
        return NgayGD;
    }

    public void setNgayGD(Date ngayGD) {
        NgayGD = ngayGD;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public int getTongHD() {
        return TongHD;
    }

    public void setTongHD(int tongHD) {
        TongHD = tongHD;
    }

    public int getSoDienCuoi() {
        return SoDienCuoi;
    }

    public void setSoDienCuoi(int soDienCuoi) {
        SoDienCuoi = soDienCuoi;
    }

    public int getSoNuocCuoi() {
        return SoNuocCuoi;
    }

    public void setSoNuocCuoi(int soNuocCuoi) {
        SoNuocCuoi = soNuocCuoi;
    }

    public int getTrangThaiHD() {
        return TrangThaiHD;
    }

    public void setTrangThaiHD(int trangThaiHD) {
        TrangThaiHD = trangThaiHD;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }
}
