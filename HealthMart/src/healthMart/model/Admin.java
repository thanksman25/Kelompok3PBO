package healthmart.model;

/**
 * Class Admin - Merepresentasikan akun Admin
 * Extends dari Akun dan mempunyai role ADMIN
 */
public class Admin extends Akun {
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor Admin
     */
    public Admin(String username, String password, String nama, String email) {
        super(username, password, nama, email);
    }
    
    /**
     * Return role admin
     */
    @Override
    public String getRole() {
        return "ADMIN";
    }
    
    /**
     * Method untuk verifikasi kredensial admin
     */
    public boolean verifyCredentials(String password) {
        return this.password.equals(password);
    }
    
    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", nama='" + nama + '\'' +
                ", email='" + email + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}
