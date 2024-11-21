package Model;

public class Sach {
    private String maSach;
    private String tenSach;
    private String theLoai;
    private String tacGia;
    private String nhaXuatBan;
    private int namXuatBan;
    private String ngayNhap;
    private String ngonNgu;
    private int soTrang;
    private int soLuong;
    private int giaTien;
    private int daMuon;
    private String tinhTrang;
    
    public Sach() {
        
    }
    
    public Sach(String maSach, String tenSach, String theLoai, String tacGia, String nhaXuatBan, int namXuatBan, String ngayNhap, String ngonNgu, int soTrang, int soLuong, int giaTien, int daMuon, String tinhTrang) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.tacGia = tacGia;
        this.nhaXuatBan = nhaXuatBan;
        this.namXuatBan = namXuatBan;
        this.ngayNhap = ngayNhap;
        this.ngonNgu = ngonNgu;
        this.soTrang = soTrang;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.daMuon = daMuon;
        this.tinhTrang = tinhTrang;
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

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public int getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(int namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getDaMuon() {
        return daMuon;
    }

    public void setDaMuon(int daMuon) {
        this.daMuon = daMuon;
    } 

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    

}
