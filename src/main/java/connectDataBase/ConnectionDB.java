package connectDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ConnectionDB {//Kết nối với SQL 

    private static Connection conn;

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://TienCuongg:1433;databaseName=Final_QuanLyThuVien;encrypt=true;trustServerCertificate=true";
            String username = "sa";
            String password = "123";

            conn = DriverManager.getConnection(url, username, password);
//            if (conn != null) {
//                JOptionPane.showMessageDialog(null, "Kết nối DB thành công");
//            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Kết nối DB thất bại");
        }
        return conn;
    }   
 
    public static void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }

}
