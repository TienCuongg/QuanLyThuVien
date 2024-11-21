package Manager;

import java.sql.*;
import connectDataBase.*;
import javax.swing.JOptionPane;


public class CheckDangNhap {
    public static Connection conn = null;
    public static ResultSet rs = null;
    public static PreparedStatement pst = null;
    
    public static String testDangNhap(){
        try {
            conn = ConnectionDB.getConnection();
            if (conn != null) {
                return "Kết nối dữ liệu thành công.";
            } else {
                return "Kết nối dữ liệu thất bại.";
            }
        } catch (Exception e) {
            return "Kết nối dữ liệu thất bại." + e.getMessage();
        }    
    }
    
    public static ResultSet cLog(String tendangnhap, String matkhau){
        String sql = "SELECT * FROM nhanvien WHERE tinhtrang is NULL AND tendangnhap = ? and matkhau = ?";
        try {
            if (conn == null) {
                conn = ConnectionDB.getConnection();
            }
            pst = conn.prepareStatement(sql);
            pst.setString(1, tendangnhap);
            pst.setString(2, matkhau);
            
            rs = pst.executeQuery();
            return rs;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Lỗi " + e.getMessage());
            return rs = null;
        }
    }
}
