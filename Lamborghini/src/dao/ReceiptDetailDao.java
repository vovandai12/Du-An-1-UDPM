package dao;

import helper.DatabaseHelper;
import helper.MessageDialogHelper;
import java.sql.ResultSet;
import java.util.List;
import model.ReceiptDetails;
import java.sql.SQLException;
import javax.swing.JDialog;

/**
 *
 * @author ACER
 */
public class ReceiptDetailDao extends EntityDAO<ReceiptDetails, Object> {

    private final String INSERT = "INSERT INTO [dbo].[ReceiptDetails] ([CodeRecieptDetail],[CodeProduct],[Amount]) VALUES (?,?,?)";
    private final String DELETE = "DELETE FROM [dbo].[ReceiptDetails] WHERE [CodeRecieptDetail] = ?";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[ReceiptDetails] WHERE [CodeRecieptDetail] = ?";

    @Override
    public ReceiptDetails readFromResultSet(ResultSet rs) {
        try {
            ReceiptDetails entity = new ReceiptDetails();
            entity.setID(rs.getInt("ID"));
            entity.setCodeRecieptDetailString(rs.getString("CodeRecieptDetail"));
            entity.setCodeProductString(rs.getString("CodeProduct"));
            entity.setAmountInt(rs.getInt("Amount"));
            return entity;
        } catch (SQLException e) {
            System.out.println("*ReadFromResultSet - (ReceiptDetailDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (ReadFromResultSet - (ReceiptDetailDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public void insert(ReceiptDetails entity) {
        try {
            DatabaseHelper.executeUpdate(INSERT,
                    entity.getCodeRecieptDetailString(),
                    entity.getCodeProductString(),
                    entity.getAmountInt());
        } catch (Exception e) {
            System.out.println("*insert - (ReceiptDetailDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (insert - (ReceiptDetailDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public void update(ReceiptDetails entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Object primarykey) {
        try {
            DatabaseHelper.executeUpdate(DELETE, primarykey);
        } catch (Exception e) {
            System.out.println("*delete - (ReceiptDetailDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (delete - (ReceiptDetailDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public List<ReceiptDetails> select_All() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ReceiptDetails> select_By_Fk(Object foreignkey) {
        try {
            return select(SELECT_BY_PK, foreignkey);
        } catch (Exception e) {
            System.out.println("*select_All - (ReceiptDetailDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_All - (ReceiptDetailDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public List<ReceiptDetails> select_By_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ReceiptDetails select_By_PK(Object primarykey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ReceiptDetails select_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ReceiptDetails select_Fk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ReceiptDetails getEnityByPossition(Object index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
