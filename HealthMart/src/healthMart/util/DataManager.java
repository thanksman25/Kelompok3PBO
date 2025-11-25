package healthmart.util;

import healthmart.model.*;
import java.io.*;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class DataManager - Mengelola penyimpanan dan loading data (text files)
 */
public class DataManager {
    
    private static final String DATA_DIR = "data/";
    private static final String ADMIN_FILE = DATA_DIR + "admin.txt";
    private static final String CUSTOMER_FILE = DATA_DIR + "customer.txt";
    private static final String OBAT_FILE = DATA_DIR + "obat.txt";
    private static final String TRANSAKSI_FILE = DATA_DIR + "transaksi.txt";
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    /**
     * Constructor DataManager
     */
    public DataManager() {
        // Buat folder data jika belum ada
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Simpan admin ke file teks (username|password|nama|email) per baris
     */
    public boolean saveAdmin(ArrayList<Admin> admins) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
            for (Admin a : admins) {
                bw.write(escape(a.getUsername()) + "|" + escape(a.getPassword()) + "|" + escape(a.getNama()) + "|" + escape(a.getEmail()));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving admin: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load admin dari file teks
     */
    public ArrayList<Admin> loadAdmin() {
        ArrayList<Admin> res = new ArrayList<>();
        File f = new File(ADMIN_FILE);
        if (!f.exists()) return res;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                String username = unescape(parts.length>0?parts[0]:"");
                String password = unescape(parts.length>1?parts[1]:"");
                String nama = unescape(parts.length>2?parts[2]:"");
                String email = unescape(parts.length>3?parts[3]:"");
                res.add(new Admin(username, password, nama, email));
            }
        } catch (IOException e) {
            System.out.println("Admin file not found or error reading: " + e.getMessage());
        }
        return res;
    }
    
