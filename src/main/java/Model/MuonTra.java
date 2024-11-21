package Model;

import java.sql.Date;

public class MuonTra {
    private String maMuonTra;
    private String maDocGia;
    private String tenDocGia;    
    private String maSach;
    private String tenSach;
    private String theLoai;
    private String tenTacGia;
    private String maNhanVien;
    private String ngayMuon;
    private String ngayTra;
    private String maPhieu;
    
    
    public MuonTra() {
        
    }

    public MuonTra(String maMuonTra, String maDocGia, String tenDocGia, String maSach, String tenSach, String theLoai, String tenTacGia, String maNhanVien, String ngayMuon, String ngayTra, String maPhieu) {
        this.maMuonTra = maMuonTra;
        this.maDocGia = maDocGia;
        this.tenDocGia = tenDocGia;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.tenTacGia = tenTacGia;
        this.maNhanVien = maNhanVien;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.maPhieu = maPhieu;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }
    

    public String getMaMuonTra() {
        return maMuonTra;
    }

    public void setMaMuonTra(String maMuonTra) {
        this.maMuonTra = maMuonTra;
    }

    public String getMaDocGia() {
        return maDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        this.maDocGia = maDocGia;
    }

    public String getTenDocGia() {
        return tenDocGia;
    }

    public void setTenDocGia(String tenDocGia) {
        this.tenDocGia = tenDocGia;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    
}
