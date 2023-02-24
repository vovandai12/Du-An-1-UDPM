package helper;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import model.Users;

/**
 *
 * @author ACER
 */
public class ShareHelper {

    /**
     * Ảnh biểu tượng của ứng dụng, xuất hiện trên mọi cửa sổ
     */
    public static final Image APP_ICON;
    public static final ImageIcon APP_ICON_1;

    static {
        /**
         * Tải biểu tượng ứng dụng CÁCH TẢI ẢNH TỪ TRONG PROJECT icon là thư mục
         * con của src
         *
         */
        String file = "/icon/lamborghini.png";
        APP_ICON = new ImageIcon(ShareHelper.class.getResource(file)).getImage();
        APP_ICON_1 = new ImageIcon(ShareHelper.class.getResource(file));
    }

    /**
     * Đối tượng này chứa thông tin người sử dụng sau khi đăng nhnập
     */
    public static Users USER = null;

    /**
     * Xóa thông tin của người sử dụng khi có yêu cầu đăng xuất
     *
     * @return
     */
    public static boolean logoff() {
        ShareHelper.USER = null;
        return true;
    }

    /**
     * Kiểm tra xem đăng nhập hay chưa
     *
     * @return đăng nhập hay chưa
     */
    public static boolean authenticated() {
        return ShareHelper.USER != null;
    }

    /**
     * Sao chép file vào thư mục images (tạo nếu chưa có thư mục images)
     *
     * @param file là đối tượng file ảnh
     * @return chép được hay không
     */
    public static boolean saveLogo(File file) {
        File dir = new File("images");  //khai báo thư mục images ngang hàng với src
        // Tạo thư mục nếu chưa tồn tại
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir, file.getName());
        try {
            // Copy vào thư mục images (đè nếu đã tồn tại)
            Path source = Paths.get(file.getAbsolutePath());
            Path destination = Paths.get(newFile.getAbsolutePath());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Đọc hình ảnh trong thư mục images theo tenFile
     *
     * @param fileName
     * @param height
     * @param width
     * @return ImageIcon ảnh đọc được
     */
    public static ImageIcon readLogo(String fileName, int height, int width) {
        File path = new File("images", fileName);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }
}
