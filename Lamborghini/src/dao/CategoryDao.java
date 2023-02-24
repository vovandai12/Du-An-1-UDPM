package dao;

import helper.DatabaseHelper;
import helper.MessageDialogHelper;
import java.sql.ResultSet;
import java.util.List;
import model.Categoris;
import java.sql.SQLException;
import javax.swing.JDialog;

/**
 *
 * @author ACER
 */
public class CategoryDao extends EntityDAO<Categoris, Object> {

    private final String INSERT = "INSERT INTO [dbo].[Categoris] ([CodeCate],[NameList],[CategoryType]) VALUES (?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[Categoris] SET [NameList] = ?,[CategoryType] = ? WHERE [CodeCate] = ?";
    private final String DELETE = "DELETE FROM [dbo].[Categoris] WHERE [CodeCate] = ?";
    private final String SELECT_ALL = "SELECT * FROM [dbo].[Categoris]";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[Categoris] WHERE [CodeCate] = ?";

    @Override
    public Categoris readFromResultSet(ResultSet rs) {
        try {
            Categoris entity = new Categoris();
            entity.setCodeCateString(rs.getString("CodeCate"));
            entity.setNameListString(rs.getString("NameList"));
            entity.setCategoryTypeString(rs.getString("CategoryType"));
            return entity;
        } catch (SQLException e) {
            System.out.println("*ReadFromResultSet - (CategoryDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (ReadFromResultSet - (CategoryDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public void insert(Categoris entity) {
        try {
            DatabaseHelper.executeUpdate(INSERT,
                    entity.getCodeCateString(),
                    entity.getNameListString(),
                    entity.getCategoryTypeString());
        } catch (Exception e) {
            System.out.println("*insert - (CategoryDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (insert - (CategoryDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public void update(Categoris entity) {
        try {
            DatabaseHelper.executeUpdate(UPDATE,
                    entity.getNameListString(),
                    entity.getCategoryTypeString(),
                    entity.getCodeCateString());
        } catch (Exception e) {
            System.out.println("*update - (CategoryDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (update - (CategoryDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public void delete(Object primarykey) {
        try {
            DatabaseHelper.executeUpdate(DELETE, primarykey);
        } catch (Exception e) {
            System.out.println("*delete - (CategoryDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (delete - (CategoryDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public List<Categoris> select_All() {
        try {
            return select(SELECT_ALL);
        } catch (Exception e) {
            System.out.println("*select_All - (CategoryDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_All - (CategoryDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public List<Categoris> select_By_Fk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Categoris> select_By_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Categoris select_By_PK(Object primarykey) {
        try {
            List<Categoris> list = select(SELECT_BY_PK, primarykey);
            return list.size() > 0 ? list.get(0) : null;
        } catch (Exception e) {
            System.out.println("*select_By_PK - (CategoryDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_By_PK - (CategoryDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public Categoris select_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Categoris select_Fk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Categoris getEnityByPossition(Object index) {
        List<Categoris> list = select_All();
        int id = Integer.parseInt((String) index);
        if (id >= 0 && id < list.size()) {
            return list.get(id);
        }
        return null;
    }

}
