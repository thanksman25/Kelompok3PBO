package healthmart.payment;

/**
 * Class COD - Implementasi metode pembayaran Cash On Delivery
 */
public class COD extends Pembayaran {
    
    private String alamatPengiriman;
    private String nomorHpPenerima;
    private boolean isBayarDiTempat;
    
    /**
     * Constructor COD
     */
    public COD(String idPembayaran, double jumlah, String alamatPengiriman, 
               String nomorHpPenerima) {
        super(idPembayaran, jumlah);
        this.alamatPengiriman = alamatPengiriman;
        this.nomorHpPenerima = nomorHpPenerima;
        this.isBayarDiTempat = false;
    }
    
    /**
     * Verifikasi pembayaran COD
     */
    @Override
    public boolean verifikasiPembayaran() {
        // COD tidak perlu verifikasi pembayaran online
        // Pembayaran dilakukan saat barang diterima
        try {
            Thread.sleep(500);
            this.isVerified = true;
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    
    /**
     * Dapatkan detail pembayaran COD
     */
    @Override
    public String getDetailPembayaran() {
        return "Alamat Pengiriman: " + alamatPengiriman + "\n" +
               "Nomor HP Penerima: " + nomorHpPenerima + "\n" +
               "Jumlah: Rp " + String.format("%.2f", jumlah) + "\n" +
               "Status: Bayar di Tempat";
    }
    
    /**
     * Dapatkan tipe pembayaran
     */
    @Override
    public String getTipePembayaran() {
        return "Cash On Delivery (COD)";
    }
    
    /**
     * Getter Alamat Pengiriman
     */
    public String getAlamatPengiriman() {
        return alamatPengiriman;
    }
    
    /**
     * Getter Nomor HP Penerima
     */
    public String getNomorHpPenerima() {
        return nomorHpPenerima;
    }
    
    /**
     * Confirm pembayaran di tempat
     */
    public void confirmPembayaranDiTempat() {
        this.isBayarDiTempat = true;
    }
    
    /**
     * Cek apakah sudah bayar di tempat
     */
    public boolean isBayarDiTempat() {
        return isBayarDiTempat;
    }
    
    @Override
    public String toString() {
        return "COD{" +
                "idPembayaran='" + idPembayaran + '\'' +
                ", jumlah=" + jumlah +
                ", isVerified=" + isVerified +
                ", alamatPengiriman='" + alamatPengiriman + '\'' +
                '}';
    }
}
