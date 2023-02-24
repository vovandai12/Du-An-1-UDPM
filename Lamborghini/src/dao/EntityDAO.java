package dao;

import helper.DatabaseHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
abstract class EntityDAO<EnityType, KeyType> {

    /**
     * Đọc dữ liệu
     *
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract EnityType readFromResultSet(ResultSet rs);

    /**
     * Đọc dữ liệu và thêm vào list
     *
     * @param sql là câu lệnh truy vấn
     * @param args mảng dữ liệu
     * @return
     */
    public List<EnityType> select(String sql, Object... args) {
        List<EnityType> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = DatabaseHelper.executeQuery(sql, args);
                while (rs.next()) {
                    list.add(readFromResultSet(rs));
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (Exception ex) {
            throw new RuntimeException();
        }
        return list;
    }

    /**
     * Thêm mới thực thể vào CSDL
     *
     * @param EnityType là thực thể chứa thông tin bản ghi
     */
    public abstract void insert(EnityType entity);

    /**
     * Cập nhập thực thể vào CSDL
     *
     * @param EnityType là thực thể chứa thông tin bản ghi
     */
    public abstract void update(EnityType entity);

    /**
     * Xoá thực thể vào CSDL
     *
     * @param KeyType là khoá chính bảng ghi
     */
    public abstract void delete(KeyType primarykey);

    /**
     * Trả về danh sách thực thể
     *
     * @param EnityType là thực thể chứa thông tin bản ghi
     */
    public abstract List<EnityType> select_All();

    /**
     * Trả về list
     *
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract List<EnityType> select_By_Fk(KeyType foreignkey);

    /**
     * Trả về list
     *
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract List<EnityType> select_By_No_Key(KeyType keyType);

    /**
     * Trả về thực thể
     *
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract EnityType select_By_PK(KeyType primarykey);

    /**
     * Trả về thực thể
     *
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract EnityType select_No_Key(KeyType keyType);

    /**
     * Trả về thực thể
     *
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract EnityType select_Fk(KeyType foreignkey);

    /**
     * Trả về vị trí thực thể
     *
     * @param EnityType là thực thể chứa thông tin bản ghi
     * @param KeyType là khoá bảng ghi
     */
    public abstract EnityType getEnityByPossition(KeyType index);
}
