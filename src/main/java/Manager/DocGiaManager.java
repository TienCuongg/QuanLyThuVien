package Manager;

import Model.DocGia;
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
import java.util.Calendar;
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

public class DocGiaManager {
    Connection conn;
    ArrayList<DocGia> lstDocGia= new ArrayList();  
    private PreparedStatement st;
    public static final int HAN_THE = 5; // hạn sử dụng thẻ là 5 năm.
    public DocGiaManager() {
        conn = new ConnectionDB().getConnection();
    }
    
    
    public ArrayList<DocGia> getAllDocGia(String search) {                           
        try {            
            String sql;
            PreparedStatement pst;

            if (search == null || search.trim().isEmpty()) {
                sql = "SELECT * FROM docgia";
                pst = conn.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM docgia " +
                      "WHERE LOWER(CAST(madocgia AS VARCHAR) + CAST(tendocgia AS VARCHAR) + " +
                      "CAST(ngaysinh AS VARCHAR) + CAST(gioitinh AS VARCHAR) + " +
                      "CAST(diachi AS VARCHAR) + CAST(email AS VARCHAR) + " +
                      "CAST(dienthoai AS VARCHAR) + CAST(ngaylapthe AS VARCHAR))" +
                      "LIKE ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, "%" + search.toLowerCase() + "%");
            }
            
            ResultSet rs = pst.executeQuery();       
            while(rs.next()) {
                DocGia dg = new DocGia();
                dg.setMaDocGia(rs.getString("madocgia"));
                dg.setTenDocGia(rs.getString("tendocgia"));
                dg.setNgaySinh(rs.getString("ngaysinh"));
                dg.setGioiTinh(rs.getString("gioitinh"));
                dg.setDiaChi(rs.getString("diachi"));
                dg.setEmail(rs.getString("email"));
                dg.setDienThoai(rs.getInt("dienthoai")); 
                dg.setNgayLapThe(rs.getString("ngaylapthe"));
                
                lstDocGia.add(dg);              
            }
        } catch (SQLException ex) {
               System.out.println("Loi getALL: " + ex.toString());  
        }
        return  lstDocGia;
    }
     
     
    public boolean themDocGia(DocGia docGia) {
        boolean check = false; 
        try {
            DocGia dg = docGia;
            st = conn.prepareStatement("insert into docgia(madocgia, tendocgia, ngaysinh, "
                    +"gioitinh, diachi, email, dienthoai, ngaylapthe) values(?,?,?,?,?,?,?,?)");
            st.setString(1, dg.getMaDocGia());
            st.setString(2, dg.getTenDocGia());
            st.setString(3, dg.getNgaySinh());
            st.setString(4, dg.getGioiTinh());
            st.setString(5, dg.getDiaChi());
            st.setString(6, dg.getEmail());
            st.setInt(7, dg.getDienThoai());
            st.setString(8, dg.getNgayLapThe());
            
            int result = st.executeUpdate();
            check = result != 0;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return check;
    }
    
    public boolean suaDocGia(DocGia docgia) {
        boolean check = false;
        DocGia dg = docgia;
        try {
            st = conn.prepareStatement("UPDATE docgia SET tendocgia=?, ngaysinh=?, "
                    +"gioitinh=?, diachi=?, email=?, dienthoai=?, ngaylapthe=? WHERE madocgia =?");
            
            st.setString(1, dg.getTenDocGia());
            st.setString(2, dg.getNgaySinh());
            st.setString(3, dg.getGioiTinh());
            st.setString(4,dg.getDiaChi());
            st.setString(5, dg.getEmail());
            st.setInt(6, dg.getDienThoai());
            st.setString(7, dg.getNgayLapThe());
            st.setString(8, dg.getMaDocGia());
            int result = st.executeUpdate();
            check = result != 0;
        } catch (SQLException ex) {
               System.out.println(ex.toString());  
        }
        return check;
    }
    
    
    public void xoaDocGia(String maDocGia) {
//        try {
//            String sql = "SELECT COUNT(masach) FROM muontra WHERE madocgia = " + maDocGia +" AND ngaytra IS NULL";
//            PreparedStatement pst = conn.prepareStatement(sql);   
//            ResultSet rs = pst.executeQuery();
//            if(rs.next()){
//                int dangMuon= rs.getInt("COUNT(masach)");
//                if(dangMuon == 0){
//                    st = conn.prepareStatement("UPDATE docgia SET  trangthai=?  WHERE madocgia =?");
//            
//                    st.setString(1, "đã xóa");
//                    st.setString(2, maDocGia);
//                    st.executeUpdate();
//                    JOptionPane.showMessageDialog(null, "Đã Xóa Độc Giả!" , "Thông báo", 1);
//                }else 
//                    JOptionPane.showMessageDialog(null, "Độc Giả Này Đang Mượn Sách", "Lỗi Xóa Độc Giả", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (SQLException ex) {
////            Logger.getLogger(MuonTraManager.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra!" , "Thông báo", 2);
//        }
          try {
            String sqlCommand = "delete from docgia where madocgia = ?";
            st = conn.prepareStatement(sqlCommand);
            st.setString(1, maDocGia);
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Đã Xóa Độc Giả!" , "Thông báo", 1);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(null, "Độc Giả Này Đã Được Sử Dụng", "Lỗi Xóa Độc Giả", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void loadDataDocGiaFromDB(JTable jtableDocGia,String search) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DocGiaManager docGiaManager = new DocGiaManager();
        if(search.equals("Nhập mã độc giả/ tên độc giả/ email cần tìm")){
            search = "";
        }
        lstDocGia= docGiaManager.getAllDocGia(search);
        //Dua danh sach muon tra len jTable
        String[] colTieuDe = new String[]{ "MÃ ĐỘC GIẢ", "HỌ TÊN", "NGÀY SINH", "GIỚI TÍNH", "ĐỊA CHỈ", "EMAIL", "ĐIỆN THOẠI", "NGÀY LẬP THẺ", "NGÀY HẾT HẠN" };
        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);
        Object[] row;
        for (DocGia dg : lstDocGia) {
            row = new Object[9];
            row[0] = dg.getMaDocGia();
            row[1] = dg.getTenDocGia();
            row[2] = dg.getNgaySinh();
            row[3] = dg.getGioiTinh();
            row[4] = dg.getDiaChi();
            row[5] = dg.getEmail();
            row[6] = dg.getDienThoai();
            row[7] = dg.getNgayLapThe();
            
            // tính ngày hết hạn thẻ = ngày lập cộng với HAN_THE.
            Calendar c1 = Calendar.getInstance();
            c1.setTime(java.sql.Date.valueOf(dg.getNgayLapThe()));
            c1.roll(Calendar.YEAR, HAN_THE);                
            row[8] = dateFormat.format(c1.getTime());
       
            model.addRow(row);
        }       
        jtableDocGia.setModel(model);
    }   
    
    public void loadDataDocGiaMTFromDB(JTable jtableDocGia,String search) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DocGiaManager docGiaManager = new DocGiaManager();
        if(search.equals("Nhập mã độc giả/ tên độc giả/ email cần tìm")){
            search = "";
        }
        lstDocGia= docGiaManager.getAllDocGia(search);
        //Dua danh sach muon tra len jTable
        String[] colTieuDe = new String[]{ "MÃ ĐỘC GIẢ", "HỌ TÊN", "NGÀY SINH", "GIỚI TÍNH", "NGÀY HẾT HẠN" };
        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);
        Object[] row;
        for (DocGia dg : lstDocGia) {
            row = new Object[5];
            row[0] = dg.getMaDocGia();
            row[1] = dg.getTenDocGia();
            row[2] = dg.getNgaySinh();
            row[3] = dg.getGioiTinh();
            
            // tính ngày hết hạn thẻ = ngày lập cộng với HAN_THE.
            Calendar c1 = Calendar.getInstance();
            c1.setTime(java.sql.Date.valueOf(dg.getNgayLapThe()));
            c1.roll(Calendar.YEAR, HAN_THE);                
            row[4] = dateFormat.format(c1.getTime());
       
            model.addRow(row);
        }       
        jtableDocGia.setModel(model);
    }
    
    public boolean nhapDocGiaTuExcel(String excelFilePath) throws IOException {
        try {
            String sqlCommand = "DELETE FROM docgia";
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
            DocGia dg = new DocGia();
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
                            dg.setMaDocGia(nextCell.getStringCellValue());
                            System.out.println("ma: "+dg.getMaDocGia());
                            break;
                        case 1:
                            dg.setTenDocGia(nextCell.getStringCellValue());
                            System.out.println("tên: "+dg.getTenDocGia());
                            break;
                        case 2:
                            if(nextCell.getCellType() == CellType.NUMERIC || nextCell.getCellType() == CellType.FORMULA) {
                                if (DateUtil.isCellDateFormatted(nextCell)) {
                                    Date date = nextCell.getDateCellValue();
                                    if (date != null) {
                                            String ngaySinh = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                            dg.setNgaySinh(ngaySinh);
                                    }
                                }
                            } else if (nextCell.getCellType() == CellType.STRING) {
                                // Trường hợp ô ngày tháng là chuỗi văn bản
                                String ngaySinhStr = nextCell.getStringCellValue();
                                dg.setNgaySinh(ngaySinhStr);
                            }
                            System.out.println("ngay sinh: "+dg.getNgaySinh());
                            break;
                        case 3:
                            dg.setGioiTinh(nextCell.getStringCellValue());
                            System.out.println("gioi tinh: "+dg.getGioiTinh());
                            break;
                        case 4:
                            dg.setDiaChi(nextCell.getStringCellValue());
                            System.out.println("dia chi: "+dg.getDiaChi());
                            break;
                        case 5:
                            dg.setEmail(nextCell.getStringCellValue());
                            System.out.println("email: "+dg.getEmail());
                            break;
                        case 6:
                            if (nextCell.getCellType() == CellType.NUMERIC) {
                                    dg.setDienThoai((int) nextCell.getNumericCellValue());
                            }
                            break;
                        case 7:
                            if(nextCell.getCellType() == CellType.NUMERIC || nextCell.getCellType() == CellType.FORMULA) {
                                if (DateUtil.isCellDateFormatted(nextCell)) {
                                    Date date = nextCell.getDateCellValue();
                                    if (date != null) {
                                            String ngayLapThe = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                            dg.setNgayLapThe(ngayLapThe);
                                    }
                                }
                            } else if (nextCell.getCellType() == CellType.STRING) {
                                // Trường hợp ô ngày tháng là chuỗi văn bản
                                String ngayLapTheStr = nextCell.getStringCellValue();
                                dg.setNgayLapThe(ngayLapTheStr);
                            }
                            System.out.println("Ngay Lap The: "+dg.getNgayLapThe());
                            break;
                    }
                }
            } catch (Exception e) {
                    System.out.println("Lỗi định dạng thông tin trong file excel!");
//                    System.out.println("tên "+dg.getTenDocGia());
//                    System.out.println("ngày sinh "+dg.getNgaySinh());
//                    System.out.println("Giới tính "+dg.getGioiTinh());
//                    System.out.println("Địa chỉ "+dg.getDiaChi());
//                    System.out.println("Điện Thoại "+dg.getDienThoai());
//                    System.out.println("Email "+dg.getEmail());
            }
            
            if(dg.getTenDocGia() != null && dg.getNgaySinh() != null && dg.getGioiTinh() != null &&
                    dg.getDiaChi() != null && dg.getEmail() != null && dg.getDienThoai() != 0 &&
                    dg.getNgayLapThe() != null ){
                try {
                    st = conn.prepareStatement("insert into docgia(madocgia, tendocgia, ngaysinh,"
                            +" gioitinh, diachi, email, dienthoai, ngaylapthe) values(?,?,?,?,?,?,?,?)");
                    st.setString(1, dg.getMaDocGia());
                    st.setString(2, dg.getTenDocGia());
                    st.setString(3, dg.getNgaySinh());
                    st.setString(4, dg.getGioiTinh());
                    st.setString(5, dg.getDiaChi());
                    st.setString(6, dg.getEmail());
                    st.setInt(7, dg.getDienThoai());
                    st.setString(8, dg.getNgayLapThe());

                    int result = st.executeUpdate();
                    check = result != 0;
                } catch (SQLException e) {
                    System.out.println("Loi o Xuat Excel");
                    System.out.println(e.toString());
                }
            }
        }
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
            throw new IllegalArgumentException("Định dạng file đã chọn không phải excel!");
        }
        return workbook;
    }
    
}
