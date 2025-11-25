package healthmart.model;

import java.io.Serializable;

/**
 * Class Akun - Merepresentasikan akun user (Admin atau Customer)
 * Ini adalah superclass untuk Admin dan Customer
 */
public abstract class Akun implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String username;
    protected String password;
    protected String nama;
    protected String email;
    
    /**
     * Constructor Akun
     */
    public Akun(String username, String password, String nama, String email) {
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.email = email;
    }
    
    /**
     * Getter username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Setter username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Getter password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Setter password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Getter nama
     */
    public String getNama() {
        return nama;
    }
    
    /**
     * Setter nama
     */
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    /**
     * Getter email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Setter email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Abstract method - Setiap subclass harus implement ini
     */
    public abstract String getRole();
    
    @Override
    public String toString() {
        return "Akun{" +
                "username='" + username + '\'' +
                ", nama='" + nama + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
