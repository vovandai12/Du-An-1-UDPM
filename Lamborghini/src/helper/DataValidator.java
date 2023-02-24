package helper;

import java.awt.Color;
import java.util.Random;
import java.util.regex.Pattern;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ACER
 */
public class DataValidator {

    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();

    /**
     * Định dạng sau lỗi
     *
     * @param field
     * @param error
     */
    public static void error(JTextField field, boolean error) {
        if (error) {
            field.setBackground(new Color(255, 255, 255));
            field.setForeground(new Color(0, 0, 0));
        } else {
            field.setBackground(new Color(255, 255, 0));
            field.setForeground(new Color(102, 0, 0));
            field.requestFocus();
        }
    }

    /**
     * Tạo chuỗi ngẫu nhiên
     *
     * @param numberOfCharactor - số kí tự
     * @return
     */
    public static String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    private static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    /**
     * Kiểm tra định dang ngày tháng
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateEmptyDate(JTextField field, StringBuilder sb, String errorMessage) {
        boolean isDate = false;
        String datePattern = "\\d{4}-\\d{1,2}-\\d{1,2}";
        isDate = field.getText().matches(datePattern);
        if (isDate) {
            error(field, true);
        } else {
            sb.append(errorMessage).append("\n");
            error(field, false);
        }
    }

    /**
     * Kiểm tra JTextField có bằng rỗng
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateEmpty(JTextField field, StringBuilder sb, String errorMessage) {
        if (field.getText().equals("")) {
            sb.append(errorMessage).append("\n");
            error(field, false);
        } else {
            error(field, true);
        }
    }

    /**
     * Kiểm tra jTextArea có bằng rỗng
     *
     * @param jTextArea
     * @param sb
     * @param errorMessage
     */
    public static void validateEmpty(JTextArea jTextArea, StringBuilder sb, String errorMessage) {
        if (jTextArea.getText().equals("")) {
            sb.append(errorMessage).append("\n");
            jTextArea.setBackground(new Color(255, 255, 0));
            jTextArea.setForeground(new Color(102, 0, 0));
            jTextArea.requestFocus();
        } else {
            jTextArea.setBackground(new Color(255, 255, 255));
            jTextArea.setForeground(new Color(0, 0, 0));
        }
    }

    /**
     * Kiểm tra JPasswordField có bằng rỗng
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateEmpty(JPasswordField field, StringBuilder sb, String errorMessage) {
        String password = new String(field.getPassword());
        if (password.equals("") || password.length() < 10) {
            sb.append(errorMessage).append("\n");
            error(field, false);
        } else {
            error(field, true);
        }
    }

    /**
     * Kiểm tra định dạng email
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateEmail(JTextField field, StringBuilder sb, String errorMessage) {
        String emailPattern = "^[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        if (pattern.matcher(field.getText()).find()) {
            error(field, true);
        } else {
            sb.append(errorMessage).append("\n");
            error(field, false);
        }
    }
    
    /**
     * Kiểm tra định dạng email
     *
     * @param email
     * @param sb
     * @param errorMessage
     */
    public static void validateEmail(String email, StringBuilder sb, String errorMessage) {
        String emailPattern = "^[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        if (pattern.matcher(email).find()) {
//            error(field, true);
        } else {
            sb.append(errorMessage).append("\n");
//            error(field, false);
        }
    }

    /**
     * Kiểm tra định dạng số điện thoại
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validatePhone(JTextField field, StringBuilder sb, String errorMessage) {
        String phonePattern = "(086|096|097|098|032|033|034|035|036|037|038|039|089|090|093|070|079|077|078|076|088|091|094|083|"
                + "084|085|081|082|092|056|058|099|059)[0-9]{7}";
        Pattern pattern = Pattern.compile(phonePattern);
        if (pattern.matcher(field.getText()).find()) {
            error(field, true);
        } else {
            sb.append(errorMessage).append("\n");
            error(field, false);
        }
    }

    /**
     * Kiểm tra định dạng họ và tên
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateCheckName(JTextField field, StringBuilder sb, String errorMessage) {
        if (field.getText().length() > 3 && field.getText().length() <= 50) {
            error(field, true);
        } else {
            sb.append(errorMessage).append("\n");
            error(field, false);
        }
    }

    /**
     * Kiểm tra chuỗi có phải là kiểu int
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateCheckInt(JTextField field, StringBuilder sb, String errorMessage) {
        try {
            int i = Integer.parseInt(field.getText());
            error(field, true);
        } catch (NumberFormatException e) {
            sb.append(errorMessage).append("\n");
            error(field, false);
        }
    }
    
    /**
     * Kiểm tra chuỗi có phải là kiểu long
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateCheckLong(JTextField field, StringBuilder sb, String errorMessage) {
        try {
            long i = Long.parseLong(field.getText());
            error(field, true);
        } catch (NumberFormatException e) {
            sb.append(errorMessage).append("\n");
            error(field, false);
        }
    }

    /**
     * Kiểm tra chuỗi có phải là kiểu float
     *
     * @param field
     * @param sb
     * @param errorMessage
     */
    public static void validateCheckFloat(JTextField field, StringBuilder sb, String errorMessage) {
        try {
            float i = Float.valueOf(field.getText());
            error(field, true);
        } catch (NumberFormatException e) {
            sb.append(errorMessage).append("\n");
            error(field, false);
        }
    }

    /**
     * Kiểm tra độ dài chuỗi
     *
     * @param field
     * @param sb
     * @param errorMessage
     * @param index
     */
    public static void validateEmptyLength(JTextField field, StringBuilder sb, String errorMessage, int index) {
        if (field.getText().equals("") || field.getText().length() <= index) {
            sb.append(errorMessage).append("\n");
            error(field, false);
        } else {
            error(field, true);
        }
    }

    /**
     * Kiểm tra 2 JTextField có bằng nhau
     *
     * @param field1
     * @param field
     * @return
     */
    public static Boolean validateEmpty(JTextField field1, JTextField field) {
        if (field.getText().equals(field1.getText())) {
            error(field, false);
            return false;
        } else {
            error(field, true);
            return true;
        }
    }
}
