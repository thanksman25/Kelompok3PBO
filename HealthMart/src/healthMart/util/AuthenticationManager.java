package healthmart.util;

import healthmart.model.Akun;
import java.util.ArrayList;

/**
 * Class AuthenticationManager - Mengelola autentikasi user
 */
public class AuthenticationManager {
    
    private ArrayList<Akun> semuaAkun;
    
    /**
     * Constructor AuthenticationManager
     */
    public AuthenticationManager(ArrayList<Akun> akuns) {
        this.semuaAkun = new ArrayList<>(akuns);
    }
    
    /**
     * Login user
     */
    public Akun login(String username, String password) {
        for (Akun akun : semuaAkun) {
            if (akun.getUsername().equals(username)) {
                if (akun.getPassword().equals(password)) {
                    return akun;
                } else {
                    throw new RuntimeException("Password salah");
                }
            }
        }
        throw new RuntimeException("Username tidak ditemukan");
    }
    
    /**
     * Register customer baru
     */
    public boolean register(Akun akun) {
        // Cek apakah username sudah ada
        for (Akun a : semuaAkun) {
            if (a.getUsername().equals(akun.getUsername())) {
                throw new RuntimeException("Username sudah terdaftar");
            }
        }
        
        semuaAkun.add(akun);
        return true;
    }
    
    /**
     * Cek apakah username sudah ada
     */
    public boolean isUsernameExists(String username) {
        return semuaAkun.stream().anyMatch(a -> a.getUsername().equals(username));
    }
    
    /**
     * Get semua akun
     */
    public ArrayList<Akun> getAllAkun() {
        return new ArrayList<>(semuaAkun);
    }
}
