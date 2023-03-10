package helper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
public class DatabaseHelper {

//    public static void main(String[] args) throws Exception {
//        Connection conn = openConnection();
//        if (conn != null) {
//            System.out.println("Connected");
//            DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
//            System.out.println("Driver name: " + dm.getDriverName());
//            System.out.println("Driver version: " + dm.getDriverVersion());
//            System.out.println("Product name: " + dm.getDatabaseProductName());
//            System.out.println("Product version: " + dm.getDatabaseProductVersion());
//        }
//    }
    /**
     * Kết nối CSDL trả về Connection
     *
     * @return
     * @throws java.lang.Exception
     */
//    public static Connection openConnection() throws Exception {
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        String connectionUrl = "jdbc:sqlserver://localhost;database=LAMBORGHINIS";
//        String username = "sa";
//        String password = "sa";
//        Connection conn = DriverManager.getConnection(connectionUrl, username, password);
//        return conn;
//    }
    public static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=LAMBORGHINIS";
    public static String username = "sa";
    public static String password = "sa";

    //nạp driver
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * nạp truyền giá trị đối số vào prepareStatement có thể statment không có
     * đối số prepareStatement có thể là prepareStatement hoặc
     * CallableStatements
     *
     * @param sql câu lệnh sql statement (có đối số hoặc không)
     * @param args mảng đối số của câu lệnh sql (có hoặc không)
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static PreparedStatement preparedStatement(String sql, Object... args) throws SQLException, Exception {
        Connection con = DriverManager.getConnection(dburl, username, password);
        PreparedStatement pstmt = null;
        if (sql.startsWith("{")) {
            pstmt = con.prepareCall(sql);    //có thể gán biến kiểu PreparedStatement là prepareCall (CallableStatement)
        } else {
            pstmt = con.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    /**
     * thao tác (INSERT, UPDATE, DELETE) thực thi prepareStatement (đã được
     * truyền đối số ở hàm trên) khi làm chỉ cần gọi hàm này, từ trong hàm này
     * nó sẽ gọi ra hàm preparedStatement ở trên
     *
     * @param sql (String) câu lệnh sql statement (có đối số hoặc không)
     * @param args mảng đối số của câu lệnh sql (có hoặc không)
     * @throws java.lang.Exception
     */
    public static void executeUpdate(String sql, Object... args) throws Exception {
        try {
            PreparedStatement pstmt = preparedStatement(sql, args);
            try {
                pstmt.executeUpdate();
            } finally {
                pstmt.getConnection().close();            //đóng Connection từ statement
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * thao tác truy vấn (SELECT) thực thi prepareStatement (đã được truyền đối
     * số ở hàm trên) khi làm chỉ cần gọi hàm này, từ trong hàm này nó sẽ gọi ra
     * hàm preparedStatement ở trên
     *
     * @param sql (String) câu lệnh sql statement (có đối số hoặc không)
     * @param args mảng đối số của câu lệnh sql (có hoặc không)
     * @return
     * @throws java.lang.Exception
     */
    public static ResultSet executeQuery(String sql, Object... args) throws Exception {
        try {
            PreparedStatement pstmt = preparedStatement(sql, args);
            try {
                return pstmt.executeQuery();
            } finally {
                //pstmt.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);         //throw các lỗi khi chạy CT, VD không có return khi try bị lỗi
        }
    }
}
