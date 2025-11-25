package healthmart.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class RegisterPanel - Panel untuk registrasi customer
 */
public class RegisterPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private HealthMartApp app;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField namaField;
    private JTextField emailField;
    private JTextArea alamatField;
    private JTextField noTeleponField;
    
    /**
     * Constructor RegisterPanel
     */
    public RegisterPanel(HealthMartApp app) {
        this.app = app;
        setupUI();
    }
    
    /**
     * Setup UI
     */
    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(244, 244, 244));

        // Header (big title + subtitle)
        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(new Color(244, 244, 244));
        GridBagConstraints hgb = new GridBagConstraints();
        hgb.insets = new Insets(4, 4, 4, 4);
        hgb.gridx = 0;
        hgb.gridy = 0;
        JLabel bigTitle = new JLabel("HealthMart");
        bigTitle.setFont(new Font("SansSerif", Font.BOLD, 40));
        header.add(bigTitle, hgb);
        hgb.gridy = 1;
        JLabel subtitle = new JLabel("Daftar Akun Baru");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        header.add(subtitle, hgb);

        add(header, BorderLayout.NORTH);

        // Panel utama
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(244, 244, 244));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Spacer to push form down a bit
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.ipady = 10;
        centerPanel.add(Box.createVerticalStrut(8), gbc);
        gbc.ipady = 0;
        
        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(usernameLabel, gbc);
        
        // Username field
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(260, 28));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(usernameField, gbc);
        
        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(passwordLabel, gbc);
        
        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(260, 28));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(passwordField, gbc);
        
        // Nama label
        JLabel namaLabel = new JLabel("Nama Lengkap:");
        namaLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(namaLabel, gbc);
        
        // Nama field
        namaField = new JTextField(20);
        namaField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        namaField.setPreferredSize(new Dimension(260, 28));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(namaField, gbc);
        
        // Email label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(emailLabel, gbc);
        
        // Email field
        emailField = new JTextField(20);
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailField.setPreferredSize(new Dimension(260, 28));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(emailField, gbc);
        
        // Alamat label
        JLabel alamatLabel = new JLabel("Alamat:");
        alamatLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.NORTH;
        centerPanel.add(alamatLabel, gbc);
        
        // Alamat field (TextArea)
        alamatField = new JTextArea(3, 20);
        alamatField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        alamatField.setLineWrap(true);
        alamatField.setWrapStyleWord(true);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(new JScrollPane(alamatField), gbc);
        
        // No Telepon label
        JLabel noTeleponLabel = new JLabel("No. Telepon:");
        noTeleponLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(noTeleponLabel, gbc);
        
        // No Telepon field
        noTeleponField = new JTextField(20);
        noTeleponField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        noTeleponField.setPreferredSize(new Dimension(260, 28));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(noTeleponField, gbc);
        
        // Button panel
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btnRow.setBackground(new Color(244, 244, 244));
        
        // Back button
        JButton backButton = new JButton("Back");
        stylePillButton(backButton);
        backButton.setPreferredSize(new Dimension(120, 36));
        backButton.setBackground(new Color(255, 255, 255));
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener((ActionEvent e) -> app.showLoginPanel());
        
        // Register button
        JButton registerButton = new JButton("Register");
        stylePillButton(registerButton);
        registerButton.setPreferredSize(new Dimension(120, 36));
        registerButton.setBackground(new Color(255, 255, 255));
        registerButton.setForeground(Color.BLACK);
        registerButton.addActionListener((ActionEvent e) -> register());
        
        btnRow.add(backButton);
        btnRow.add(registerButton);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        centerPanel.add(btnRow, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void stylePillButton(JButton b) {
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        b.setOpaque(true);
    }
    
    /**
     * Register action
     */
    private void register() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String nama = namaField.getText().trim();
        String email = emailField.getText().trim();
        String alamat = alamatField.getText().trim();
        String noTelepon = noTeleponField.getText().trim();
        
        if (username.isEmpty() || password.isEmpty() || nama.isEmpty() || email.isEmpty() || 
            alamat.isEmpty() || noTelepon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        app.register(username, password, nama, email, alamat, noTelepon);
    }
}
