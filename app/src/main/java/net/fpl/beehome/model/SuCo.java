package net.fpl.beehome.model;

public class  SuCo {
    String id_suco,id_phong, moTa, ngayBaoCao;

    public static final String TB_NAME = "tb_suCo";
    public static final String COL_IDPHONG = "moTa";
    public static final String COL_NGAYBAOCAO = "ngayBaoCao";

    public SuCo() {
    }


    public String getId_phong() {
        return id_phong;
    }

    public void setId_phong(String id_phong) {
        this.id_phong = id_phong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getNgayBaoCao() {
        return ngayBaoCao;
    }

    public void setNgayBaoCao(String ngayBaoCao) {
        this.ngayBaoCao = ngayBaoCao;
    }

    public String getId_suco() {
        return id_suco;
    }

    public void setId_suco(String id_suco) {
        this.id_suco = id_suco;
    }
}
