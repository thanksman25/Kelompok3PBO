package healthmart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Keranjang - Mengelola keranjang belanja customer
 */
public class Keranjang implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<ItemKeranjang> items;
    private String customerId;
    
    /**
     * Constructor Keranjang
     */
    public Keranjang(String customerId) {
        this.customerId = customerId;
        this.items = new ArrayList<>();
    }
    
    /**
     * Tambah item ke keranjang
     */
    public void tambahItem(Obat obat, int jumlah) {
        // Cek apakah obat sudah ada di keranjang
        for (ItemKeranjang item : items) {
            if (item.getObat().getId().equals(obat.getId())) {
                item.setJumlah(item.getJumlah() + jumlah);
                return;
            }
        }
        // Jika belum ada, tambah sebagai item baru
        items.add(new ItemKeranjang(obat, jumlah));
    }
    
    /**
     * Hapus item dari keranjang
     */
    public boolean hapusItem(String obatId) {
        return items.removeIf(item -> item.getObat().getId().equals(obatId));
    }
    
    /**
     * Update jumlah item
     */
    public boolean updateJumlah(String obatId, int jumlahBaru) {
        for (ItemKeranjang item : items) {
            if (item.getObat().getId().equals(obatId)) {
                if (jumlahBaru <= 0) {
                    items.remove(item);
                } else {
                    item.setJumlah(jumlahBaru);
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * Dapatkan semua item
     */
    public ArrayList<ItemKeranjang> getItems() {
        return new ArrayList<>(items);
    }
    
    /**
     * Hitung total harga
     */
    public double getTotalHarga() {
        double total = 0;
        for (ItemKeranjang item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
    
    /**
     * Hitung total jumlah item
     */
    public int getTotalJumlah() {
        int total = 0;
        for (ItemKeranjang item : items) {
            total += item.getJumlah();
        }
        return total;
    }
    
    /**
     * Kosongkan keranjang
     */
    public void kosongkan() {
        items.clear();
    }
    
    /**
     * Cek apakah keranjang kosong
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    /**
     * Dapatkan jumlah item unik
     */
    public int getJumlahItemUnik() {
        return items.size();
    }
    
    /**
     * Getter customerId
     */
    public String getCustomerId() {
        return customerId;
    }
    
    @Override
    public String toString() {
        return "Keranjang{" +
                "customerId='" + customerId + '\'' +
                ", jumlahItem=" + items.size() +
                ", totalHarga=" + getTotalHarga() +
                '}';
    }
}
