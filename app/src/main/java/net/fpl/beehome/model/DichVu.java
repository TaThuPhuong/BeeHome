package net.fpl.beehome.model;

public class DichVu {
    private int idDichVu;
    private String tenDichVu;
    private String donViTinh;
    private int gia;

    public static final String TB_NAME = "tb_dichVu";
    public static final String COL_ID = "id_dichVu";
    public static final String COL_NAME = "name_dichVu";
    public static final String COL_DONVI = "donVi_dichVu";
    public static final String COL_GIA = "gia_dichVu";

    public int getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(int idDichVu) {
        this.idDichVu = idDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public DichVu() {
    }

    public DichVu(int idDichVu, String tenDichVu, String donViTinh, int gia) {
        this.idDichVu = idDichVu;
        this.tenDichVu = tenDichVu;
        this.donViTinh = donViTinh;
        this.gia = gia;
    }
}
