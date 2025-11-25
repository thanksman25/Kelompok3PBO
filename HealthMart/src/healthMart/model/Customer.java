package healthmart.model;

/**
 * Class Customer - Merepresentasikan akun Customer
 * Extends dari Akun dan mempunyai role CUSTOMER
 */
public class Customer extends Akun {
    private static final long serialVersionUID = 1L;
    
    private String alamat;
    private String noTelepon;
    
    /**
     * Constructor Customer
     */
    public Customer(String username, String password, String nama, String email) {
        super(username, password, nama, email);
        this.alamat = "";
        this.noTelepon = "";
    }
    
    /**
     * Constructor Customer dengan alamat dan nomor telepon
     */
    public Customer(String username, String password, String nama, String email, 
                   String alamat, String noTelepon) {
        super(username, password, nama, email);
        this.alamat = alamat;
        this.noTelepon = noTelepon;
    }
    
    /**
     * Getter alamat
     */
    public String getAlamat() {
        return alamat;
    }
    
    /**
     * Setter alamat
     */
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    /**
     * Getter nomor telepon
     */
    public String getNoTelepon() {
        return noTelepon;
    }
    
    /**
     * Setter nomor telepon
     */
    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }
    
    /**
     * Return role customer
     */
    @Override
    public String getRole() {
        return "CUSTOMER";
    }
    
    /**
     * Method untuk verifikasi kredensial customer
     */
    public boolean verifyCredentials(String password) {
        return this.password.equals(password);
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", nama='" + nama + '\'' +
                ", email='" + email + '\'' +
                ", alamat='" + alamat + '\'' +
                ", noTelepon='" + noTelepon + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}
