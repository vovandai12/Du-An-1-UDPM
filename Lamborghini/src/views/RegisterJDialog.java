package views;

import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import dao.UserDao;
import helper.DataValidator;
import helper.DateHelper;
import helper.MessageDialogHelper;
import java.awt.Color;
import javax.swing.JOptionPane;
import main.Main;
import model.Users;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author ACER
 */
public class RegisterJDialog extends javax.swing.JDialog {

    /**
     * Creates new form RegisterJDialog
     *
     * @param parent
     * @param modal
     */
    public RegisterJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        dateChooser.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                System.out.println(date.getYear() + "-" + date.getMonth() + "-" + date.getDay());
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateChooser.hidePopup();
                }
            }
        });
    }

    //Exit
    private void exit() {
        if (MessageDialogHelper.showComfirmDialog(this, "Bạn có muốn thoát chương trình ?",
                "Thoát chương trình") == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    //Phương thức lấy dữ liệu user từ form
    private Users getModel() {
        try {
            Users entity = new Users();
            entity.setEmailString(txtEmail.getText().trim());
            entity.setUserNameString(txtUserName.getText().trim());
            entity.setPassWordString(DigestUtils.md5Hex(new String(txtPass.getPassword())));
            entity.setPositionBoolean(false);
            boolean sex = false;
            if (rbtMen.isSelected()) {
                sex = true;
            }
            entity.setSexBoolean(sex);
            entity.setBirthDayDate(DateHelper.toDate(txtBirthDay.getText(), "yyyy-MM-dd"));
            entity.setAddressString(txtAddress.getText().trim());
            return entity;
        } catch (NumberFormatException e) {
            System.out.println("*RegisterJDialog(getModelUser) - " + e.toString());
            return null;
        }
    }

    //Phương thức checkSpam
    private StringBuilder checkSpam() {
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmail(txtEmail, sb, "Email trống hoặc không đúng định dạng !");
        DataValidator.validateEmpty(txtPass, sb, "Mật khẩu trống hoặc không đủ 10 ký tự !");
        DataValidator.validateCheckName(txtUserName, sb, "Họ và tên trống hoặc nhỏ hơn 3 ký tự (tối đa 50 ký tự)");
        DataValidator.validateEmpty(txtAddress, sb, "Địa chỉ không được để trống !");
        return sb;
    }

    //Phương thức register
    private void register() {
        StringBuilder sb = checkSpam();
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            return;
        }
        try {
            UserDao dao = new UserDao();
            Users entity = dao.select_By_PK(txtEmail.getText().trim());
            if (entity != null) {
                MessageDialogHelper.showErrorDialog(this, "Email đã tồn tại !", "Lỗi email");
                DataValidator.error(txtEmail, false);
            } else {
                Users model = getModel();
                try {
                    dao.insert(model);
                    MessageDialogHelper.showMessageDialog(this, "Đã đăng ký thành công !", "Thông báo tin nhắn");
                    dispose();
                    Main main = new Main();
                    main.setVisible(true);
                } catch (Exception e) {
                    MessageDialogHelper.showErrorDialog(this, "Lỗi đăng ký : " + e.toString(), "Lỗi");
                    System.out.println("*RegisterJDialog(register) - " + e.toString());
                }
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi đăng ký : " + e.toString(), "Lỗi");
            System.out.println("*RegisterJDialog(register) - " + e.toString());
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        dateChooser = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtEmail = new swing.MyTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtPass = new swing.MyPasswordField();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtUserName = new swing.MyTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtBirthDay = new swing.MyTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        rbtMen = new swing.RadioButtonCustom();
        rbtWomen = new swing.RadioButtonCustom();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtAddress = new swing.MyTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnRegister = new swing.Button();
        btnExit = new swing.Button();

        dateChooser.setDateFormat("yyyy-MM-dd");
        dateChooser.setTextRefernce(txtBirthDay);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setMinimumSize(new java.awt.Dimension(1200, 800));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1200, 800));

        jPanel1.setMaximumSize(new java.awt.Dimension(300, 800));
        jPanel1.setMinimumSize(new java.awt.Dimension(300, 800));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 800));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel13.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("DialogInput", 2, 24)); // NOI18N
        jLabel9.setText("<html> <p>1.Form nhập không được để trống </p> <br> <p>2.Email phải đúng định dạng (Local-Part@Domail Name)</p> <br> <p>3.Mật khẩu phải từ 10 ký tự trở lên</p> <br> <p>4.Họ và tên phải lớn hơn 3 ký tự. Không chứa ký tự đặt biệt.Tối đa 50 ký tự</p> <br> <p>5.Ngày sinh phải đúng định dạng (yyyy-MM-dd)</p> <html>");
        jPanel13.add(jLabel9, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Chú ý");
        jLabel8.setPreferredSize(new java.awt.Dimension(384, 100));
        jPanel14.add(jLabel8, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setForeground(new java.awt.Color(51, 51, 55));
        jPanel2.setMaximumSize(new java.awt.Dimension(800, 800));
        jPanel2.setMinimumSize(new java.awt.Dimension(800, 800));
        jPanel2.setPreferredSize(new java.awt.Dimension(800, 800));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setMaximumSize(new java.awt.Dimension(800, 700));
        jPanel3.setMinimumSize(new java.awt.Dimension(800, 700));
        jPanel3.setPreferredSize(new java.awt.Dimension(800, 700));

        jPanel6.setMaximumSize(new java.awt.Dimension(600, 100));
        jPanel6.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel6.setPreferredSize(new java.awt.Dimension(600, 100));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_at_sign_30px.png"))); // NOI18N
        jLabel2.setText("Email");
        jLabel2.setMaximumSize(new java.awt.Dimension(600, 40));
        jLabel2.setMinimumSize(new java.awt.Dimension(600, 40));
        jLabel2.setPreferredSize(new java.awt.Dimension(600, 40));
        jPanel6.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setForeground(new java.awt.Color(0, 0, 0));
        txtEmail.setText("EMAIL");
        txtEmail.setCaretColor(new java.awt.Color(0, 0, 0));
        txtEmail.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtEmail.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel6.add(txtEmail, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel6);

        jPanel7.setMaximumSize(new java.awt.Dimension(600, 100));
        jPanel7.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel7.setPreferredSize(new java.awt.Dimension(600, 100));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_skip_30px.png"))); // NOI18N
        jLabel3.setText("Mật khẩu");
        jLabel3.setMaximumSize(new java.awt.Dimension(600, 40));
        jLabel3.setMinimumSize(new java.awt.Dimension(600, 40));
        jLabel3.setPreferredSize(new java.awt.Dimension(600, 40));
        jPanel7.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        txtPass.setBackground(new java.awt.Color(255, 255, 255));
        txtPass.setForeground(new java.awt.Color(0, 0, 0));
        txtPass.setText("myPasswordField1");
        txtPass.setCaretColor(new java.awt.Color(0, 0, 0));
        txtPass.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtPass.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel7.add(txtPass, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel7);

        jPanel8.setMaximumSize(new java.awt.Dimension(600, 100));
        jPanel8.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel8.setPreferredSize(new java.awt.Dimension(600, 100));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_female_profile_30px.png"))); // NOI18N
        jLabel4.setText("Họ và tên");
        jLabel4.setMaximumSize(new java.awt.Dimension(600, 40));
        jLabel4.setMinimumSize(new java.awt.Dimension(600, 40));
        jLabel4.setPreferredSize(new java.awt.Dimension(600, 40));
        jPanel8.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        txtUserName.setBackground(new java.awt.Color(255, 255, 255));
        txtUserName.setForeground(new java.awt.Color(0, 0, 0));
        txtUserName.setText("HỌ VÀ TÊN");
        txtUserName.setCaretColor(new java.awt.Color(0, 0, 0));
        txtUserName.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtUserName.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel8.add(txtUserName, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel8);

        jPanel9.setMaximumSize(new java.awt.Dimension(600, 100));
        jPanel9.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel9.setPreferredSize(new java.awt.Dimension(600, 100));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_birthday_30px.png"))); // NOI18N
        jLabel5.setText("Ngày sinh");
        jLabel5.setMaximumSize(new java.awt.Dimension(600, 40));
        jLabel5.setMinimumSize(new java.awt.Dimension(600, 40));
        jLabel5.setPreferredSize(new java.awt.Dimension(600, 40));
        jPanel9.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        txtBirthDay.setBackground(new java.awt.Color(255, 255, 255));
        txtBirthDay.setForeground(new java.awt.Color(0, 0, 0));
        txtBirthDay.setCaretColor(new java.awt.Color(0, 0, 0));
        txtBirthDay.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtBirthDay.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel9.add(txtBirthDay, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel9);

        jPanel10.setMaximumSize(new java.awt.Dimension(600, 100));
        jPanel10.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel10.setPreferredSize(new java.awt.Dimension(600, 100));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_venus_symbol_30px.png"))); // NOI18N
        jLabel6.setText("Giới tính");
        jLabel6.setMaximumSize(new java.awt.Dimension(600, 40));
        jLabel6.setMinimumSize(new java.awt.Dimension(600, 40));
        jLabel6.setPreferredSize(new java.awt.Dimension(600, 40));
        jPanel10.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 10));

        rbtMen.setBackground(new java.awt.Color(153, 153, 0));
        buttonGroup1.add(rbtMen);
        rbtMen.setForeground(new java.awt.Color(0, 0, 0));
        rbtMen.setSelected(true);
        rbtMen.setText("Nam");
        rbtMen.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jPanel12.add(rbtMen);

        buttonGroup1.add(rbtWomen);
        rbtWomen.setForeground(new java.awt.Color(0, 0, 0));
        rbtWomen.setText("Nữ");
        rbtWomen.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jPanel12.add(rbtWomen);

        jPanel10.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel10);

        jPanel11.setMaximumSize(new java.awt.Dimension(600, 100));
        jPanel11.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel11.setPreferredSize(new java.awt.Dimension(600, 100));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_home_address_30px.png"))); // NOI18N
        jLabel7.setText("Địa chỉ");
        jLabel7.setMaximumSize(new java.awt.Dimension(600, 40));
        jLabel7.setMinimumSize(new java.awt.Dimension(600, 40));
        jLabel7.setPreferredSize(new java.awt.Dimension(600, 40));
        jPanel11.add(jLabel7, java.awt.BorderLayout.PAGE_START);

        txtAddress.setBackground(new java.awt.Color(255, 255, 255));
        txtAddress.setForeground(new java.awt.Color(0, 0, 0));
        txtAddress.setText("ĐỊA CHỈ");
        txtAddress.setCaretColor(new java.awt.Color(0, 0, 0));
        txtAddress.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtAddress.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel11.add(txtAddress, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel11);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setMaximumSize(new java.awt.Dimension(800, 100));
        jPanel4.setMinimumSize(new java.awt.Dimension(800, 100));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ĐĂNG KÝ");
        jLabel1.setMaximumSize(new java.awt.Dimension(800, 100));
        jLabel1.setMinimumSize(new java.awt.Dimension(800, 100));
        jLabel1.setPreferredSize(new java.awt.Dimension(800, 100));
        jPanel4.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setMaximumSize(new java.awt.Dimension(800, 80));
        jPanel5.setMinimumSize(new java.awt.Dimension(800, 80));
        jPanel5.setPreferredSize(new java.awt.Dimension(800, 80));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 70, 15));

        btnRegister.setForeground(new java.awt.Color(0, 0, 0));
        btnRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_signin_45px.png"))); // NOI18N
        btnRegister.setText("Đăng ký");
        btnRegister.setEffectColor(new java.awt.Color(0, 102, 204));
        btnRegister.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        btnRegister.setPreferredSize(new java.awt.Dimension(150, 50));
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });
        jPanel5.add(btnRegister);

        btnExit.setForeground(new java.awt.Color(0, 0, 0));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_cancel_45px.png"))); // NOI18N
        btnExit.setText("Thoát");
        btnExit.setEffectColor(new java.awt.Color(0, 102, 204));
        btnExit.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        btnExit.setPreferredSize(new java.awt.Dimension(150, 50));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        jPanel5.add(btnExit);

        jPanel2.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);

        setSize(new java.awt.Dimension(1184, 817));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        exit();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        register();
    }//GEN-LAST:event_btnRegisterActionPerformed

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
            java.util.logging.Logger.getLogger(RegisterJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegisterJDialog dialog = new RegisterJDialog(new javax.swing.JFrame(), true);
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
    private swing.Button btnExit;
    private swing.Button btnRegister;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.raven.datechooser.DateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private swing.RadioButtonCustom rbtMen;
    private swing.RadioButtonCustom rbtWomen;
    private swing.MyTextField txtAddress;
    private swing.MyTextField txtBirthDay;
    private swing.MyTextField txtEmail;
    private swing.MyPasswordField txtPass;
    private swing.MyTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}