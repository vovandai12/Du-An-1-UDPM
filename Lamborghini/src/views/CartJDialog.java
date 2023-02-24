package views;

import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import dao.CustomerDao;
import dao.ProductDao;
import dao.ReceiptDao;
import dao.ReceiptDetailDao;
import helper.DataValidator;
import helper.DateHelper;
import helper.MessageDialogHelper;
import helper.ShareHelper;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Customers;
import model.Products;
import model.ReceiptDetails;
import model.Receipts;
import swing.ScrollBarCustom;

/**
 *
 * @author ACER
 */
public class CartJDialog extends javax.swing.JDialog {

    private ProductDao dao = new ProductDao();
    private ReceiptDao daoReceipt = new ReceiptDao();
    private ReceiptDetailDao daoReceiptDetail = new ReceiptDetailDao();
    private CustomerDao daoCustomer = new CustomerDao();
    private List<Products> listCart = new ArrayList<>();

    /**
     * Creates new form CartJDialog
     */
    public CartJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        customJtable();
        fixTable(jScrollPane1);
        fixTable(jScrollPane2);
        fillTable();
        fillTableCart();
        reset();
        dateChooser.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
//                System.out.println(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateChooser.hidePopup();
                }
            }
        });
        setEnabled();
    }

    private void setEnabled() {
        txtUserName.setEnabled(false);
        txtAddress.setEnabled(false);
        txtEmail.setEnabled(false);
        txtBirthDay.setEnabled(false);
    }

    private void setEnableds() {
        txtUserName.setEnabled(true);
        txtAddress.setEnabled(true);
        txtEmail.setEnabled(true);
        txtBirthDay.setEnabled(true);
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

    private void customJtable() {
        tableList.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int row, int column) {
                super.getTableCellRendererComponent(jtable, o, bln, bln1, row, column);
                if (column != 4) {
                    setHorizontalAlignment(JLabel.LEFT);
                } else {
                    setHorizontalAlignment(JLabel.CENTER);
                }
                return this;
            }
        });
        tableList.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
        //  set image view for column 2
        tableList.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int i, int i1) {
                Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                if (o == null) {
                    //  No Image
                    JLabel label = new JLabel("No Image");
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setOpaque(selected);
                    label.setBackground(com.getBackground());
                    return label;
                } else {
                    if (o instanceof Icon) {
                        //  Has Image
                        Icon image = (ImageIcon) o;
                        JLabel label = new JLabel(image);
                        label.setHorizontalAlignment(JLabel.CENTER);
                        label.setOpaque(selected);
                        label.setBackground(com.getBackground());
                        return label;
                    } else {
                        //  Image updating
                        JLabel label = new JLabel("Updating ...");
                        label.setHorizontalAlignment(JLabel.CENTER);
                        label.setOpaque(selected);
                        label.setBackground(com.getBackground());
                        return label;
                    }
                }
            }
        });
    }

    //load all data
    private void fillTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) tableList.getModel();
            model.setRowCount(0);
            for (Products entity : dao.select_All()) {
                Long vnd = Long.parseLong(entity.getPriceString());
                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                String str = currencyVN.format(vnd);
                model.addRow(new Object[]{
                    entity.getCodeProductString(),
                    entity.getNameProductString(),
                    ShareHelper.readLogo(entity.getAvataString(), 80, 150),
                    str});
            }
            model.fireTableDataChanged();
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi dữ liệu bảng : " + e.toString(), "Lỗi");
            System.out.println("*CartJDialog(fillTable) - " + e.toString());
        }
    }

    private void fillTableCart() {
        try {
            DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
            model.setRowCount(0);
            for (Products entity : listCart) {
                int amount = 1;
                Long vnd = Long.parseLong(entity.getPriceString()) * amount;
                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                String str = currencyVN.format(vnd);
                model.addRow(new Object[]{
                    entity.getCodeProductString(),
                    entity.getNameProductString(),
                    amount,
                    str});
            }
            model.fireTableDataChanged();
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi dữ liệu bảng : " + e.toString(), "Lỗi");
            System.out.println("*CartJDialog(fillTableCart) - " + e.toString());
        }
    }

    //clickTableMenu
    private void clickTableMenu() {
        try {
            int row = tableList.getSelectedRow();
            if (row >= 0) {
                String code = (String) tableList.getValueAt(row, 0);
                Products entity = dao.select_By_PK(code);
                listCart.add(entity);
                MessageDialogHelper.showMessageDialog(this, "Đã thêm sản phẩm vào giỏ hàng !", "Thông báo tin nhắn");
                fillTableCart();
                updateCartInfo();
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi clickTable : " + e.toString(), "Lỗi");
            System.out.println("*CartJDialog(clickTable) - " + e.toString());
        }
    }

    //Phương thức checkSpam
    private StringBuilder checkSpam() {
        StringBuilder sb = new StringBuilder();
        DataValidator.validateCheckName(txtUserName, sb, "Họ và tên trống hoặc nhỏ hơn 3 ký tự (tối đa 50 ký tự)");
        DataValidator.validatePhone(txtPhone, sb, "Số điện thoại trống hoặc không đúng định dạng !");
        DataValidator.validateEmpty(txtAddress, sb, "Địa chỉ không được để trống !");
        DataValidator.validateEmail(txtEmail, sb, "Email trống hoặc không đúng định dạng !");
        return sb;
    }

    //getModel-Lấy dữ liệu
    private Customers getModelCustomer() {
        try {
            Customers entity = new Customers();
            entity.setNameString(txtUserName.getText().trim());
            entity.setEmailString(txtEmail.getText().trim());
            entity.setPhoneString(txtPhone.getText().trim());
            entity.setAddressString(txtAddress.getText().trim());
            entity.setBirthDayDate(DateHelper.toDate(txtBirthDay.getText(), "yyyy-MM-dd"));
            entity.setSexBoolean(!rbtWomen.isSelected());
            return entity;
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi lấy dữ liệu : " + e.toString(), "Lỗi");
            System.out.println("*CartJDialog(getModelCustomer) - " + e.toString());
            return null;
        }
    }

    //setModel-Xuất dữ liệu
    private void setModelCustomer(Customers entity) {
        try {
            txtUserName.setText(entity.getNameString());
            txtAddress.setText(entity.getAddressString());
            txtEmail.setText(entity.getEmailString());
            boolean sex = entity.getSexBoolean();
            if (sex) {
                rbtMen.setSelected(true);
            } else {
                rbtWomen.setSelected(true);
            }
            txtBirthDay.setText(DateHelper.toString(entity.getBirthDayDate(), "yyyy-MM-dd"));
            entity.setNameString(txtUserName.getText().trim());
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi lấy dữ liệu : " + e.toString(), "Lỗi");
            System.out.println("*CartJDialog(setModelCustomer) - " + e.toString());
        }
    }

    private Receipts getModelReceipt() {
        try {
            Receipts entity = new Receipts();
            entity.setEmailUserString(ShareHelper.USER.getEmailString());
            entity.setExportDate(DateHelper.now());
            entity.setVATInt(20);
            return entity;
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi lấy dữ liệu : " + e.toString(), "Lỗi");
            System.out.println("*CartJDialog(getModelReceipt) - " + e.toString());
            return null;
        }
    }

    //Phương thức reset
    private void reset() {
        JTextField[] jTextField = {txtAddress, txtEmail, txtPhone, txtUserName};
        for (JTextField jTextField1 : jTextField) {
            DataValidator.error(jTextField1, true);
        }
        txtAddress.setText("ĐỊA CHỈ");
        txtEmail.setText("EMAIL");
        txtPhone.setText("SỐ ĐIỆN THOẠI");
        txtUserName.setText("HỌ VÀ TÊN");
        jlbTotailProduct.setText("Tổng tiền sản phẩm : 0 VND");
        jlbDiscount.setText("Giảm giá (0%) : 0 VND");
        jlbVAT.setText("Thuế VAT (20%) : 0 VND");
        jlbTotail.setText("Tổng tiền : 0 VND");
    }

    //Phương thức cập nhật thông tin giỏ hàng
    private void updateCartInfo() {
        try {
            int vnd = 0;
            for (Products products : listCart) {
                vnd += Integer.parseInt(products.getPriceString());
            }
            jlbTotailProduct.setText("Tổng tiền sản phẩm :" + vnd + "VND");
            jlbVAT.setText("Thuế VAT (20%) :" + vnd * 0.2 + "VND");
            jlbTotail.setText("Tổng tiền :" + (vnd - (vnd * 0.2)) + "VND");
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi updateCartInfo : " + e.toString(), "Lỗi");
            System.out.println("*CartJDialog(updateCartInfo) - " + e.toString());
        }
    }

    //Phương thức đặt hàng
    private void checkOut() {
        StringBuilder sb = checkSpam();
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            return;
        }
        try {
            if (listCart == null) {
                MessageDialogHelper.showMessageDialog(this, "Vui lòng thêm sản phẩm vào giỏ hàng !", "Thông báo tin nhắn");
            } else {
                Customers entity = getModelCustomer();
                entity.setCodeCustomerString(DataValidator.randomAlphaNumeric(20));
                if (daoCustomer.select_By_PK(entity.getCodeCustomerString()) != null) {
                    MessageDialogHelper.showMessageDialog(this, "Mã khách hàng đã tồn tại !", "Thông báo tin nhắn");
                } else {
                    Receipts entity1 = getModelReceipt();
                    entity1.setCodeReceiptString(DataValidator.randomAlphaNumeric(20));
                    if (daoReceipt.select_By_PK(entity1.getCodeReceiptString()) != null) {
                        MessageDialogHelper.showMessageDialog(this, "Mã hóa đơn đã tồn tại !", "Thông báo tin nhắn");
                    } else {
                        String phone = txtPhone.getText().trim();
                        Customers customers = daoCustomer.select_No_Key(phone);
                        if (customers != null) {
                            customers.setReturnInt(customers.getReturnInt() + 1);
                            daoCustomer.update(customers);
                            entity1.setCodeCustormerString(customers.getCodeCustomerString());
                        } else {
                            daoCustomer.insert(entity);
                            entity1.setCodeCustormerString(entity.getCodeCustomerString());
                        }
                        try {
                            daoReceipt.insert(entity1);
                            for (Products products : listCart) {
                                ReceiptDetails entity2 = new ReceiptDetails();
                                entity2.setCodeRecieptDetailString(entity1.getCodeReceiptString());
                                entity2.setCodeProductString(products.getCodeProductString());
                                entity2.setAmountInt(1);
                                daoReceiptDetail.insert(entity2);
                            }
                            MessageDialogHelper.showMessageDialog(this, "Đặt hàng thành công !", "Thông báo tin nhắn");
                            reset();
                            listCart.clear();
                            fillTable();
                            fillTableCart();
                        } catch (Exception e) {
                            MessageDialogHelper.showErrorDialog(this, "Lỗi checkOut : " + e.toString(), "Lỗi");
                            System.out.println("*CartJDialog(checkOut) - " + e.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi checkOut : " + e.toString(), "Lỗi");
            System.out.println("*CartJDialog(checkOut) - " + e.toString());
        }
    }

    //Phương thức kiểm tra khách hàng
    private void check() {
        try {
            String phone = txtPhone.getText().trim();
            StringBuilder sb = new StringBuilder();
            DataValidator.validatePhone(txtPhone, sb, "Số điện thoại trống hoặc không đúng định dạng !");
            if (sb.length() > 0) {
                MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            } else {
                setEnableds();
                Customers customers = daoCustomer.select_No_Key(phone);
                if (customers != null) {
                    MessageDialogHelper.showMessageDialog(this, "Khách hàng đã có dữ liệu !", "Thông báo tin nhắn");
                    setModelCustomer(customers);
                }
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi check : " + e.toString(), "Lỗi");
            System.out.println("*CartJDialog(check) - " + e.toString());
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

        menu = new javax.swing.JPopupMenu();
        itemAdd = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        dateChooser = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCart = new swing.TableDark();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUserName = new swing.MyTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new swing.MyTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtPhone = new swing.MyTextField();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtAddress = new swing.MyTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        rbtMen = new swing.RadioButtonCustom();
        rbtWomen = new swing.RadioButtonCustom();
        jPanel16 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtBirthDay = new swing.MyTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnCheck = new swing.Button();
        btnSubmit = new swing.Button();
        jPanel7 = new javax.swing.JPanel();
        jlbTotailProduct = new javax.swing.JLabel();
        jlbDiscount = new javax.swing.JLabel();
        jlbVAT = new javax.swing.JLabel();
        jlbTotail = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableList = new javax.swing.JTable();

        itemAdd.setText("Thêm vào giỏ hàng");
        itemAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAddActionPerformed(evt);
            }
        });
        menu.add(itemAdd);

        dateChooser.setDateFormat("yyyy-MM-dd\n");
        dateChooser.setTextRefernce(txtBirthDay);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GIỎ HÀNG");
        setMinimumSize(new java.awt.Dimension(1750, 900));
        setPreferredSize(new java.awt.Dimension(1750, 900));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        tableCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Tên sản phẩm", "Số lượng", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCart.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tableCart.setRowHeight(50);
        jScrollPane2.setViewportView(tableCart);
        if (tableCart.getColumnModel().getColumnCount() > 0) {
            tableCart.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(0, 300));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jPanel9.setPreferredSize(new java.awt.Dimension(400, 300));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        jPanel10.setPreferredSize(new java.awt.Dimension(380, 80));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_coworking_30px.png"))); // NOI18N
        jLabel1.setText("Họ và tên");
        jLabel1.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel10.add(jLabel1, java.awt.BorderLayout.LINE_START);

        txtUserName.setForeground(new java.awt.Color(51, 51, 51));
        txtUserName.setText("HỌ VÀ TÊN");
        txtUserName.setCaretColor(new java.awt.Color(51, 51, 51));
        txtUserName.setFont(new java.awt.Font("DialogInput", 2, 16)); // NOI18N
        txtUserName.setPreferredSize(new java.awt.Dimension(99, 50));
        jPanel10.add(txtUserName, java.awt.BorderLayout.PAGE_END);

        jPanel9.add(jPanel10);

        jPanel11.setPreferredSize(new java.awt.Dimension(380, 80));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_at_sign_30px.png"))); // NOI18N
        jLabel6.setText("Email");
        jLabel6.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel11.add(jLabel6, java.awt.BorderLayout.LINE_START);

        txtEmail.setForeground(new java.awt.Color(51, 51, 51));
        txtEmail.setText("EMAIL");
        txtEmail.setCaretColor(new java.awt.Color(51, 51, 51));
        txtEmail.setFont(new java.awt.Font("DialogInput", 2, 16)); // NOI18N
        txtEmail.setPreferredSize(new java.awt.Dimension(99, 50));
        jPanel11.add(txtEmail, java.awt.BorderLayout.PAGE_END);

        jPanel9.add(jPanel11);

        jPanel12.setPreferredSize(new java.awt.Dimension(380, 80));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_phone_30px.png"))); // NOI18N
        jLabel7.setText("Số điện thoại");
        jLabel7.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel12.add(jLabel7, java.awt.BorderLayout.LINE_START);

        txtPhone.setForeground(new java.awt.Color(51, 51, 51));
        txtPhone.setText("SỐ ĐIỆN THOẠI");
        txtPhone.setCaretColor(new java.awt.Color(51, 51, 51));
        txtPhone.setFont(new java.awt.Font("DialogInput", 2, 16)); // NOI18N
        txtPhone.setPreferredSize(new java.awt.Dimension(99, 50));
        jPanel12.add(txtPhone, java.awt.BorderLayout.PAGE_END);

        jPanel9.add(jPanel12);

        jPanel4.add(jPanel9);

        jPanel13.setPreferredSize(new java.awt.Dimension(400, 300));
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        jPanel14.setPreferredSize(new java.awt.Dimension(380, 80));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_home_address_30px.png"))); // NOI18N
        jLabel8.setText("Địa chỉ");
        jLabel8.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel14.add(jLabel8, java.awt.BorderLayout.LINE_START);

        txtAddress.setForeground(new java.awt.Color(51, 51, 51));
        txtAddress.setText("ĐỊA CHỈ");
        txtAddress.setCaretColor(new java.awt.Color(51, 51, 51));
        txtAddress.setFont(new java.awt.Font("DialogInput", 2, 16)); // NOI18N
        txtAddress.setPreferredSize(new java.awt.Dimension(99, 50));
        jPanel14.add(txtAddress, java.awt.BorderLayout.PAGE_END);

        jPanel13.add(jPanel14);

        jPanel15.setPreferredSize(new java.awt.Dimension(380, 80));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_venus_symbol_30px.png"))); // NOI18N
        jLabel9.setText("Giới tính");
        jLabel9.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel15.add(jLabel9, java.awt.BorderLayout.LINE_START);

        jPanel8.setPreferredSize(new java.awt.Dimension(99, 50));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 5));

        buttonGroup1.add(rbtMen);
        rbtMen.setSelected(true);
        rbtMen.setText("Nam");
        rbtMen.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jPanel8.add(rbtMen);

        rbtWomen.setBackground(new java.awt.Color(255, 102, 102));
        buttonGroup1.add(rbtWomen);
        rbtWomen.setText("Nữ");
        rbtWomen.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jPanel8.add(rbtWomen);

        jPanel15.add(jPanel8, java.awt.BorderLayout.PAGE_END);

        jPanel13.add(jPanel15);

        jPanel16.setPreferredSize(new java.awt.Dimension(380, 80));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("DialogInput", 1, 14)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_birthday_30px.png"))); // NOI18N
        jLabel10.setText("Ngày sinh");
        jLabel10.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel16.add(jLabel10, java.awt.BorderLayout.LINE_START);

        txtBirthDay.setForeground(new java.awt.Color(51, 51, 51));
        txtBirthDay.setCaretColor(new java.awt.Color(51, 51, 51));
        txtBirthDay.setFont(new java.awt.Font("DialogInput", 2, 16)); // NOI18N
        txtBirthDay.setPreferredSize(new java.awt.Dimension(99, 50));
        jPanel16.add(txtBirthDay, java.awt.BorderLayout.PAGE_END);

        jPanel13.add(jPanel16);

        jPanel4.add(jPanel13);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setPreferredSize(new java.awt.Dimension(0, 200));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 30));

        btnCheck.setForeground(new java.awt.Color(51, 51, 51));
        btnCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_paid_40px.png"))); // NOI18N
        btnCheck.setText("Kiểm tra");
        btnCheck.setEffectColor(new java.awt.Color(102, 204, 0));
        btnCheck.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        btnCheck.setPreferredSize(new java.awt.Dimension(250, 50));
        btnCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckActionPerformed(evt);
            }
        });
        jPanel6.add(btnCheck);

        btnSubmit.setForeground(new java.awt.Color(51, 51, 51));
        btnSubmit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_click_&_collect_40px.png"))); // NOI18N
        btnSubmit.setText("Đặt hàng");
        btnSubmit.setEffectColor(new java.awt.Color(102, 204, 0));
        btnSubmit.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        btnSubmit.setPreferredSize(new java.awt.Dimension(250, 50));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        jPanel6.add(btnSubmit);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel7.setPreferredSize(new java.awt.Dimension(500, 0));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        jlbTotailProduct.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jlbTotailProduct.setText("Tổng tiền sản phẩm : 1.000.000.000.000 VND");
        jlbTotailProduct.setMaximumSize(new java.awt.Dimension(500, 50));
        jlbTotailProduct.setMinimumSize(new java.awt.Dimension(500, 50));
        jlbTotailProduct.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel7.add(jlbTotailProduct);

        jlbDiscount.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jlbDiscount.setText("Giảm giá (2%) : 50.000.000 VND");
        jlbDiscount.setMaximumSize(new java.awt.Dimension(500, 50));
        jlbDiscount.setMinimumSize(new java.awt.Dimension(500, 50));
        jlbDiscount.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel7.add(jlbDiscount);

        jlbVAT.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jlbVAT.setText("Thuế VAT (20%) : 30.000.000 VND");
        jlbVAT.setMaximumSize(new java.awt.Dimension(500, 50));
        jlbVAT.setMinimumSize(new java.awt.Dimension(500, 50));
        jlbVAT.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel7.add(jlbVAT);

        jlbTotail.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jlbTotail.setForeground(new java.awt.Color(153, 0, 0));
        jlbTotail.setText("Tổng tiền : 500.000.000 VND");
        jlbTotail.setMaximumSize(new java.awt.Dimension(500, 50));
        jlbTotail.setMinimumSize(new java.awt.Dimension(500, 50));
        jlbTotail.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel7.add(jlbTotail);

        jPanel5.add(jPanel7, java.awt.BorderLayout.WEST);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setMinimumSize(new java.awt.Dimension(900, 900));
        jPanel2.setPreferredSize(new java.awt.Dimension(900, 900));
        jPanel2.setLayout(new java.awt.BorderLayout());

        tableList.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tableList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Tên sản phẩm", "Hình", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableList.setRowHeight(100);
        tableList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableList);
        if (tableList.getColumnModel().getColumnCount() > 0) {
            tableList.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListMouseReleased
        menu.show(tableList, evt.getX(), evt.getY());
    }//GEN-LAST:event_tableListMouseReleased

    private void itemAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAddActionPerformed
        clickTableMenu();
    }//GEN-LAST:event_itemAddActionPerformed

    private void btnCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckActionPerformed
        check();
    }//GEN-LAST:event_btnCheckActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        checkOut();
    }//GEN-LAST:event_btnSubmitActionPerformed

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
            java.util.logging.Logger.getLogger(CartJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CartJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CartJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CartJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CartJDialog dialog = new CartJDialog(new javax.swing.JFrame(), true);
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
    private swing.Button btnCheck;
    private swing.Button btnSubmit;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.raven.datechooser.DateChooser dateChooser;
    private javax.swing.JMenuItem itemAdd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jlbDiscount;
    private javax.swing.JLabel jlbTotail;
    private javax.swing.JLabel jlbTotailProduct;
    private javax.swing.JLabel jlbVAT;
    private javax.swing.JPopupMenu menu;
    private swing.RadioButtonCustom rbtMen;
    private swing.RadioButtonCustom rbtWomen;
    private swing.TableDark tableCart;
    private javax.swing.JTable tableList;
    private swing.MyTextField txtAddress;
    private swing.MyTextField txtBirthDay;
    private swing.MyTextField txtEmail;
    private swing.MyTextField txtPhone;
    private swing.MyTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
