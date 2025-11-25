package healthmart.model;

import java.io.Serializable;

/**
 * Class ItemKeranjang - Merepresentasikan item dalam keranjang
 */
public class ItemKeranjang implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Obat obat;
    private int jumlah;
    
    /**
     * Constructor ItemKeranjang
     */
    public ItemKeranjang(Obat obat, int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0");
        }
        this.obat = obat;
        this.jumlah = jumlah;
    }
    
    /**
     * Getter obat
     */
    public Obat getObat() {
        return obat;
    }
    
    /**
     * Getter jumlah
     */
    public int getJumlah() {
        return jumlah;
    }
    
    /**
     * Setter jumlah
     */
    public void setJumlah(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0");
        }
        this.jumlah = jumlah;
    }
    
    /**
     * Hitung subtotal
     */
    public double getSubtotal() {
        return obat.getHarga() * jumlah;
    }
    
    @Override
    public String toString() {
        return "ItemKeranjang{" +
                "obat=" + obat.getNama() +
                ", jumlah=" + jumlah +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}
