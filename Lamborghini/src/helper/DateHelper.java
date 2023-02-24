package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class DateHelper {

    public static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * chuuyển String sang Date
     *
     * @param date truyền vào date kiểu String
     * @param pattern truyền vào kiểu
     * @return trả về date kiểu Date
     */
    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {
                DATE_FORMATER.applyPattern(pattern[0]);
            }
            if (date == null) {
                return DateHelper.now();
            }
            return DATE_FORMATER.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * chuyển Date sang String
     *
     * @param date chuyền vào date kiểu date
     * @param pattern định dạng date
     * @return date kiểu String đã định theo dạng pattern
     */
    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            DATE_FORMATER.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = DateHelper.now();
        }
        return DATE_FORMATER.format(date);
    }

    /**
     * chuyển utl.Date sang sql.Date
     *
     * @param date chuyền vào date kiểu date
     * @return date kiểu sql.Date
     */
    public static Date toDate(Date date) {
        java.util.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }

    /**
     * Lấy thời gian hiện tại
     *
     * @return date
     */
    public static Date now() {
        return new Date();
    }
}
