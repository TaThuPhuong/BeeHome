package net.fpl.beehome.model;

public class HopDong {
    public static final String TB_NAME = "tb_hopDong";
    public static final String COL_IDHOPDONG = "id_hop_dong";
    public static final String COL_IDCHUTRO = "id_chu_tro";
    public static final String COL_IDPHONG = "id_phong";
    public static final String COL_IDTHANHVIEN = "id_thanh_vien";
    public static final String COL_KYHAN = "KyHan";
    public static final String COL_NGAYKYHD = "NgayKiHD";
    public static final String COL_NGAYBATDAU = "NgayBatDau";
    public static final String COL_NGAYKETTHUC = "NgayKetThuc";
    public static final String COL_SONGUOITHUE = "SoNguoiThue";
    String id_hop_dong, id_chu_tro, id_phong, id_thanh_vien, NgayKiHD, NgayBatDau, NgayKetThuc;
    Integer SoNguoiThue, KyHan;


    public HopDong() {
    }

    public String getId_hop_dong() {
        return id_hop_dong;
    }

    public void setId_hop_dong(String id_hop_dong) {
        this.id_hop_dong = id_hop_dong;
    }

    public String getId_chu_tro() {
        return id_chu_tro;
    }

    public void setId_chu_tro(String id_chu_tro) {
        this.id_chu_tro = id_chu_tro;
    }

    public String getId_phong() {
        return id_phong;
    }

    public void setId_phong(String id_phong) {
        this.id_phong = id_phong;
    }

    public String getId_thanh_vien() {
        return id_thanh_vien;
    }

    public void setId_thanh_vien(String id_thanh_vien) {
        this.id_thanh_vien = id_thanh_vien;
    }

    public Integer getKyHan() {
        return KyHan;
    }

    public void setKyHan(Integer kyHan) {
        KyHan = kyHan;
    }

    public String getNgayKiHD() {
        return NgayKiHD;
    }

    public void setNgayKiHD(String ngayKiHD) {
        NgayKiHD = ngayKiHD;
    }

    public String getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        NgayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        NgayKetThuc = ngayKetThuc;
    }

    public Integer getSoNguoiThue() {
        return SoNguoiThue;
    }

    public void setSoNguoiThue(Integer soNguoiThue) {
        SoNguoiThue = soNguoiThue;
    }
}
