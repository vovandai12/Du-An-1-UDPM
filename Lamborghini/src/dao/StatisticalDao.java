package dao;

import helper.DatabaseHelper;
import helper.MessageDialogHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;

/**
 *
 * @author ACER
 */
public class StatisticalDao {

    private String SELECT_RECEIPT = "SELECT A.[CodeReceipt], B.[UserName], C.[Name], A.[ExportDate], \n"
            + "COUNT(D.[CodeRecieptDetail]) AS SOSANPHAM, C.[Return], A.[VAT],\n"
            + "((SUM(CONVERT(INT,E.[Price]))) - (SUM(CONVERT(INT,E.[Price])) * 0.2)) AS TONGTIEN\n"
            + "FROM [dbo].[Receipts] A\n"
            + "INNER JOIN [dbo].[Users] B ON B.[Email] = A.[EmailUser]\n"
            + "INNER JOIN [dbo].[Customers] C ON C.[CodeCustomer] = A.[CodeCustormer]\n"
            + "INNER JOIN [dbo].[ReceiptDetails] D ON D.[CodeRecieptDetail] = A.[CodeReceipt]\n"
            + "INNER JOIN [dbo].[Products] E ON E.[CodeProduct] = D.[CodeProduct]\n"
            + "GROUP BY B.[UserName], C.[Name], A.[ExportDate], C.[Return], A.[VAT], A.[CodeReceipt]";

    private String SELECT_RECEIPT_DETAIL = "SELECT A.[CodeProduct], B.[NameProduct], A.[Amount], B.[Price]\n"
            + "FROM [dbo].[ReceiptDetails] A\n"
            + "INNER JOIN [dbo].[Products] B ON A.[CodeProduct] = B.[CodeProduct]\n"
            + "WHERE A.[CodeRecieptDetail] = ?";
    private String SELECT_RECEIPT_MONEY = "SELECT SUM(CONVERT(INT,E.[Price])) AS TONGTIENSANPHAM, \n"
            + "C.[Return], (SUM(CONVERT(INT,E.[Price])) * ((C.[Return])/100)) AS TONGTIENTHUE,\n"
            + "A.[VAT], (SUM(CONVERT(INT,E.[Price])) * 0.2) AS TONGTIENVAT,\n"
            + "((SUM(CONVERT(INT,E.[Price]))) - ((SUM(CONVERT(INT,E.[Price])) * 0.2) - SUM(CONVERT(INT,E.[Price])) * ((C.[Return])/100))) AS TONGTIEN\n"
            + "FROM [dbo].[Receipts] A\n"
            + "INNER JOIN [dbo].[Users] B ON B.[Email] = A.[EmailUser]\n"
            + "INNER JOIN [dbo].[Customers] C ON C.[CodeCustomer] = A.[CodeCustormer]\n"
            + "INNER JOIN [dbo].[ReceiptDetails] D ON D.[CodeRecieptDetail] = A.[CodeReceipt]\n"
            + "INNER JOIN [dbo].[Products] E ON E.[CodeProduct] = D.[CodeProduct]\n"
            + "WHERE D.[CodeRecieptDetail] = ?\n"
            + "GROUP BY B.[UserName], C.[Name], A.[ExportDate], C.[Return], A.[VAT], A.[CodeReceipt]";
    private String SELECT_TOTAIL_MONEY = "SELECT \n"
            + "((SUM(CONVERT(INT,E.[Price]))) - ((SUM(CONVERT(INT,E.[Price])) * 0.2) - (SUM(CONVERT(INT,E.[Price])) * ((C.[Return])/100)))) AS TONGTIEN\n"
            + "FROM [dbo].[Receipts] A\n"
            + "INNER JOIN [dbo].[Users] B ON B.[Email] = A.[EmailUser]\n"
            + "INNER JOIN [dbo].[Customers] C ON C.[CodeCustomer] = A.[CodeCustormer]\n"
            + "INNER JOIN [dbo].[ReceiptDetails] D ON D.[CodeRecieptDetail] = A.[CodeReceipt]\n"
            + "INNER JOIN [dbo].[Products] E ON E.[CodeProduct] = D.[CodeProduct]\n"
            + "GROUP BY B.[UserName], C.[Name], A.[ExportDate], C.[Return], A.[VAT], A.[CodeReceipt]";
    private String SELECT_TOTAIL_MONEY_7 = "SELECT \n"
            + "((SUM(CONVERT(INT,E.[Price]))) - ((SUM(CONVERT(INT,E.[Price])) * 0.2) - (SUM(CONVERT(INT,E.[Price])) * ((C.[Return])/100)))) AS TONGTIEN\n"
            + "FROM [dbo].[Receipts] A\n"
            + "INNER JOIN [dbo].[Users] B ON B.[Email] = A.[EmailUser]\n"
            + "INNER JOIN [dbo].[Customers] C ON C.[CodeCustomer] = A.[CodeCustormer]\n"
            + "INNER JOIN [dbo].[ReceiptDetails] D ON D.[CodeRecieptDetail] = A.[CodeReceipt]\n"
            + "INNER JOIN [dbo].[Products] E ON E.[CodeProduct] = D.[CodeProduct]\n"
            + "WHERE MONTH(A.[ExportDate]) = 7\n"
            + "GROUP BY MONTH(A.[ExportDate]), C.[Return]";
    private String SELECT_COUNT_USER = "SELECT COUNT([Email]) AS COUNT_USER FROM [dbo].[Users]";
    private String SELECT_COUNT_EMAIL_USER = "SELECT COUNT(DISTINCT([EmailUser])) AS COUNT_EMAIL_USER FROM [dbo].[Receipts]";
    private String SELECT_COUNT_CUSTOMER = "SELECT COUNT([CodeCustomer]) AS COUNT_CUSTOMER FROM [dbo].[Customers]";
    private String SELECT_TOTAIL_CUSTOMER_7 = "SELECT COUNT(A.[CodeCustomer]) AS COUNT_CUSTOMER_7 \n"
            + "FROM [dbo].[Customers] A\n"
            + "JOIN [dbo].[Receipts] B ON A.[CodeCustomer] = B.[CodeCustormer]\n"
            + "WHERE MONTH(B.[ExportDate]) = 7";

