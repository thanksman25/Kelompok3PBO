package healthmart.payment;

/**
 * Class QRIS - Implementasi metode pembayaran QRIS
 */
public class QRIS extends Pembayaran {
    
    private String qrisCode;
    private String merchantId;
    
    /**
     * Constructor QRIS
     */
    public QRIS(String idPembayaran, double jumlah, String qrisCode, String merchantId) {
        super(idPembayaran, jumlah);
        this.qrisCode = qrisCode;
        this.merchantId = merchantId;
    }
    
    /**
     * Verifikasi pembayaran QRIS
     */
    @Override
    public boolean verifikasiPembayaran() {
        // Simulasi verifikasi QRIS
        try {
            Thread.sleep(1000);
            this.isVerified = true;
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    
    /**
     * Dapatkan detail pembayaran QRIS
     */
    @Override
    public String getDetailPembayaran() {
        return "QRIS Code: " + qrisCode + "\n" +
               "Merchant ID: " + merchantId + "\n" +
               "Jumlah: Rp " + String.format("%.2f", jumlah);
    }
    
    /**
     * Dapatkan tipe pembayaran
     */
    @Override
    public String getTipePembayaran() {
        return "QRIS";
    }
    
    /**
     * Getter QRIS Code
     */
    public String getQrisCode() {
        return qrisCode;
    }
    
    /**
     * Getter Merchant ID
     */
    public String getMerchantId() {
        return merchantId;
    }
    
    @Override
    public String toString() {
        return "QRIS{" +
                "idPembayaran='" + idPembayaran + '\'' +
                ", jumlah=" + jumlah +
                ", isVerified=" + isVerified +
                ", qrisCode='" + qrisCode + '\'' +
                '}';
    }
}
