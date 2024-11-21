package Manager;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class XuatExcelManager {
    public static void xuatRaExcel (JTable jTable1, String tenSheet) throws FileNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file", "xls", "xlsx");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Chọn vị trí lưu");
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);
        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            String Location = chooser.getSelectedFile().toString();
            Workbook fWorkbook = null;
            if(Location.endsWith("xlsx") || Location.endsWith("xls")){
                if (Location.endsWith("xlsx")) {
                    fWorkbook = new XSSFWorkbook();
                } else if (Location.endsWith("xls")) {
                    fWorkbook = new HSSFWorkbook();
                }
                Sheet sheet;
                sheet = fWorkbook.createSheet(tenSheet);
                TableModel model = jTable1.getModel();

                //Tạo thanh tiêu đề trên cùng.
                TableColumnModel tcm;
                tcm = jTable1.getColumnModel();
                Row hRow = sheet.createRow((short) 0);
                for(int j = 0; j < tcm.getColumnCount(); j++) {                       
                    Cell cell;
                    cell = hRow.createCell((short) j);
                    cell.setCellValue(tcm.getColumn(j).getHeaderValue().toString());
                }

                // Tạo mục còn lại.
                     for (int i = 0; i < model.getRowCount(); i++) {
                    Row fRow = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Cell cell = fRow.createCell(j);

                        // Kiểm tra giá trị null trước khi gọi .toString()
                        Object value = model.getValueAt(i, j);
                        if (value != null) {
                            if (value instanceof Integer) {
                                cell.setCellValue((Integer) value);
                            } else if (value instanceof Double) {
                                cell.setCellValue((Double) value);
                                } else {
                                cell.setCellValue(value.toString());
                                    }
                                 } else {
                                    cell.setCellValue(""); // Hoặc giá trị mặc định, ví dụ: "N/A"
                                    }
                    }
                }

                FileOutputStream fileOutputStream;
                fileOutputStream = new FileOutputStream(Location);
                try (BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream)) {
                    fWorkbook.write(bos);
                    JOptionPane.showMessageDialog(null, "Đã xuất ra file excel!" , "Thông báo", 1);
                }catch(Exception e) {
                    JOptionPane.showMessageDialog(null, "Lỗi lưu file", "Thông báo", 2);
                }
                fileOutputStream.close();
            } else {
                JOptionPane.showMessageDialog(null, "Tên file phải kết thúc là .xls hoặc .xlsx", "Lỗi Xuất File", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
