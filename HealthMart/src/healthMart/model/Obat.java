package healthmart.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class Obat - Merepresentasikan obat yang dijual
 */
public class Obat implements Serializable, Comparable<Obat> {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String nama;
    private String deskripsi;
    private int harga;
    private int stok;
    private String kategori;
    
    /**
     * Constructor Obat dengan parameter minimal
     */
    public Obat(String id, String nama, int harga, int stok) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        this.deskripsi = "";
        this.kategori = "";
    }
    
    /**
     * Constructor Obat lengkap
     */
    public Obat(String id, String nama, String deskripsi, int harga, int stok, String kategori) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.stok = stok;
        this.kategori = kategori;
    }
    
    /**
     * Getter ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Setter ID
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Getter nama
     */
    public String getNama() {
        return nama;
    }
    
    /**
     * Setter nama
     */
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    /**
     * Getter deskripsi
     */
    public String getDeskripsi() {
        return deskripsi;
    }
    
    /**
     * Setter deskripsi
     */
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
    /**
     * Getter harga
     */
    public int getHarga() {
        return harga;
    }
    
    /**
     * Setter harga
     */
    public void setHarga(int harga) {
        this.harga = harga;
    }
    
    /**
     * Getter stok
     */
    public int getStok() {
        return stok;
    }
    
    /**
     * Setter stok
     */
    public void setStok(int stok) {
        this.stok = stok;
    }
    
    /**
     * Kurangi stok
     */
    public void kurangiStok(int jumlah) {
        if (this.stok >= jumlah) {
            this.stok -= jumlah;
        } else {
            throw new IllegalArgumentException("Stok tidak cukup");
        }
    }
    
    /**
     * Tambah stok
     */
    public void tambahStok(int jumlah) {
        this.stok += jumlah;
    }
    
    /**
     * Getter kategori
     */
    public String getKategori() {
        return kategori;
    }
    
    /**
     * Setter kategori
     */
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    
    /**
     * Method untuk cek apakah stok tersedia
     */
    public boolean isAvailable(int jumlah) {
        return stok >= jumlah;
    }
    
    @Override
    public int compareTo(Obat other) {
        return this.nama.compareTo(other.nama);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obat obat = (Obat) o;
        return Objects.equals(id, obat.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Obat{" +
                "id='" + id + '\'' +
                ", nama='" + nama + '\'' +
                ", harga=" + harga +
                ", stok=" + stok +
                ", kategori='" + kategori + '\'' +
                '}';
    }
}
