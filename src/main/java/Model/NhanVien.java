package Model;

public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private String chucVu;
    private String gioiTinh;
    private String ngaySinh;
    private int dienThoai;
    private String email;
    private String tenDangNhap;
    private String matKhau;
    private String tinhTrang;
    
    public NhanVien() {
        
    }

    public NhanVien(String maNhanVien, String tenNhanVien, String chucVu, String gioiTinh, String ngaySinh, int dienThoai, String email, String tenDangNhap, String matKhau) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.chucVu = chucVu;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.dienThoai = dienThoai;
        this.email = email;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(int dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    public String getTinhTrang() {
        return matKhau;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
