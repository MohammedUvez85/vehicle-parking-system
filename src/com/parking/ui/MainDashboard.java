package com.parking.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.parking.model.Vehicle;
import com.parking.service.ParkingLot;

public class MainDashboard extends JFrame {

    private ParkingLot lot;
    private JTextField vehicleField;
    private DefaultTableModel tableModel;
    private JPanel slotPanel;
    private JLabel timeLabel;
    private JScrollPane scroll;   // ⭐ table container

    private final int TOTAL_SLOTS = 10;

    public MainDashboard(ParkingLot lot) {
        this.lot = lot;

        setTitle("VEHICLE PARKING SYSTEM");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(20, 40, 60),
                        getWidth(), 0, new Color(0, 160, 120)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(950, 70));

        JLabel title = new JLabel("🚗 🅿️ VEHICLE PARKING SYSTEM 🏍️");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        timeLabel = new JLabel();
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        header.add(title, BorderLayout.WEST);
        header.add(timeLabel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        startClock();

        // ================= MAIN =================
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(45, 45, 45));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);

        // ================= INPUT =================
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        inputPanel.setOpaque(false);

        vehicleField = new JTextField(18);
        vehicleField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        vehicleField.setPreferredSize(new Dimension(220, 36));

        inputPanel.add(vehicleField);
        mainPanel.add(inputPanel);

        // ================= BUTTONS =================
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        JButton parkBtn = createButton("🚗 PARK", new Color(0, 180, 110));
        JButton removeBtn = createButton("❌ REMOVE", new Color(200, 60, 60));
        JButton viewBtn = createButton("📋 VIEW PARKED", new Color(60, 140, 255));
        JButton logoutBtn = createButton("🚪 LOGOUT", new Color(130, 130, 130));

        buttonPanel.add(parkBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(logoutBtn);

        mainPanel.add(buttonPanel);

        // ================= PARKING SLOTS =================
        slotPanel = new JPanel(new GridLayout(2, 5, 15, 15));
        slotPanel.setOpaque(false);
        slotPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Parking Slots",
                0, 0,
                new Font("SansSerif", Font.BOLD, 14),
                Color.WHITE
        ));

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(slotPanel);

        // ================= TABLE (HIDDEN INITIALLY) =================
        String[] columns = {"Plate Number", "Entry Time"};
        tableModel = new DefaultTableModel(columns, 0);

        JTable table = new JTable(tableModel);
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setSelectionBackground(new Color(0, 160, 120));

        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(800, 250));
        scroll.setVisible(false);   // ⭐ HIDDEN
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(scroll);

        // ================= ACTIONS =================
        parkBtn.addActionListener(e -> performPark());
        removeBtn.addActionListener(e -> performRemove());

        viewBtn.addActionListener(e -> {
            updateTable();
            scroll.setVisible(true);   // ⭐ SHOW TABLE
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginGUI().setVisible(true);
        });

        setVisible(true);
        updateSlots();
    }

    // ================= LIVE CLOCK =================
    private void startClock() {
        Timer timer = new Timer(1000, e -> {
            String time = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss").format(new Date());
            timeLabel.setText("🕒 " + time);
        });
        timer.start();
    }

    // ================= PARK =================
    private void performPark() {
        try {
            lot.parkVehicle(vehicleField.getText().trim());
            updateSlots();
            vehicleField.setText("");
            showMsg("Vehicle Parked 🚗", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            showMsg(ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================= REMOVE =================
    private void performRemove() {
        try {
            double fee = lot.removeVehicle(vehicleField.getText().trim());
            updateSlots();
            vehicleField.setText("");
            showMsg("Vehicle Removed\nFee ₹" + fee, JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            showMsg(ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================= UPDATE TABLE =================
    private void updateTable() {
        tableModel.setRowCount(0);
        try {
            for (Vehicle v : lot.getParkedVehicles()) {
                tableModel.addRow(new Object[]{
                        v.getPlate(),
                        v.getEntryTime()
                });
            }
        } catch (Exception ignored) {}
    }

    // ================= ANIMATED SLOTS =================
    private void updateSlots() {
        slotPanel.removeAll();
        int used;
        try {
            used = lot.getParkedVehicles().size();
        } catch (Exception e) {
            used = 0;
        }

        for (int i = 1; i <= TOTAL_SLOTS; i++) {
            JLabel slot = new JLabel("SLOT " + i, SwingConstants.CENTER);
            slot.setFont(new Font("SansSerif", Font.BOLD, 14));
            slot.setOpaque(true);
            slot.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            if (i <= used) {
                slot.setBackground(new Color(220, 70, 70));
                slot.setForeground(Color.WHITE);
            } else {
                slot.setBackground(new Color(60, 180, 110));
                slot.setForeground(Color.BLACK);
            }
            slotPanel.add(slot);
        }
        slotPanel.revalidate();
        slotPanel.repaint();
    }

    // ================= BUTTON STYLE =================
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 42));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void showMsg(String msg, int type) {
        JOptionPane.showMessageDialog(this, msg, "Vehicle Parking System", type);
    }
}
