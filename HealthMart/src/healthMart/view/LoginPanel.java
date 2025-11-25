package healthmart.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class LoginPanel - Panel untuk login
 */
public class LoginPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private HealthMartApp app;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    /**
     * Constructor LoginPanel
     */
    public LoginPanel(HealthMartApp app) {
        this.app = app;
        setupUI();
    }

    /**
     * Clear input fields (used when returning to login after logout)
     */
    public void clearFields() {
        if (usernameField != null) usernameField.setText("");
        if (passwordField != null) passwordField.setText("");
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
        JLabel subtitle = new JLabel("Sistem Penjualan Alat Kesehatan dan Obat Bebas");
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
        
        // Login button
        JButton loginButton = new JButton("Login");
        stylePillButton(loginButton);
        loginButton.setPreferredSize(new Dimension(120, 36));
        loginButton.setBackground(new Color(255, 255, 255));
        loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener((ActionEvent e) -> login());
        
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btnRow.setBackground(new Color(244, 244, 244));
        JButton registerButton = new JButton("Register");
        stylePillButton(registerButton);
        registerButton.setPreferredSize(new Dimension(120, 36));
        registerButton.setBackground(new Color(255, 255, 255));
        registerButton.setForeground(Color.BLACK);
        registerButton.addActionListener((ActionEvent e) -> app.showRegisterPanel());
        btnRow.add(registerButton);
        btnRow.add(loginButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(btnRow, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    /**
     * Login action
     */
    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password harus diisi", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        app.login(username, password);
    }

    private void stylePillButton(JButton b) {
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        b.setOpaque(true);
    }
}
