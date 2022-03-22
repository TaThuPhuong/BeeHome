package net.fpl.beehome.model;

public class Phong {
    private String IDPhong, soPhong, trangThai, vatTu;
    private int giaPhong, soDienDau, soNuocDau;
    public static final String TB_NAME = "tb_phong";
    public static final String COL_ID = "id_phong";
    public static final String COL_SO_PHONG = "so_phong";
    public static final String COL_VAT_TU = "vat_tu";
    public static final String COL_TRANG_THAI = "trang_thai";
    public static final String COL_GIA_PHONG = "gia_phong";
    public static final String COL_SO_DIEN_DAU = "so_dien_dau";
    public static final String COL_SO_NUOC_DAU = "so_nuoc_dau";

    public Phong() {
    }

    public Phong(String IDPhong, String soPhong, String trangThai, String vatTu, int giaPhong, int soDienDau, int soNuocDau) {
        this.IDPhong = IDPhong;
        this.soPhong = soPhong;
        this.trangThai = trangThai;
        this.vatTu = vatTu;
        this.giaPhong = giaPhong;
        this.soDienDau = soDienDau;
        this.soNuocDau = soNuocDau;
    }

    public String getIDPhong() {
        return IDPhong;
    }

    public void setIDPhong(String IDPhong) {
        this.IDPhong = IDPhong;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getVatTu() {
        return vatTu;
    }

    public void setVatTu(String vatTu) {
        this.vatTu = vatTu;
    }

    public int getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(int giaPhong) {
        this.giaPhong = giaPhong;
    }

    public int getSoDienDau() {
        return soDienDau;
    }

    public void setSoDienDau(int soDienDau) {
        this.soDienDau = soDienDau;
    }

    public int getSoNuocDau() {
        return soNuocDau;
    }

    public void setSoNuocDau(int soNuocDau) {
        this.soNuocDau = soNuocDau;
    }

    @Override
    public String toString() {
        return "Phong{" +
                "IDPhong='" + IDPhong + '\'' +
                ", soPhong='" + soPhong + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", vatTu='" + vatTu + '\'' +
                ", giaPhong=" + giaPhong +
                ", soDienDau=" + soDienDau +
                ", soNuocDau=" + soNuocDau +
                '}';
    }


}
