package com.parking.db;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.parking.model.Vehicle;

public class Database {

    private static final String URL = "jdbc:sqlite:parking.db";

    // ================= GET CONNECTION =================
   public static Connection getConnection() throws SQLException {
    try {
        Class.forName("org.sqlite.JDBC"); // 🔥 LOAD DRIVER
    } catch (ClassNotFoundException e) {
        throw new SQLException("SQLite JDBC Driver not found", e);
    }
    return DriverManager.getConnection(URL);
}


    // ================= CREATE TABLE (AUTO) =================
    static {
        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS vehicles (
                    plate TEXT PRIMARY KEY,
                    entry_time TEXT
                )
            """;

            st.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================= PARK VEHICLE =================
    public static void insertVehicle(String plate) throws Exception {
        String sql = "INSERT INTO vehicles (plate, entry_time) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, plate);
            ps.setString(2, LocalDateTime.now().toString());
            ps.executeUpdate();
        }
    }

    // ================= REMOVE VEHICLE =================
    public static boolean deleteVehicle(String plate) throws Exception {
        String sql = "DELETE FROM vehicles WHERE plate = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, plate);
            return ps.executeUpdate() > 0;
        }
    }

    // ================= GET ONE VEHICLE =================
    public static Vehicle getVehicle(String plate) throws Exception {
        String sql = "SELECT * FROM vehicles WHERE plate = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Vehicle(
                        rs.getString("plate"),
                        rs.getString("entry_time")
                );
            }
        }
        return null;
    }

    // ================= GET ALL VEHICLES =================
    public static List<Vehicle> getAllVehicles() throws Exception {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles ORDER BY entry_time";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Vehicle(
                        rs.getString("plate"),
                        rs.getString("entry_time")
                ));
            }
        }
        return list;
    }
}
