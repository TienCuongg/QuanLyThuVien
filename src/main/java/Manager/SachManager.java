package Manager;

import Model.Sach;
import connectDataBase.ConnectionDB;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

public class SachManager {
    Connection conn;
    ArrayList<Sach> lstSach = new ArrayList();  
    private PreparedStatement st;
    private ResultSet rs;
    public SachManager() {
        conn = new ConnectionDB().getConnection();
    }
    
    
    public ArrayList<Sach> getAllSach(String search) {   
        ArrayList<Sach> lstSach = new ArrayList<>();
        try {
            String sql;
            PreparedStatement pst;

            if (search == null || search.trim().isEmpty()) {
                sql = "SELECT * FROM Sach";
                pst = conn.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM Sach " +
                      "WHERE LOWER(CAST(masach AS VARCHAR) + CAST(tensach AS VARCHAR) + " +
                      "CAST(theloai AS VARCHAR) + CAST(tacgia AS VARCHAR) + " +
                      "CAST(nhaxuatban AS VARCHAR) + CAST(namxuatban AS VARCHAR) + " +
                      "CAST(ngonngu AS VARCHAR) + CAST(giatien AS VARCHAR) + " +
                      "CAST(damuon AS VARCHAR))" +
                      "LIKE ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, "%" + search.toLowerCase() + "%");
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Sach s = new Sach();
                s.setMaSach(rs.getString("masach"));
                s.setTenSach(rs.getString("tensach"));
                s.setTheLoai(rs.getString("theloai"));
                s.setTacGia(rs.getString("tacgia"));
                s.setNhaXuatBan(rs.getString("nhaxuatban"));
                s.setNamXuatBan(rs.getInt("namxuatban")); 
                s.setNgayNhap(rs.getString("ngaynhap"));
                s.setNgonNgu(rs.getString("ngonngu")); 
                s.setSoTrang(rs.getInt("sotrang")); 
                s.setSoLuong(rs.getInt("soluong")); 
                s.setGiaTien(rs.getInt("giatien")); 
                s.setDaMuon(rs.getInt("damuon"));

                lstSach.add(s);              
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString()); 
            System.out.println("Loi get all");
        }
        return lstSach;
    }

     
     
    public boolean themSach(Sach sach) {
        boolean check = false; 
        try {
            Sach s = sach;
            st = conn.prepareStatement("insert into sach(masach, tensach, theloai, tacgia, nhaxuatban, "
                    +"namxuatban, ngaynhap, ngonngu, sotrang, soluong, giatien, damuon) values(?,?,?,?,?,?,?,?,?,?,?,?)");
            st.setString(1, s.getMaSach());
            st.setString(2, s.getTenSach());
            st.setString(3, s.getTheLoai());
            st.setString(4, s.getTacGia());
            st.setString(5, s.getNhaXuatBan());
            st.setInt(6, s.getNamXuatBan());
            st.setString(7, s.getNgayNhap());
            st.setString(8, s.getNgonNgu());
            st.setInt(9, s.getSoTrang());
            st.setInt(10, s.getSoLuong());
            st.setInt(11, s.getGiaTien());
            st.setInt(12, s.getDaMuon());
            st.setInt(12, s.getDaMuon());
            int result = st.executeUpdate();
            check = result != 0;
        } catch (SQLException e) {
            System.out.println(e.toString());
//            JOptionPane.showMessageDialog(null, "Sách chưa được thêm" , "Thông báo", 1); 
        }
        return check;
    }
    
   public boolean suaSach(Sach sach) {
        boolean check = false;
        Sach s = sach;
        try {
            st = conn.prepareStatement( "UPDATE sach SET  tensach=?, theloai=?, tacgia=?, nhaxuatban=?,"
                    +" namxuatban=?, ngaynhap=?, ngonngu=?, sotrang=?, soluong=?, giatien=? WHERE masach =? AND damuon <= ?" );
            
            st.setString(1, s.getTenSach());
            st.setString(2, s.getTheLoai());
            st.setString(3, s.getTacGia());
            st.setString(4,s.getNhaXuatBan());
            st.setInt(5, s.getNamXuatBan());
            st.setString(6, s.getNgayNhap());
            st.setString(7,s.getNgonNgu());
            st.setInt(8, s.getSoTrang());
            st.setInt(9, s.getSoLuong());
            st.setInt(10, s.getGiaTien());
            st.setString(11, s.getMaSach());  
            st.setInt(12, s.getDaMuon());
                    
            int result = st.executeUpdate();
            check = result != 0;
        } catch (SQLException ex) {
               System.out.println(ex.toString());  
        }
        return check;
    }
    

