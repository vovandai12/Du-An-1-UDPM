package views;

import chart.ModelChart;
import helper.DatabaseHelper;
import java.awt.Color;
import java.sql.ResultSet;

/**
 *
 * @author ACER
 */
public class HomeJPanel extends javax.swing.JPanel {

    /**
     * Creates new form TrangChuJPanel
     */
    public HomeJPanel() {
        initComponents();
        chart.addLegend("Số sản phẩm bán được", new Color(12, 84, 175), new Color(0, 108, 247));
        chart.addLegend("Số khách hàng trong tháng", new Color(54, 4, 143), new Color(104, 49, 200));
        chart.addLegend("Số hóa đơn đã xuất", new Color(5, 125, 0), new Color(95, 209, 69));
        chart.addLegend("Số nhân viên bán hàng", new Color(186, 37, 37), new Color(241, 100, 120));
        String string = "SELECT\n"
                + "MONTH(A.[ExportDate]) AS Thang,\n"
                + "COUNT(B.[CodeProduct]) AS SoSanPham,\n"
                + "COUNT(DISTINCT(C.[CodeCustomer])) AS SoKhachHang,\n"
                + "COUNT(DISTINCT(A.[CodeReceipt])) AS SoHoaDon,\n"
                + "COUNT(DISTINCT(A.[EmailUser])) AS SoNhanVien\n"
                + "FROM [dbo].[Receipts] A\n"
                + "JOIN [dbo].[ReceiptDetails] B ON A.[CodeReceipt] = B.[CodeRecieptDetail]\n"
                + "JOIN [dbo].[Customers] C ON C.[CodeCustomer] = A.[CodeCustormer]\n"
                + "GROUP BY MONTH(A.[ExportDate])";
//        chart.addData(new ModelChart("January", new double[]{500, 200, 80, 89}));
        try {
            ResultSet rs = DatabaseHelper.executeQuery(string);
            while (rs.next()) {
                chart.addData(new ModelChart("Tháng " + rs.getString("Thang"), new double[]{
                    Double.valueOf(rs.getString("SoSanPham")),
                    Double.valueOf(rs.getString("SoKhachHang")),
                    Double.valueOf(rs.getString("SoHoaDon")),
                    Double.valueOf(rs.getString("SoNhanVien"))}));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        chart.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        chart = new chart.Chart();

        setMaximumSize(new java.awt.Dimension(1400, 780));
        setMinimumSize(new java.awt.Dimension(1400, 780));
        setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BIỂU ĐỒ THỐNG KÊ");
        add(jLabel1, java.awt.BorderLayout.PAGE_START);
        add(chart, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private chart.Chart chart;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}