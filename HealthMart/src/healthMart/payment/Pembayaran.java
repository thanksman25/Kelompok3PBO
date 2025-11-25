package healthmart.payment;

/**
 * Abstract class Pembayaran - Base class untuk semua metode pembayaran
 * Mendemonstrasikan Abstraction dan Polymorphism dalam OOP
 */
public abstract class Pembayaran {
    
    protected String idPembayaran;
    protected double jumlah;
    protected boolean isVerified;
    
    /**
     * Constructor Pembayaran
     */
    public Pembayaran(String idPembayaran, double jumlah) {
        this.idPembayaran = idPembayaran;
        this.jumlah = jumlah;
        this.isVerified = false;
    }
    
    /**
     * Abstract method - Setiap metode pembayaran harus implement
     */
    public abstract boolean verifikasiPembayaran();
    
    /**
     * Abstract method - Detail metode pembayaran
     */
    public abstract String getDetailPembayaran();
    
    /**
     * Abstract method - Tipe pembayaran
     */
    public abstract String getTipePembayaran();
    
    /**
     * Getter ID Pembayaran
     */
    public String getIdPembayaran() {
        return idPembayaran;
    }
    
    /**
     * Getter Jumlah
     */
    public double getJumlah() {
        return jumlah;
    }
    
    /**
     * Getter Status Verifikasi
     */
    public boolean isVerified() {
        return isVerified;
    }
    
    /**
     * Setter Status Verifikasi
     */
    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
