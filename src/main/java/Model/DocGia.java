package Model;

public class DocGia {
    String maDocGia;
    String tenDocGia;
    String ngaySinh;
    String gioiTinh;
    String diaChi;
    String email;    
    int dienThoai;
    String ngaylapthe;
    
    public DocGia(){
        
    }
    public DocGia(String maDocGia, String tenDocGia, String ngaySinh, String gioiTinh, String diaChi, String email, int dienThoai, String ngayLapThe) {
        this.maDocGia = maDocGia;
        this.tenDocGia = tenDocGia;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.email = email;
        this.dienThoai = dienThoai;
        this.ngaylapthe = ngayLapThe;
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

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(int dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getNgayLapThe() {
        return ngaylapthe;
    }

    public void setNgayLapThe(String ngaylapthe) {
        this.ngaylapthe = ngaylapthe;
    }

}
