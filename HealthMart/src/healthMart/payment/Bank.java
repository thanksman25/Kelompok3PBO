package healthmart.payment;

/**
 * Class Bank - Implementasi metode pembayaran Bank Transfer
 */
public class Bank extends Pembayaran {
    
    private String namaBank;
    private String nomorRekeningPenerima;
    private String nomorRekeningPengirim;
    
    /**
     * Constructor Bank
     */
    public Bank(String idPembayaran, double jumlah, String namaBank, 
                String nomorRekeningPenerima, String nomorRekeningPengirim) {
        super(idPembayaran, jumlah);
        this.namaBank = namaBank;
        this.nomorRekeningPenerima = nomorRekeningPenerima;
        this.nomorRekeningPengirim = nomorRekeningPengirim;
    }
    
    /**
     * Verifikasi pembayaran Bank Transfer
     */
    @Override
    public boolean verifikasiPembayaran() {
        // Simulasi verifikasi bank transfer
        try {
            Thread.sleep(2000);
            this.isVerified = true;
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    
    /**
     * Dapatkan detail pembayaran Bank
     */
    @Override
    public String getDetailPembayaran() {
        return "Bank: " + namaBank + "\n" +
               "Rekening Tujuan: " + nomorRekeningPenerima + "\n" +
               "Rekening Pengirim: " + nomorRekeningPengirim + "\n" +
               "Jumlah: Rp " + String.format("%.2f", jumlah);
    }
    
    /**
     * Dapatkan tipe pembayaran
     */
    @Override
    public String getTipePembayaran() {
        return "Bank Transfer";
    }
    
    /**
     * Getter Nama Bank
     */
    public String getNamaBank() {
        return namaBank;
    }
    
    /**
     * Getter Nomor Rekening Penerima
     */
    public String getNomorRekeningPenerima() {
        return nomorRekeningPenerima;
    }
    
    /**
     * Getter Nomor Rekening Pengirim
     */
    public String getNomorRekeningPengirim() {
        return nomorRekeningPengirim;
    }
    
    @Override
    public String toString() {
        return "Bank{" +
                "idPembayaran='" + idPembayaran + '\'' +
                ", jumlah=" + jumlah +
                ", isVerified=" + isVerified +
                ", namaBank='" + namaBank + '\'' +
                '}';
    }
}
