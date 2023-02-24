package views;

import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import dao.CategoryDao;
import dao.ProductDao;
import helper.DataValidator;
import helper.DateHelper;
import helper.MessageDialogHelper;
import helper.ShareHelper;
import java.awt.Color;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Categoris;
import model.Products;
import swing.ScrollBarCustom;

/**
 *
 * @author ACER
 */
public class ManagerProductJDialog extends javax.swing.JDialog {
    
    private JFileChooser fileChooser = new JFileChooser();
    private ProductDao dao = new ProductDao();
    private CategoryDao daoCate = new CategoryDao();
    private List<Products> list = dao.select_All();
    private int pos = -1;

    /**
     * Creates new form ManagerProductJDialog
     *
     * @param parent
     * @param modal
     */
    public ManagerProductJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    
    private void init() {
        setIconImage(ShareHelper.APP_ICON);
        tableproduct.fixTable(jScrollPane2);
        fixTable(jScrollPane1);
        fillComboBox();
        fillComboBoxColor();
        fillComboBoxCompareto();
        fillTable(list);
        txtCodeProduct.disable();
        if (!ShareHelper.USER.getPositionBoolean()) {
            btnInsert.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }
        opposite();
        dateChooser.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
//                System.out.println(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateChooser.hidePopup();
                }
            }
        });
        dateChooser1.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