    public void xoaSach(String maSach) {
//        try {
//            String sql = "SELECT COUNT(madocgia) as dangmuon FROM muontra WHERE masach = '" + maSach + "' AND ngaytra IS NULL";
//            PreparedStatement pst = conn.prepareStatement(sql);   
//            ResultSet rs = pst.executeQuery();
//            if(rs.next()){
//                int dangMuon= rs.getInt("dangmuon");
//                if(dangMuon == 0){
//                    st = conn.prepareStatement("UPDATE sach SET  tinhtrang = ?  WHERE masach = ?");
//            
//                    st.setString(1, "đã xóa");
//                    st.setString(2, maSach);
//                    st.executeUpdate();
//                    JOptionPane.showMessageDialog(null, "Đã Xóa Sách!" , "Thông báo", 1);
//                } else 
//                    JOptionPane.showMessageDialog(null, "Sách Này Đang Được Mượn!", "Lỗi Xóa Sách", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (SQLException ex) {
////            Logger.getLogger(MuonTraManager.class.getName()).log(Level.SEVERE, null, ex);
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra!" , "Thông báo", 2);
//        }
        try {
            String sqlCommand = "delete from sach where masach = ?";
            st = conn.prepareStatement(sqlCommand);
            st.setString(1, maSach);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Đã Xóa Sách!" , "Thông báo", 1);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(null, "Sách Này Đang Được Mượn", "Lỗi Xóa Sách", JOptionPane.ERROR_MESSAGE);
        }
    }
    
//    public void khoiPhucSach(String maSach) {
//        try {
//            String sql = "SELECT COUNT(madocgia) as dangmuon FROM muontra WHERE masach = '" + maSach + "' AND ngaytra IS NULL";
//            PreparedStatement pst = conn.prepareStatement(sql);   
//            ResultSet rs = pst.executeQuery();
//            if(rs.next()){
//                int dangMuon= rs.getInt("dangmuon");
//                if(dangMuon == 0){
//                    st = conn.prepareStatement("UPDATE sach SET  tinhtrang = ?  WHERE masach = ?");
//            
//                    st.setString(1, "");
//                    st.setString(2, maSach);
//                    st.executeUpdate();
//                    JOptionPane.showMessageDialog(null, "Đã Khôi Phục Tình Trạng!" , "Thông báo", 1);
//                } else 
//                    JOptionPane.showMessageDialog(null, "Chưa khôi phục được!", "Lỗi Xóa Sách", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (SQLException ex) {
////            Logger.getLogger(MuonTraManager.class.getName()).log(Level.SEVERE, null, ex);
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra!" , "Thông báo", 2);
//        }
//    }
    
//    public void loadDataSachFromDB(JTable jtableSach,String search) {
//     
//        SachManager sachManager = new SachManager();
//        if(search.equals("Nhập mã sách/ tên sách/ thể loại/ tác giả cần tìm")){
//            search = "";
//        }
//        lstSach= sachManager.getAllSach(search);
//        //Dua danh sach muon tra len jTable
//        String[] colTieuDe = new String[]{ "MÃ SÁCH", "TÊN SÁCH", "THỂ LOẠI", "TÌNH TRẠNG", 
//            "TÁC GIẢ", "NHÀ XUẤT BẢN", "NĂM XUẤT BẢN", "NGÀY NHẬP", "NGÔN NGỮ", "SỐ TRANG", "SỐ LƯỢNG", "GIÁ TIỀN" };
//        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);
//
//        Object[] row;
//        for (Sach s : lstSach) {
//            row = new Object[12];
//            row[0] = s.getMaSach();
//            row[1] = s.getTenSach();
//            row[2] = s.getTheLoai();
//            row[4] = s.getTacGia();
//            row[5] = s.getNhaXuatBan();
//            row[6] = s.getNamXuatBan();
//            row[7] = s.getNgayNhap();
//            row[8] = s.getNgonNgu();
//            row[9] = s.getSoTrang();
//            row[10] = s.getSoLuong();
//            if (s.getSoLuong() > s.getDaMuon())
//                row[3] = "Sẵn sàng";
//            else row[3] = "Đã hết";
//            row[11] = s.getGiaTien();
//       
//            model.addRow(row);
//        }
//       
//        jtableSach.setModel(model);
//    }

    
    public void loadDataSachFromDB(JTable jtableSach, String search) {
        SachManager sachManager = new SachManager();
        if (search.equals("Nhập mã sách/ tên sách/ thể loại/ tác giả cần tìm")) {
            search = "";
        }

        lstSach = sachManager.getAllSach(search);  // Lấy danh sách sách đã lọc

        // Cấu hình tên cột cho jTable
        String[] colTieuDe = new String[]{ "MÃ SÁCH", "TÊN SÁCH", "THỂ LOẠI", "TÁC GIẢ", "NHÀ XUẤT BẢN", "NĂM XUẤT BẢN",
                "NGÀY NHẬP", "NGÔN NGỮ", "SỐ TRANG", "SỐ LƯỢNG", "GIÁ TIỀN", "ĐÃ MƯỢN"};
        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);

