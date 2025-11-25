package healthmart.view;

import healthmart.model.*;
import healthmart.controller.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * AdminDashboard.java
 * 
 * Panel dashboard admin untuk mengelola obat dan transaksi.
 * Menyediakan interface untuk menambah, mengedit, menghapus obat,
 * melihat daftar obat, dan mengelola transaksi.
 * 
 * @author HealthMart Development Team
 * @version 1.0
 */
public class AdminDashboard extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private AdminDriver adminDriver;
    private HealthMartApp app = null;
    private JTabbedPane tabbedPane;
    
    // Components untuk tab Kelola Obat
    private JTextField tfObatID;
    private JTextField tfNama;
    private JTextArea taDeskripsi;
    private JTextField tfHarga;
    private JTextField tfStok;
    private JTextField tfKategori;
    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;
    private JButton btnClear;
    private JList<String> listObat;
    
    // Components untuk tab Lihat Obat
    private JTable tableObat;
    private DefaultTableModel modelTableObat;
    
    // Components untuk tab Lihat Transaksi
    private JTable tableTransaksi;
    private DefaultTableModel modelTableTransaksi;
    private JButton btnKonfirmasi;
    private JButton btnTolak;
    
    // Components global
    private JButton btnLogout;
    private ListObat listObatData;
    
    /**
     * Constructor untuk AdminDashboard
     * 
     * @param adminDriver AdminDriver yang mengelola logika admin
     */
    public AdminDashboard(AdminDriver adminDriver) {
        this.adminDriver = adminDriver;
        this.listObatData = adminDriver.getListObat();
        initLayout();
    }

    public AdminDashboard(HealthMartApp app, AdminDriver adminDriver) {
        this(adminDriver);
        this.app = app;
    }

    private void initLayout() {
        setLayout(new BorderLayout());
        // header
        add(createHeaderPanel(), BorderLayout.NORTH);
        // tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Kelola Produk", createKelolaObatPanel());
        tabbedPane.addTab("Terima Transaksi", createLihatTransaksiPanel());
        tabbedPane.addTab("Laporan", createLihatObatPanel());
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    /**
     * Membuat panel untuk kelola obat
     * 
     * @return JPanel dengan form kelola obat
     */
    private JPanel createKelolaObatPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel form
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        
        formPanel.add(new JLabel("ID Obat:"));
        tfObatID = new JTextField(8);
        tfObatID.setEditable(true);
        formPanel.add(tfObatID);
        
        formPanel.add(new JLabel("Nama Obat:"));
        tfNama = new JTextField(15);
        formPanel.add(tfNama);
        
        formPanel.add(new JLabel("Deskripsi:"));
        taDeskripsi = new JTextArea(2, 15);
        taDeskripsi.setLineWrap(true);
        taDeskripsi.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(taDeskripsi));
        
        formPanel.add(new JLabel("Harga:"));
        tfHarga = new JTextField(12);
        formPanel.add(tfHarga);
        
        formPanel.add(new JLabel("Stok:"));
        tfStok = new JTextField(10);
        formPanel.add(tfStok);
        
        formPanel.add(new JLabel("Kategori:"));
        tfKategori = new JTextField(12);
        formPanel.add(tfKategori);
        
        // Panel untuk buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnClear);
        
        formPanel.add(buttonPanel);
        
        // Panel untuk list obat
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Daftar Obat"));
        listObat = new JList<>();
        JScrollPane scrollList = new JScrollPane(listObat);
        listPanel.add(scrollList, BorderLayout.CENTER);
        
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(listPanel, BorderLayout.CENTER);
        
        // Add action listeners
        btnTambah.addActionListener(e -> tambahObat());
        btnEdit.addActionListener(e -> editObat());
        btnHapus.addActionListener(e -> hapusObat());
        btnClear.addActionListener(e -> clearFormObat());
        listObat.addListSelectionListener(e -> loadObatToForm());
        
        refreshListObat();
        
        return panel;
    }
    
    /**
     * Membuat panel untuk melihat semua obat
     * 
     * @return JPanel dengan tabel obat
     */
    private JPanel createLihatObatPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table with columns: ID, Nama, Harga, Stok, Kategori
        String[] columns = {"ID", "Nama", "Harga", "Stok", "Kategori"};
        modelTableObat = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableObat = new JTable(modelTableObat);
        tableObat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableObat.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableObat.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableObat.getColumnModel().getColumn(2).setPreferredWidth(80);
        tableObat.getColumnModel().getColumn(3).setPreferredWidth(80);
        tableObat.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tableObat);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh data
        refreshTableObat();
        
        return panel;
    }
    
    /**
     * Membuat panel untuk melihat transaksi
     * 
     * @return JPanel dengan tabel transaksi dan tombol aksi
     */
    private JPanel createLihatTransaksiPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table with columns: ID, Customer, Total, Status, Tanggal
        String[] columns = {"ID", "Customer", "Total", "Status", "Tanggal"};
        modelTableTransaksi = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableTransaksi = new JTable(modelTableTransaksi);
        tableTransaksi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTransaksi.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableTransaksi.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableTransaksi.getColumnModel().getColumn(2).setPreferredWidth(80);
        tableTransaksi.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableTransaksi.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tableTransaksi);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        btnKonfirmasi = new JButton("Konfirmasi");
        btnTolak = new JButton("Tolak");
        buttonPanel.add(btnKonfirmasi);
        buttonPanel.add(btnTolak);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        btnKonfirmasi.addActionListener(e -> konfirmasiTransaksi());
        btnTolak.addActionListener(e -> tolakTransaksi());
        
        // Refresh data
        refreshTableTransaksi();
        
        return panel;
    }
    
    /**
     * Membuat button panel dengan tombol logout
     * 
     * @return JPanel dengan tombol logout
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLogout = new JButton("Logout");
        styleHeaderButton(btnLogout);
        btnLogout.addActionListener(e -> logout());
        panel.add(btnLogout);
        return panel;
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(56, 112, 160));
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        String name = "Administrator (Admin)";
        if (adminDriver != null && adminDriver.getAkun() != null) {
            name = adminDriver.getAkun().getNama() + " (Admin)";
        }

        JLabel welcome = new JLabel("Selamat datang, " + name);
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 16));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setOpaque(false);
        btnLogout = new JButton("Logout");
        styleHeaderButton(btnLogout);
        btnLogout.addActionListener(e -> logout());
        right.add(btnLogout);

        header.add(welcome, BorderLayout.CENTER);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    private void styleHeaderButton(JButton b) {
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)));
    }
    
    /**
     * Menambah obat baru
     */
    private void tambahObat() {
        try {
            String id = tfObatID.getText().trim();
            String nama = tfNama.getText().trim();
            String deskripsi = taDeskripsi.getText().trim();
            int harga = Integer.parseInt(tfHarga.getText().trim());
            int stok = Integer.parseInt(tfStok.getText().trim());
            String kategori = tfKategori.getText().trim();
            
            if (id.isEmpty() || nama.isEmpty() || kategori.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID, Nama, dan Kategori tidak boleh kosong!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (harga < 0 || stok < 0) {
                JOptionPane.showMessageDialog(this, "Harga dan Stok tidak boleh negatif!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Obat obat = new Obat(id, nama, deskripsi, harga, stok, kategori);
            adminDriver.tambahObat(obat);
            
            JOptionPane.showMessageDialog(this, "Obat berhasil ditambahkan!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFormObat();
            refreshTableObat();
            refreshListObat();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan Stok harus berupa angka!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Mengedit obat yang dipilih
     */
    private void editObat() {
        int selectedIndex = listObat.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Pilih obat terlebih dahulu!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String id = tfObatID.getText().trim();
            String nama = tfNama.getText().trim();
            String deskripsi = taDeskripsi.getText().trim();
            int harga = Integer.parseInt(tfHarga.getText().trim());
            int stok = Integer.parseInt(tfStok.getText().trim());
            String kategori = tfKategori.getText().trim();
            
            if (nama.isEmpty() || kategori.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama dan kategori tidak boleh kosong!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Obat obatBaru = new Obat(id, nama, deskripsi, harga, stok, kategori);
            adminDriver.editObat(id, obatBaru);
            
            JOptionPane.showMessageDialog(this, "Obat berhasil diperbarui!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFormObat();
            refreshTableObat();
            refreshListObat();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Menghapus obat yang dipilih
     */
    private void hapusObat() {
        int selectedIndex = listObat.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Pilih obat terlebih dahulu!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String id = tfObatID.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus obat ini?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                adminDriver.hapusObat(id);
                JOptionPane.showMessageDialog(this, "Obat berhasil dihapus!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormObat();
                refreshTableObat();
                refreshListObat();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Membersihkan form input
     */
    private void clearFormObat() {
        tfObatID.setText("");
        tfNama.setText("");
        taDeskripsi.setText("");
        tfHarga.setText("");
        tfStok.setText("");
        tfKategori.setText("");
        listObat.clearSelection();
    }
    
    /**
     * Memuat data obat ke form saat dipilih dari list
     */
    private void loadObatToForm() {
        int selectedIndex = listObat.getSelectedIndex();
        if (selectedIndex != -1) {
            ArrayList<Obat> daftarObat = listObatData.getAllObat();
            if (selectedIndex < daftarObat.size()) {
                Obat obat = daftarObat.get(selectedIndex);
                tfObatID.setText(obat.getId());
                tfNama.setText(obat.getNama());
                taDeskripsi.setText(obat.getDeskripsi());
                tfHarga.setText(String.valueOf(obat.getHarga()));
                tfStok.setText(String.valueOf(obat.getStok()));
                tfKategori.setText(obat.getKategori());
            }
        }
    }
    
    /**
     * Merefresh tabel obat dengan data terbaru
     */
    private void refreshTableObat() {
        modelTableObat.setRowCount(0);
        ArrayList<Obat> daftarObat = listObatData.getAllObat();
        for (Obat obat : daftarObat) {
            Object[] row = {
                obat.getId(),
                obat.getNama(),
                String.format("Rp %d", obat.getHarga()),
                obat.getStok(),
                obat.getKategori()
            };
            modelTableObat.addRow(row);
        }
    }
    
    /**
     * Merefresh list obat
     */
    private void refreshListObat() {
        ArrayList<Obat> daftarObat = listObatData.getAllObat();
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Obat obat : daftarObat) {
            model.addElement(obat.getId() + " - " + obat.getNama());
        }
        listObat.setModel(model);
    }
    
    /**
     * Merefresh tabel transaksi dengan data terbaru
     */
    private void refreshTableTransaksi() {
        modelTableTransaksi.setRowCount(0);
        ArrayList<Transaksi> semuaTransaksi = adminDriver.lihatSemuaTransaksi();
        if (semuaTransaksi != null) {
            for (Transaksi transaksi : semuaTransaksi) {
                Object[] row = {
                    transaksi.getIdTransaksi(),
                    transaksi.getNamaCustomer(),
                    String.format("Rp %.0f", transaksi.getTotalHarga()),
                    transaksi.getStatus().getLabel(),
                    transaksi.getTanggalTransaksi()
                };
                modelTableTransaksi.addRow(row);
            }
        }
    }
    
    /**
     * Mengkonfirmasi transaksi yang dipilih
     */
    private void konfirmasiTransaksi() {
        int selectedRow = tableTransaksi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi terlebih dahulu!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String idTransaksi = (String) modelTableTransaksi.getValueAt(selectedRow, 0);
        if (adminDriver.konfirmasiTransaksi(idTransaksi)) {
            JOptionPane.showMessageDialog(this, "Transaksi berhasil dikonfirmasi!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTableTransaksi();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengkonfirmasi transaksi!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Menolak transaksi yang dipilih
     */
    private void tolakTransaksi() {
        int selectedRow = tableTransaksi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi terlebih dahulu!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String idTransaksi = (String) modelTableTransaksi.getValueAt(selectedRow, 0);
        if (adminDriver.tolakTransaksi(idTransaksi)) {
            JOptionPane.showMessageDialog(this, "Transaksi berhasil ditolak!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTableTransaksi();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menolak transaksi!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Logout dari aplikasi
     */
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", 
            "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (app != null) {
                app.logout();
            } else {
                System.exit(0);
            }
        }
    }
}
