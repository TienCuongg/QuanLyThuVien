package Manager;

import Model.ThongKe;
import connectDataBase.ConnectionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ThongKeManager {
    Connection conn;
    ArrayList<ThongKe> lstThongKe= new ArrayList();  
    public ThongKeManager() {
        conn = new ConnectionDB().getConnection();
    }
    
    public ArrayList<ThongKe> getDocGiaTK(Date ngaybd, Date ngaykt) {                           
        try {            
            PreparedStatement pst = conn.prepareStatement("SELECT m.madocgia,tendocgia, COUNT(m.madocgia)as soLuotMuon "
                    + "FROM muontra m, docgia d "
                    + "where d.madocgia = m.madocgia "
                    + "and (ngaymuon between '" + ngaybd + "' and '" + ngaykt + "') "
                    + "GROUP BY m.madocgia, tendocgia ORDER BY soLuotMuon DESC");  
            ResultSet rs = pst.executeQuery();       
            while(rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setMaDoiTuong(rs.getString("madocgia"));
                tk.setThongKe(rs.getString("tendocgia"));
                tk.setSoLuong(rs.getInt("soLuotMuon")); 

                lstThongKe.add(tk);              
            }
        } catch (SQLException ex) {
               System.out.println("Loi get Doc Gia: " +ex.toString());  
    }
        return lstThongKe;
    }
    
    public ArrayList<ThongKe> getSachTK(Date ngaybd, Date ngaykt) {                           
        try { 
            PreparedStatement pst = conn.prepareStatement("SELECT m.masach, tensach, COUNT(m.masach) as soLuotMuon "
                    + "FROM muontra m, sach s "
                    + "where s.masach = m.masach "
                    + "and (ngaymuon between '" + ngaybd + "' and '" + ngaykt + "') "
                    + "GROUP BY m.masach, tensach ORDER BY soLuotMuon DESC");
            
            ResultSet rs = pst.executeQuery();       
            while(rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setMaDoiTuong(rs.getString("masach")); 
                tk.setThongKe(rs.getString("tensach"));
                tk.setSoLuong(rs.getInt("soLuotMuon")); 
                  
                lstThongKe.add(tk);              
            }
        } catch (SQLException ex) {
               System.out.println("Loi get Sach : " +ex.toString());  
    }
        return lstThongKe;
    }
    
    public ArrayList<ThongKe> getTheLoaiTK(Date ngaybd, Date ngaykt) {                           
        try {  
            PreparedStatement pst = conn.prepareStatement("SELECT theloai, COUNT(m.masach) as soLuotMuon "
                    + "FROM muontra m, sach s "
                    + "where s.masach = m.masach "
                    + "and (ngaymuon between '" + ngaybd + "' and '" + ngaykt + "') "
                    + "GROUP BY s.theloai ORDER BY soLuotMuon DESC");
             
            ResultSet rs = pst.executeQuery();  
            int count = 1;
            while(rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setMaDoiTuong(String.valueOf(count)); 
                tk.setThongKe(rs.getString("theloai"));
                tk.setSoLuong(rs.getInt("soLuotMuon")); 
                  
                lstThongKe.add(tk);
                count++;
            }
        } catch (SQLException ex) {
            System.out.println("Loi get The Loai : " +ex.toString());      }
        return lstThongKe;
    }
    
    public ArrayList<ThongKe> getTacGiaTK(Date ngaybd, Date ngaykt) {                           
        try {       
            PreparedStatement pst = conn.prepareStatement("SELECT tacgia, COUNT(m.masach) as soLuotMuon "
                    + "FROM muontra m, sach s "
                    + "where s.masach = m.masach "
                    + "and (ngaymuon between '" + ngaybd + "' and '" + ngaykt + "') "
                    + "GROUP BY s.tacgia ORDER BY soLuotMuon DESC");
            
            ResultSet rs = pst.executeQuery();  
            int count = 1;
            while(rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setMaDoiTuong(String.valueOf(count)); 
                tk.setThongKe(rs.getString("tacgia"));
                tk.setSoLuong(rs.getInt("soLuotMuon")); 
                  
                lstThongKe.add(tk); 
                count++;
            }
        } catch (SQLException ex) {
               System.out.println("Loi get Tac Gia : " +ex.toString());  
    }
        return lstThongKe;
    }
    
    public ArrayList<ThongKe> getNhaXuatBanTK(Date ngaybd, Date ngaykt) {                           
        try {       
            PreparedStatement pst = conn.prepareStatement("SELECT nhaxuatban, COUNT(m.masach) as soLuotMuon "
                    + "FROM muontra m, sach s "
                    + "where s.masach = m.masach "
                    + "and (ngaymuon between '" + ngaybd + "' and '" + ngaykt + "') "
                    + "GROUP BY s.nhaxuatban ORDER BY soLuotMuon DESC");
             
            ResultSet rs = pst.executeQuery();  
            int count = 1;
            while(rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setMaDoiTuong(String.valueOf(count)); 
                tk.setThongKe(rs.getString("nhaxuatban"));
                tk.setSoLuong(rs.getInt("soLuotMuon")); 
                  
               lstThongKe.add(tk); 
               count++;
            }
        } catch (SQLException ex) {
               System.out.println("Loi get NXB : " +ex.toString());  
    }
        return lstThongKe;
    }
    
    public void getDanhSachViPham(JTable jTableThongKe,Date ngaybd, Date ngaykt) {
     
        String[] colTieuDe = new String[]{ "MÃ MƯỢN TRẢ", "MÃ ĐỘC GIẢ", "TÊN ĐỘC GIẢ", 
            "TÊN SÁCH","NGÀY MƯỢN","NGÀY TRẢ", "LÝ DO", "TIỀN PHẠT", "TÌNH TRẠNG"};
        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);
        Object[] row;
        try {            
            PreparedStatement pst = conn.prepareStatement("SELECT v.mamuontra, m.madocgia, d.tendocgia,"
                    +" s.tensach, quahan, lydo, m.ngaymuon, m.ngaytra, v.xuly "
                    + "FROM muontra m, sach s, vipham v, docgia d "
                    + "WHERE m.mamuontra = v.mamuontra "
                    + "AND d.madocgia = m.madocgia AND s.masach= m.masach "
                    +" AND (m.ngaytra BETWEEN '" + ngaybd + "' AND '" + ngaykt + "') "
                    + "ORDER BY d.madocgia DESC");  
            ResultSet rs = pst.executeQuery();  
            while(rs.next()) {
                row = new Object[9];
                row[0] = rs.getString("mamuontra");
                row[1] = rs.getString("madocgia");
                row[2] = rs.getString("tendocgia");
                row[3] = rs.getString("tensach");
                row[4] = rs.getString("ngaymuon");
                row[5] = rs.getString("ngaytra");
                row[6] = rs.getString("lydo");
                row[7] = rs.getInt("quahan")*2000; //tiền phạt = quá hạn * 2000.
                if(rs.getString("xuly")!= null){
                    row[8]= "Đã xử lý";
                }else row[8]="Chưa xử lý";
                
                model.addRow(row);
            }
            jTableThongKe.setModel(model);
        } catch (SQLException ex) {
               System.out.println("Loi get DS Vi Pham : " +ex.toString());  
        }
    }
    
    public ArrayList<ThongKe> getSachHetTK() {                           
        try {            
            PreparedStatement pst = conn.prepareStatement("SELECT masach, tensach, soluong " 
                    + " FROM sach "
                    + " WHERE soluong = damuon");  
            ResultSet rs = pst.executeQuery();       
            while(rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setMaDoiTuong(rs.getString("masach")); 
                tk.setThongKe(rs.getString("tensach"));
                tk.setSoLuong(rs.getInt("soluong")); 
                  
                lstThongKe.add(tk);              
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());  
        }
        return lstThongKe;
    }

}
