package dao;

import helper.DatabaseHelper;
import helper.MessageDialogHelper;
import java.sql.ResultSet;
import java.util.List;
import model.Customers;
import java.sql.SQLException;
import javax.swing.JDialog;

/**
 *
 * @author ACER
 */
public class CustomerDao extends EntityDAO<Customers, Object> {

    private final String INSERT = "INSERT INTO [dbo].[Customers] ([CodeCustomer],[Name],[Email],[Phone],[Address],[Note],[Sex],[Brithday],[Return]) "
            + "VALUES (?,?,?,?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[Customers] SET [Name] = ?,[Email] = ?,[Phone] = ?,[Address] = ?,[Note] = ?,[Sex] = ?,[Brithday] = ?,[Return] = ? "
            + "WHERE [CodeCustomer] = ?";
    private final String DELETE = "DELETE FROM [dbo].[Customers] WHERE [CodeCustomer] = ?";
    private final String SELECT_ALL = "SELECT * FROM [dbo].[Customers]";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[Customers] WHERE [CodeCustomer] = ?";
    private final String SELECT_BY_NO_KEY = "SELECT * FROM [dbo].[Customers] WHERE [Phone] = ?";

    @Override
    public Customers readFromResultSet(ResultSet rs) {
        try {
            Customers entity = new Customers();
            entity.setCodeCustomerString(rs.getString("CodeCustomer"));
            entity.setNameString(rs.getString("Name"));
            entity.setEmailString(rs.getString("Email"));
            entity.setPhoneString(rs.getString("Phone"));
            entity.setAddressString(rs.getString("Address"));
            entity.setNoteString(rs.getString("Note"));
            entity.setSexBoolean(rs.getBoolean("Sex"));
            entity.setBirthDayDate(rs.getDate("Brithday"));
            entity.setReturnInt(rs.getInt("Return"));
            return entity;
        } catch (SQLException e) {
            System.out.println("*ReadFromResultSet - (CustomerDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (ReadFromResultSet - (CustomerDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public void insert(Customers entity) {
        try {
            DatabaseHelper.executeUpdate(INSERT,
                    entity.getCodeCustomerString(),
                    entity.getNameString(),
                    entity.getEmailString(),
                    entity.getPhoneString(),
                    entity.getAddressString(),
                    entity.getNoteString(),
                    entity.getSexBoolean(),
                    entity.getBirthDayDate(),
                    entity.getReturnInt());
        } catch (Exception e) {
            System.out.println("*insert - (CustomerDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (insert - (CustomerDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public void update(Customers entity) {
        try {
            DatabaseHelper.executeUpdate(UPDATE,
                    entity.getNameString(),
                    entity.getEmailString(),
                    entity.getPhoneString(),
                    entity.getAddressString(),
                    entity.getNoteString(),
                    entity.getSexBoolean(),
                    entity.getBirthDayDate(),
                    entity.getReturnInt(),
                    entity.getCodeCustomerString());
        } catch (Exception e) {
            System.out.println("*update - (CustomerDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (update - (CustomerDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public void delete(Object primarykey) {
        try {
            DatabaseHelper.executeUpdate(DELETE, primarykey);
        } catch (Exception e) {
            System.out.println("*delete - (CustomerDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (delete - (CustomerDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public List<Customers> select_All() {
        try {
            return select(SELECT_ALL);
        } catch (Exception e) {
            System.out.println("*select_All - (CustomerDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_All - (CustomerDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public List<Customers> select_By_Fk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Customers> select_By_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Customers select_By_PK(Object primarykey) {
        try {
            List<Customers> list = select(SELECT_BY_PK, primarykey);
            return list.size() > 0 ? list.get(0) : null;
        } catch (Exception e) {
            System.out.println("*select_By_PK - (CustomerDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_By_PK - (CustomerDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public Customers select_No_Key(Object keyType) {
        try {
            List<Customers> list = select(SELECT_BY_NO_KEY, keyType);
            return list.size() > 0 ? list.get(0) : null;
        } catch (Exception e) {
            System.out.println("*select_No_Key - (CustomerDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_No_Key - (CustomerDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public Customers select_Fk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Customers getEnityByPossition(Object index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
