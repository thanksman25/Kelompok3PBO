package healthmart.view;

import healthmart.model.*;
import healthmart.controller.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * CustomerDashboard.java
 * 
 * Panel dashboard customer untuk melihat obat, mengelola keranjang,
 * melakukan checkout, dan melihat riwayat transaksi.
 * 
 * @author HealthMart Development Team
 * @version 1.0
 */
public class CustomerDashboard extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private CustomerDriver customerDriver;
    private HealthMartApp app = null;
    private JTabbedPane tabbedPane;
    
    // Components untuk tab Lihat Obat
    private JTable tableLihatObat;
    private DefaultTableModel modelTableLihatObat;
    private JSpinner spinnerJumlah;
    private JButton btnTambahKeranjang;
    
    // Components untuk tab Keranjang
    private JTable tableKeranjang;
    private DefaultTableModel modelTableKeranjang;
    private JButton btnHapusItem;
    private JButton btnUpdateQty;
    private JButton btnCheckout;
    private JLabel lblTotalHarga;
    
    // Components untuk tab Checkout
    private JRadioButton rbQRIS;
    private JRadioButton rbBankTransfer;
    private JRadioButton rbCOD;
    private ButtonGroup bgMetodePembayaran;
    private JTextArea taDetailCheckout;
    private JButton btnKonfirmasiCheckout;
    
    // Components untuk tab Riwayat
    private JTable tableRiwayat;
    private DefaultTableModel modelTableRiwayat;
    
    // Components global
    private JButton btnLogout;
    private ListObat listObatData;
    
    /**
     * Constructor untuk CustomerDashboard
     * 
     * @param customerDriver CustomerDriver yang mengelola logika customer
     */
    public CustomerDashboard(CustomerDriver customerDriver) {
        this.customerDriver = customerDriver;
        this.listObatData = customerDriver.getListObat();
        initLayout();
    }

    public CustomerDashboard(HealthMartApp app, CustomerDriver customerDriver) {
        this(customerDriver);
        this.app = app;
    }

    private void initLayout() {
        setLayout(new BorderLayout());
        add(createHeaderPanel(), BorderLayout.NORTH);
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Lihat Produk", createLihatObatPanel());
        tabbedPane.addTab("Belanja", createKeranjangPanel());
        tabbedPane.addTab("Aktivitas Pembelian", createCheckoutPanel());
        tabbedPane.addTab("Riwayat Transaksi", createRiwayatPanel());
        add(tabbedPane, BorderLayout.CENTER);

        // Initial refresh
        refreshTablObat();
        refreshTableKeranjang();
        refreshTableRiwayat();
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(56, 112, 160));
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        String name = "a (Customer)";
        if (customerDriver != null && customerDriver.getAkun() != null) {
            name = customerDriver.getAkun().getNama() + " (Customer)";
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
     * Membuat panel untuk melihat semua obat
     * 
     * @return JPanel dengan tabel obat dan tombol tambah keranjang
     */
    private JPanel createLihatObatPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table with columns: ID, Nama, Harga, Stok
        String[] columns = {"ID", "Nama", "Harga", "Stok"};
        modelTableLihatObat = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableLihatObat = new JTable(modelTableLihatObat);
        tableLihatObat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableLihatObat.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableLihatObat.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableLihatObat.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableLihatObat.getColumnModel().getColumn(3).setPreferredWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(tableLihatObat);
        
        // Panel untuk tambah ke keranjang
        JPanel tambahPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        tambahPanel.setBorder(BorderFactory.createTitledBorder("Tambah ke Keranjang"));
        
        tambahPanel.add(new JLabel("Jumlah:"));
        spinnerJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        tambahPanel.add(spinnerJumlah);
        
        btnTambahKeranjang = new JButton("Tambah ke Keranjang");
        btnTambahKeranjang.addActionListener(e -> tambahKeKeranjang());
        tambahPanel.add(btnTambahKeranjang);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(tambahPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Membuat panel untuk keranjang belanja
     * 
     * @return JPanel dengan tabel keranjang dan tombol aksi
     */
    private JPanel createKeranjangPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table with columns: Obat, Qty, Subtotal
        String[] columns = {"ID Obat", "Nama Obat", "Qty", "Harga Satuan", "Subtotal"};
        modelTableKeranjang = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableKeranjang = new JTable(modelTableKeranjang);
        tableKeranjang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableKeranjang.getColumnModel().getColumn(0).setPreferredWidth(60);
        tableKeranjang.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableKeranjang.getColumnModel().getColumn(2).setPreferredWidth(50);
        tableKeranjang.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableKeranjang.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tableKeranjang);
        
        // Panel untuk total dan buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        
        // Panel untuk total
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(new JLabel("Total Harga:"));
        lblTotalHarga = new JLabel("Rp 0");
        lblTotalHarga.setFont(new Font("Arial", Font.BOLD, 14));
        totalPanel.add(lblTotalHarga);
        
        // Panel untuk buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        btnHapusItem = new JButton("Hapus Item");
        btnHapusItem.addActionListener(e -> hapusItemKeranjang());
        buttonPanel.add(btnHapusItem);
        
        btnUpdateQty = new JButton("Update Qty");
        btnUpdateQty.addActionListener(e -> updateQtyKeranjang());
        buttonPanel.add(btnUpdateQty);
        
        btnCheckout = new JButton("Checkout");
        btnCheckout.addActionListener(e -> goToCheckout());
        buttonPanel.add(btnCheckout);
        
        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Membuat panel untuk checkout
     * 
     * @return JPanel dengan form checkout
     */
    private JPanel createCheckoutPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel untuk pilih metode pembayaran
        JPanel metodePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        metodePanel.setBorder(BorderFactory.createTitledBorder("Pilih Metode Pembayaran"));
        
        bgMetodePembayaran = new ButtonGroup();
        
        rbQRIS = new JRadioButton("QRIS");
        rbQRIS.setSelected(true);
        rbQRIS.addActionListener(e -> updateCheckoutDetail());
        bgMetodePembayaran.add(rbQRIS);
        metodePanel.add(rbQRIS);
        
        rbBankTransfer = new JRadioButton("Bank Transfer");
        rbBankTransfer.addActionListener(e -> updateCheckoutDetail());
        bgMetodePembayaran.add(rbBankTransfer);
        metodePanel.add(rbBankTransfer);
        
        rbCOD = new JRadioButton("Cash on Delivery (COD)");
        rbCOD.addActionListener(e -> updateCheckoutDetail());
        bgMetodePembayaran.add(rbCOD);
        metodePanel.add(rbCOD);
        
        // Panel untuk detail checkout
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("Detail Checkout"));
        
        taDetailCheckout = new JTextArea(10, 40);
        taDetailCheckout.setEditable(false);
        taDetailCheckout.setLineWrap(true);
        taDetailCheckout.setWrapStyleWord(true);
        taDetailCheckout.setFont(new Font("Monospaced", Font.PLAIN, 11));
        
        JScrollPane scrollPane = new JScrollPane(taDetailCheckout);
        detailPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button konfirmasi checkout
        btnKonfirmasiCheckout = new JButton("Konfirmasi Checkout");
        btnKonfirmasiCheckout.setPreferredSize(new Dimension(200, 40));
        btnKonfirmasiCheckout.addActionListener(e -> konfirmasiCheckout());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnKonfirmasiCheckout);
        
        panel.add(metodePanel, BorderLayout.NORTH);
        panel.add(detailPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Membuat panel untuk riwayat transaksi
     * 
     * @return JPanel dengan tabel riwayat transaksi
     */
    private JPanel createRiwayatPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table with columns: ID, Total, Status, Tanggal
        String[] columns = {"ID Transaksi", "Total", "Status", "Tanggal", "Metode"};
        modelTableRiwayat = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableRiwayat = new JTable(modelTableRiwayat);
        tableRiwayat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableRiwayat.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableRiwayat.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableRiwayat.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableRiwayat.getColumnModel().getColumn(3).setPreferredWidth(150);
        tableRiwayat.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tableRiwayat);
        panel.add(scrollPane, BorderLayout.CENTER);
        
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
        btnLogout.setPreferredSize(new Dimension(100, 40));
        btnLogout.addActionListener(e -> logout());
        panel.add(btnLogout);
        return panel;
    }
    
    /**
     * Menambah obat ke keranjang
     */
    private void tambahKeKeranjang() {
        int selectedRow = tableLihatObat.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih obat terlebih dahulu!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String idObat = (String) modelTableLihatObat.getValueAt(selectedRow, 0);
            int jumlah = (Integer) spinnerJumlah.getValue();
            
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (customerDriver.tambahKeKeranjang(idObat, jumlah)) {
                JOptionPane.showMessageDialog(this, "Obat berhasil ditambahkan ke keranjang!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTableKeranjang();
                spinnerJumlah.setValue(1);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan obat ke keranjang (stok mungkin tidak cukup)!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Menghapus item dari keranjang
     */
    private void hapusItemKeranjang() {
        int selectedRow = tableKeranjang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih item terlebih dahulu!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String idObat = (String) modelTableKeranjang.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus item ini?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                customerDriver.hapusFromKeranjang(idObat);
                JOptionPane.showMessageDialog(this, "Item berhasil dihapus!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTableKeranjang();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Update quantity item keranjang
     */
    private void updateQtyKeranjang() {
        int selectedRow = tableKeranjang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih item terlebih dahulu!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String input = JOptionPane.showInputDialog(this, "Masukkan jumlah baru:");
            if (input != null) {
                int jumlahBaru = Integer.parseInt(input);
                if (jumlahBaru <= 0) {
                    JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String idObat = (String) modelTableKeranjang.getValueAt(selectedRow, 0);
                customerDriver.updateKeranjang(idObat, jumlahBaru);
                refreshTableKeranjang();
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input harus berupa angka!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Pindah ke tab checkout
     */
    private void goToCheckout() {
        if (customerDriver.lihatKeranjang().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang kosong! Tambahkan item terlebih dahulu.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        updateCheckoutDetail();
        tabbedPane.setSelectedIndex(2); // Tab Checkout
    }
    
    /**
     * Update detail checkout
     */
    private void updateCheckoutDetail() {
        ArrayList<ItemKeranjang> items = customerDriver.lihatKeranjang();
        StringBuilder sb = new StringBuilder();
        
        sb.append("=== DETAIL CHECKOUT ===\n\n");
        sb.append("Item dalam Keranjang:\n");
        sb.append("------------------------\n");
        
        double total = 0;
        
        for (ItemKeranjang item : items) {
            Obat obat = item.getObat();
            double subtotal = obat.getHarga() * item.getJumlah();
            total += subtotal;
            
            sb.append(String.format("%s\n", obat.getNama()));
            sb.append(String.format("  Qty: %d x Rp %d = Rp %.0f\n", 
                item.getJumlah(), obat.getHarga(), subtotal));
        }
        
        sb.append("------------------------\n");
        sb.append(String.format("TOTAL: Rp %.0f\n\n", total));
        sb.append("Metode Pembayaran:\n");
        if (rbQRIS.isSelected()) {
            sb.append("✓ QRIS\n");
            sb.append("\nKlik 'Konfirmasi Checkout' untuk melanjutkan.\n");
        } else if (rbBankTransfer.isSelected()) {
            sb.append("✓ Bank Transfer\n");
            sb.append("\nDetail rekening akan ditampilkan saat konfirmasi.\n");
        } else if (rbCOD.isSelected()) {
            sb.append("✓ Cash on Delivery (COD)\n");
            sb.append("\nAlamat pengiriman akan dikonfirmasi saat checkout.\n");
        }
        
        taDetailCheckout.setText(sb.toString());
    }
    
    /**
     * Konfirmasi checkout
     */
    private void konfirmasiCheckout() {
        ArrayList<ItemKeranjang> items = customerDriver.lihatKeranjang();
        
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang kosong!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String metodePayment = "";
        if (rbQRIS.isSelected()) {
            metodePayment = "QRIS";
            // Show QRIS code confirmation
            String qrisCode = "00020126440014ID.CO.SHOPEE.WWW01051234567890050300000000055405100005802ID5913HEALTHMART6009JAKARTA62410513TRX12345678\n\n[QR CODE PLACEHOLDER]";
            int result = JOptionPane.showConfirmDialog(this, 
                "Kode QRIS:\n\n" + qrisCode + "\n\nSilakan scan dengan aplikasi pembayaran Anda.\n\nTekan 'Ya' setelah menyelesaikan pembayaran.", 
                "Pembayaran QRIS", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        } else if (rbBankTransfer.isSelected()) {
            metodePayment = "Bank Transfer";
            // Show bank details confirmation
            String bankInfo = "Detail Transfer:\n\n" +
                "Bank: BNI (Bank Negara Indonesia)\n" +
                "No. Rekening: 1234567890\n" +
                "Atas Nama: HealthMart Store\n" +
                "Cabang: Jakarta Pusat\n\n" +
                "Mohon transfer sesuai nominal yang tertera.\n" +
                "Konfirmasi akan diproses dalam 1x24 jam.\n\n" +
                "Tekan 'Ya' setelah transfer berhasil.";
            int result = JOptionPane.showConfirmDialog(this, bankInfo, 
                "Detail Bank Transfer", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        } else if (rbCOD.isSelected()) {
            metodePayment = "COD";
            // Show COD delivery address confirmation
            String custAddress = "Alamat tidak tersedia";
            if (customerDriver != null && customerDriver.getAkun() instanceof Customer) {
                Customer customer = (Customer) customerDriver.getAkun();
                custAddress = customer.getAlamat() + "\n" + customer.getNoTelepon();
            }
            String codInfo = "Pengiriman COD (Cash on Delivery):\n\n" +
                "Alamat Pengiriman:\n" + custAddress + "\n\n" +
                "Pembayaran dilakukan saat barang diterima.\n" +
                "Kurir akan menghubungi Anda sebelum tiba.\n\n" +
                "Tekan 'Ya' untuk melanjutkan.";
            int result = JOptionPane.showConfirmDialog(this, codInfo, 
                "Konfirmasi Alamat COD", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        try {
            Transaksi transaksi = customerDriver.checkout(metodePayment);
            if (transaksi != null) {
                // Add to global transaksi list (app) so it persists and is visible to admin
                if (app != null) {
                    app.addTransaksi(transaksi);
                }

                JOptionPane.showMessageDialog(this, "Checkout berhasil! Terima kasih atas pembelian Anda.\nID Transaksi: " + transaksi.getIdTransaksi(), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                customerDriver.kosongkanKeranjang();
                refreshTableKeranjang();
                refreshTableRiwayat();
                tabbedPane.setSelectedIndex(3); // Tab Riwayat
            } else {
                JOptionPane.showMessageDialog(this, "Gagal melakukan checkout!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Merefresh tabel obat
     */
    private void refreshTablObat() {
        modelTableLihatObat.setRowCount(0);
        ArrayList<Obat> daftarObat = listObatData.getAllObat();
        for (Obat obat : daftarObat) {
            Object[] row = {
                obat.getId(),
                obat.getNama(),
                String.format("Rp %d", obat.getHarga()),
                obat.getStok()
            };
            modelTableLihatObat.addRow(row);
        }
    }
    
    /**
     * Merefresh tabel keranjang
     */
    private void refreshTableKeranjang() {
        modelTableKeranjang.setRowCount(0);
        ArrayList<ItemKeranjang> items = customerDriver.lihatKeranjang();
        
        double totalHarga = 0;
        for (ItemKeranjang item : items) {
            Obat obat = item.getObat();
            double subtotal = obat.getHarga() * item.getJumlah();
            totalHarga += subtotal;
            
            Object[] row = {
                obat.getId(),
                obat.getNama(),
                item.getJumlah(),
                String.format("Rp %d", obat.getHarga()),
                String.format("Rp %.0f", subtotal)
            };
            modelTableKeranjang.addRow(row);
        }
        
        lblTotalHarga.setText(String.format("Rp %.0f", totalHarga));
    }
    
    /**
     * Merefresh tabel riwayat transaksi
     */
    private void refreshTableRiwayat() {
        modelTableRiwayat.setRowCount(0);
        ArrayList<Transaksi> riwayat = customerDriver.lihatRiwayatTransaksi();
        
        if (riwayat != null) {
            for (Transaksi transaksi : riwayat) {
                Object[] row = {
                    transaksi.getIdTransaksi(),
                    String.format("Rp %.0f", transaksi.getTotalHarga()),
                    transaksi.getStatus().getLabel(),
                    transaksi.getTanggalTransaksi(),
                    transaksi.getMetodePayment()
                };
                modelTableRiwayat.addRow(row);
            }
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
