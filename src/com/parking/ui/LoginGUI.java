package com.parking.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.parking.service.ParkingLot;

public class LoginGUI extends JFrame {

    private JTextField userField;
    private JPasswordField passField;
    private JLabel statusLabel;

    public LoginGUI() {

    setTitle("Vehicle Parking System - Login");

    // ✅ Allow resizing
    setResizable(true);

    // ✅ Start maximized
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // ================= DARK BACKGROUND PANEL =================
    JPanel panel = new JPanel() {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(18, 18, 18),
                    0, getHeight(), new Color(40, 40, 40)
            );
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    };
    panel.setLayout(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ================= TITLE =================
        JLabel title = new JLabel("VEHICLE PARKING SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // ================= USERNAME =================
        gbc.gridy++;
        gbc.gridwidth = 1;

        JLabel userLbl = new JLabel("Username");
        userLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userLbl.setForeground(Color.LIGHT_GRAY);
        panel.add(userLbl, gbc);

        gbc.gridx = 1;
        userField = createTextField("Enter username");
        panel.add(userField, gbc);

        // ================= PASSWORD =================
        gbc.gridx = 0;
        gbc.gridy++;

        JLabel passLbl = new JLabel("Password");
        passLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passLbl.setForeground(Color.LIGHT_GRAY);
        panel.add(passLbl, gbc);

        gbc.gridx = 1;
        passField = new JPasswordField(18);
        stylePasswordField(passField);
        panel.add(passField, gbc);

        // ================= STATUS LABEL =================
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel, gbc);

        // ================= LOGIN BUTTON =================
        gbc.gridy++;

        JButton loginBtn = new JButton("LOGIN");
        styleButton(loginBtn);
        panel.add(loginBtn, gbc);

        getRootPane().setDefaultButton(loginBtn);
        loginBtn.addActionListener(e -> loginAction());

        // ================= FOOTER =================
        gbc.gridy++;
        JLabel footer = new JLabel("© 2025 Smart Vehicle Parking System",
                SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footer.setForeground(new Color(180, 180, 180));
        panel.add(footer, gbc);

        add(panel);
    }

    // ================= LOGIN ACTION =================
    private void loginAction() {

        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty() || user.equals("Enter username")) {
            statusLabel.setText("Please enter Username and Password");
            return;
        }

        if (user.equals("admin") && pass.equals("admin")) {
            showLoginSuccessDialog(this);
        } else {
            statusLabel.setText("Invalid Username or Password");
            passField.setText("");
        }
    }

    // ================= SUCCESS DIALOG =================
    private void showLoginSuccessDialog(JFrame parent) {

        JDialog dialog = new JDialog(parent, true);
        dialog.setSize(480, 300);
        dialog.setLocationRelativeTo(parent);
        dialog.setUndecorated(true);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(30, 30, 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel icon = new JLabel("🅿️");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        panel.add(icon, gbc);

        gbc.gridy++;
        JLabel title = new JLabel("LOGIN SUCCESSFUL");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title, gbc);

        gbc.gridy++;
        JLabel subtitle = new JLabel("Welcome to Smart Vehicle Parking System");
        subtitle.setForeground(Color.LIGHT_GRAY);
        panel.add(subtitle, gbc);

        gbc.gridy++;
        JButton continueBtn = new JButton("ENTER DASHBOARD");
        styleButton(continueBtn);

        continueBtn.addActionListener(e -> {
            dialog.dispose();
            parent.dispose();
            ParkingLot lot = new ParkingLot(10);
            new MainDashboard(lot).setVisible(true);
        });

        panel.add(continueBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ================= UI HELPERS =================
    private JTextField createTextField(String placeholder) {

        JTextField field = new JTextField(18);
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.setBackground(new Color(45, 45, 45));
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return field;
    }

    private void stylePasswordField(JPasswordField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setBackground(new Color(45, 45, 45));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBackground(new Color(0, 170, 110));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0, 150, 95));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0, 170, 110));
            }
        });
    }

    // ================= MAIN =================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}
