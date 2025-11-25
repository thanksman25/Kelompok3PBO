package healthmart.view;

import healthmart.model.*;
import healthmart.controller.*;
import healthmart.util.*;
import healthmart.payment.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class HealthMartApp - Main application window dengan Swing GUI
 */
public class HealthMartApp extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    
    // Komponen utama
    private ListObat listObat;
    private ArrayList<Admin> admins;
    private ArrayList<Customer> customers;
    private ArrayList<Transaksi> semuaTransaksi;
    
    private AdminDriver adminDriver;
    private CustomerDriver customerDriver;
    private AuthenticationManager authManager;
    private DataManager dataManager;
    
    // Panel
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private LoginPanel loginPanel;
    
    /**
     * Constructor HealthMartApp
     */
    public HealthMartApp() {
        setTitle("HealthMart - Aplikasi Penjualan Obat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize data
        initializeData();
        
        // Setup UI
        setupUI();
    }
    
    /**
     * Initialize data dari file atau buat default
     */
    private void initializeData() {
        dataManager = new DataManager();
        
        // Load data dari file
        admins = dataManager.loadAdmin();
        customers = dataManager.loadCustomer();
        listObat = dataManager.loadObat();
        semuaTransaksi = dataManager.loadTransaksi();
        
        // Jika belum ada data, buat default
        if (admins.isEmpty()) {
            Admin admin = new Admin("admin", "admin123", "Administrator", "admin@healthmart.com");
            admins.add(admin);
            dataManager.saveAdmin(admins);
        }
        
        // Jika belum ada obat, buat default
        if (listObat.getJumlahObat() == 0) {
            listObat.tambahObat(new Obat("OB001", "Paracetamol 500mg", "Obat penurun panas", 3000, 50, "Analgesik"));
            listObat.tambahObat(new Obat("OB002", "Vitamin C 1000mg", "Multivitamin", 5000, 30, "Vitamin"));
            listObat.tambahObat(new Obat("OB003", "Amoxicillin 500mg", "Antibiotik", 8000, 20, "Antibiotik"));
            listObat.tambahObat(new Obat("OB004", "Ibuprofen 400mg", "Obat antiinflamasi", 4500, 25, "NSAID"));
            listObat.tambahObat(new Obat("OB005", "Omeprazole 20mg", "Obat asam lambung", 7500, 15, "Gastrointestinal"));
            dataManager.saveObat(listObat);
        }
        
        // Create auth manager
        ArrayList<Akun> semuaAkun = new ArrayList<>();
        semuaAkun.addAll(admins);
        semuaAkun.addAll(customers);
        authManager = new AuthenticationManager(semuaAkun);
    }
    
    /**
     * Setup UI komponen
     */
    private void setupUI() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Add panels
        loginPanel = new LoginPanel(this);
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(new RegisterPanel(this), "REGISTER");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    /**
     * Show login panel
     */
    public void showLoginPanel() {
        if (loginPanel != null) loginPanel.clearFields();
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    /**
     * Show register panel
     */
    public void showRegisterPanel() {
        cardLayout.show(mainPanel, "REGISTER");
    }
    
    /**
     * Show admin dashboard
     */
    public void showAdminDashboard(Admin admin) {
        adminDriver = new AdminDriver(admin, listObat);
        adminDriver.setSemuaTransaksi(semuaTransaksi);
        
        mainPanel.add(new AdminDashboard(this, adminDriver), "ADMIN");
        cardLayout.show(mainPanel, "ADMIN");
    }
    
    /**
     * Show customer dashboard
     */
    public void showCustomerDashboard(Customer customer) {
        customerDriver = new CustomerDriver(customer, listObat);
        // Load customer's transaksi history from global list
        for (Transaksi t : semuaTransaksi) {
            if (t.getCustomerId().equals(customer.getUsername())) {
                customerDriver.tambahRiwayatTransaksi(t);
            }
        }
        mainPanel.add(new CustomerDashboard(this, customerDriver), "CUSTOMER");
        cardLayout.show(mainPanel, "CUSTOMER");
    }
    
    /**
     * Login
     */
    public void login(String username, String password) {
        try {
            System.out.println("DEBUG: Attempting login with username=" + username);
            System.out.println("DEBUG: Admins count=" + admins.size() + ", Customers count=" + customers.size());
            System.out.println("DEBUG: AuthManager akun count=" + authManager.getAllAkun().size());
            
            Akun akun = authManager.login(username, password);
            System.out.println("DEBUG: Login successful, akun role=" + akun.getRole());
            
            if (akun instanceof Admin) {
                showAdminDashboard((Admin) akun);
            } else if (akun instanceof Customer) {
                showCustomerDashboard((Customer) akun);
            }
        } catch (RuntimeException e) {
            System.out.println("DEBUG: Login error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Login Gagal: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Register customer baru
     */
    public void register(String username, String password, String nama, String email, String alamat, String noTelepon) {
        try {
            if (!ValidationUtil.isValidUsername(username)) {
                throw new RuntimeException("Username harus minimal 3 karakter");
            }
            if (!ValidationUtil.isValidPassword(password)) {
                throw new RuntimeException("Password harus minimal 4 karakter");
            }
            if (!ValidationUtil.isValidEmail(email)) {
                throw new RuntimeException("Email tidak valid");
            }
            if (!ValidationUtil.isValidNama(nama)) {
                throw new RuntimeException("Nama harus minimal 3 karakter");
            }
            
            if (authManager.isUsernameExists(username)) {
                throw new RuntimeException("Username sudah terdaftar");
            }
            
            Customer customer = new Customer(username, password, nama, email, alamat, noTelepon);
            customers.add(customer);
            authManager.register(customer);
            
            dataManager.saveCustomer(customers);
            
            JOptionPane.showMessageDialog(this, "Registrasi berhasil!\nSilahkan login dengan akun baru Anda", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            showLoginPanel();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Registrasi Gagal: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Logout dan back to login
     */
    public void logout() {
        // Save data
        dataManager.saveAdmin(admins);
        dataManager.saveCustomer(customers);
        dataManager.saveObat(listObat);
        dataManager.saveTransaksi(semuaTransaksi);
        
        adminDriver = null;
        customerDriver = null;
        
        // Refresh authentication manager with latest data
        admins = dataManager.loadAdmin();
        customers = dataManager.loadCustomer();
        ArrayList<Akun> semuaAkun = new ArrayList<>();
        semuaAkun.addAll(admins);
        semuaAkun.addAll(customers);
        authManager = new AuthenticationManager(semuaAkun);
        
        // Reuse existing panels: clear login fields and show login card
        if (loginPanel != null) loginPanel.clearFields();
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    /**
     * Get list obat
     */
    public ListObat getListObat() {
        return listObat;
    }
    
    /**
     * Get semua transaksi
     */
    public ArrayList<Transaksi> getSemuaTransaksi() {
        return semuaTransaksi;
    }

    /**
     * Tambah transaksi baru ke list global dan simpan segera.
     */
    public void addTransaksi(Transaksi transaksi) {
        if (transaksi == null) return;
        if (semuaTransaksi == null) semuaTransaksi = new ArrayList<>();
        semuaTransaksi.add(transaksi);
        // update admin driver if present
        if (adminDriver != null) {
            adminDriver.tambahTransaksi(transaksi);
        }
        // persist immediately
        if (dataManager != null) {
            dataManager.saveTransaksi(semuaTransaksi);
        }
    }
    
    /**
     * Get admin driver
     */
    public AdminDriver getAdminDriver() {
        return adminDriver;
    }
    
    /**
     * Get customer driver
     */
    public CustomerDriver getCustomerDriver() {
        return customerDriver;
    }
    
    /**
     * Get data manager
     */
    public DataManager getDataManager() {
        return dataManager;
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HealthMartApp app = new HealthMartApp();
            app.setVisible(true);
        });
    }
}
