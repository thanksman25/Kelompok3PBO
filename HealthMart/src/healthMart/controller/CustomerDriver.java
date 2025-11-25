package healthmart.controller;

import healthmart.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class CustomerDriver - Controller untuk fungsi Customer
 */
public class CustomerDriver extends Driver {
    
    private Keranjang keranjang;
    private ArrayList<Transaksi> riwayatTransaksi;
    private ArrayList<Invoice> riwayatInvoice;
    
    /**
     * Constructor CustomerDriver
     */
    public CustomerDriver(Customer customer, ListObat listObat) {
        super(customer, listObat);
        this.keranjang = new Keranjang(customer.getUsername());
        this.riwayatTransaksi = new ArrayList<>();
        this.riwayatInvoice = new ArrayList<>();
    }
    
    /**
     * Lihat semua obat yang tersedia
     */
    public ArrayList<Obat> lihatSemuaObat() {
        return listObat.getAllObat();
    }
    
    /**
     * Cari obat berdasarkan nama
     */
    public List<Obat> cariObat(String nama) {
        return listObat.cariObatByNama(nama);
    }
    
    /**
     * Tambah obat ke keranjang
     */
    public boolean tambahKeKeranjang(String idObat, int jumlah) {
        Obat obat = listObat.cariObatById(idObat);
        if (obat != null) {
            if (obat.isAvailable(jumlah)) {
                keranjang.tambahItem(obat, jumlah);
                System.out.println("✓ " + obat.getNama() + " (qty: " + jumlah + ") ditambahkan ke keranjang");
                return true;
            } else {
                System.out.println("✗ Stok " + obat.getNama() + " tidak cukup");
                return false;
            }
        } else {
            System.out.println("✗ Obat tidak ditemukan");
            return false;
        }
    }
    
    /**
     * Lihat keranjang
     */
    public ArrayList<ItemKeranjang> lihatKeranjang() {
        return keranjang.getItems();
    }
    
    /**
     * Hapus item dari keranjang
     */
    public boolean hapusFromKeranjang(String idObat) {
        return keranjang.hapusItem(idObat);
    }
    
    /**
     * Update jumlah item di keranjang
     */
    public boolean updateKeranjang(String idObat, int jumlahBaru) {
        return keranjang.updateJumlah(idObat, jumlahBaru);
    }
    
    /**
     * Get total harga keranjang
     */
    public double getTotalKeranjang() {
        return keranjang.getTotalHarga();
    }
    
    /**
     * Get total jumlah item di keranjang
     */
    public int getTotalItemKeranjang() {
        return keranjang.getTotalJumlah();
    }
    
    /**
     * Checkout dan buat transaksi
     */
    public Transaksi checkout(String metodePayment) {
        if (keranjang.isEmpty()) {
            System.out.println("✗ Keranjang masih kosong");
            return null;
        }
        
        ArrayList<ItemKeranjang> items = keranjang.getItems();
        Transaksi transaksi = new Transaksi(
            ((Customer) akun).getUsername(),
            akun.getNama(),
            items,
            metodePayment
        );
        
        riwayatTransaksi.add(transaksi);
        System.out.println("✓ Transaksi berhasil dibuat: " + transaksi.getIdTransaksi());
        
        return transaksi;
    }
    
    /**
     * Kosongkan keranjang
     */
    public void kosongkanKeranjang() {
        keranjang.kosongkan();
        System.out.println("✓ Keranjang dikosongkan");
    }
    
    /**
     * Lihat riwayat transaksi
     */
    public ArrayList<Transaksi> lihatRiwayatTransaksi() {
        return new ArrayList<>(riwayatTransaksi);
    }
    
    /**
     * Lihat riwayat invoice
     */
    public ArrayList<Invoice> lihatRiwayatInvoice() {
        return new ArrayList<>(riwayatInvoice);
    }
    
    /**
     * Generate invoice dari transaksi yang dikonfirmasi
     */
    public Invoice generateInvoice(Transaksi transaksi) {
        if (transaksi.getStatus() == Transaksi.StatusTransaksi.CONFIRMED) {
            Invoice invoice = new Invoice(transaksi);
            riwayatInvoice.add(invoice);
            System.out.println("✓ Invoice berhasil dibuat: " + invoice.getNomorInvoice());
            return invoice;
        }
        return null;
    }
    
    /**
     * Tambah transaksi (untuk set riwayat dari database)
     */
    public void tambahRiwayatTransaksi(Transaksi transaksi) {
        riwayatTransaksi.add(transaksi);
    }
    
    /**
     * Tambah invoice (untuk set riwayat dari database)
     */
    public void tambahRiwayatInvoice(Invoice invoice) {
        riwayatInvoice.add(invoice);
    }
    
    @Override
    public void showMainMenu() {
        System.out.println("\n=== MENU CUSTOMER ===");
        System.out.println("1. Lihat Semua Obat");
        System.out.println("2. Cari Obat");
        System.out.println("3. Lihat Keranjang");
        System.out.println("4. Tambah ke Keranjang");
        System.out.println("5. Checkout");
        System.out.println("6. Lihat Riwayat Transaksi");
        System.out.println("7. Lihat Invoice");
        System.out.println("8. Logout");
    }
    
    @Override
    public String toString() {
        return "CustomerDriver{" +
                "akun=" + akun +
                ", keranjangItems=" + keranjang.getJumlahItemUnik() +
                ", riwayatTransaksi=" + riwayatTransaksi.size() +
                '}';
    }
}
