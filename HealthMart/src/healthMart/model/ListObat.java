package healthmart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class ListObat - Mengelola daftar obat menggunakan ArrayList
 */
public class ListObat implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Obat> daftarObat;
    
    /**
     * Constructor ListObat
     */
    public ListObat() {
        this.daftarObat = new ArrayList<>();
    }
    
    /**
     * Tambah obat ke dalam list
     */
    public void tambahObat(Obat obat) {
        if (!isDuplicate(obat.getId())) {
            daftarObat.add(obat);
        } else {
            throw new IllegalArgumentException("Obat dengan ID " + obat.getId() + " sudah ada");
        }
    }
    
    /**
     * Hapus obat dari list
     */
    public boolean hapusObat(String idObat) {
        return daftarObat.removeIf(obat -> obat.getId().equals(idObat));
    }
    
    /**
     * Cari obat berdasarkan ID
     */
    public Obat cariObatById(String id) {
        for (Obat obat : daftarObat) {
            if (obat.getId().equals(id)) {
                return obat;
            }
        }
        return null;
    }
    
    /**
     * Cari obat berdasarkan nama
     */
    public List<Obat> cariObatByNama(String nama) {
        List<Obat> hasil = new ArrayList<>();
        for (Obat obat : daftarObat) {
            if (obat.getNama().toLowerCase().contains(nama.toLowerCase())) {
                hasil.add(obat);
            }
        }
        return hasil;
    }
    
    /**
     * Dapatkan semua obat
     */
    public ArrayList<Obat> getAllObat() {
        return new ArrayList<>(daftarObat);
    }
    
    /**
     * Update obat
     */
    public boolean updateObat(String id, Obat obatBaru) {
        for (int i = 0; i < daftarObat.size(); i++) {
            if (daftarObat.get(i).getId().equals(id)) {
                daftarObat.set(i, obatBaru);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Cek apakah obat sudah ada (duplikat)
     */
    private boolean isDuplicate(String id) {
        return daftarObat.stream().anyMatch(o -> o.getId().equals(id));
    }
    
    /**
     * Dapatkan jumlah obat
     */
    public int getJumlahObat() {
        return daftarObat.size();
    }
    
    /**
     * Sort obat berdasarkan nama
     */
    public void sortByNama() {
        Collections.sort(daftarObat);
    }
    
    /**
     * Sort obat berdasarkan harga
     */
    public void sortByHarga() {
        daftarObat.sort((o1, o2) -> Double.compare(o1.getHarga(), o2.getHarga()));
    }
    
    @Override
    public String toString() {
        return "ListObat{" +
                "jumlahObat=" + daftarObat.size() +
                '}';
    }
}
