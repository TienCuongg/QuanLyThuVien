package Model;

public class ThongKe {
    String thongKe;
    String maDoiTuong;
    int soLuong;

    public ThongKe() {
    }

    public ThongKe(String thongKe,String maDoiTuong, int soLuong) {
        this.thongKe = thongKe;
        this.maDoiTuong = maDoiTuong;
        this.soLuong = soLuong;
    }

    public String getThongKe() {
        return thongKe;
    }

    public void setThongKe(String thongKe) {
        this.thongKe = thongKe;
    }

    public String getMaDoiTuong() {
        return maDoiTuong;
    }

    public void setMaDoiTuong(String maDoiTuong) {
        this.maDoiTuong = maDoiTuong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