    /**
     * Simpan customer ke file teks (username|password|nama|email|alamat|noTelepon)
     */
    public boolean saveCustomer(ArrayList<Customer> customers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE))) {
            for (Customer c : customers) {
                bw.write(escape(c.getUsername()) + "|" + escape(c.getPassword()) + "|" + escape(c.getNama()) + "|" + escape(c.getEmail()) + "|" + escape(c.getAlamat()) + "|" + escape(c.getNoTelepon()));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving customer: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load customer dari file teks
     */
    public ArrayList<Customer> loadCustomer() {
        ArrayList<Customer> res = new ArrayList<>();
        File f = new File(CUSTOMER_FILE);
        if (!f.exists()) return res;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                String username = unescape(parts.length>0?parts[0]:"");
                String password = unescape(parts.length>1?parts[1]:"");
                String nama = unescape(parts.length>2?parts[2]:"");
                String email = unescape(parts.length>3?parts[3]:"");
                String alamat = unescape(parts.length>4?parts[4]:"");
                String noTel = unescape(parts.length>5?parts[5]:"");
                Customer c = new Customer(username, password, nama, email, alamat, noTel);
                res.add(c);
            }
        } catch (IOException e) {
            System.out.println("Customer file not found or error reading: " + e.getMessage());
        }
        return res;
    }
    
    /**
     * Simpan list obat ke file teks (id|nama|deskripsi|harga|stok|kategori)
     */
    public boolean saveObat(ListObat listObat) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OBAT_FILE))) {
            for (Obat o : listObat.getAllObat()) {
                bw.write(escape(o.getId()) + "|" + escape(o.getNama()) + "|" + escape(o.getDeskripsi()) + "|" + o.getHarga() + "|" + o.getStok() + "|" + escape(o.getKategori()));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving obat: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load list obat dari file teks
     */
    public ListObat loadObat() {
        ListObat list = new ListObat();
        File f = new File(OBAT_FILE);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                String id = unescape(parts.length>0?parts[0]:"");
                String nama = unescape(parts.length>1?parts[1]:"");
                String deskripsi = unescape(parts.length>2?parts[2]:"");
                int harga = 0;
                try { harga = Integer.parseInt(parts.length>3?parts[3]:"0"); } catch (NumberFormatException ex) {}
                int stok = 0;
                try { stok = Integer.parseInt(parts.length>4?parts[4]:"0"); } catch (NumberFormatException ex) {}
                String kategori = unescape(parts.length>5?parts[5]:"");
                list.tambahObat(new Obat(id, nama, deskripsi, harga, stok, kategori));
            }
        } catch (IOException e) {
            System.out.println("Obat file not found or error reading: " + e.getMessage());
        }
        return list;
    }
    
    /**
     * Simpan transaksi ke file teks.
     * Format per baris:
     * idTransaksi|customerId|namaCustomer|metodePayment|status|tanggalTransaksi|tanggalKonfirmasi|totalHarga|items
     * items => idObat:jumlah;idObat:jumlah
     */
    public boolean saveTransaksi(ArrayList<Transaksi> transaksis) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSAKSI_FILE))) {
            for (Transaksi t : transaksis) {
                StringBuilder itemsSb = new StringBuilder();
                for (ItemKeranjang it : t.getItems()) {
                    if (itemsSb.length() > 0) itemsSb.append(";");
                    itemsSb.append(escape(it.getObat().getId())).append(":" ).append(it.getJumlah());
                }
                String tanggal = t.getTanggalTransaksi() != null ? t.getTanggalTransaksi().format(ISO) : "";
                String tanggalKonf = t.getTanggalKonfirmasi() != null ? t.getTanggalKonfirmasi().format(ISO) : "";
                bw.write(escape(t.getIdTransaksi()) + "|" + escape(t.getCustomerId()) + "|" + escape(t.getNamaCustomer()) + "|" + escape(t.getMetodePayment()) + "|" + escape(t.getStatus().name()) + "|" + tanggal + "|" + tanggalKonf + "|" + t.getTotalHarga() + "|" + escape(itemsSb.toString()));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving transaksi: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Load transaksi dari file teks
     */
    public ArrayList<Transaksi> loadTransaksi() {
        ArrayList<Transaksi> res = new ArrayList<>();
        File f = new File(TRANSAKSI_FILE);
        if (!f.exists()) return res;
        // load obat map to resolve items
        ListObat obatList = loadObat();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                String idTrans = unescape(parts.length>0?parts[0]:"");
                String custId = unescape(parts.length>1?parts[1]:"");
                String namaCust = unescape(parts.length>2?parts[2]:"");
                String metode = unescape(parts.length>3?parts[3]:"");
                String statusStr = unescape(parts.length>4?parts[4]:"");
                String tanggalStr = parts.length>5?parts[5]:"";
                String tanggalKonfStr = parts.length>6?parts[6]:"";
                // totalHarga is parts[7] but will be recalculated
                String itemsStr = parts.length>8?unescape(parts[8]):"";

                ArrayList<ItemKeranjang> items = new ArrayList<>();
                if (!itemsStr.isEmpty()) {
                    String[] its = itemsStr.split(";");
                    for (String it : its) {
                        String[] p = it.split(":", -1);
                        String idOb = unescape(p.length>0?p[0]:"");
                        int qty = 0;
                        try { qty = Integer.parseInt(p.length>1?p[1]:"0"); } catch (NumberFormatException ex) {}
                        Obat obat = obatList.cariObatById(idOb);
                        if (obat != null) {
                            items.add(new ItemKeranjang(obat, qty));
                        }
                    }
                }
                LocalDateTime tgl = null;
                LocalDateTime tglKonf = null;
                try { if (!tanggalStr.isEmpty()) tgl = LocalDateTime.parse(tanggalStr, ISO); } catch (Exception ex) {}
                try { if (!tanggalKonfStr.isEmpty()) tglKonf = LocalDateTime.parse(tanggalKonfStr, ISO); } catch (Exception ex) {}
                Transaksi.StatusTransaksi status = Transaksi.StatusTransaksi.PENDING;
                try { status = Transaksi.StatusTransaksi.valueOf(statusStr); } catch (Exception ex) {}
                Transaksi t = new Transaksi(idTrans, custId, namaCust, items, metode, status, tgl, tglKonf);
                res.add(t);
            }
        } catch (IOException e) {
            System.out.println("Transaksi file not found or error reading: " + e.getMessage());
        }
        return res;
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("|", "\\|").replace(";", "\\;").replace(":", "\\:");
    }

    private String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\:", ":").replace("\\;", ";").replace("\\|", "|").replace("\\\\", "\\");
    }
}
