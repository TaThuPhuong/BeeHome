package net.fpl.beehome.model;

public class HopDong {
    String IDHopDong, IDChuTro, IDPhong, IDThanhVien, KyHan, NgayKiHD, NgayBatDau, NgayKetThuc;
    int SoNguoiThue;

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


    public HopDong() {
    }

    public String getIDHopDong() {
        return IDHopDong;
    }

    public void setIDHopDong(String IDHopDong) {
        this.IDHopDong = IDHopDong;
    }

    public String getIDChuTro() {
        return IDChuTro;
    }

    public void setIDChuTro(String IDChuTro) {
        this.IDChuTro = IDChuTro;
    }

    public String getIDPhong() {
        return IDPhong;
    }

    public void setIDPhong(String IDPhong) {
        this.IDPhong = IDPhong;
    }

    public String getIDThanhVien() {
        return IDThanhVien;
    }

    public void setIDThanhVien(String IDThanhVien) {
        this.IDThanhVien = IDThanhVien;
    }

    public String getKyHan() {
        return KyHan;
    }

    public void setKyHan(String kyHan) {
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

    public int getSoNguoiThue() {
        return SoNguoiThue;
    }

    public void setSoNguoiThue(int soNguoiThue) {
        SoNguoiThue = soNguoiThue;
    }
}