//                System.out.println(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateChooser1.hidePopup();
                }
            }
        });
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

    //Chuyển hướng từ form -> list
    private void redirect() {
        cardList.setVisible(true);
        cardForm.setVisible(false);
    }

    //Chuyển hướng từ list -> form
    private void opposite() {
        cardForm.setVisible(true);
        cardList.setVisible(false);
    }

    //Mở hộp thoại-chọn file
    private void selectImageAvata() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { //nếu người dùng đã chọn đc file
            File file = fileChooser.getSelectedFile();    //lấy file người dùng chọn
            if (ShareHelper.saveLogo(file)) {  //sao chép file đã chọn thư mục logos
                // Hiển thị hình lên form
                jlbAvata.setIcon(ShareHelper.readLogo(file.getName(), 400, 860)); //file.getName(); lấy tên của file
                //ImageIcon readLogo(String tenFile) đọc file trong thư mục logos theo tên file trả về ImageIcon
                //void setIcon(ImageIcon icon) set Icon cho lbl
                jlbAvata.setToolTipText(file.getName());
            }
        }
    }
    
    private void selectImagePicture(JLabel jlb) {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { //nếu người dùng đã chọn đc file
            File file = fileChooser.getSelectedFile();    //lấy file người dùng chọn
            if (ShareHelper.saveLogo(file)) {  //sao chép file đã chọn thư mục logos
                // Hiển thị hình lên form
                jlb.setIcon(ShareHelper.readLogo(file.getName(), 137, 200)); //file.getName(); lấy tên của file
                //ImageIcon readLogo(String tenFile) đọc file trong thư mục logos theo tên file trả về ImageIcon
                //void setIcon(ImageIcon icon) set Icon cho lbl
                jlb.setToolTipText(file.getName());
            }
        }
    }

    //Load dữ liệu cate cbx
    private void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxCate.getModel();
        model.removeAllElements();
        try {
            List<Categoris> list = daoCate.select_All();
            for (Categoris cate : list) {
                model.addElement(cate);
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi load cbx : " + e.toString(), "Lỗi");
            System.out.println("*ManagerProductJDialog(fillComboBox) - " + e.toString());
        }
    }

    //Load dữ liệu cate cbx
    private void fillComboBoxCompareto() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxCateComparto.getModel();
        model.removeAllElements();
        try {
            model.addElement("--All--");
            for (Categoris cate : daoCate.select_All()) {
                model.addElement(cate);
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi load cbx : " + e.toString(), "Lỗi");
            System.out.println("*ManagerProductJDialog(fillComboBoxCompareto) - " + e.toString());
        }
    }

    //Load dữ liệu color cbx
    private void fillComboBoxColor() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxColor.getModel();
        model.removeAllElements();
        try {
            List<String> list = new ArrayList<>();
            list.add("white");
            list.add("sky blue");
            list.add("green");
            list.add("gold and silver");
            list.add("orange green");
            list.add("yellow");
            list.add("balck");
            list.add("blue");
            for (String string : list) {
                model.addElement(string);
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi load cbx : " + e.toString(), "Lỗi");
            System.out.println("*ManagerProductJDialog(fillComboBoxColor) - " + e.toString());
        }
    }

    //setModel-Xuất dữ liệu
    private void setModel(Products entity) {
        try {
            txtCodeProduct.setText(entity.getCodeProductString());
            txtNameProduct.setText(entity.getNameProductString());
            txtPrice.setText(entity.getPriceString());
            cbxCate.getModel().setSelectedItem(daoCate.select_By_PK(entity.getCodeCateString()));
            txtImportDate.setText(DateHelper.toString(entity.getImportDate(), "yyyy-MM-dd"));
            txtProductionDay.setText(DateHelper.toString(entity.getProductionDate(), "yyyy-MM-dd"));
            jsliderAmount.setValue(entity.getAmountInt());
            cbxColor.getModel().setSelectedItem(entity.getColorString());
            txtNote.setText(entity.getNoteString());
            if (entity.getAvataString() != null) {
                jlbAvata.setIcon(ShareHelper.readLogo(entity.getAvataString(), 400, 860));
            } else {
                jlbAvata.setIcon(ShareHelper.readLogo("noImage.png", 400, 860));
            }
            
            if (entity.getPicture1String() != null) {
                jlbPicture1.setIcon(ShareHelper.readLogo(entity.getPicture1String(), 137, 200));
            } else {
                jlbPicture1.setIcon(ShareHelper.readLogo("noImage.png", 137, 200));
            }
            
            if (entity.getPicture2String() != null) {
                jlbPicture2.setIcon(ShareHelper.readLogo(entity.getPicture2String(), 137, 200));
            } else {
                jlbPicture2.setIcon(ShareHelper.readLogo("noImage.png", 137, 200));
            }
            
            if (entity.getPicture3String() != null) {
                jlbPicture3.setIcon(ShareHelper.readLogo(entity.getPicture3String(), 137, 200));
            } else {
                jlbPicture3.setIcon(ShareHelper.readLogo("noImage.png", 137, 200));
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi hiễn thị dữ liệu : " + e.toString(), "Lỗi");
            System.out.println("*ManagerProductJDialog(setModel) - " + e.toString());
        }
    }

    //getModel-Lấy dữ liệu
    private Products getModel() {
        try {
            Products entity = new Products();
            entity.setCodeProductString(txtCodeProduct.getText().trim());
            entity.setNameProductString(txtNameProduct.getText().trim());
            entity.setPriceString(txtPrice.getText().trim());
            entity.setCodeCateString(((Categoris) cbxCate.getSelectedItem()).getCodeCateString());
            entity.setImportDate(DateHelper.toDate(txtImportDate.getText(), "yyyy-MM-dd"));
            entity.setProductionDate(DateHelper.toDate(txtProductionDay.getText(), "yyyy-MM-dd"));
            entity.setAmountInt(jsliderAmount.getValue());
            entity.setColorString((String) cbxColor.getSelectedItem());
            entity.setAvataString(jlbAvata.getToolTipText());
            entity.setPicture1String(jlbPicture1.getToolTipText());
            entity.setPicture2String(jlbPicture2.getToolTipText());
            entity.setPicture3String(jlbPicture3.getToolTipText());
            entity.setNoteString(txtNote.getText().trim());
            return entity;
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi lấy dữ liệu : " + e.toString(), "Lỗi");
            System.out.println("*ManagerProductJDialog(getModel) - " + e.toString());
            return null;
        }
    }

    //load all data
    private void fillTable(List<Products> list) {
        try {
            DefaultTableModel model = (DefaultTableModel) tableproduct.getModel();
            model.setRowCount(0);
            for (Products entity : list) {
                Long vnd = Long.parseLong(entity.getPriceString());
                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                String str = currencyVN.format(vnd);
                model.addRow(new Object[]{
                    entity.getCodeProductString(),
                    entity.getNameProductString(),
                    daoCate.select_By_PK(entity.getCodeCateString()).getNameListString(),
                    str,
                    DateHelper.toString(entity.getImportDate(), "yyyy-MM-dd"),
                    entity.getColorString(),
                    entity.getAmountInt() + " chiếc",
                    DateHelper.toString(entity.getProductionDate(), "yyyy-MM-dd"),});
            }
            model.fireTableDataChanged();
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi dữ liệu bảng : " + e.toString(), "Lỗi");
            System.out.println("*ManagerProductJDialog(fillTable) - " + e.toString());
        }
    }

    //clickTable
    private void clickTable() {
        try {
            int row = tableproduct.getSelectedRow();
            if (row >= 0) {
                String code = (String) tableproduct.getValueAt(row, 0);
                setModel(dao.select_By_PK(code));
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi clickTable : " + e.toString(), "Lỗi");
            System.out.println("*ManagerProductJDialog(clickTable) - " + e.toString());
        }
    }

    //Phương thức checkSpam
    private StringBuilder checkSpam() {
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtNameProduct, sb, "Tên sản phẩm không được để trống !");
        DataValidator.validateCheckLong(txtPrice, sb, "Giá trống hoặc không phải kiểu số !");
        if (jlbAvata.getToolTipText() == null) {
            sb.append("Hình ảnh sản phẩm không được để trống !").append("\n");
        }
        return sb;
    }

    //Hiễn thị theo vị trí
    private void selectPos(int pos) {
        Products entity = dao.getEnityByPossition(String.valueOf(pos));
        setModel(entity);
    }

    //Phương thức reset
    private void reset() {
        JTextField[] jTextField = {txtCodeProduct, txtNameProduct, txtPrice};
        for (JTextField jTextField1 : jTextField) {
            DataValidator.error(jTextField1, true);
        }
        txtCodeProduct.setText("MÃ SẢN PHẨM");
        txtNameProduct.setText("TÊN SẢN PHẨM");
        txtPrice.setText("GIÁ SẢN PHẨM");
        txtNote.setText("GHI CHÚ");
        txtNote.setBackground(new Color(255, 255, 255));
        txtNote.setForeground(new Color(0, 0, 0));
        jlbAvata.setIcon(ShareHelper.readLogo("noImage.png", 180, 133));
        jlbPicture1.setIcon(ShareHelper.readLogo("noImage.png", 137, 200));
        jlbPicture2.setIcon(ShareHelper.readLogo("noImage.png", 137, 200));
        jlbPicture3.setIcon(ShareHelper.readLogo("noImage.png", 137, 200));
    }

    //Phương thức update
    private void update() {
        StringBuilder sb = checkSpam();
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            return;
        }
        try {
            if (dao.select_By_PK(txtCodeProduct.getText()) == null) {
                MessageDialogHelper.showMessageDialog(this, "Sản phẩm này không tồn tại !", "Thông báo tin nhắn");
            } else {
                try {
                    Products entity = getModel();
                    dao.update(entity);
                    MessageDialogHelper.showMessageDialog(this, "Cập nhập sản phẩm thành công !", "Thông báo tin nhắn");
                    reset();
                    redirect();
                    fillTable(list);
                } catch (Exception e) {
                    MessageDialogHelper.showErrorDialog(this, "Lỗi update : " + e.toString(), "Lỗi");
                    System.out.println("*ManagerProductJDialog(update) - " + e.toString());
                }
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi update : " + e.toString(), "Lỗi");
            System.out.println("*ManagerProductJDialog(update) - " + e.toString());
        }
    }

    //Phương thức insert
    private void insert() {
        StringBuilder sb = checkSpam();
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(this, sb.toString(), "Lỗi nhập liệu");
            return;
        }
        try {
            Products entity = getModel();
            entity.setCodeCateString(DataValidator.randomAlphaNumeric(20));
            if (dao.select_By_PK(entity.getCodeProductString()) != null) {
                MessageDialogHelper.showMessageDialog(this, "Mã sản phẩm đã tồn tại !", "Thông báo tin nhắn");
            } else {
                try {
                    dao.insert(entity);
                    MessageDialogHelper.showMessageDialog(this, "Lưu sản phẩm thành công !", "Thông báo tin nhắn");
                    reset();
                    redirect();
                    fillTable(list);
                } catch (Exception e) {
                    MessageDialogHelper.showErrorDialog(this, "Lỗi insert : " + e.toString(), "Lỗi");
                    System.out.println("*ManagerProductJDialog(insert) - " + e.toString());
                }
            }
        } catch (Exception e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi insert : " + e.toString(), "Lỗi");
            System.out.println("*ManagerProductJDialog(insert) - " + e.toString());
        }
    }

    //Phương thức delete
    private void delete() {
        try {
            if (MessageDialogHelper.showComfirmDialog(this, "Bạn có muốn xoá sản phẩm này không ?", "Cảnh báo xóa sản phẩm")
                    == JOptionPane.NO_OPTION) {
            } else {
                Products entity = dao.select_By_PK(txtCodeProduct.getText());
                if (entity == null) {
                    MessageDialogHelper.showMessageDialog(this, "Không tìm thấy sản phẩm !", "Thông báo tin nhắn");
                } else {
                    try {
                        dao.delete(txtCodeProduct.getText());
                        MessageDialogHelper.showMessageDialog(this, "Xoá dữ liệu sản phẩm thành công !", "Thông báo tin nhắn");
                        reset();
                        redirect();
                        fillTable(list);
                    } catch (Exception e) {
                        MessageDialogHelper.showErrorDialog(this, "Lỗi delete : " + e.toString(), "Lỗi");
                        System.out.println("*ManagerProductJDialog(delete) - " + e.toString());
                    }
                }
            }
        } catch (NumberFormatException e) {
            MessageDialogHelper.showErrorDialog(this, "Lỗi delete : " + e.toString(), "Lỗi");
            System.out.println("*ManagerProductJDialog(delete) - " + e.toString());
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

        dateChooser = new com.raven.datechooser.DateChooser();
        dateChooser1 = new com.raven.datechooser.DateChooser();
        cardForm = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnFirst = new swing.Button();
        btnPrevious = new swing.Button();
        btnNext = new swing.Button();
        btnLast = new swing.Button();
        btnHome = new swing.Button();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jlbAvata = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jlbPicture1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jlbPicture2 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jlbPicture3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCodeProduct = new swing.MyTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtNameProduct = new swing.MyTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtPrice = new swing.MyTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        cbxCate = new swing.ComboBoxSuggestion();
        jPanel27 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtImportDate = new swing.MyTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtProductionDay = new swing.MyTextField();
        jPanel26 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jsliderAmount = new swing.JsliderCustom();
        jPanel25 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        cbxColor = new swing.ComboBoxSuggestion();
        jPanel4 = new javax.swing.JPanel();
        btnInsert = new swing.Button();
        btnUpdate = new swing.Button();
        btnDelete = new swing.Button();
        button4 = new swing.Button();
        cardList = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxCateComparto = new swing.ComboBoxSuggestion();
        btnSearch = new swing.Button();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableproduct = new swing.TableDark();

        dateChooser.setForeground(new java.awt.Color(0, 0, 153));
        dateChooser.setDateFormat("yyyy-MM-dd");
        dateChooser.setTextRefernce(txtImportDate);

        dateChooser1.setForeground(new java.awt.Color(0, 0, 153));
        dateChooser1.setDateFormat("yyyy-MM-dd");
        dateChooser1.setTextRefernce(txtProductionDay);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ SẢN PHẨM");
        setMinimumSize(new java.awt.Dimension(1800, 950));
        getContentPane().setLayout(new java.awt.CardLayout());

        cardForm.setLayout(new java.awt.BorderLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(900, 950));
        jPanel1.setMinimumSize(new java.awt.Dimension(900, 950));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 950));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel10.setPreferredSize(new java.awt.Dimension(900, 100));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 25));

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

        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_home_40px.png"))); // NOI18N
        btnHome.setEffectColor(new java.awt.Color(204, 0, 51));
        btnHome.setPreferredSize(new java.awt.Dimension(50, 50));
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        jPanel10.add(btnHome);

        jPanel1.add(jPanel10, java.awt.BorderLayout.PAGE_END);

        jPanel7.setMaximumSize(new java.awt.Dimension(900, 850));
        jPanel7.setMinimumSize(new java.awt.Dimension(900, 850));
        jPanel7.setPreferredSize(new java.awt.Dimension(900, 850));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        jPanel8.setMaximumSize(new java.awt.Dimension(860, 400));
        jPanel8.setMinimumSize(new java.awt.Dimension(860, 400));
        jPanel8.setPreferredSize(new java.awt.Dimension(860, 400));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jlbAvata.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbAvata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Aventador_ultimae_video_hero.jpg"))); // NOI18N
        jlbAvata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbAvataMouseClicked(evt);
            }
        });
        jPanel8.add(jlbAvata, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel8);

        jPanel9.setMaximumSize(new java.awt.Dimension(860, 140));
        jPanel9.setMinimumSize(new java.awt.Dimension(860, 140));
        jPanel9.setPreferredSize(new java.awt.Dimension(860, 140));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 2));

        jPanel12.setPreferredSize(new java.awt.Dimension(200, 137));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jlbPicture1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbPicture1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/picture.jpg"))); // NOI18N
        jlbPicture1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbPicture1MouseClicked(evt);
            }
        });
        jPanel12.add(jlbPicture1, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel12);

        jPanel13.setLayout(new java.awt.BorderLayout());

        jlbPicture2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbPicture2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/picture.jpg"))); // NOI18N
        jlbPicture2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbPicture2MouseClicked(evt);
            }
        });
        jPanel13.add(jlbPicture2, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel13);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jlbPicture3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbPicture3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/picture.jpg"))); // NOI18N
        jlbPicture3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbPicture3MouseClicked(evt);
            }
        });
        jPanel14.add(jlbPicture3, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel14);

        jPanel7.add(jPanel9);

        jPanel5.setMinimumSize(new java.awt.Dimension(860, 230));
        jPanel5.setPreferredSize(new java.awt.Dimension(860, 230));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel1.setText("Ghi chú");
        jLabel1.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel5.add(jLabel1, java.awt.BorderLayout.CENTER);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(163, 190));

        txtNote.setColumns(20);
        txtNote.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtNote.setRows(5);
        txtNote.setText("GHI CHÚ");
        jScrollPane1.setViewportView(txtNote);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.PAGE_END);

        jPanel7.add(jPanel5);

        jPanel1.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        cardForm.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setMaximumSize(new java.awt.Dimension(900, 950));
        jPanel2.setMinimumSize(new java.awt.Dimension(900, 950));
        jPanel2.setPreferredSize(new java.awt.Dimension(900, 950));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setMaximumSize(new java.awt.Dimension(900, 850));
        jPanel3.setMinimumSize(new java.awt.Dimension(900, 850));
        jPanel3.setPreferredSize(new java.awt.Dimension(900, 850));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        jPanel11.setMaximumSize(new java.awt.Dimension(860, 100));
        jPanel11.setMinimumSize(new java.awt.Dimension(860, 100));
        jPanel11.setPreferredSize(new java.awt.Dimension(860, 100));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_barcode_scanner_30px.png"))); // NOI18N
        jLabel4.setText("Mã sản phẩm");
        jLabel4.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel11.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        txtCodeProduct.setBackground(new java.awt.Color(255, 255, 255));
        txtCodeProduct.setForeground(new java.awt.Color(0, 0, 0));
        txtCodeProduct.setText("MÃ SẢN PHẨM");
        txtCodeProduct.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtCodeProduct.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel11.add(txtCodeProduct, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel11);

        jPanel19.setMaximumSize(new java.awt.Dimension(860, 100));
        jPanel19.setMinimumSize(new java.awt.Dimension(860, 100));
        jPanel19.setPreferredSize(new java.awt.Dimension(860, 100));
        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_product_30px.png"))); // NOI18N
        jLabel9.setText("Tên sản phẩm");
        jLabel9.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel19.add(jLabel9, java.awt.BorderLayout.PAGE_START);

        txtNameProduct.setBackground(new java.awt.Color(255, 255, 255));
        txtNameProduct.setForeground(new java.awt.Color(0, 0, 0));
        txtNameProduct.setText("TÊN SẢN PHẨM");
        txtNameProduct.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtNameProduct.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel19.add(txtNameProduct, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel19);

        jPanel20.setMaximumSize(new java.awt.Dimension(860, 100));
        jPanel20.setMinimumSize(new java.awt.Dimension(860, 100));
        jPanel20.setPreferredSize(new java.awt.Dimension(860, 100));
        jPanel20.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_price_30px.png"))); // NOI18N
        jLabel10.setText("Giá sản phẩm(VND)");
        jLabel10.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel20.add(jLabel10, java.awt.BorderLayout.PAGE_START);

        txtPrice.setBackground(new java.awt.Color(255, 255, 255));
        txtPrice.setForeground(new java.awt.Color(0, 0, 0));
        txtPrice.setText("GIÁ SẢN PHẨM");
        txtPrice.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtPrice.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel20.add(txtPrice, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel20);

        jPanel21.setMaximumSize(new java.awt.Dimension(860, 100));
        jPanel21.setMinimumSize(new java.awt.Dimension(860, 100));
        jPanel21.setPreferredSize(new java.awt.Dimension(860, 100));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_diversity_30px.png"))); // NOI18N
        jLabel11.setText("Danh mục");
        jLabel11.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel21.add(jLabel11, java.awt.BorderLayout.PAGE_START);

        cbxCate.setForeground(new java.awt.Color(51, 51, 51));
        cbxCate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCate.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jPanel21.add(cbxCate, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel21);

        jPanel27.setMaximumSize(new java.awt.Dimension(860, 100));
        jPanel27.setMinimumSize(new java.awt.Dimension(860, 100));
        jPanel27.setPreferredSize(new java.awt.Dimension(860, 100));
        jPanel27.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0));

        jPanel15.setMinimumSize(new java.awt.Dimension(430, 100));
        jPanel15.setPreferredSize(new java.awt.Dimension(415, 100));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_calendar_30px.png"))); // NOI18N
        jLabel12.setText("Ngày nhập");
        jLabel12.setMinimumSize(new java.awt.Dimension(40, 40));
        jLabel12.setPreferredSize(new java.awt.Dimension(40, 40));
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel15.add(jLabel12, java.awt.BorderLayout.PAGE_START);

        txtImportDate.setBackground(new java.awt.Color(255, 255, 255));
        txtImportDate.setForeground(new java.awt.Color(0, 0, 0));
        txtImportDate.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtImportDate.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel15.add(txtImportDate, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel15);

        jPanel17.setMinimumSize(new java.awt.Dimension(430, 100));
        jPanel17.setPreferredSize(new java.awt.Dimension(415, 100));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jLabel13.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_calendar_30px.png"))); // NOI18N
        jLabel13.setText("Ngày xuất xưởng");
        jLabel13.setMinimumSize(new java.awt.Dimension(40, 40));
        jLabel13.setPreferredSize(new java.awt.Dimension(40, 40));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel17.add(jLabel13, java.awt.BorderLayout.PAGE_START);

        txtProductionDay.setBackground(new java.awt.Color(255, 255, 255));
        txtProductionDay.setForeground(new java.awt.Color(0, 0, 0));
        txtProductionDay.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        txtProductionDay.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jPanel17.add(txtProductionDay, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel17);

        jPanel3.add(jPanel27);

        jPanel26.setMaximumSize(new java.awt.Dimension(860, 100));
        jPanel26.setMinimumSize(new java.awt.Dimension(860, 100));
        jPanel26.setPreferredSize(new java.awt.Dimension(860, 100));
        jPanel26.setLayout(new java.awt.BorderLayout());

        jLabel16.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_final_state_30px.png"))); // NOI18N
        jLabel16.setText("Số lượng : 50");
        jLabel16.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel26.add(jLabel16, java.awt.BorderLayout.PAGE_START);

        jsliderAmount.setForeground(new java.awt.Color(102, 0, 0));
        jsliderAmount.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsliderAmountStateChanged(evt);
            }
        });
        jPanel26.add(jsliderAmount, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel26);

        jPanel25.setMaximumSize(new java.awt.Dimension(860, 100));
        jPanel25.setMinimumSize(new java.awt.Dimension(860, 100));
        jPanel25.setPreferredSize(new java.awt.Dimension(860, 100));
        jPanel25.setLayout(new java.awt.BorderLayout());

        jLabel15.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Color_Wheel_30px.png"))); // NOI18N
        jLabel15.setText("Màu");
        jLabel15.setPreferredSize(new java.awt.Dimension(34, 40));
        jPanel25.add(jLabel15, java.awt.BorderLayout.PAGE_START);

        cbxColor.setForeground(new java.awt.Color(51, 51, 51));
        cbxColor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxColor.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        jPanel25.add(cbxColor, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel25);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setMaximumSize(new java.awt.Dimension(900, 100));
        jPanel4.setMinimumSize(new java.awt.Dimension(900, 100));
        jPanel4.setPreferredSize(new java.awt.Dimension(900, 100));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 25));

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

        button4.setForeground(new java.awt.Color(0, 0, 0));
        button4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_sync_50px.png"))); // NOI18N
        button4.setText("Làm mới");
        button4.setEffectColor(new java.awt.Color(51, 153, 0));
        button4.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        button4.setPreferredSize(new java.awt.Dimension(180, 60));
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });
        jPanel4.add(button4);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        cardForm.add(jPanel2, java.awt.BorderLayout.WEST);

        getContentPane().add(cardForm, "card2");

        cardList.setLayout(new java.awt.BorderLayout());

        jPanel6.setPreferredSize(new java.awt.Dimension(900, 60));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        jLabel2.setFont(new java.awt.Font("DialogInput", 1, 18)); // NOI18N
        jLabel2.setText("SẮP XẾP THEO - DANH MỤC");
        jPanel6.add(jLabel2);

        cbxCateComparto.setForeground(new java.awt.Color(51, 51, 51));
        cbxCateComparto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxCateComparto.setFont(new java.awt.Font("DialogInput", 2, 18)); // NOI18N
        cbxCateComparto.setPreferredSize(new java.awt.Dimension(250, 35));
        jPanel6.add(cbxCateComparto);

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_search_40px.png"))); // NOI18N
        btnSearch.setEffectColor(new java.awt.Color(0, 153, 204));
        btnSearch.setMaximumSize(new java.awt.Dimension(40, 40));
        btnSearch.setMinimumSize(new java.awt.Dimension(40, 40));
        btnSearch.setPreferredSize(new java.awt.Dimension(40, 40));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel6.add(btnSearch);

        cardList.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        tableproduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "Tên sản phẩm", "Danh mục", "Giá", "Ngày nhập", "Màu ", "Số lượng", "Ngày xuất"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableproduct.setFont(new java.awt.Font("DialogInput", 3, 18)); // NOI18N
        tableproduct.setRowHeight(50);
        tableproduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableproductMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableproduct);
        if (tableproduct.getColumnModel().getColumnCount() > 0) {
            tableproduct.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        cardList.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(cardList, "card3");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jsliderAmountStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsliderAmountStateChanged
        jLabel16.setText("Số lượng:" + jsliderAmount.getValue());
    }//GEN-LAST:event_jsliderAmountStateChanged

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        dateChooser.showPopup();
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseClicked

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        redirect();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void tableproductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableproductMouseClicked
        opposite();
        clickTable();
    }//GEN-LAST:event_tableproductMouseClicked

    private void jlbAvataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbAvataMouseClicked
        selectImageAvata();
    }//GEN-LAST:event_jlbAvataMouseClicked

    private void jlbPicture1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPicture1MouseClicked
        selectImagePicture(jlbPicture1);
    }//GEN-LAST:event_jlbPicture1MouseClicked

    private void jlbPicture2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPicture2MouseClicked
        selectImagePicture(jlbPicture2);
    }//GEN-LAST:event_jlbPicture2MouseClicked

    private void jlbPicture3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPicture3MouseClicked
        selectImagePicture(jlbPicture3);
    }//GEN-LAST:event_jlbPicture3MouseClicked

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
        reset();
    }//GEN-LAST:event_button4ActionPerformed

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

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (cbxCateComparto.getSelectedItem().toString().equals("--All--")) {
            fillTable(list);
        } else {
            Categoris cate = (Categoris) cbxCateComparto.getSelectedItem();
            List<Products> list = dao.select_By_Fk(cate.getCodeCateString());
            fillTable(list);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

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
            java.util.logging.Logger.getLogger(ManagerProductJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerProductJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerProductJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerProductJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ManagerProductJDialog dialog = new ManagerProductJDialog(new javax.swing.JFrame(), true);
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
    private swing.Button btnHome;
    private swing.Button btnInsert;
    private swing.Button btnLast;
    private swing.Button btnNext;
    private swing.Button btnPrevious;
    private swing.Button btnSearch;
    private swing.Button btnUpdate;
    private swing.Button button4;
    private javax.swing.JPanel cardForm;
    private javax.swing.JPanel cardList;
    private swing.ComboBoxSuggestion cbxCate;
    private swing.ComboBoxSuggestion cbxCateComparto;
    private swing.ComboBoxSuggestion cbxColor;
    private com.raven.datechooser.DateChooser dateChooser;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jlbAvata;
    private javax.swing.JLabel jlbPicture1;
    private javax.swing.JLabel jlbPicture2;
    private javax.swing.JLabel jlbPicture3;
    private swing.JsliderCustom jsliderAmount;
    private swing.TableDark tableproduct;
    private swing.MyTextField txtCodeProduct;
    private swing.MyTextField txtImportDate;
    private swing.MyTextField txtNameProduct;
    private javax.swing.JTextArea txtNote;
    private swing.MyTextField txtPrice;
    private swing.MyTextField txtProductionDay;
    // End of variables declaration//GEN-END:variables
}
