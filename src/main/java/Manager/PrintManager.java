/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JPanel;


public class PrintManager {
    public static void print(JPanel jpanelPrint, String tenFile){
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName(tenFile);
        job.setPrintable(new Printable(){
            public int print(Graphics pg,PageFormat pf, int pageNum){
                pf.setOrientation(PageFormat.PORTRAIT);
                if(pageNum>0){
                return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D)pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                g2.scale(1,1);

                jpanelPrint.paint(g2);
                return Printable.PAGE_EXISTS;
            }
        });
        
        boolean ok = job.printDialog();
        if(ok){
            try{
                job.print();
            }
            catch (PrinterException ex){
            }
        }
    }
}
