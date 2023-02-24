package views;

import dao.CategoryDao;
import helper.DataValidator;
import helper.MessageDialogHelper;
import helper.ShareHelper;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Categoris;

/**
 *
 * @author ACER
 */
public class ManagerCateJDialog extends javax.swing.JDialog {

    private CategoryDao dao = new CategoryDao();
    private List<Categoris> list = dao.select_All();
    private int pos = -1;

    /**
     * Creates new form ManagerCateJDialog
     *
     * @param parent
     * @param modal
     */
    public ManagerCateJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIconImage(ShareHelper.APP_ICON);
        init();
    }

    private void init() {
        setIconImage(ShareHelper.APP_ICON);
        tableCate.fixTable(jScrollPane1);
        fillTable();
        txtCodeCate.disable();
        if (!ShareHelper.USER.getPositionBoolean()) {
            btnInsert.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }
    }

    //setModel-Xuất dữ liệu
    private void setModel(Categoris entity) {
        try {
            txtCodeCate.setText(entity.getCodeCateString());
            txtNameList.setText(entity.getNameListString());
            cbxCateType.getModel().setSelectedItem(entity.getCategoryTypeString());
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi hiễn thị dữ liệu : " + e.toString(), "Lỗi");
            System.out.println("*ManagerCateJDialog(setModel) - " + e.toString());
        }
    }

    //getModel-Lấy dữ liệu
    private Categoris getModel() {
        try {
            Categoris entity = new Categoris();
            entity.setCodeCateString(txtCodeCate.getText());
            entity.setCategoryTypeString((String) cbxCateType.getSelectedItem());
            entity.setNameListString(txtNameList.getText());
            return entity;
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi lấy dữ liệu : " + e.toString(), "Lỗi");
            System.out.println("*ManagerCateJDialog(getModel) - " + e.toString());
            return null;
        }
    }

    //load all data
    private void fillTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) tableCate.getModel();
            model.setRowCount(0);
            int i = 0;
            for (Categoris entity : dao.select_All()) {
                model.addRow(new Object[]{
                    i,
                    entity.getCodeCateString(),
                    entity.getNameListString(),
                    entity.getCategoryTypeString()});
                i++;
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi dữ liệu bảng : " + e.toString(), "Lỗi");
            System.out.println("*ManagerCateJDialog(fillTable) - " + e.toString());
        }
    }

    //clickTable
    private void clickTable() {
        try {
            int row = tableCate.getSelectedRow();
            if (row >= 0) {
                int index = (int) tableCate.getValueAt(row, 0);
                Categoris entity = dao.getEnityByPossition(String.valueOf(index));
                setModel(entity);
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi clickTable : " + e.toString(), "Lỗi");
            System.out.println("*ManagerCateJDialog(clickTable) - " + e.toString());
        }
    }

    //Phương thức checkSpam
    private StringBuilder checkSpam() {
        StringBuilder sb = new StringBuilder();
        DataValidator.validateCheckName(txtNameList, sb, "Tên danh mục không được để trống !");
        return sb;
    }

    //Hiễn thị theo vị trí
    private void selectPos(int pos) {
        Categoris entity = dao.getEnityByPossition(String.valueOf(pos));
        setModel(entity);
    }

    //Phương thức reset
    private void reset() {
        txtCodeCate.setText("MÃ DANH MỤC");
        txtNameList.setText("TÊN DANH MỤC");
        DataValidator.error(txtNameList, true);
        DataValidator.error(txtCodeCate, true);
    }

    //Phương thức insert
    private void insert() {
        StringBuilder sb = checkSpam();
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            return;
        }
        try {
            Categoris entity = getModel();
            entity.setCodeCateString(DataValidator.randomAlphaNumeric(20));
            if (dao.select_By_PK(entity.getCodeCateString()) != null) {
                MessageDialogHelper.showMessageDialog(this, "Mã danh mục đã tồn tại !", "Thông báo tin nhắn");
            } else {
                try {
                    dao.insert(entity);
                    MessageDialogHelper.showMessageDialog(this, "Lưu danh mục thành công !", "Thông báo tin nhắn");
                    reset();
                    fillTable();
                } catch (Exception e) {
                    MessageDialogHelper.showErrorDialog(this, "Lỗi insert : " + e.toString(), "Lỗi");
                    System.out.println("*ManagerCateJDialog(insert) - " + e.toString());
                }
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi insert : " + e.toString(), "Lỗi");
            System.out.println("*ManagerCateJDialog(insert) - " + e.toString());
        }
    }

    //Phương thức update
    private void update() {
        StringBuilder sb = checkSpam();
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            return;
        }
        try {
            if (dao.select_By_PK(txtCodeCate.getText()) == null) {
                MessageDialogHelper.showMessageDialog(this, "Danh mục này không tồn tại !", "Thông báo tin nhắn");
            } else {
                try {
                    Categoris entity = getModel();
                    dao.update(entity);
                    MessageDialogHelper.showMessageDialog(this, "Cập nhập danh mục thành công !", "Thông báo tin nhắn");
                    reset();
                    fillTable();
                } catch (Exception e) {
                    MessageDialogHelper.showErrorDialog(this, "Lỗi update : " + e.toString(), "Lỗi");
                    System.out.println("*ManagerCateJDialog(update) - " + e.toString());
                }
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi update : " + e.toString(), "Lỗi");
            System.out.println("*ManagerCateJDialog(update) - " + e.toString());
        }
    }

    //Phương thức delete
    private void delete() {
        try {
            if (MessageDialogHelper.showComfirmDialog(this, "Bạn có muốn xoá danh mục này không ?", "Cảnh báo xóa danh mục")
                    == JOptionPane.NO_OPTION) {
            } else {
                Categoris entity = dao.select_By_PK(txtCodeCate.getText());
                if (entity == null) {
                    MessageDialogHelper.showMessageDialog(this, "Không tìm thấy danh mục !", "Thông báo tin nhắn");
                } else {
                        try {
                            dao.delete(txtCodeCate.getText());
                            MessageDialogHelper.showMessageDialog(this, "Xoá dữ liệu danh mục thành công !", "Thông báo tin nhắn");
                            reset();
                            fillTable();
                        } catch (Exception e) {
                            MessageDialogHelper.showErrorDialog(this, "Lỗi delete : " + e.toString(), "Lỗi");
                            System.out.println("*ManagerCateJDialog(delete) - " + e.toString());
                        }
                }
            }
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi delete : " + e.toString(), "Lỗi");
            System.out.println("*ManagerCateJDialog(delete) - " + e.toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnFirst = new swing.Button();
        btnPrevious = new swing.Button();
        btnNext = new swing.Button();
        btnLast = new swing.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCate = new swing.TableDark();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCodeCate = new swing.MyTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtNameList = new swing.MyTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cbxCateType = new swing.ComboBoxSuggestion();
        jPanel4 = new javax.swing.JPanel();
        btnInsert = new swing.Button();
        btnUpdate = new swing.Button();
        btnDelete = new swing.Button();
        btnReset = new swing.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ DANH MỤC");
        setMaximumSize(new java.awt.Dimension(1300, 750));
        setMinimumSize(new java.awt.Dimension(1300, 750));
        setPreferredSize(new java.awt.Dimension(1300, 750));

        jPanel1.setMaximumSize(new java.awt.Dimension(900, 950));
        jPanel1.setMinimumSize(new java.awt.Dimension(900, 950));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 950));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel10.setPreferredSize(new java.awt.Dimension(900, 60));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 5));

        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_first_40px.png"))); // NOI18N
        btnFirst.setEffectColor(new java.awt.Color(51, 153, 0));
        btnFirst.setPreferredSize(new java.awt.Dimension(50, 50));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        jPanel10.add(btnFirst);

        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_previous_40px.png"))); // NOI18N
        btnPrevious.setEffectColor(new java.awt.Color(51, 153, 0));
        btnPrevious.setPreferredSize(new java.awt.Dimension(50, 50));
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });
        jPanel10.add(btnPrevious);

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_next_40px.png"))); // NOI18N
        btnNext.setEffectColor(new java.awt.Color(51, 153, 0));
        btnNext.setPreferredSize(new java.awt.Dimension(50, 50));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        jPanel10.add(btnNext);

        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_last_40px.png"))); // NOI18N
        btnLast.setEffectColor(new java.awt.Color(51, 153, 0));
        btnLast.setPreferredSize(new java.awt.Dimension(50, 50));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        jPanel10.add(btnLast);

        jPanel1.add(jPanel10, java.awt.BorderLayout.PAGE_END);

        tableCate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Mã danh mục", "Tên danh mục", "Loại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCate.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        tableCate.setRowHeight(50);
        tableCate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCateMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableCate);
        if (tableCate.getColumnModel().getColumnCount() > 0) {
            tableCate.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setMaximumSize(new java.awt.Dimension(500, 750));
        jPanel2.setMinimumSize(new java.awt.Dimension(500, 750));
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 750));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setMaximumSize(new java.awt.Dimension(500, 650));
        jPanel3.setMinimumSize(new java.awt.Dimension(500, 650));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 650));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 60));

        jPanel11.setMaximumSize(new java.awt.Dimension(490, 100));
        jPanel11.setMinimumSize(new java.awt.Dimension(490, 100));
        jPanel11.setPreferredSize(new java.awt.Dimension(490, 100));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_barcode_scanner_30px.png"))); // NOI18N
        jLabel4.setText("Mã danh mục");
        jLabel4.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel11.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        txtCodeCate.setBackground(new java.awt.Color(255, 255, 255));
        txtCodeCate.setForeground(new java.awt.Color(0, 0, 0));
        txtCodeCate.setText("MÃ DANH MỤC");
        txtCodeCate.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtCodeCate.setPreferredSize(new java.awt.Dimension(490, 38));
        txtCodeCate.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel11.add(txtCodeCate, java.awt.BorderLayout.LINE_START);

        jPanel3.add(jPanel11);

        jPanel12.setMaximumSize(new java.awt.Dimension(490, 100));
        jPanel12.setMinimumSize(new java.awt.Dimension(490, 100));
        jPanel12.setPreferredSize(new java.awt.Dimension(490, 100));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_diversity_30px.png"))); // NOI18N
        jLabel5.setText("Tên danh mục");
        jLabel5.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel12.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        txtNameList.setBackground(new java.awt.Color(255, 255, 255));
        txtNameList.setForeground(new java.awt.Color(0, 0, 0));
        txtNameList.setText("TÊN DANH MỤC");
        txtNameList.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtNameList.setPreferredSize(new java.awt.Dimension(490, 38));
        txtNameList.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel12.add(txtNameList, java.awt.BorderLayout.LINE_START);

        jPanel3.add(jPanel12);

        jPanel13.setMaximumSize(new java.awt.Dimension(490, 100));
        jPanel13.setMinimumSize(new java.awt.Dimension(490, 100));
        jPanel13.setPreferredSize(new java.awt.Dimension(490, 100));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_libre_office_draw_30px.png"))); // NOI18N
        jLabel6.setText("Loại danh mục");
        jLabel6.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel13.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        cbxCateType.setForeground(new java.awt.Color(51, 51, 51));
        cbxCateType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Car" }));
        cbxCateType.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jPanel13.add(cbxCateType, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel13);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setMaximumSize(new java.awt.Dimension(500, 100));
        jPanel4.setMinimumSize(new java.awt.Dimension(500, 100));
        jPanel4.setPreferredSize(new java.awt.Dimension(500, 100));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 25));

        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_insert_button_40px.png"))); // NOI18N
        btnInsert.setEffectColor(new java.awt.Color(0, 102, 204));
        btnInsert.setPreferredSize(new java.awt.Dimension(50, 50));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        jPanel4.add(btnInsert);

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_40px.png"))); // NOI18N
        btnUpdate.setEffectColor(new java.awt.Color(0, 102, 204));
        btnUpdate.setPreferredSize(new java.awt.Dimension(50, 50));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel4.add(btnUpdate);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_40px.png"))); // NOI18N
        btnDelete.setEffectColor(new java.awt.Color(0, 102, 204));
        btnDelete.setPreferredSize(new java.awt.Dimension(50, 50));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel4.add(btnDelete);

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_sync_40px.png"))); // NOI18N
        btnReset.setEffectColor(new java.awt.Color(51, 153, 0));
        btnReset.setPreferredSize(new java.awt.Dimension(50, 50));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel4.add(btnReset);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);

        setSize(new java.awt.Dimension(1316, 789));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableCateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCateMouseClicked
        clickTable();
    }//GEN-LAST:event_tableCateMouseClicked

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        pos = 0;
        selectPos(pos);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        pos--;
        if (pos <= 0) {
            pos = 0;
        }
        selectPos(pos);
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        pos++;
        if (pos >= list.size() - 1) {
            pos = list.size() - 1;
        }
        selectPos(pos);
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        pos = list.size() - 1;
        selectPos(pos);
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManagerCateJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerCateJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerCateJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerCateJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ManagerCateJDialog dialog = new ManagerCateJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.Button btnDelete;
    private swing.Button btnFirst;
    private swing.Button btnInsert;
    private swing.Button btnLast;
    private swing.Button btnNext;
    private swing.Button btnPrevious;
    private swing.Button btnReset;
    private swing.Button btnUpdate;
    private swing.ComboBoxSuggestion cbxCateType;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.TableDark tableCate;
    private swing.MyTextField txtCodeCate;
    private swing.MyTextField txtNameList;
    // End of variables declaration//GEN-END:variables
}
