package Manager;

import GUI.Main;
import Model.DocGia;
import Model.MuonTra;
import connectDataBase.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MuonTraManager {
    Connection conn;
    ArrayList<MuonTra> lstMuonTra= new ArrayList();  
    private PreparedStatement st, st1;
    public static final int MUON_TOI_DA = 14;// số ngày quy định cho phép mượn tối đa
    public MuonTraManager() {
        conn = new ConnectionDB().getConnection();
    }
    
    
     public ArrayList<MuonTra> getAllMuonTra(String search){  
        String sql;
        PreparedStatement pst;
        try {            
            sql = "SELECT maphieu, mamuontra, m.madocgia, tendocgia, m.masach, tensach, ngaymuon "
                    + "FROM muontra m, sach s, docgia d "
                    + "WHERE m.ngaytra IS NULL AND LOWER(CAST(m.mamuontra AS VARCHAR) + " +
                                                        "CAST(m.maphieu AS VARCHAR) + " +
                                                        "CAST(m.madocgia AS VARCHAR) + " +
                                                        "CAST(d.tendocgia AS VARCHAR) + " +
                                                        "CAST(m.masach AS VARCHAR) + " +
                                                        "CAST(s.tensach AS VARCHAR) + " +
                                                        "CAST(m.ngaymuon AS VARCHAR)) " +
                                                    "LIKE ? " +
                                               "AND s.masach = m.masach AND d.madocgia = m.madocgia " +
                    "ORDER BY mamuontra ASC";                  
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + search.toLowerCase() + "%");
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                MuonTra mt = new MuonTra();
                mt.setMaPhieu(rs.getString("maphieu"));
                mt.setMaMuonTra(rs.getString("mamuontra"));
                mt.setMaDocGia(rs.getString("madocgia"));
                mt.setTenDocGia(rs.getString("tendocgia"));
                mt.setMaSach(rs.getString("masach"));
                mt.setTenSach(rs.getString("tensach"));
                mt.setNgayMuon(rs.getString("ngaymuon"));
                  
               lstMuonTra.add(mt);    
            }
        } catch (SQLException ex) {
               System.out.println("Loi get ALl: " +ex.toString());  
        }
        return  lstMuonTra;
    }
     
    public boolean themMuonTra(MuonTra muonTra) {
        boolean check = false; 
        try {
            MuonTra mt = muonTra;
            st = conn.prepareStatement("insert into muontra(mamuontra, madocgia, masach, manhanvien,"
                    + " ngaymuon, maphieu) values(?,?,?,?,?,?)");
            st.setString(1, mt.getMaMuonTra());
            st.setString(2, mt.getMaDocGia());
            st.setString(3, mt.getMaSach());
            st.setString(4, mt.getMaNhanVien());
            st.setString(5, mt.getNgayMuon());
            st.setString(6, mt.getMaPhieu());

            st1 = conn.prepareStatement("UPDATE sach SET  damuon = damuon + 1  WHERE masach =?" );
            st1.setString(1, mt.getMaSach());
            
            int result = st.executeUpdate();
            st1.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Độc giả mượn sách thành công!" , "Thông báo", 1);
            check = result != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Loi o them sach muon tra: " + e.toString());
            JOptionPane.showMessageDialog(null, "Sách chưa được mượn" , "Thông báo", 1); 
        }
        return check;
    }
    
    public boolean traSach(MuonTra muonTra) {
        boolean check = false;
        MuonTra mt = muonTra;
        int quaHan = 0;
        try {
            // Cập nhật ngày trả trong bảng muontra
            st = conn.prepareStatement("UPDATE muontra SET ngaytra = ? WHERE mamuontra = ?");
            st.setString(1, mt.getNgayTra());
            st.setString(2, mt.getMaMuonTra());
            int result = st.executeUpdate();
            check = result != 0;

            // Giảm số lượng sách mượn trong bảng sach
            st1 = conn.prepareStatement("UPDATE sach SET damuon = damuon - 1 WHERE masach = (SELECT masach FROM muontra WHERE mamuontra = ?)");
            st1.setString(1, mt.getMaMuonTra());
            st1.executeUpdate();

            // Kiểm tra số ngày quá hạn
            PreparedStatement pst = conn.prepareStatement("SELECT DATEDIFF(day, ngaymuon, ngaytra) AS quahan FROM muontra WHERE mamuontra = ?");
            pst.setString(1, mt.getMaMuonTra());
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                quaHan = rs.getInt("quahan") - MUON_TOI_DA;
            }

            if(quaHan> 0){
                try{
                    st = conn.prepareStatement("insert into vipham(mamuontra, quahan, lydo) values(?," + quaHan + ",?)");
                    st.setString(1, muonTra.getMaMuonTra());
                    st.setString(2, "Quá hạn");
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Độc giả trả quá hạn!" , "Thông báo", 1); 
                } catch (SQLException ex) {
                System.out.println(ex.toString());
                JOptionPane.showMessageDialog(null, "Lỗi thêm vi phạm" , "Thông báo", 2); 
                }
            }
        } catch (SQLException ex) {
               System.out.println("Loi o tra sach: " + ex.getStackTrace());
               JOptionPane.showMessageDialog(null, "Lỗi trả sách" , "Thông báo", 1); 
        }
        return check;
    }
    
    
    public boolean baoMat(MuonTra muonTra) {//báo mất sách,số lượng và đã mượn giảm 1, thêm ngày trả vào thẻ mượn. thêm vi phạm, tiền phạt = giá sách * 2
        boolean check = false;
        MuonTra mt = muonTra;
        try {
            // thêm vi phạm
            st = conn.prepareStatement("insert into vipham(mamuontra,lydo,quahan)"
                    +" values(?,?,((SELECT giatien FROM pmqltv.sach WHERE sach.masach= ?)/1000))");
            st.setString(1, mt.getMaMuonTra());
            st.setString(2, "Mất sách");
            st.setString(3, mt.getMaSach());
            int result = st.executeUpdate();
            if(result!= 0){
                //giảm số lượng sách, giảm đã mượn và set ngày trả
                st1 = conn.prepareStatement("UPDATE pmqltv.sach, pmqltv.muontra SET "
                        +" soluong= soluong - 1, damuon = damuon - 1, ngaytra=?  WHERE "
                        +"sach.masach = muontra.masach AND mamuontra = ?" );
                st1.setString(1, mt.getNgayTra());
                st1.setString(2, mt.getMaMuonTra());
                int result1 = st1.executeUpdate();
                
                //xóa sách nếu số lượng sách về 0.
//                st0 = conn.prepareStatement("UPDATE pmqltv.sach SET  trangthai= ?  WHERE soluong= 0 AND masach= ?" );
//                st0.setString(1, "đã xóa");
//                st0.setInt(2, mt.getMaSach());
//                int result2 =st0.executeUpdate();
//                if(result2==0){
//                    System.out.println("Xảy ra tại xóa sách khi số lượng về 0.");
//                }
                check = (result1 != 0);
            } else System.out.println ("chưa thêm vi phạm khi báo mất!");
        } catch (SQLException ex) {
            System.out.println("Loi o bao mat: " + ex.getStackTrace());
            System.out.println(ex.toString());  
        }
        return check;
    }
    
    public void xoaMuonTra(String maMuonTra, String maSach) {
        try {
            st = conn.prepareStatement("delete from muontra where mamuontra = ?");
            st.setString(1, maMuonTra);
            int delete = st.executeUpdate();
            if(delete!= 0){
                try{
                    st1 = conn.prepareStatement("UPDATE sach SET damuon = damuon - 1  WHERE masach = ?");
                    st1.setString(1, maSach);          
                    st1.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Đã xóa thẻ mượn!" , "Thông báo", 1);
                } catch (SQLException ex) {
                    System.out.println("Lỗi giảm đã mượn của sách khi xóa thẻ mượn.");  
                }
            }
        } catch (SQLException ex) {
            System.out.println("Loi xoa MT: " +ex.toString());
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra!" , "Thông báo", 2); 
        }
    }

    public int demMuonTra(String maDocGia){
        int tongmuon = 0;
        try {
            String sql = "SELECT COUNT(masach) as soQuyenMuon FROM muontra WHERE madocgia = '" + maDocGia + "' AND ngaytra IS NULL";
            PreparedStatement pst = conn.prepareStatement(sql);   
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                tongmuon = rs.getInt("soQuyenMuon");
            }
        } catch (SQLException ex) {
            System.out.println("Loi o dem muon tra: " + ex.getStackTrace());
            Logger.getLogger(MuonTraManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tongmuon;
    }
    
    public String getMaPhieu() {
        String maPhieu = "";
        try {
            String sql = "SELECT MAX(maphieu) AS mp FROM muontra";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String maxPhieu = rs.getString("mp");
                if (maxPhieu != null && maxPhieu.startsWith("MP")) {
                    // Extract the numeric part
                    int numPart = Integer.parseInt(maxPhieu.substring(2));
                    maPhieu = "MP" + (numPart + 1);
                } else {
                    maPhieu = "MP1"; // start from MP1 if no max value is found
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in getMaPhieu: " + ex.toString());
            Logger.getLogger(MuonTraManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maPhieu;
    }
    
    public String getMaMuonTra() {
        String maPhieu = "";
        try {
            String sql = "SELECT MAX(mamuontra) AS mmt FROM muontra";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String maxPhieu = rs.getString("mmt");
                if (maxPhieu != null && maxPhieu.startsWith("MMT")) {
                    // Extract the numeric part
                    int numPart = Integer.parseInt(maxPhieu.substring(3));
                    maPhieu = "MMT" + (numPart + 1);
                } else {
                    maPhieu = "MMT1"; // start from MP1 if no max value is found
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in getMaMuonTra: " + ex.toString());
            Logger.getLogger(MuonTraManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maPhieu;
    }

    
    public void loadDataMuonTraFromDB(JTable jtableMuonTra,String search) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        MuonTraManager muontraManager = new MuonTraManager();    
        if(search.equals("Nhập mã mượn trả/ mã hoặc tên độc giả/ tên sách")){
            search = "";
        }
        lstMuonTra= muontraManager.getAllMuonTra(search);
        //Dua danh sach muon tra len jTable
        String[] colTieuDe = new String[]{ "MÃ MƯỢN TRẢ","MÃ PHIẾU","MÃ ĐỘC GIẢ", "TÊN ĐỘC GIẢ",
            "MÃ SÁCH", "TÊN SÁCH",  "NGÀY MƯỢN", "HẠN TRẢ" };
        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);
        Object[] row;
        for (MuonTra mt : lstMuonTra) {
            row = new Object[8];
            row[0] = mt.getMaMuonTra();
            row[1] = mt.getMaPhieu();
            row[2] = mt.getMaDocGia();
            row[3] = mt.getTenDocGia();
            row[4] = mt.getMaSach();
            row[5] = mt.getTenSach();
            row[6] = mt.getNgayMuon();

            java.util.Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(mt.getNgayMuon());// truyền đúng định dạng ngày trên bảng           
            } catch (ParseException ex) {
                System.out.println("Loi o ngay muon load db: " +ex.toString());
            }
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.DATE, MUON_TOI_DA);                

            row[7] = dateFormat.format(c1.getTime());//hạn trả sách sau MUON_TOI_DA ngày.
            model.addRow(row);
        }       
        jtableMuonTra.setModel(model);
    } 
    
    public void loadDataTraSachFromDB(JTable jtableMuonTra,String maDocGia) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] colTieuDe = new String[]{ "MÃ MƯỢN TRẢ", "MÃ ĐỘC GIẢ", "TÊN ĐỘC GIẢ",
            "MÃ SÁCH", "TÊN SÁCH", "NGÀY MƯỢN", "HẠN TRẢ" };
        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);
        Object[] row;
        String sql;
        PreparedStatement pst;
        try {            
            sql = "SELECT mamuontra, m.madocgia,tendocgia, m.masach,tensach,ngaymuon " +
                  "FROM muontra m, sach s, docgia d " +
                  "WHERE m.ngaytra IS NULL AND m.madocgia = '" + maDocGia + 
                                          "' AND s.masach = m.masach AND d.madocgia = m.madocgia " +
                  "ORDER BY mamuontra ASC";
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();       
            while(rs.next()) {
                row = new Object[7];
                row[0] = rs.getString("mamuontra");
                row[1] = rs.getString("madocgia");
                row[2] = rs.getString("tendocgia");
                row[3] = rs.getString("masach");
                row[4] = rs.getString("tensach");
                row[5] = rs.getDate("ngaymuon");
                
                Calendar c1 = Calendar.getInstance();
                c1.setTime(rs.getDate("ngaymuon"));
                c1.add(Calendar.DATE, MUON_TOI_DA);                
                
                row[6] = dateFormat.format(c1.getTime());//hạn trả sách sau 14 ngày.
                model.addRow(row);
            }
        } catch (SQLException ex) {
               System.out.println("Loi load DB: " +ex.toString());  
        }
        jtableMuonTra.setModel(model);
    }
    
    public void printMuonTra(String maMuonTra, DocGia docGia, MuonTra muonTra){
        try {            
            PreparedStatement pst = conn.prepareStatement("SELECT d.tendocgia, d.diachi, d.email, d.gioitinh, d.dienthoai, d.ngaysinh, d.madocgia, m.ngaymuon "
                    + "FROM muontra m, sach s, docgia d "
                    + "WHERE m.mamuontra = '" + maMuonTra + "' AND s.masach = m.masach AND d.madocgia = m.madocgia");   
            ResultSet rs = pst.executeQuery();       
            while(rs.next()) {
                docGia.setTenDocGia(rs.getString("tendocgia"));
                docGia.setDiaChi(rs.getString("diachi"));
                docGia.setEmail(rs.getString("email"));
                docGia.setGioiTinh(rs.getString("gioitinh"));
                docGia.setDienThoai(rs.getInt("dienthoai"));
                docGia.setNgaySinh(rs.getString("ngaysinh"));
                docGia.setMaDocGia(rs.getString("madocgia"));
                muonTra.setNgayMuon(rs.getString("ngaymuon"));
            }
        } catch (SQLException ex) {
               System.out.println("Loi o print Muon Tra : " +ex.toString());  
        }
    }
}
