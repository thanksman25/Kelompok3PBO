package healthmart.controller;

import healthmart.model.*;
import java.util.ArrayList;

/**
 * Class AdminDriver - Controller untuk fungsi Admin
 */
public class AdminDriver extends Driver {
    
    private ArrayList<Transaksi> semuaTransaksi;
    
    /**
     * Constructor AdminDriver
     */
    public AdminDriver(Admin admin, ListObat listObat) {
        super(admin, listObat);
        this.semuaTransaksi = new ArrayList<>();
    }
    
    /**
     * Tambah obat baru
     */
    public void tambahObat(Obat obat) {
        try {
            listObat.tambahObat(obat);
            System.out.println("✓ Obat berhasil ditambahkan: " + obat.getNama());
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Gagal menambah obat: " + e.getMessage());
        }
    }
    
    /**
     * Edit obat
     */
    public boolean editObat(String idObat, Obat obatBaru) {
        if (listObat.updateObat(idObat, obatBaru)) {
            System.out.println("✓ Obat berhasil diupdate: " + obatBaru.getNama());
            return true;
        } else {
            System.out.println("✗ Obat dengan ID " + idObat + " tidak ditemukan");
            return false;
        }
    }
    
    /**
     * Hapus obat
     */
    public boolean hapusObat(String idObat) {
        if (listObat.hapusObat(idObat)) {
            System.out.println("✓ Obat berhasil dihapus");
            return true;
        } else {
            System.out.println("✗ Obat dengan ID " + idObat + " tidak ditemukan");
            return false;
        }
    }
    
    /**
     * Lihat semua obat
     */
    public ArrayList<Obat> lihatSemuaObat() {
        return listObat.getAllObat();
    }
    
    /**
     * Set transaksi untuk dikelola admin
     */
    public void setSemuaTransaksi(ArrayList<Transaksi> transaksi) {
        this.semuaTransaksi = new ArrayList<>(transaksi);
    }
    
    /**
     * Lihat semua transaksi
     */
    public ArrayList<Transaksi> lihatSemuaTransaksi() {
        return new ArrayList<>(semuaTransaksi);
    }
    
    /**
     * Konfirmasi transaksi
     */
    public boolean konfirmasiTransaksi(String idTransaksi) {
        for (Transaksi transaksi : semuaTransaksi) {
            if (transaksi.getIdTransaksi().equals(idTransaksi)) {
                if (transaksi.getStatus() == Transaksi.StatusTransaksi.PENDING) {
                    transaksi.konfirmasi();
                    // Kurangi stok obat
                    for (ItemKeranjang item : transaksi.getItems()) {
                        item.getObat().kurangiStok(item.getJumlah());
                    }
                    System.out.println("✓ Transaksi " + idTransaksi + " berhasil dikonfirmasi");
                    return true;
                } else {
                    System.out.println("✗ Transaksi tidak dalam status PENDING");
                    return false;
                }
            }
        }
        System.out.println("✗ Transaksi tidak ditemukan");
        return false;
    }
    
    /**
     * Tolak transaksi
     */
    public boolean tolakTransaksi(String idTransaksi) {
        for (Transaksi transaksi : semuaTransaksi) {
            if (transaksi.getIdTransaksi().equals(idTransaksi)) {
                if (transaksi.getStatus() == Transaksi.StatusTransaksi.PENDING) {
                    transaksi.tolak();
                    System.out.println("✓ Transaksi " + idTransaksi + " berhasil ditolak");
                    return true;
                } else {
                    System.out.println("✗ Transaksi tidak dalam status PENDING");
                    return false;
                }
            }
        }
        System.out.println("✗ Transaksi tidak ditemukan");
        return false;
    }
    
    /**
     * Dapatkan transaksi berdasarkan ID
     */
    public Transaksi getTransaksiById(String idTransaksi) {
        for (Transaksi transaksi : semuaTransaksi) {
            if (transaksi.getIdTransaksi().equals(idTransaksi)) {
                return transaksi;
            }
        }
        return null;
    }
    
    /**
     * Tambah transaksi
     */
    public void tambahTransaksi(Transaksi transaksi) {
        semuaTransaksi.add(transaksi);
    }
    
    @Override
    public void showMainMenu() {
        System.out.println("\n=== MENU ADMIN ===");
        System.out.println("1. Kelola Obat (Tambah/Edit/Hapus)");
        System.out.println("2. Lihat Semua Obat");
        System.out.println("3. Lihat Semua Transaksi");
        System.out.println("4. Konfirmasi/Tolak Transaksi");
        System.out.println("5. Logout");
    }
    
    @Override
    public String toString() {
        return "AdminDriver{" +
                "akun=" + akun +
                ", jumlahObat=" + listObat.getJumlahObat() +
                ", jumlahTransaksi=" + semuaTransaksi.size() +
                '}';
    }
}
