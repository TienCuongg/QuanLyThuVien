package Manager;

import Model.NhanVien;
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
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhanVienManager {
    Connection conn;
    ArrayList<NhanVien> lstNhanVien= new ArrayList();  
    private PreparedStatement st;
    public NhanVienManager() {
        conn = new ConnectionDB().getConnection();
    }
    
    
     public ArrayList<NhanVien> getAllNhanVien(String search){                           
        try {            
            String sql;
            PreparedStatement pst;

            if (search == null || search.trim().isEmpty()) {
                sql = "SELECT * FROM nhanvien";
                pst = conn.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM nhanvien " +
                      "WHERE LOWER(CAST(manhanvien AS VARCHAR) + CAST(tennhanvien AS VARCHAR) + " +
                      "CAST(chucvu AS VARCHAR) + CAST(gioitinh AS VARCHAR) + " +
                      "CAST(ngaysinh AS VARCHAR) + CAST(dienthoai AS VARCHAR) + " +
                      "CAST(email AS VARCHAR) + CAST(tendangnhap AS VARCHAR))" +
                      "LIKE ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, "%" + search.toLowerCase() + "%");
            }         
            ResultSet rs = pst.executeQuery();       
            while(rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("manhanvien"));
                nv.setTenNhanVien(rs.getString("tennhanvien"));
                nv.setChucVu(rs.getString("chucvu"));
                nv.setGioiTinh(rs.getString("gioitinh"));
                nv.setNgaySinh(rs.getString("ngaysinh"));
                nv.setDienThoai(rs.getInt("dienthoai")); 
                nv.setEmail(rs.getString("email"));
                nv.setTenDangNhap(rs.getString("tendangnhap"));
                
                lstNhanVien.add(nv);              
            }
        } catch (SQLException ex) {
               System.out.println("Loi SQL get all: " +ex.toString());  
     }
        return  lstNhanVien;
     }
     
     
    public boolean themNhanVien(NhanVien nhanvien) {
        boolean check = false; 
        try {
            NhanVien nv = nhanvien;
            st = conn.prepareStatement("insert into nhanvien(manhanvien, tennhanvien, chucvu,"
                    +" gioitinh, ngaysinh, dienthoai, email, tendangnhap) values(?,?,?,?,?,?,?,?)");
            st.setString(1, nv.getMaNhanVien());
            st.setString(2, nv.getTenNhanVien());
            st.setString(3, nv.getChucVu());
            st.setString(4, nv.getGioiTinh());
            st.setString(5, nv.getNgaySinh());            
            st.setInt(6, nv.getDienThoai());
            st.setString(7, nv.getEmail());
            st.setString(8, nv.getTenDangNhap());

            int result = st.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Đã thêm Nhân viên thành công!" , "Thông báo", 1);
            check = result != 0;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return check;
    }
    
    public boolean suaNhanVien(NhanVien nhanvien) {
        boolean check = false;
        NhanVien nv = nhanvien;
        try {
            st = conn.prepareStatement( "UPDATE nhanvien SET  tennhanvien=?, chucvu=?,"
                    +" gioitinh=?, ngaysinh=?, dienthoai=?, email=?, tendangnhap=?  WHERE manhanvien =?" );
            
            st.setString(1, nv.getTenNhanVien());
            st.setString(2, nv.getChucVu());
            st.setString(3, nv.getGioiTinh());
            st.setString(4,nv.getNgaySinh());
            st.setInt(5, nv.getDienThoai());
            st.setString(6, nv.getEmail());
            st.setString(7, nv.getTenDangNhap());
            st.setString(8, nv.getMaNhanVien());
            int result = st.executeUpdate();
            check = result != 0;
        } catch (SQLException ex) {
            System.out.println(ex.toString());  
        }
        return check;
    }
    
    public boolean doiMatKhau(String maNhanVien, String matKhau) {
        boolean check = false;
        try {
            st = conn.prepareStatement( "UPDATE nhanvien SET  matkhau = ? WHERE manhanvien = ?" );
            st.setString(1, matKhau);
            st.setString(2, maNhanVien);
            int result = st.executeUpdate();
            check = result != 0;
        } catch (SQLException ex) {
               System.out.println(ex.toString());  
        }
        return check;
    }
    
    public void xoaNhanVien(String maNhanVien) {
//        try {
//                st = conn.prepareStatement("UPDATE nhanvien SET  tinhtrang = ?  WHERE manhanvien =?");
////
//                st.setString(1, "đã xóa");
//                st.setString(2, maNhanVien);
//                st.executeUpdate();
//                JOptionPane.showMessageDialog(null, "Đã Xóa Nhân Viên!" , "Thông báo", 1);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra!" , "Thông báo", 2);
//        }
        
        try {
            String sqlCommand = "delete from nhanvien where manhanvien = ?";
            st = conn.prepareStatement(sqlCommand);
            st.setString(1, maNhanVien);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Đã Xóa Nhân Viên!" , "Thông báo", 1);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(null, "Nhân Viên Này Đã Được Sử Dụng", "Lỗi Xóa Nhân Viên", JOptionPane.ERROR_MESSAGE);
        }
    }
    
//    public void khoiPhucNhanVien(String maNhanVien) {
//        try {
//            st = conn.prepareStatement("UPDATE nhanvien SET  tinhtrang = ?  WHERE manhanvien = ?");
//            
//            st.setString(1, "");
//            st.setString(2, maNhanVien);
//            st.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Đã Khôi Phục Tình Trạng!" , "Thông báo", 1);
//        } catch (SQLException ex) {
////            Logger.getLogger(MuonTraManager.class.getName()).log(Level.SEVERE, null, ex);
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra!" , "Thông báo", 2);
//        }
//    }

    
    
    public void loadDataNhanVienFromDB(JTable jtableNhanVien,String search) {
        NhanVienManager nhanVienManager = new NhanVienManager();
        if(search.equals("Nhập mã nhân viên/ tên nhân viên/ chức vụ cần tìm")){
            search = "";
        }
        lstNhanVien= nhanVienManager.getAllNhanVien(search);
        //Dua danh sach nhan vien len jTable
        String[] colTieuDe = new String[]{ "MÃ NHÂN VIÊN", "HỌ TÊN", "CHỨC VỤ", "GIỚI TÍNH", "NGÀY SINH", "ĐIỆN THOẠI", "EMAIL", "TÊN ĐĂNG NHẬP", "TÌNH TRẠNG" };
        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);
          try {
            // Lấy dữ liệu từ DB đã lọc
            PreparedStatement pst = conn.prepareStatement("SELECT * \n" +
                                                            "FROM nhanvien \n" +
                                                            "WHERE LOWER(manhanvien) LIKE ? \n" +
                                                            "   OR LOWER(tennhanvien) LIKE ? \n" +
                                                            "   OR LOWER(chucvu) LIKE ? \n" +
                                                            "   OR LOWER(gioitinh) LIKE ? \n" +
                                                            "   OR LOWER(ngaysinh) LIKE ? \n" +
                                                            "   OR LOWER(dienthoai) LIKE ? \n" +
                                                            "   OR LOWER(email) LIKE ? \n" +
                                                            "   OR LOWER(tendangnhap) LIKE ?");
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
                    rs.getString("manhanvien"),
                    rs.getString("tennhanvien"),
                    rs.getString("chucvu"),
                    rs.getString("gioitinh"),
                    rs.getString("ngaysinh"),
                    rs.getInt("dienthoai"),
                    rs.getString("email"),
                    rs.getString("tendangnhap"),
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Loi o load db");
        }
        jtableNhanVien.setModel(model);
    }
    
    public boolean nhapNhanVienTuExcel(String excelFilePath) throws IOException {
        try {
            String sqlCommand = "DELETE FROM nhanvien";
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

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();

            Iterator<Cell> cellIterator = nextRow.cellIterator();
            NhanVien nv = new NhanVien();
            try {
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int rowIndex = nextCell.getRowIndex();
                    if(rowIndex == 0){
                        break;
                    }
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            nv.setMaNhanVien(nextCell.getStringCellValue());
//                            System.out.println("Ma NV: " + nv.getMaNhanVien());
                            break;
                        case 1:
                            nv.setTenNhanVien(nextCell.getStringCellValue());
//                            System.out.println("Ten NV: " + nv.getTenNhanVien());
                            break;
                        case 2:
                            nv.setChucVu(nextCell.getStringCellValue());            
//                            System.out.println("Chuc Vu: " + nv.getChucVu());
                            break;
                        case 3:
                            nv.setGioiTinh(nextCell.getStringCellValue());
//                            System.out.println("Gioi Tinh: " + nv.getGioiTinh());
                            break;
                        case 4:
                            if (nextCell.getCellType() == CellType.NUMERIC || nextCell.getCellType() == CellType.FORMULA) {
                                    if (DateUtil.isCellDateFormatted(nextCell)) {
                                        Date date = nextCell.getDateCellValue();
                                        if (date != null) {
                                            String ngaySinh = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                            nv.setNgaySinh(ngaySinh);
                                        }
                                    }
                                } else if (nextCell.getCellType() == CellType.STRING) {
                                    // Trường hợp ô ngày tháng là chuỗi văn bản
                                    String ngaySinhStr = nextCell.getStringCellValue();
                                    nv.setNgaySinh(ngaySinhStr);
                            }
//                            System.out.println("Ngay sinh: " + nv.getNgaySinh());
                            break;
                        case 5:
                            if (nextCell.getCellType() == CellType.NUMERIC) {
                                    nv.setDienThoai((int) nextCell.getNumericCellValue());
                            }
                            break;
                        case 6:
                            nv.setEmail(nextCell.getStringCellValue());
//                            System.out.println("Email : " + nv.getEmail());
                            break;
                        case 7:
                            nv.setTenDangNhap(nextCell.getStringCellValue());
//                            System.out.println("Ten Dang Nhap: " + nv.getTenDangNhap());
                            break;
                    }
                    
                }
            } catch (Exception e) {
                    System.out.println("Lỗi định dạng thông tin trong file excel!");
            }
            
            if(nv.getMaNhanVien()!= null && nv.getTenNhanVien() != null && nv.getNgaySinh() != null && nv.getGioiTinh() != null &&
                    nv.getChucVu() != null && nv.getEmail() != null && nv.getDienThoai() != 0 && nv.getTenDangNhap() != null){
                try {
                    st = conn.prepareStatement("insert into nhanvien(manhanvien, tennhanvien, "
                            +"chucvu, gioitinh, ngaysinh, dienthoai, email, tendangnhap) values(?,?,?,?,?,?,?,?)");
                    st.setString(1, nv.getMaNhanVien());
                    st.setString(2, nv.getTenNhanVien());
                    st.setString(3, nv.getChucVu());
                    st.setString(4, nv.getGioiTinh());
                    st.setString(5, nv.getNgaySinh());
                    st.setInt(6, nv.getDienThoai());
                    st.setString(7, nv.getEmail());
                    st.setString(8, nv.getTenDangNhap());
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
        inputStream.close();
        return check;
    }
    
    private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException {
	Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("Định dạng file đã chọn không phải excel!");
        }
        return workbook;
    }
}
