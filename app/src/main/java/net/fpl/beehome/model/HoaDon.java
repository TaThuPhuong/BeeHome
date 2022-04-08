package net.fpl.beehome.model;
import java.util.Date;

public class HoaDon {
    private String IDHoaDon, IDPhong, GhiChu;
    private Date HanGD, ThangHD, NgayGD;
    private int TongHD;
    private int SoDienCuoi;
    private int SoNuocCuoi;
    private int TrangThaiHD;
    //    0: chưa thanh toán, 1: đã thanh toán, 2: qua han
    private int giamGia;
    private int tienPhong;
    private int tienDV, tienDVC;


    public int getTienDVC() {
        return tienDVC;
    }

    public void setTienDVC(int tienDVC) {
        this.tienDVC = tienDVC;
    }

    public static final String TB_NAME = "tb_hoaDon";

    public HoaDon() {
    }

    public HoaDon(String IDHoaDon, String IDPhong, String ghiChu, Date hanGD, Date thangHD, Date ngayGD, int tongHD, int soDienCuoi, int soNuocCuoi, int trangThaiHD, int giamGia, int tienPhong, int tienDV, int tienDVC) {
        this.IDHoaDon = IDHoaDon;
        this.IDPhong = IDPhong;
        GhiChu = ghiChu;
        HanGD = hanGD;
        ThangHD = thangHD;
        NgayGD = ngayGD;
        TongHD = tongHD;
        SoDienCuoi = soDienCuoi;
        SoNuocCuoi = soNuocCuoi;
        TrangThaiHD = trangThaiHD;
        this.giamGia = giamGia;
        this.tienPhong = tienPhong;
        this.tienDV = tienDV;
        this.tienDVC = tienDVC;
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


    public Date getHanGD() {
        return HanGD;
    }

    public void setHanGD(Date hanGD) {
        HanGD = hanGD;
    }

    public Date getThangHD() {
        return ThangHD;
    }

    public void setThangHD(Date thangHD) {
        ThangHD = thangHD;
    }

    public int getTienPhong() {
        return tienPhong;
    }

    public void setTienPhong(int tienPhong) {
        this.tienPhong = tienPhong;
    }

    public int getTienDV() {
        return tienDV;
    }

    public void setTienDV(int tienDV) {
        this.tienDV = tienDV;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "IDHoaDon='" + IDHoaDon + '\'' +
                ", IDPhong='" + IDPhong + '\'' +
                ", NgayGD='" + NgayGD + '\'' +
                ", GhiChu='" + GhiChu + '\'' +
                ", HanGD='" + HanGD + '\'' +
                ", TongHD=" + TongHD +
                ", SoDienCuoi=" + SoDienCuoi +
                ", SoNuocCuoi=" + SoNuocCuoi +
                ", TrangThaiHD=" + TrangThaiHD +
                ", giamGia=" + giamGia +
                ", tienPhong=" + tienPhong +
                ", tienDV=" + tienDV +
                '}';
    }
}
