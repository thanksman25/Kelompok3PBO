package healthmart.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Class Transaksi - Merepresentasikan transaksi penjualan
 */
public class Transaksi implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum StatusTransaksi {
        PENDING("Pending"),
        CONFIRMED("Dikonfirmasi"),
        REJECTED("Ditolak"),
        COMPLETED("Selesai");
        
        private final String label;
        
        StatusTransaksi(String label) {
            this.label = label;
        }
        
        public String getLabel() {
            return label;
        }
    }
    
    private String idTransaksi;
    private String customerId;
    private String namaCustomer;
    private ArrayList<ItemKeranjang> items;
    private double totalHarga;
    private String metodePayment;
    private StatusTransaksi status;
    private LocalDateTime tanggalTransaksi;
    private LocalDateTime tanggalKonfirmasi;
    
    /**
     * Constructor Transaksi (new transaction)
     */
    public Transaksi(String customerId, String namaCustomer, ArrayList<ItemKeranjang> items, String metodePayment) {
        this.idTransaksi = "TRX" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);
        this.customerId = customerId;
        this.namaCustomer = namaCustomer;
        this.items = new ArrayList<>(items);
        this.metodePayment = metodePayment;
        this.status = StatusTransaksi.PENDING;
        this.tanggalTransaksi = LocalDateTime.now();
        this.tanggalKonfirmasi = null;
        this.totalHarga = hitungTotal();
    }

    /**
     * Constructor Transaksi (for loading from storage)
     */
    public Transaksi(String idTransaksi, String customerId, String namaCustomer, ArrayList<ItemKeranjang> items,
                     String metodePayment, StatusTransaksi status, LocalDateTime tanggalTransaksi,
                     LocalDateTime tanggalKonfirmasi) {
        this.idTransaksi = idTransaksi;
        this.customerId = customerId;
        this.namaCustomer = namaCustomer;
        this.items = new ArrayList<>(items);
        this.metodePayment = metodePayment;
        this.status = status != null ? status : StatusTransaksi.PENDING;
        this.tanggalTransaksi = tanggalTransaksi != null ? tanggalTransaksi : LocalDateTime.now();
        this.tanggalKonfirmasi = tanggalKonfirmasi;
        this.totalHarga = hitungTotal();
    }
    
    /**
     * Hitung total harga
     */
    private double hitungTotal() {
        double total = 0;
        for (ItemKeranjang item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
    
    /**
     * Getter ID Transaksi
     */
    public String getIdTransaksi() {
        return idTransaksi;
    }
    
    /**
     * Getter Customer ID
     */
    public String getCustomerId() {
        return customerId;
    }
    
    /**
     * Getter Nama Customer
     */
    public String getNamaCustomer() {
        return namaCustomer;
    }
    
    /**
     * Getter Items
     */
    public ArrayList<ItemKeranjang> getItems() {
        return new ArrayList<>(items);
    }
    
    /**
     * Getter Total Harga
     */
    public double getTotalHarga() {
        return totalHarga;
    }
    
    /**
     * Getter Metode Payment
     */
    public String getMetodePayment() {
        return metodePayment;
    }
    
    /**
     * Getter Status
     */
    public StatusTransaksi getStatus() {
        return status;
    }
    
    /**
     * Setter Status
     */
    public void setStatus(StatusTransaksi status) {
        this.status = status;
        if (status == StatusTransaksi.CONFIRMED) {
            this.tanggalKonfirmasi = LocalDateTime.now();
        }
    }
    
    /**
     * Getter Tanggal Transaksi
     */
    public LocalDateTime getTanggalTransaksi() {
        return tanggalTransaksi;
    }
    
    /**
     * Getter Tanggal Konfirmasi
     */
    public LocalDateTime getTanggalKonfirmasi() {
        return tanggalKonfirmasi;
    }
    
    /**
     * Format tanggal untuk display
     */
    public String getTanggalTransaksiFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return tanggalTransaksi.format(formatter);
    }
    
    /**
     * Konfirmasi transaksi
     */
    public void konfirmasi() {
        if (this.status == StatusTransaksi.PENDING) {
            this.status = StatusTransaksi.CONFIRMED;
            this.tanggalKonfirmasi = LocalDateTime.now();
        }
    }
    
    /**
     * Tolak transaksi
     */
    public void tolak() {
        if (this.status == StatusTransaksi.PENDING) {
            this.status = StatusTransaksi.REJECTED;
        }
    }
    
    /**
     * Selesaikan transaksi
     */
    public void selesaikan() {
        if (this.status == StatusTransaksi.CONFIRMED) {
            this.status = StatusTransaksi.COMPLETED;
        }
    }
    
    @Override
    public String toString() {
        return "Transaksi{" +
                "idTransaksi='" + idTransaksi + '\'' +
                ", customerId='" + customerId + '\'' +
                ", totalHarga=" + totalHarga +
                ", metodePayment='" + metodePayment + '\'' +
                ", status=" + status +
                '}';
    }
}
