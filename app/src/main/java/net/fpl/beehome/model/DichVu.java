package net.fpl.beehome.model;

public class DichVu {
    private String tenDichVu;
    private int gia;
    private String donVi;

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public DichVu(String tenDichVu, int gia, String donVi) {
        this.tenDichVu = tenDichVu;
        this.gia = gia;
        this.donVi = donVi;
    }

    public static final String TB_NAME = "tb_dichVu";
    public static final String COL_NAME = "tenDichVu";
    public static final String COL_GIA = "gia";
    public static final String COL_DONVI = "donVi";

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return "DichVu{" +
                "tenDichVu='" + tenDichVu + '\'' +
                ", gia=" + gia +
                ", donVi='" + donVi + '\'' +
                '}';
    }

    public DichVu() {
    }

}