        try {
            // Lấy dữ liệu từ DB đã lọc
            PreparedStatement pst = conn.prepareStatement("SELECT * \n" +
                                                                "FROM sach \n" +
                                                                "WHERE LOWER(masach) LIKE ? \n" +
                                                                "   OR LOWER(tensach) LIKE ? \n" +
                                                                "   OR LOWER(theloai) LIKE ? \n" +
                                                                "   OR LOWER(tacgia) LIKE ? \n" +
                                                                "   OR LOWER(nhaxuatban) LIKE ? \n" +
                                                                "   OR LOWER(namxuatban) LIKE ? \n" +
                                                                "   OR LOWER(ngonngu) LIKE ? \n" +
                                                                "   OR LOWER(giatien) LIKE ? \n");
            String searchPattern = "%" + search.toLowerCase() + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            pst.setString(4, searchPattern);
            pst.setString(5, searchPattern);
            pst.setString(6, searchPattern);
            pst.setString(7, searchPattern);
            pst.setString(8, searchPattern);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Object row[] = {
                    rs.getString("masach"),
                    rs.getString("tensach"),
                    rs.getString("theloai"),
                    rs.getString("tacgia"),
                    rs.getString("nhaxuatban"),
                    rs.getInt("namxuatban"),
                    rs.getString("ngaynhap"),
                    rs.getString("ngonngu"),
                    rs.getInt("sotrang"),
                    rs.getInt("soluong"),
                    rs.getInt("giatien"),
                    rs.getInt("damuon"),
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Loi o load db");
        }
        jtableSach.setModel(model);
    }

    
   public void loadDataSachMuonFromDB(JTable jtableSach, String search, String[] maSach, String maDocGia) { 
        String[] colTieuDe = new String[]{ "MÃ SÁCH", "TÊN SÁCH", "THỂ LOẠI", "NGÔN NGỮ", "TÁC GIẢ", "NHÀ XUẤT BẢN", "GIÁ TIỀN" };
        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);
        Object[] row;
        if (search.equals("Nhập mã sách/ tên sách/ thể loại cần tìm")) { //phục vụ cho hiển thị gợi ý - input hint trong jtext.
            search = "";
        }

        // Tạo chuỗi điều kiện cho các mã sách đã chọn
        String sqlSachDaChon = "";
        for (int i = 0; i < maSach.length; i++) {
            sqlSachDaChon += "masach != '" + maSach[i] + "' AND ";
        }

        // Tạo chuỗi điều kiện cho các mã sách đã mượn
        String sqlSachDaMuon = "";
        try {
            PreparedStatement pst1 = conn.prepareStatement("SELECT masach FROM muontra WHERE ngaytra IS NULL AND madocgia = ?");
            pst1.setString(1, maDocGia);
            ResultSet rs = pst1.executeQuery();
            while (rs.next()) {
                sqlSachDaMuon += "masach != '" + rs.getString("masach") + "' AND ";
            }
        } catch (SQLException ex) {
            System.out.println("Đoạn lỗi trong mục thêm sql sách đã mượn");
        }

        if (!sqlSachDaChon.isEmpty()) {
            sqlSachDaChon = sqlSachDaChon.substring(0, sqlSachDaChon.length() - 5);
        }
        if (!sqlSachDaMuon.isEmpty()) {
            sqlSachDaMuon = sqlSachDaMuon.substring(0, sqlSachDaMuon.length() - 5);
        }

