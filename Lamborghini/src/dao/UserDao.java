package dao;

import helper.DatabaseHelper;
import helper.MessageDialogHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JDialog;
import model.Users;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author ACER
 */
public class UserDao extends EntityDAO<Users, Object> {

    private final String INSERT = "INSERT INTO [dbo].[Users] ([Email],[UserName],[PassWord],[Position],[Sex],[Birthday],[Address],[Avata],[Note])"
            + "VALUES (?,?,?,?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[Users] SET [UserName] = ?,[PassWord] = ?,[Position] = ?,[Sex] = ?,[Birthday] = ?,[Address] = ?,[Avata] = ?"
            + ",[Note] = ? WHERE [Email] = ?";
    private final String DELETE = "DELETE FROM [dbo].[Users] WHERE [Email] = ?";
    private final String SELECT_ALL = "SELECT * FROM [dbo].[Users]";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[Users] WHERE [Email] = ?";

    @Override
    public Users readFromResultSet(ResultSet rs) {
        try {
            Users entity = new Users();
            entity.setEmailString(rs.getString("Email"));
            entity.setUserNameString(rs.getString("UserName"));
            entity.setPassWordString(rs.getString("PassWord"));
            entity.setPositionBoolean(rs.getBoolean("Position"));
            entity.setSexBoolean(rs.getBoolean("Sex"));
            entity.setBirthDayDate(rs.getDate("Birthday"));
            entity.setAddressString(rs.getString("Address"));
            entity.setAvataString(rs.getString("Avata"));
            entity.setNoteString(rs.getString("Note"));
            return entity;
        } catch (SQLException e) {
            System.out.println("*ReadFromResultSet - (UserDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (ReadFromResultSet - (UserDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public void insert(Users entity) {
        try {
            DatabaseHelper.executeUpdate(INSERT,
                    entity.getEmailString(),
                    entity.getUserNameString(),
                    DigestUtils.md5Hex(entity.getPassWordString()),
                    entity.getPositionBoolean(),
                    entity.getSexBoolean(),
                    entity.getBirthDayDate(),
                    entity.getAddressString(),
                    entity.getAvataString(),
                    entity.getNoteString());
        } catch (Exception e) {
            System.out.println("*insert - (UserDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (insert - (UserDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public void update(Users entity) {
        try {
            DatabaseHelper.executeUpdate(UPDATE,
                    entity.getUserNameString(),
                    DigestUtils.md5Hex(entity.getPassWordString()),
                    entity.getPositionBoolean(),
                    entity.getSexBoolean(),
                    entity.getBirthDayDate(),
                    entity.getAddressString(),
                    entity.getAvataString(),
                    entity.getNoteString(),
                    entity.getEmailString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("*update - (UserDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (update - (UserDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public void delete(Object primarykey) {
        try {
            DatabaseHelper.executeUpdate(DELETE, primarykey);
        } catch (Exception e) {
            System.out.println("*delete - (UserDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (delete - (UserDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public List<Users> select_All() {
        try {
            return select(SELECT_ALL);
        } catch (Exception e) {
            System.out.println("*select_All - (UserDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_All - (UserDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public List<Users> select_By_Fk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Users> select_By_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Users select_By_PK(Object primarykey) {
        try {
            List<Users> list = select(SELECT_BY_PK, primarykey);
            return list.size() > 0 ? list.get(0) : null;
        } catch (Exception e) {
            System.out.println("*select_By_PK - (UserDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_By_PK - (UserDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public Users select_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Users select_Fk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Users getEnityByPossition(Object index) {
        List<Users> list = select_All();
        int id = Integer.parseInt((String) index);
        if (id >= 0 && id < list.size()) {
            return list.get(id);
        }
        return null;
    }

}
