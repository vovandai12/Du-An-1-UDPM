package main;

import helper.MessageDialogHelper;
import helper.ShareHelper;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Users;
import views.CartJDialog;
import views.ChanePassJDialog;
import views.HomeJPanel;
import views.InfoJDialog;
import views.JfreeChartJDialog;
import views.LoaddingJDialog;
import views.LoginJDialog;
import views.ManagerCateJDialog;
import views.ManagerProductJDialog;
import views.ManagerReceiptJDialog;
import views.ManagerUserJDialog;

/**
 *
 * @author ACER
 */
public class Main extends javax.swing.JFrame {

    private JPanel childJPanel;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        init();
    }

    private void init() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIconImage(ShareHelper.APP_ICON);
        childJPanel = new HomeJPanel();
        openJPanel(childJPanel);
        loaddingJDialog();
        loginJDialog();
    }

    //Phương thức mở jpanel
    private void openJPanel(JPanel childJPanel) {
        jPanelViews.removeAll();
        jPanelViews.add(childJPanel);
        jPanelViews.validate();
        jPanelViews.repaint();
    }

    //Mở cửa sổ loadding
    private void loaddingJDialog() {
        new LoaddingJDialog(this, true).setVisible(true);
    }

    //Mở cửa sổ login
    private void loginJDialog() {
        new LoginJDialog(this, true).setVisible(true);
    }

    //Exit
    private void exit() {
        if (MessageDialogHelper.showComfirmDialog(this, "Bạn có muốn thoát chương trình ?",
                "Thoát chương trình") == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    //Logout
    private void logout() {
        if (ShareHelper.logoff()) {
            MessageDialogHelper.showMessageDialog(this, "Đã đăng xuất thành công !", "Thông báo tin nhắn");
            if (ShareHelper.logoff() == true) {
                dispose();
                Main main = new Main();
                main.setVisible(true);
            }
        }
    }

    //Login
    private void login() {
        Users entity = ShareHelper.USER;
        if (entity != null) {
            MessageDialogHelper.showErrorDialog(this, "Bạn đã đăng nhập rồi !", "Thông báo lỗi");
            return;
        }
        dispose();
        loginJDialog();
    }

    //chanePass
    private void chanePass() {
        Users entity = ShareHelper.USER;
        if (entity == null) {
            MessageDialogHelper.showErrorDialog(this, "Bạn chưa đăng nhập !", "Thông báo lỗi");
            return;
        }
        if (MessageDialogHelper.showComfirmDialog(this, "Bạn có muốn đổi mật khẩu không ?",
                "Đổi mật khẩu") == JOptionPane.YES_OPTION) {
            dispose();
            new ChanePassJDialog(this, true).setVisible(true);
        }
    }

    //manaUser
    private void manaUser() {
        new ManagerUserJDialog(this, true).setVisible(true);
    }

    //manaCate
    private void manaCate() {
        new ManagerCateJDialog(this, true).setVisible(true);
    }

    //manaProduct
    private void manaProduct() {
        new ManagerProductJDialog(this, true).setVisible(true);
    }

    //manaReceipt
    private void manaReceipt() {
        new ManagerReceiptJDialog(this, true).setVisible(true);
    }

    //info
    private void info() {
        new InfoJDialog(this, true).setVisible(true);
    }

    //order
    private void order() {
        new CartJDialog(this, true).setVisible(true);
    }

    //jfreeChart
    private void jfreeChart() {
        new JfreeChartJDialog(this, true).setVisible(true);
    }

    private void openWebsite() throws URISyntaxException {
        try {
            Desktop.getDesktop().browse(new URI("https://ap.poly.edu.vn/"));
        } catch (IOException ex) {
            ex.printStackTrace();
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

        jPanelViews = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMILogin = new javax.swing.JMenuItem();
        jMILogout = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMIChanePass = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMIExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMIManaUser = new javax.swing.JMenuItem();
        jMIManaCate = new javax.swing.JMenuItem();
        jMIManaProduct = new javax.swing.JMenuItem();
        jMIManaReceipt = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMIManaSeChart = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMIWindown = new javax.swing.JMenuItem();
        jMIInfo = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMIOrder = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QUẢN LÝ CỬA HÀNG LAMBORGHINI");
        setMinimumSize(new java.awt.Dimension(1400, 810));
        setResizable(false);

        jPanelViews.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanelViews, java.awt.BorderLayout.CENTER);

        jMenuBar1.setMinimumSize(new java.awt.Dimension(58, 50));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(58, 50));

        jMenu1.setText("Hệ thống");
        jMenu1.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(120, 30));

        jMILogin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMILogin.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMILogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_login_25px.png"))); // NOI18N
        jMILogin.setMnemonic('\u0110');
        jMILogin.setText("Đăng nhập");
        jMILogin.setToolTipText("");
        jMILogin.setPreferredSize(new java.awt.Dimension(220, 50));
        jMILogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMILoginActionPerformed(evt);
            }
        });
        jMenu1.add(jMILogin);

        jMILogout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMILogout.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMILogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_logout_rounded_left_25px.png"))); // NOI18N
        jMILogout.setMnemonic('\u0110');
        jMILogout.setText("Đăng xuất");
        jMILogout.setPreferredSize(new java.awt.Dimension(220, 50));
        jMILogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMILogoutActionPerformed(evt);
            }
        });
        jMenu1.add(jMILogout);
        jMenu1.add(jSeparator1);

        jMIChanePass.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMIChanePass.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMIChanePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_secure_25px.png"))); // NOI18N
        jMIChanePass.setMnemonic('\u0110');
        jMIChanePass.setText("Đổi mật khẩu");
        jMIChanePass.setPreferredSize(new java.awt.Dimension(220, 50));
        jMIChanePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIChanePassActionPerformed(evt);
            }
        });
        jMenu1.add(jMIChanePass);
        jMenu1.add(jSeparator2);

        jMIExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMIExit.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMIExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_shutdown_25px.png"))); // NOI18N
        jMIExit.setMnemonic('K');
        jMIExit.setText("Kết thúc");
        jMIExit.setPreferredSize(new java.awt.Dimension(220, 50));
        jMIExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIExitActionPerformed(evt);
            }
        });
        jMenu1.add(jMIExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Quản lý");
        jMenu2.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(120, 30));

        jMIManaUser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMIManaUser.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMIManaUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_staff_25px.png"))); // NOI18N
        jMIManaUser.setMnemonic('N');
        jMIManaUser.setText("Nhân viên");
        jMIManaUser.setToolTipText("");
        jMIManaUser.setPreferredSize(new java.awt.Dimension(220, 50));
        jMIManaUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIManaUserActionPerformed(evt);
            }
        });
        jMenu2.add(jMIManaUser);

        jMIManaCate.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMIManaCate.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMIManaCate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_category_25px.png"))); // NOI18N
        jMIManaCate.setMnemonic('D');
        jMIManaCate.setText("Danh mục");
        jMIManaCate.setToolTipText("");
        jMIManaCate.setPreferredSize(new java.awt.Dimension(220, 50));
        jMIManaCate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIManaCateActionPerformed(evt);
            }
        });
        jMenu2.add(jMIManaCate);

        jMIManaProduct.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMIManaProduct.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMIManaProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_product_25px.png"))); // NOI18N
        jMIManaProduct.setMnemonic('S');
        jMIManaProduct.setText("Sản phẩm");
        jMIManaProduct.setToolTipText("");
        jMIManaProduct.setPreferredSize(new java.awt.Dimension(220, 50));
        jMIManaProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIManaProductActionPerformed(evt);
            }
        });
        jMenu2.add(jMIManaProduct);

        jMIManaReceipt.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMIManaReceipt.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMIManaReceipt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_receipt_25px.png"))); // NOI18N
        jMIManaReceipt.setMnemonic('H');
        jMIManaReceipt.setText("Hóa đơn");
        jMIManaReceipt.setToolTipText("");
        jMIManaReceipt.setPreferredSize(new java.awt.Dimension(220, 50));
        jMIManaReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIManaReceiptActionPerformed(evt);
            }
        });
        jMenu2.add(jMIManaReceipt);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Thống kê");
        jMenu3.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jMenu3.setPreferredSize(new java.awt.Dimension(120, 30));

        jMIManaSeChart.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMIManaSeChart.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMIManaSeChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_chart_25px.png"))); // NOI18N
        jMIManaSeChart.setMnemonic('B');
        jMIManaSeChart.setText("Biểu đồ");
        jMIManaSeChart.setToolTipText("");
        jMIManaSeChart.setPreferredSize(new java.awt.Dimension(220, 50));
        jMIManaSeChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIManaSeChartActionPerformed(evt);
            }
        });
        jMenu3.add(jMIManaSeChart);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Trợ giúp");
        jMenu4.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jMenu4.setPreferredSize(new java.awt.Dimension(120, 30));

        jMIWindown.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMIWindown.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMIWindown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_windows_client_25px.png"))); // NOI18N
        jMIWindown.setMnemonic('H');
        jMIWindown.setText("Hướng dẫn sử dụng");
        jMIWindown.setToolTipText("");
        jMIWindown.setPreferredSize(new java.awt.Dimension(220, 50));
        jMIWindown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIWindownActionPerformed(evt);
            }
        });
        jMenu4.add(jMIWindown);

        jMIInfo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMIInfo.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMIInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_info_popup_25px.png"))); // NOI18N
        jMIInfo.setMnemonic('T');
        jMIInfo.setText("Thông tin sản phẩm");
        jMIInfo.setToolTipText("");
        jMIInfo.setPreferredSize(new java.awt.Dimension(220, 50));
        jMIInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIInfoActionPerformed(evt);
            }
        });
        jMenu4.add(jMIInfo);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Đặt hàng");
        jMenu5.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jMenu5.setPreferredSize(new java.awt.Dimension(120, 30));

        jMIOrder.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMIOrder.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jMIOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_create_order_25px.png"))); // NOI18N
        jMIOrder.setMnemonic('H');
        jMIOrder.setText("Đặt hàng");
        jMIOrder.setToolTipText("");
        jMIOrder.setPreferredSize(new java.awt.Dimension(220, 50));
        jMIOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIOrderActionPerformed(evt);
            }
        });
        jMenu5.add(jMIOrder);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMIExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIExitActionPerformed
        exit();
    }//GEN-LAST:event_jMIExitActionPerformed

    private void jMILogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMILogoutActionPerformed
        logout();
    }//GEN-LAST:event_jMILogoutActionPerformed

    private void jMILoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMILoginActionPerformed
        login();
    }//GEN-LAST:event_jMILoginActionPerformed

    private void jMIChanePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIChanePassActionPerformed
        chanePass();
    }//GEN-LAST:event_jMIChanePassActionPerformed

    private void jMIManaUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIManaUserActionPerformed
        manaUser();
    }//GEN-LAST:event_jMIManaUserActionPerformed

    private void jMIManaCateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIManaCateActionPerformed
        manaCate();
    }//GEN-LAST:event_jMIManaCateActionPerformed

    private void jMIManaProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIManaProductActionPerformed
        manaProduct();
    }//GEN-LAST:event_jMIManaProductActionPerformed

    private void jMIManaReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIManaReceiptActionPerformed
        manaReceipt();
    }//GEN-LAST:event_jMIManaReceiptActionPerformed

    private void jMIInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIInfoActionPerformed
        info();
    }//GEN-LAST:event_jMIInfoActionPerformed

    private void jMIOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIOrderActionPerformed
        order();
    }//GEN-LAST:event_jMIOrderActionPerformed

    private void jMIManaSeChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIManaSeChartActionPerformed
        jfreeChart();
    }//GEN-LAST:event_jMIManaSeChartActionPerformed

    private void jMIWindownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIWindownActionPerformed
        try {
            openWebsite();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMIWindownActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMIChanePass;
    private javax.swing.JMenuItem jMIExit;
    private javax.swing.JMenuItem jMIInfo;
    private javax.swing.JMenuItem jMILogin;
    private javax.swing.JMenuItem jMILogout;
    private javax.swing.JMenuItem jMIManaCate;
    private javax.swing.JMenuItem jMIManaProduct;
    private javax.swing.JMenuItem jMIManaReceipt;
    private javax.swing.JMenuItem jMIManaSeChart;
    private javax.swing.JMenuItem jMIManaUser;
    private javax.swing.JMenuItem jMIOrder;
    private javax.swing.JMenuItem jMIWindown;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanelViews;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables

}