        try {
            String sql = "SELECT * FROM sach WHERE tinhtrang IS NULL AND soluong > damuon";

            if (!sqlSachDaMuon.isEmpty()) {
                sql += " AND (" + sqlSachDaMuon + ")";
            }

            if (!sqlSachDaChon.isEmpty()) {
                sql += " AND (" + sqlSachDaChon + ")";
            }

            sql += " AND (LOWER(masach) LIKE ? "
                    + "OR LOWER(tensach) LIKE ? "
                    + "OR LOWER(theloai) LIKE ? "
                    + "OR LOWER(tacgia) LIKE ? "
                    + "OR LOWER(ngonngu) LIKE ? "
                    + "OR LOWER(nhaxuatban) LIKE ? "
                    + "OR LOWER(giatien) LIKE ?)";

            PreparedStatement pst = conn.prepareStatement(sql);
            String searchPattern = "%" + search.toLowerCase() + "%";

            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            pst.setString(4, searchPattern);
            pst.setString(5, searchPattern);
            pst.setString(6, searchPattern);
            pst.setString(7, searchPattern);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                row = new Object[7];
                row[0] = rs.getString("masach");
                row[1] = rs.getString("tensach");
                row[2] = rs.getString("theloai");
                row[3] = rs.getString("ngonngu");
                row[4] = rs.getString("tacgia");
                row[5] = rs.getString("nhaxuatban");
                row[6] = rs.getInt("giatien");
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Loi o load sach mt: " + ex.toString());
        }
        jtableSach.setModel(model);
}

    
    public void loadSachPhieuMuon(JTable jtableSachPhieuMuon, String maPhieu){
        String[] colTieuDe = new String[]{ "MÃ", "TÊN SÁCH", "THỂ LOẠI", "TÁC GIẢ", "NHÀ XUẤT BẢN" };
        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);
        Object[] row;
        
        try {            
            PreparedStatement pst = conn.prepareStatement("SELECT s.masach, tensach, theloai, tacgia, nhaxuatban"
                    + " FROM sach s, muontra m where m.mamuontra = '" + maPhieu +"' AND s.masach = m.masach");            
            ResultSet rs = pst.executeQuery();       
            if (!rs.next()) {
                System.out.println("Không có dữ liệu cho mã phiếu: " + maPhieu);
            } else {
                do {
                    row = new Object[5];
                    row[0] = rs.getString("masach");
                    row[1] = rs.getString("tensach");
                    row[2] = rs.getString("theloai");
                    row[3] = rs.getString("tacgia");
                    row[4] = rs.getString("nhaxuatban");
                    model.addRow(row);
                } while (rs.next());
            }
        } catch (SQLException ex) {
            System.out.println("Loi o load sach phieu muon: "  + ex.toString());  
        }
        jtableSachPhieuMuon.setModel(model);
        jtableSachPhieuMuon.getColumnModel().getColumn(0).setPreferredWidth(70);
        jtableSachPhieuMuon.getColumnModel().getColumn(1).setPreferredWidth(170);
        jtableSachPhieuMuon.getColumnModel().getColumn(2).setPreferredWidth(80);
        jtableSachPhieuMuon.getColumnModel().getColumn(3).setPreferredWidth(130);
        jtableSachPhieuMuon.getColumnModel().getColumn(4).setPreferredWidth(120);
    }
    
    public boolean nhapSachTuExcel(String excelFilePath) throws IOException {
        try {
            String sqlCommand = "DELETE FROM sach";
                st = conn.prepareStatement(sqlCommand);
                st.executeUpdate();
//                System.out.println("Dữ liệu trong bảng đã được xóa.");
            } catch (SQLException ex) {
                System.out.println("Lỗi khi xóa bảng: " + ex.toString());
                JOptionPane.showMessageDialog(null, "Lỗi khi xóa bảng", "Lỗi Xóa bảng", JOptionPane.ERROR_MESSAGE);
                return false;  // Trả về false nếu không thể xóa dữ liệu
        }
        boolean check = false;
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = getWorkbook(inputStream, excelFilePath);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        // Bỏ qua dòng tiêu đề (dòng đầu tiên)
        if (iterator.hasNext()) {
            iterator.next();  // Bỏ qua dòng tiêu đề
        }

        // Duyệt qua từng dòng trong sheet
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            Sach s = new Sach();

            try {
                // Duyệt qua các ô trong dòng và đọc giá trị của từng cột
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            s.setMaSach(nextCell.getStringCellValue());
                            break;
                        case 1:
                            s.setTenSach(nextCell.getStringCellValue());
                            break;
                        case 2:
                            s.setTheLoai(nextCell.getStringCellValue());
                            break;
                        case 3:
                            s.setTacGia(nextCell.getStringCellValue());
                            break;
                        case 4:
                            s.setNhaXuatBan(nextCell.getStringCellValue());
                            break;
                        case 5:
                            if (nextCell.getCellType() == CellType.NUMERIC) {
                                s.setNamXuatBan((int) nextCell.getNumericCellValue());
                            }
                            break;
                        case 6:
                            if (nextCell.getCellType() == CellType.NUMERIC || nextCell.getCellType() == CellType.FORMULA) {
                                if (DateUtil.isCellDateFormatted(nextCell)) {
                                    Date date = nextCell.getDateCellValue();
                                    if (date != null) {
                                        String ngayNhap = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                        s.setNgayNhap(ngayNhap);
                                    }
                                }
                            } else if (nextCell.getCellType() == CellType.STRING) {
                                // Trường hợp ô ngày tháng là chuỗi văn bản
                                String ngayNhapStr = nextCell.getStringCellValue();
                                s.setNgayNhap(ngayNhapStr);
                            }
                            break;
                        case 7:
                            s.setNgonNgu(nextCell.getStringCellValue());
                            break;
                        case 8:
                            if (nextCell.getCellType() == CellType.NUMERIC) {
                                s.setSoTrang((int) nextCell.getNumericCellValue());
                            }
                            break;
                        case 9:
                            if (nextCell.getCellType() == CellType.NUMERIC) {
                                s.setSoLuong((int) nextCell.getNumericCellValue());
                            }
                            break;
                        case 10:
                            if (nextCell.getCellType() == CellType.NUMERIC) {
                                s.setGiaTien((int) nextCell.getNumericCellValue());
                            }
                            break;
                        case 11:
                            if (nextCell.getCellType() == CellType.NUMERIC) {
                                s.setDaMuon((int) nextCell.getNumericCellValue());
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Lỗi định dạng thông tin trong file Excel!");
            }

            // Kiểm tra nếu tất cả các trường thông tin đều hợp lệ trước khi nhập vào DB
            if (s.getMaSach() != null && s.getTenSach() != null && s.getTheLoai() != null &&
                    s.getTacGia() != null && s.getNhaXuatBan() != null && s.getNamXuatBan() != 0 &&
                    s.getNgayNhap() != null && s.getNgonNgu() != null &&
                    s.getSoTrang() != 0 && s.getSoLuong() != 0 && s.getGiaTien() != 0) {
                try {
                    st = conn.prepareStatement("INSERT INTO sach(masach, tensach, theloai, tacgia, nhaxuatban, " +
                            "namxuatban, ngaynhap, ngonngu, sotrang, soluong, giatien, damuon) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
                    st.setString(1, s.getMaSach());
                    st.setString(2, s.getTenSach());
                    st.setString(3, s.getTheLoai());
                    st.setString(4, s.getTacGia());
                    st.setString(5, s.getNhaXuatBan());
                    st.setInt(6, s.getNamXuatBan());
                    st.setString(7, s.getNgayNhap());
                    st.setString(8, s.getNgonNgu());
                    st.setInt(9, s.getSoTrang());
                    st.setInt(10, s.getSoLuong());
                    st.setInt(11, s.getGiaTien());
                    st.setInt(12, s.getDaMuon());

                    int result = st.executeUpdate();
                    check = result != 0;
                } catch (SQLException e) {
                    System.out.println("Lỗi SQL khi thêm sách vào cơ sở dữ liệu: " + e.toString());
                }
            } else {
                // Thông báo nếu thiếu thông tin quan trọng
                System.out.println("Thông tin dòng excel không đầy đủ, bỏ qua dòng này.");
            }
        }

        // Đảm bảo đóng tài nguyên
        inputStream.close();
        return check;
    }


	
    //Sử dụng để đọc được cả định dạng .xls và xlsx
    private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException {
	Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("Định dạng file không phải excel!");
        }
        return workbook;
    }
}
