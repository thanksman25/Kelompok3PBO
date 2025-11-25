package healthmart.util;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Class ValidationUtil - Utility untuk validasi input
 */
public class ValidationUtil {
    
    private static final String EMAIL_PATTERN = 
        "^[A-Za-z0-9+_.-]+@(.+)$";
    
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    
    /**
     * Validasi email
     */
    public static boolean isValidEmail(String email) {
        return email != null && pattern.matcher(email).matches();
    }
    
    /**
     * Validasi username
     */
    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && !username.contains(" ");
    }
    
    /**
     * Validasi password
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 4;
    }
    
    /**
     * Validasi nama
     */
    public static boolean isValidNama(String nama) {
        return nama != null && nama.length() >= 3;
    }
    
    /**
     * Validasi harga
     */
    public static boolean isValidHarga(double harga) {
        return harga > 0;
    }
    
    /**
     * Validasi stok
     */
    public static boolean isValidStok(int stok) {
        return stok >= 0;
    }
    
    /**
     * Validasi nomor
     */
    public static boolean isValidNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validasi double
     */
    public static boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
