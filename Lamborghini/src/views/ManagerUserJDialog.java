package views;

import dao.UserDao;
import helper.DataValidator;
import helper.DateHelper;
import helper.MessageDialogHelper;
import helper.ShareHelper;
import java.awt.Color;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Users;
import org.apache.commons.codec.digest.DigestUtils;
import swing.ScrollBarCustom;

/**
 *
 * @author ACER
 */
public class ManagerUserJDialog extends javax.swing.JDialog {

    private JFileChooser fileChooser = new JFileChooser();
    private UserDao dao = new UserDao();
    private List<Users> list = dao.select_All();
    private int pos = -1;

    /**
     * Creates new form ManagerUserJDialog
     *
     * @param parent
     * @param modal
     */
    public ManagerUserJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setIconImage(ShareHelper.APP_ICON);
        tableUser.fixTable(jScrollPane3);
        fixTable(jScrollPane2);
        fillTable();
        txtEmail.disable();
        if (!ShareHelper.USER.getPositionBoolean()) {
            btnInsert.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
            rbtMana.setEnabled(false);
            rbtEmloyee.setEnabled(false);
        }
    }

    //Custom jScrollPane
    private void fixTable(JScrollPane scroll) {
        scroll.setVerticalScrollBar(new ScrollBarCustom());
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel);
        scroll.getViewport().setBackground(new Color(255, 255, 255));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 2));
    }

    //Mở hộp thoại-chọn file
    private void selectImage() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { //nếu người dùng đã chọn đc file
            File file = fileChooser.getSelectedFile();    //lấy file người dùng chọn
            if (ShareHelper.saveLogo(file)) {  //sao chép file đã chọn thư mục logos
                // Hiển thị hình lên form
                jLbAvata.setIcon(ShareHelper.readLogo(file.getName(), 180, 133)); //file.getName(); lấy tên của file
                //ImageIcon readLogo(String tenFile) đọc file trong thư mục logos theo tên file trả về ImageIcon
                //void setIcon(ImageIcon icon) set Icon cho lbl
                jLbAvata.setToolTipText(file.getName());
            }
        }
    }

    //setModel-Xuất dữ liệu
    private void setModel(Users entity) {
        try {
            txtUserName.setText(entity.getUserNameString());
            txtBirthDay.setText(DateHelper.toString(entity.getBirthDayDate(), "yyyy-MM-dd"));
            txtEmail.setText(entity.getEmailString());
            txtEmail.setToolTipText(entity.getPassWordString());
            txtAddress.setText(entity.getAddressString());
            boolean sex = entity.getSexBoolean();
            if (sex) {
                rbtMen.setSelected(true);
            } else {
                rbtWomen.setSelected(true);
            }
            boolean position = dao.select_By_PK(txtEmail.getText().trim()).getPositionBoolean();
            if (position) {
                rbtMana.setSelected(true);
            } else {
                rbtEmloyee.setSelected(true);
            }
            txtNote.setText(entity.getNoteString());
            if (entity.getAvataString() != null) {
                jLbAvata.setIcon(ShareHelper.readLogo(entity.getAvataString(), 180, 133));
                /*
            ImageIcon readLogo(String tenFile) đọc file trong thư mục logos theo tên file trả về ImageIcon
            void setIcon(ImageIcon icon) set Icon cho lbl
                 */
            } else {
                jLbAvata.setIcon(ShareHelper.readLogo("noImage.png", 180, 133));
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi hiễn thị dữ liệu : " + e.toString(), "Lỗi");
            System.out.println("*ManagerUserJDialog(setModel) - " + e.toString());
        }
    }

    //getModel-Lấy dữ liệu
    private Users getModel() {
        try {
            Users entity = new Users();
            entity.setUserNameString(txtUserName.getText().trim());
            entity.setBirthDayDate(DateHelper.toDate(txtBirthDay.getText(), "yyyy-MM-dd"));
            entity.setEmailString(txtEmail.getText().trim());
            entity.setAddressString(txtAddress.getText().trim());
            entity.setSexBoolean(!rbtWomen.isSelected());
            entity.setPositionBoolean(!rbtEmloyee.isSelected());
            entity.setNoteString(txtNote.getText().trim());
            entity.setAvataString(jLbAvata.getToolTipText());
            return entity;
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi lấy dữ liệu : " + e.toString(), "Lỗi");
            System.out.println("*ManagerUserJDialog(getModel) - " + e.toString());
            return null;
        }
    }

    //load all data
    private void fillTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) tableUser.getModel();
            model.setRowCount(0);
            int i = 1;
            for (Users entity : dao.select_All()) {
                String sexs = (entity.getSexBoolean() == true) ? "Nam" : "Nữ";
                String position = (entity.getPositionBoolean() == true) ? "Trưởng phòng" : "Nhân viên";
                model.addRow(new Object[]{
                    i,
                    entity.getUserNameString(),
                    sexs,
                    DateHelper.toString(entity.getBirthDayDate(), "yyyy-MM-dd"),
                    entity.getEmailString(),
                    entity.getAddressString(),
                    position});
                i++;
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi dữ liệu bảng : " + e.toString(), "Lỗi");
            System.out.println("*ManagerUserJDialog(fillTable) - " + e.toString());
        }
    }

    //clickTable
    private void clickTable() {
        try {
            int row = tableUser.getSelectedRow();
            if (row >= 0) {
                String email = (String) tableUser.getValueAt(row, 4);
                Users entity = dao.select_By_PK(email);
                setModel(entity);
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi clickTable : " + e.toString(), "Lỗi");
            System.out.println("*ManagerUserJDialog(clickTable) - " + e.toString());
        }
    }

    //Phương thức checkSpam
    private StringBuilder checkSpam(String method) {
        StringBuilder sb = new StringBuilder();
        if (jLbAvata.getToolTipText() == null) {
            sb.append("Hình ảnh không được để trống !").append("\n");
        }
        if (!method.equals("insert")) {
            DataValidator.validateEmail(txtEmail, sb, "Email trống hoặc không đúng định dạng !");
        }
        DataValidator.validateCheckName(txtUserName, sb, "Họ và tên trống hoặc nhỏ hơn 3 ký tự (tối đa 50 ký tự)");
//        DataValidator.validateEmptyDate(txtBirthDay, sb, "Ngày sinh trống hoặc không đúng định dạng (yyyy-MM-dd)");
        DataValidator.validateEmpty(txtAddress, sb, "Địa chỉ không được để trống !");
        DataValidator.validateEmpty(txtNote, sb, "Ghi chú không được để trống !");
        return sb;
    }

    //Phương thức reset
    private void reset() {
        JTextField[] jTextField = {txtUserName, txtBirthDay, txtEmail, txtAddress};
        for (JTextField jTextField1 : jTextField) {
            DataValidator.error(jTextField1, true);
        }
        txtUserName.setText("HỌ VÀ TÊN");
        txtBirthDay.setText("NGÀY SINH");
        txtEmail.setText("EMAIL");
        txtAddress.setText("ĐỊA CHỈ");
        txtNote.setText("GHI CHÚ");
        txtNote.setBackground(new Color(255, 255, 255));
        txtNote.setForeground(new Color(0, 0, 0));
        jLbAvata.setIcon(ShareHelper.readLogo("noImage.png", 180, 133));
    }

    //Phương thức update
    private void update() {
        StringBuilder sb = checkSpam("");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            return;
        }
        try {
            if (dao.select_By_PK(txtEmail.getText()) == null) {
                MessageDialogHelper.showMessageDialog(this, "Nhân viên này không tồn tại !", "Thông báo tin nhắn");
            } else {
                try {
                    Users entity = getModel();
                    entity.setPassWordString(txtEmail.getToolTipText());
                    dao.update(entity);
                    MessageDialogHelper.showMessageDialog(this, "Cập nhập nhân viên thành công !", "Thông báo tin nhắn");
                    reset();
                    fillTable();
                } catch (Exception e) {
                    MessageDialogHelper.showErrorDialog(this, "Lỗi update : " + e.toString(), "Lỗi");
                    System.out.println("*ManagerUserJDialog(update) - " + e.toString());
                }
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi update : " + e.toString(), "Lỗi");
            System.out.println("*ManagerUserJDialog(update) - " + e.toString());
        }
    }

    //Phương thức delete
    private void delete() {
        try {
            if (MessageDialogHelper.showComfirmDialog(this, "Bạn có muốn xoá nhân viên này không ?", "Cảnh báo xóa nhân viên")
                    == JOptionPane.NO_OPTION) {
            } else {
                Users entity = dao.select_By_PK(txtEmail.getText());
                if (entity == null) {
                    MessageDialogHelper.showMessageDialog(this, "Không tìm thấy nhân viên !", "Thông báo tin nhắn");
                } else {
                    if (ShareHelper.USER.getEmailString().equals(txtEmail.getText().trim())) {
                        MessageDialogHelper.showMessageDialog(this, "Bạn không thể xóa tài khoản đang đăng nhập !", "Thông báo tin nhắn");
                    } else {
                        if (MessageDialogHelper.showComfirmDialog(this, "Nếu bạn xóa nhân viên, tài khoản đăng nhập cũng sẽ xóa ?", "Cảnh báo xóa nhân viên")
                                == JOptionPane.NO_OPTION) {
                        } else {
                            try {
                                dao.delete(txtEmail.getText());
                                MessageDialogHelper.showMessageDialog(this, "Xoá dữ liệu nhân viên thành công !", "Thông báo tin nhắn");
                                reset();
                                fillTable();
                            } catch (Exception e) {
                                MessageDialogHelper.showErrorDialog(this, "Lỗi delete : " + e.toString(), "Lỗi");
                                System.out.println("*ManagerUserJDialog(delete) - " + e.toString());
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi delete : " + e.toString(), "Lỗi");
            System.out.println("*ManagerUserJDialog(delete) - " + e.toString());
        }
    }

    //Phương thức insert
    private void insert() {
        StringBuilder sb = checkSpam("insert");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            return;
        }
        try {
            String email = MessageDialogHelper.showInputDialog(this, "Vui lòng nhập email mới !", "Nhập liệu");
            StringBuilder sb1 = new StringBuilder();
            DataValidator.validateEmail(email, sb1, "Email trống hoặc không đúng định dạng !");
            if (sb1.length() > 0) {
                MessageDialogHelper.showErrorDialog(this, sb1.toString(), "Lỗi nhập liệu");
            } else {
                if (dao.select_By_PK(email) != null) {
                    MessageDialogHelper.showMessageDialog(this, "Email này đã tồn tại !", "Thông báo tin nhắn");
                } else {
                    Users entity = getModel();
                    entity.setEmailString(email);
                    entity.setPassWordString(DigestUtils.md5Hex("12345678910"));
                    entity.setPositionBoolean(false);
                    try {
                        dao.insert(entity);
                        MessageDialogHelper.showMessageDialog(this, "Lưu nhân viên thành công !", "Thông báo tin nhắn");
                        MessageDialogHelper.showMessageDialog(this, "Email đăng nhập: " + email + "-" + "Mật khẩu mặc định: 12345678910", "Thông báo tin nhắn");
                        reset();
                        fillTable();
                    } catch (Exception e) {
                        MessageDialogHelper.showErrorDialog(this, "Lỗi insert : " + e.toString(), "Lỗi");
                        System.out.println("*ManagerUserJDialog(insert) - " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi insert : " + e.toString(), "Lỗi");
            System.out.println("*ManagerUserJDialog(insert) - " + e.toString());
        }
    }

    //Hiễn thị theo vị trí
    private void selectPos(int pos) {
        Users entity = dao.getEnityByPossition(String.valueOf(pos));
        setModel(entity);
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        dateChooser = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnFirst = new swing.Button();
        btnPrevious = new swing.Button();
        btnNext = new swing.Button();
        btnLast = new swing.Button();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableUser = new swing.TableDark();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtUserName = new swing.MyTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtBirthDay = new swing.MyTextField();
        jPanel7 = new javax.swing.JPanel();
        jLbAvata = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new swing.MyTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtAddress = new swing.MyTextField();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        rbtMen = new swing.RadioButtonCustom();
        rbtWomen = new swing.RadioButtonCustom();
        jPanel17 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        rbtMana = new swing.RadioButtonCustom();
        rbtEmloyee = new swing.RadioButtonCustom();
        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        btnInsert = new swing.Button();
        btnUpdate = new swing.Button();
        btnDelete = new swing.Button();
        btnReset = new swing.Button();

        dateChooser.setDateFormat("yyyy-MM-dd\n");
        dateChooser.setTextRefernce(txtBirthDay);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ NHÂN VIÊN");
        setMaximumSize(new java.awt.Dimension(1800, 950));
        setMinimumSize(new java.awt.Dimension(1800, 950));

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

        tableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Họ và tên", "Giới tính", "Ngày sinh", "Email", "Địa chỉ", "Chức vụ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableUser.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        tableUser.setRowHeight(50);
        tableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUserMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableUser);
        if (tableUser.getColumnModel().getColumnCount() > 0) {
            tableUser.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel1.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setMaximumSize(new java.awt.Dimension(900, 950));
        jPanel2.setMinimumSize(new java.awt.Dimension(900, 950));
        jPanel2.setPreferredSize(new java.awt.Dimension(900, 950));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setMaximumSize(new java.awt.Dimension(900, 850));
        jPanel3.setMinimumSize(new java.awt.Dimension(900, 850));
        jPanel3.setPreferredSize(new java.awt.Dimension(900, 850));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        jPanel5.setMaximumSize(new java.awt.Dimension(860, 250));
        jPanel5.setMinimumSize(new java.awt.Dimension(860, 250));
        jPanel5.setPreferredSize(new java.awt.Dimension(860, 250));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jPanel6.setMaximumSize(new java.awt.Dimension(610, 250));
        jPanel6.setMinimumSize(new java.awt.Dimension(610, 250));
        jPanel6.setPreferredSize(new java.awt.Dimension(610, 250));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 15));

        jPanel8.setMaximumSize(new java.awt.Dimension(610, 100));
        jPanel8.setMinimumSize(new java.awt.Dimension(610, 100));
        jPanel8.setPreferredSize(new java.awt.Dimension(610, 100));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_female_profile_30px.png"))); // NOI18N
        jLabel2.setText("Họ và tên");
        jLabel2.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel8.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        txtUserName.setBackground(new java.awt.Color(255, 255, 255));
        txtUserName.setForeground(new java.awt.Color(0, 0, 0));
        txtUserName.setText("HỌ VÀ TÊN");
        txtUserName.setCaretColor(new java.awt.Color(0, 0, 0));
        txtUserName.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtUserName.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel8.add(txtUserName, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel8);

        jPanel9.setMaximumSize(new java.awt.Dimension(610, 100));
        jPanel9.setMinimumSize(new java.awt.Dimension(610, 100));
        jPanel9.setPreferredSize(new java.awt.Dimension(610, 100));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_birthday_30px.png"))); // NOI18N
        jLabel3.setText("Ngày sinh");
        jLabel3.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel9.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        txtBirthDay.setBackground(new java.awt.Color(255, 255, 255));
        txtBirthDay.setForeground(new java.awt.Color(0, 0, 0));
        txtBirthDay.setCaretColor(new java.awt.Color(0, 0, 0));
        txtBirthDay.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtBirthDay.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel9.add(txtBirthDay, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel9);

        jPanel5.add(jPanel6);

        jPanel7.setMaximumSize(new java.awt.Dimension(250, 250));
        jPanel7.setMinimumSize(new java.awt.Dimension(250, 250));
        jPanel7.setPreferredSize(new java.awt.Dimension(250, 250));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLbAvata.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbAvata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/mau-anh-the-dep-sat-net.png"))); // NOI18N
        jLbAvata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbAvataMouseClicked(evt);
            }
        });
        jPanel7.add(jLbAvata, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel7);

        jPanel3.add(jPanel5);

        jPanel11.setMaximumSize(new java.awt.Dimension(860, 100));
        jPanel11.setMinimumSize(new java.awt.Dimension(860, 100));
        jPanel11.setPreferredSize(new java.awt.Dimension(860, 100));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_at_sign_30px.png"))); // NOI18N
        jLabel4.setText("Email");
        jLabel4.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel11.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setForeground(new java.awt.Color(0, 0, 0));
        txtEmail.setText("EMAIL");
        txtEmail.setCaretColor(new java.awt.Color(0, 0, 0));
        txtEmail.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtEmail.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel11.add(txtEmail, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel11);

        jPanel12.setMaximumSize(new java.awt.Dimension(860, 100));
        jPanel12.setMinimumSize(new java.awt.Dimension(860, 100));
        jPanel12.setPreferredSize(new java.awt.Dimension(860, 100));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_home_address_30px.png"))); // NOI18N
        jLabel5.setText("Địa chỉ");
        jLabel5.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel12.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        txtAddress.setBackground(new java.awt.Color(255, 255, 255));
        txtAddress.setForeground(new java.awt.Color(0, 0, 0));
        txtAddress.setText("ĐỊA CHỈ");
        txtAddress.setCaretColor(new java.awt.Color(0, 0, 0));
        txtAddress.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtAddress.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel12.add(txtAddress, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel12);

        jPanel13.setMaximumSize(new java.awt.Dimension(860, 100));
        jPanel13.setMinimumSize(new java.awt.Dimension(860, 100));
        jPanel13.setPreferredSize(new java.awt.Dimension(860, 100));
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jPanel14.setMaximumSize(new java.awt.Dimension(430, 100));
        jPanel14.setMinimumSize(new java.awt.Dimension(430, 100));
        jPanel14.setPreferredSize(new java.awt.Dimension(430, 100));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_venus_symbol_30px.png"))); // NOI18N
        jLabel6.setText("Giới tính");
        jLabel6.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel14.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 10));

        buttonGroup1.add(rbtMen);
        rbtMen.setForeground(new java.awt.Color(0, 0, 0));
        rbtMen.setSelected(true);
        rbtMen.setText("Nam");
        rbtMen.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jPanel16.add(rbtMen);

        rbtWomen.setBackground(new java.awt.Color(153, 153, 0));
        buttonGroup1.add(rbtWomen);
        rbtWomen.setForeground(new java.awt.Color(0, 0, 0));
        rbtWomen.setText("Nữ");
        rbtWomen.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jPanel16.add(rbtWomen);

        jPanel14.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel14);

        jPanel17.setMaximumSize(new java.awt.Dimension(430, 100));
        jPanel17.setMinimumSize(new java.awt.Dimension(430, 100));
        jPanel17.setPreferredSize(new java.awt.Dimension(430, 100));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_coworking_30px.png"))); // NOI18N
        jLabel7.setText("Chức vụ");
        jLabel7.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel17.add(jLabel7, java.awt.BorderLayout.PAGE_START);

        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 10));

        buttonGroup2.add(rbtMana);
        rbtMana.setForeground(new java.awt.Color(0, 0, 0));
        rbtMana.setSelected(true);
        rbtMana.setText("Trưởng phòng");
        rbtMana.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jPanel18.add(rbtMana);

        rbtEmloyee.setBackground(new java.awt.Color(153, 153, 0));
        buttonGroup2.add(rbtEmloyee);
        rbtEmloyee.setForeground(new java.awt.Color(0, 0, 0));
        rbtEmloyee.setText("Nhân viên");
        rbtEmloyee.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jPanel18.add(rbtEmloyee);

        jPanel17.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel17);

        jPanel3.add(jPanel13);

        jPanel15.setMaximumSize(new java.awt.Dimension(860, 150));
        jPanel15.setMinimumSize(new java.awt.Dimension(860, 150));
        jPanel15.setPreferredSize(new java.awt.Dimension(860, 150));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_clipboard_30px.png"))); // NOI18N
        jLabel8.setText("Ghi chú");
        jLabel8.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel15.add(jLabel8, java.awt.BorderLayout.PAGE_START);

        txtNote.setColumns(20);
        txtNote.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtNote.setRows(5);
        txtNote.setText("GHI CHÚ");
        jScrollPane2.setViewportView(txtNote);

        jPanel15.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel15);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setMaximumSize(new java.awt.Dimension(900, 100));
        jPanel4.setMinimumSize(new java.awt.Dimension(900, 100));
        jPanel4.setPreferredSize(new java.awt.Dimension(900, 100));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        btnInsert.setForeground(new java.awt.Color(0, 0, 0));
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_insert_button_50px.png"))); // NOI18N
        btnInsert.setText("Thêm");
        btnInsert.setEffectColor(new java.awt.Color(0, 102, 204));
        btnInsert.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        btnInsert.setPreferredSize(new java.awt.Dimension(150, 60));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        jPanel4.add(btnInsert);

        btnUpdate.setForeground(new java.awt.Color(0, 0, 0));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_50px.png"))); // NOI18N
        btnUpdate.setText("Cập nhật");
        btnUpdate.setEffectColor(new java.awt.Color(0, 102, 204));
        btnUpdate.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        btnUpdate.setPreferredSize(new java.awt.Dimension(180, 60));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel4.add(btnUpdate);

        btnDelete.setForeground(new java.awt.Color(0, 0, 0));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_50px.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.setEffectColor(new java.awt.Color(0, 102, 204));
        btnDelete.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        btnDelete.setPreferredSize(new java.awt.Dimension(150, 60));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel4.add(btnDelete);

        btnReset.setForeground(new java.awt.Color(0, 0, 0));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_sync_50px.png"))); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.setEffectColor(new java.awt.Color(51, 153, 0));
        btnReset.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        btnReset.setPreferredSize(new java.awt.Dimension(180, 60));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel4.add(btnReset);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);

        setSize(new java.awt.Dimension(1816, 989));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLbAvataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbAvataMouseClicked
        selectImage();
    }//GEN-LAST:event_jLbAvataMouseClicked

    private void tableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUserMouseClicked
        clickTable();
    }//GEN-LAST:event_tableUserMouseClicked

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

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

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insert();
    }//GEN-LAST:event_btnInsertActionPerformed

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
            java.util.logging.Logger.getLogger(ManagerUserJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerUserJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerUserJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerUserJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ManagerUserJDialog dialog = new ManagerUserJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private com.raven.datechooser.DateChooser dateChooser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLbAvata;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private swing.RadioButtonCustom rbtEmloyee;
    private swing.RadioButtonCustom rbtMana;
    private swing.RadioButtonCustom rbtMen;
    private swing.RadioButtonCustom rbtWomen;
    private swing.TableDark tableUser;
    private swing.MyTextField txtAddress;
    private swing.MyTextField txtBirthDay;
    private swing.MyTextField txtEmail;
    private javax.swing.JTextArea txtNote;
    private swing.MyTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
