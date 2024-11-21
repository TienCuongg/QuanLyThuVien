package Manager;

import connectDataBase.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViPhamManager {
    Connection conn;
    private PreparedStatement st;
    public ViPhamManager() {
        conn = new ConnectionDB().getConnection();
    }
    public void loadDataViPhamFromDB(JTable jtableViPham,String search) {
        
        String[] colTieuDe = new String[]{"MÃ MƯỢN TRẢ", "MÃ ĐỘC GIẢ", "TÊN ĐỘC GIẢ", 
            "TÊN SÁCH","NGÀY MƯỢN","NGÀY TRẢ", "LÝ DO", "TIỀN PHẠT"};
        DefaultTableModel model = new DefaultTableModel(colTieuDe, 0);
        Object[] row;
        if(search.equals("Nhập mã độc giả/ tên độc giả/ tên sách cần tìm")){
            search = "";
        }
        
        String sql;
        PreparedStatement pst;
        try {            
            sql = "SELECT v.mamuontra, m.madocgia, d.tendocgia, s.tensach, v.quahan, v.lydo, m.ngaymuon, m.ngaytra "
                    + "FROM muontra m, sach s, vipham v, docgia d "
                    + "WHERE m.mamuontra = v.mamuontra AND d.madocgia= m.madocgia AND s.masach = m.masach "
                    + "AND v.xuly IS NULL "
                    + "AND LOWER (CAST(d.madocgia  AS VARCHAR) + " +
                                  "CAST(d.tendocgia AS VARCHAR) + " +
                                  "CAST(v.lydo AS VARCHAR) + " +
                                  "CAST(m.mamuontra AS VARCHAR) + " +
                                  "CAST(m.ngaymuon AS VARCHAR) + " +
                                  "CAST(s.masach AS VARCHAR) + " +
                                  "CAST(s.tensach AS VARCHAR) + " +
                                  "CAST(m.ngaytra AS VARCHAR)) " +
                       "LIKE ? " +
                    "ORDER BY madocgia ASC";                  
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + search.toLowerCase() + "%");
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
                
                model.addRow(row);
            }
            jtableViPham.setModel(model);
        } catch (SQLException ex) {
            System.out.println("Loi o db vi pham: " +ex.toString());
        }        
    }
    
    public boolean xulyViPham(String maMuonTra) {
        boolean check = false;
        try {
            st = conn.prepareStatement("UPDATE vipham SET xuly = ? WHERE mamuontra =?");
            
            st.setString(1, "Đã xử lý");
            st.setString(2, maMuonTra);
            int result = st.executeUpdate();
            check = result != 0;
        } catch (SQLException ex) {
               System.out.println(ex.toString());  
        }
        return check;
    }
}