    //Phương thức thống kê hóa đơn theo định dạng
    public List<Object[]> select_receipt(String key) {
        List<Object[]> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            switch (key) {
                case "All":
                    rs = DatabaseHelper.executeQuery(SELECT_RECEIPT);
                    break;
                case "MoneyDESC":
                    rs = DatabaseHelper.executeQuery(SELECT_RECEIPT + " ORDER BY TONGTIEN DESC");
                    break;
                case "MoneyASC":
                    rs = DatabaseHelper.executeQuery(SELECT_RECEIPT + " ORDER BY TONGTIEN ASC");
                    break;
                case "AmountDESC":
                    rs = DatabaseHelper.executeQuery(SELECT_RECEIPT + " ORDER BY SOSANPHAM DESC");
                    break;
                case "AmountASC":
                    rs = DatabaseHelper.executeQuery(SELECT_RECEIPT + " ORDER BY SOSANPHAM ASC");
                    break;
                case "DateDESC":
                    rs = DatabaseHelper.executeQuery(SELECT_RECEIPT + " ORDER BY A.[ExportDate] DESC");
                    break;
                case "DateASC":
                    rs = DatabaseHelper.executeQuery(SELECT_RECEIPT + " ORDER BY A.[ExportDate] ASC");
                    break;
                default:
                    break;
            }
            while (rs.next()) {
                Object[] model = {
                    rs.getString("CodeReceipt"),
                    rs.getString("UserName"),
                    rs.getString("Name"),
                    rs.getDate("ExportDate"),
                    rs.getInt("SOSANPHAM"),
                    rs.getInt("Return"),
                    rs.getInt("VAT"),
                    rs.getFloat("TONGTIEN")
                };
                list.add(model);
            }
            return list;
        } catch (Exception e) {
            System.out.println("*select_receipt - (StatisticalDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_receipt - (StatisticalDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    //Phương thức hiễn thị hóa đơn chi tiết
    public List<Object[]> select_receipt_detail(String key) {
        List<Object[]> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(SELECT_RECEIPT_DETAIL, key);
            int i = 1;
            while (rs.next()) {
                Object[] model = {
                    i,
                    rs.getString("CodeProduct"),
                    rs.getString("NameProduct"),
                    rs.getInt("Amount"),
                    rs.getString("Price")
                };
                list.add(model);
                i++;
            }
            return list;
        } catch (Exception e) {
            System.out.println("*select_receipt_detail - (StatisticalDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_receipt_detail - (StatisticalDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    //Phương thức hiễn thị hóa đơn chi tiết
    public List<Object[]> select_receipt_detail_money(String key) {
        List<Object[]> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(SELECT_RECEIPT_MONEY, key);
            while (rs.next()) {
                Object[] model = {
                    rs.getFloat("TONGTIENSANPHAM"),
                    rs.getInt("Return"),
                    rs.getFloat("TONGTIENTHUE"),
                    rs.getInt("VAT"),
                    rs.getFloat("TONGTIENVAT"),
                    rs.getFloat("TONGTIEN")
                };
                list.add(model);
            }
            return list;
        } catch (Exception e) {
            System.out.println("*select_receipt_detail_money - (StatisticalDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_receipt_detail_money - (StatisticalDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    //Phương thức tổng tiền
    public Float select_totail_money() {
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(SELECT_TOTAIL_MONEY);
            float total = 0;
            while (rs.next()) {
                total += rs.getFloat("TONGTIEN");
            }
            return total;
        } catch (Exception e) {
            System.out.println("*select_totail_money - (StatisticalDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_totail_money - (StatisticalDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    //Phương thức tổng tiền tháng 7
    public Float select_totail_money_7() {
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(SELECT_TOTAIL_MONEY_7);
            float total = 0;
            while (rs.next()) {
                total += rs.getFloat("TONGTIEN");
            }
            return total;
        } catch (Exception e) {
            System.out.println("*select_totail_money_7 - (StatisticalDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_totail_money_7 - (StatisticalDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    //Phương thức tổng user
    public Float select_count_user() {
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(SELECT_COUNT_USER);
            float user = 0;
            while (rs.next()) {
                user += rs.getFloat("COUNT_USER");
            }
            return user;
        } catch (Exception e) {
            System.out.println("*select_count_user - (StatisticalDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_count_user - (StatisticalDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    //Phương thức tổng user tham gia bán hàng
    public Float select_count_email_user() {
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(SELECT_COUNT_EMAIL_USER);
            float user = 0;
            while (rs.next()) {
                user += rs.getFloat("COUNT_EMAIL_USER");
            }
            return user;
        } catch (Exception e) {
            System.out.println("*select_count_email_user - (StatisticalDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_count_email_user - (StatisticalDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    //Phương thức tổng khách hàng
    public Float select_count_customer() {
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(SELECT_COUNT_CUSTOMER);
            float user = 0;
            while (rs.next()) {
                user += rs.getFloat("COUNT_CUSTOMER");
            }
            return user;
        } catch (Exception e) {
            System.out.println("*select_count_customer - (StatisticalDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_count_customer - (StatisticalDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }

    //Phương thức tổng khách hàng tháng 7
    public Float select_totail_customer_7() {
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(SELECT_TOTAIL_CUSTOMER_7);
            float user = 0;
            while (rs.next()) {
                user += rs.getFloat("COUNT_CUSTOMER_7");
            }
            return user;
        } catch (Exception e) {
            System.out.println("*select_totail_customer_7 - (StatisticalDao) - " + e.toString());
            MessageDialogHelper.showErrorDialog(new JDialog(), "Lỗi truy vấn (select_totail_customer_7 - (StatisticalDao))"
                    + e.toString(), "Lỗi truy vấn");
        }
        return null;
    }
}
