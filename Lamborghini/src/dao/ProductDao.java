package dao;

import helper.DatabaseHelper;
import helper.MessageDialogHelper;
import java.sql.ResultSet;
import java.util.List;
import model.Products;
import java.sql.SQLException;
import javax.swing.JDialog;

/**
 *
 * @author ACER
 */
public class ProductDao extends EntityDAO<Products, Object> {

    private final String INSERT = "INSERT INTO [dbo].[Products]([CodeProduct],[NameProduct],[Categoris],[Price],[Picture1],[Picture2],[Picture3]"
            + ",[Avata],[ImportDate],[Color],[Note],[Amount],[ProductionDay]) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE [dbo].[Products] SET [NameProduct] = ?,[Categoris] = ?,[Price] = ?,[Picture1] = ?,[Picture2] = ?"
            + ",[Picture3] = ?,[Avata] = ?,[ImportDate] = ?,[Color] = ?,[Note] = ?,[Amount] = ?,[ProductionDay] = ? WHERE [CodeProduct] = ?";
    private final String DELETE = "DELETE FROM [dbo].[Products] WHERE [CodeProduct] = ?";
    private final String SELECT_ALL = "SELECT * FROM [dbo].[Products]";
    private final String SELECT_BY_PK = "SELECT * FROM [dbo].[Products] WHERE [CodeProduct] = ?";
    private final String SELECT_BY_FK = "SELECT * FROM [dbo].[Products] WHERE [Categoris] = ?";

    @Override
    public Products readFromResultSet(ResultSet rs) {
        try {
            Products entity = new Products();
            entity.setCodeProductString(rs.getString("CodeProduct"));
            entity.setNameProductString(rs.getString("NameProduct"));
            entity.setCodeCateString(rs.getString("Categoris"));
            entity.setPriceString(rs.getString("Price"));
            entity.setPicture1String(rs.getString("Picture1"));
            entity.setPicture2String(rs.getString("Picture2"));
            entity.setPicture3String(rs.getString("Picture3"));
            entity.setAvataString(rs.getString("Avata"));
            entity.setImportDate(rs.getDate("ImportDate"));
            entity.setColorString(rs.getString("Color"));
            entity.setNoteString(rs.getString("Note"));
            entity.setAmountInt(rs.getInt("Amount"));
            entity.setProductionDate(rs.getDate("ProductionDay"));
            return entity;
        } catch (SQLException e) {
            System.out.println("*ReadFromResultSet - (ProductDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (ReadFromResultSet - (ProductDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public void insert(Products entity) {
        try {
            DatabaseHelper.executeUpdate(INSERT,
                    entity.getCodeProductString(),
                    entity.getNameProductString(),
                    entity.getCodeCateString(),
                    entity.getPriceString(),
                    entity.getPicture1String(),
                    entity.getPicture2String(),
                    entity.getPicture3String(),
                    entity.getAvataString(),
                    entity.getImportDate(),
                    entity.getColorString(),
                    entity.getNoteString(),
                    entity.getAmountInt(),
                    entity.getProductionDate());
        } catch (Exception e) {
            System.out.println("*insert - (ProductDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (insert - (ProductDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public void update(Products entity) {
        try {
            DatabaseHelper.executeUpdate(UPDATE,
                    entity.getNameProductString(),
                    entity.getCodeCateString(),
                    entity.getPriceString(),
                    entity.getPicture1String(),
                    entity.getPicture2String(),
                    entity.getPicture3String(),
                    entity.getAvataString(),
                    entity.getImportDate(),
                    entity.getColorString(),
                    entity.getNoteString(),
                    entity.getAmountInt(),
                    entity.getProductionDate(),
                    entity.getCodeProductString());
        } catch (Exception e) {
            System.out.println("*update - (ProductDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (update - (ProductDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public void delete(Object primarykey) {
        try {
            DatabaseHelper.executeUpdate(DELETE, primarykey);
        } catch (Exception e) {
            System.out.println("*delete - (ProductDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (delete - (ProductDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
    }

    @Override
    public List<Products> select_All() {
        try {
            return select(SELECT_ALL);
        } catch (Exception e) {
            System.out.println("*select_All - (ProductDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_All - (ProductDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public List<Products> select_By_Fk(Object foreignkey) {
        try {
            return select(SELECT_BY_FK, foreignkey);
        } catch (Exception e) {
            System.out.println("*select_By_Fk - (ProductDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_By_Fk - (ProductDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public List<Products> select_By_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Products select_By_PK(Object primarykey) {
        try {
            List<Products> list = select(SELECT_BY_PK, primarykey);
            return list.size() > 0 ? list.get(0) : null;
        } catch (Exception e) {
            System.out.println("*select_By_PK - (ProductDao) ->" + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_By_PK - (ProductDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    @Override
    public Products select_No_Key(Object keyType) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Products select_Fk(Object foreignkey) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Products getEnityByPossition(Object index) {
        List<Products> list = select_All();
        int id = Integer.parseInt((String) index);
        if (id >= 0 && id < list.size()) {
            return list.get(id);
        }
        return null;
    }

}
