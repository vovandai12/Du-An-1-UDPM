package dao;

import helper.DatabaseHelper;
import helper.MessageDialogHelper;
import java.sql.ResultSet;
import java.util.List;
import model.Receipts;
import java.sql.SQLException;
import javax.swing.JDialog;

/**
 *
 * @author ACER
 */
public class ReceiptDao extends EntityDAO<Receipts, Object> {

    private final String INSERT = "INSERT INTO [dbo].[Receipts] ([CodeReceipt],[EmailUser],[CodeCustormer],[ExportDate]"
            + ",[Discount],[VAT]) VALUES (?,?,?,?,?,?)";
    private final String DELETE = "DELETE FROM [dbo].[Receipts] WHERE [CodeReceipt] = ?";
    private final String SELECT_ALL = "SELECT * FROM [dbo].[Receipts]";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[Receipts] WHERE [CodeReceipt] = ?";

    @Override
    public Receipts readFromResultSet(ResultSet rs) {
        try {
            Receipts entity = new Receipts();
            entity.setCodeReceiptString(rs.getString("CodeReceipt"));
            entity.setEmailUserString(rs.getString("EmailUser"));
            entity.setCodeCustormerString(rs.getString("CodeCustormer"));
            entity.setExportDate(rs.getDate("ExportDate"));
            entity.setDiscountInt(rs.getInt("Discount"));
            entity.setVATInt(rs.getInt("VAT"));
            return entity;
        } catch (SQLException e) {
            System.out.println("*ReadFromResultSet - (ReceiptDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (ReadFromResultSet - (ReceiptDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public void insert(Receipts entity) {
        try {
            DatabaseHelper.executeUpdate(INSERT,
                    entity.getCodeReceiptString(),
                    entity.getEmailUserString(),
                    entity.getCodeCustormerString(),
                    entity.getExportDate(),
                    entity.getDiscountInt(),
                    entity.getVATInt());
        } catch (Exception e) {
            System.out.println("*insert - (ReceiptDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (insert - (ReceiptDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public void update(Receipts entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Object primarykey) {
        try {
            DatabaseHelper.executeUpdate(DELETE, primarykey);
        } catch (Exception e) {
            System.out.println("*delete - (ReceiptDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (delete - (ReceiptDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public List<Receipts> select_All() {
        try {
            return select(SELECT_ALL);
        } catch (Exception e) {
            System.out.println("*select_All - (ReceiptDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_All - (ReceiptDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public List<Receipts> select_By_Fk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Receipts> select_By_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Receipts select_By_PK(Object primarykey) {
        try {
            List<Receipts> list = select(SELECT_BY_PK, primarykey);
            return list.size() > 0 ? list.get(0) : null;
        } catch (Exception e) {
            System.out.println("*select_By_PK - (ReceiptDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_By_PK - (ReceiptDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public Receipts select_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Receipts select_Fk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Receipts getEnityByPossition(Object index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
