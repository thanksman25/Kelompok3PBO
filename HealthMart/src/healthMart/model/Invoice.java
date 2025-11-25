package healthmart.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Class Invoice - Merepresentasikan invoice/struk pembelian
 */
public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nomorInvoice;
    private String customerId;
    private String namaCustomer;
    private ArrayList<ItemKeranjang> items;
    private double subtotal;
    private double pajak;
    private double total;
    private String metodePayment;
    private LocalDateTime tanggalInvoice;
    
    /**
     * Constructor Invoice dari Transaksi
     */
    public Invoice(Transaksi transaksi) {
        this.nomorInvoice = "INV" + transaksi.getIdTransaksi();
        this.customerId = transaksi.getCustomerId();
        this.namaCustomer = transaksi.getNamaCustomer();
        this.items = transaksi.getItems();
        this.metodePayment = transaksi.getMetodePayment();
        this.tanggalInvoice = transaksi.getTanggalTransaksi();
        
        // Hitung subtotal
        this.subtotal = transaksi.getTotalHarga();
        
        // Hitung pajak (PPN 10%)
        this.pajak = subtotal * 0.10;
        
        // Hitung total
        this.total = subtotal + pajak;
    }
    
    /**
     * Getter Nomor Invoice
     */
    public String getNomorInvoice() {
        return nomorInvoice;
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
     * Getter Subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }
    
    /**
     * Getter Pajak
     */
    public double getPajak() {
        return pajak;
    }
    
    /**
     * Getter Total
     */
    public double getTotal() {
        return total;
    }
    
    /**
     * Getter Metode Payment
     */
    public String getMetodePayment() {
        return metodePayment;
    }
    
    /**
     * Getter Tanggal Invoice
     */
    public LocalDateTime getTanggalInvoice() {
        return tanggalInvoice;
    }
    
    /**
     * Format tanggal untuk display
     */
    public String getTanggalInvoiceFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return tanggalInvoice.format(formatter);
    }
    
    /**
     * Generate invoice text untuk ditampilkan
     */
    public String generateInvoiceText() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("           HEALTHMART - INVOICE\n");
        sb.append("========================================\n");
        sb.append("Invoice #: ").append(nomorInvoice).append("\n");
        sb.append("Tanggal: ").append(getTanggalInvoiceFormat()).append("\n");
        sb.append("----------------------------------------\n");
        sb.append("Customer: ").append(namaCustomer).append("\n");
        sb.append("ID Customer: ").append(customerId).append("\n");
        sb.append("----------------------------------------\n");
        sb.append("DAFTAR PEMBELIAN:\n");
        sb.append("----------------------------------------\n");
        
        int no = 1;
        for (ItemKeranjang item : items) {
            sb.append(no).append(". ").append(item.getObat().getNama()).append("\n");
            sb.append("   Qty: ").append(item.getJumlah())
                    .append(" x Rp ").append(String.format("%.2f", item.getObat().getHarga()))
                    .append(" = Rp ").append(String.format("%.2f", item.getSubtotal()))
                    .append("\n");
            no++;
        }
        
        sb.append("----------------------------------------\n");
        sb.append("Subtotal: Rp ").append(String.format("%.2f", subtotal)).append("\n");
        sb.append("Pajak (10%): Rp ").append(String.format("%.2f", pajak)).append("\n");
        sb.append("----------------------------------------\n");
        sb.append("TOTAL: Rp ").append(String.format("%.2f", total)).append("\n");
        sb.append("Metode Pembayaran: ").append(metodePayment).append("\n");
        sb.append("========================================\n");
        sb.append("     Terima Kasih Telah Berbelanja\n");
        sb.append("========================================\n");
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return "Invoice{" +
                "nomorInvoice='" + nomorInvoice + '\'' +
                ", customerId='" + customerId + '\'' +
                ", total=" + total +
                ", tanggalInvoice=" + tanggalInvoice +
                '}';
    }
}
