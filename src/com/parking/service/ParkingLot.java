package com.parking.service;

import java.util.List;
import com.parking.db.Database;
import com.parking.exception.ParkingFullException;
import com.parking.exception.VehicleNotFoundException;
import com.parking.model.Vehicle;

public class ParkingLot {

    private int capacity;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public void parkVehicle(String plate) throws Exception {
        if (plate == null || plate.isEmpty()) {
            throw new Exception("Enter vehicle number");
        }

        List<Vehicle> vehicles = Database.getAllVehicles();
        if (vehicles.size() >= capacity) {
            throw new ParkingFullException("Parking is FULL");
        }

        Database.insertVehicle(plate);
    }

    public double removeVehicle(String plate) throws Exception {
        if (plate == null || plate.isEmpty()) {
            throw new Exception("Enter vehicle number");
        }

        boolean removed = Database.deleteVehicle(plate);
        if (!removed) {
            throw new VehicleNotFoundException("Vehicle not found");
        }

        // simple fixed fee
        return 50.0;
    }

    public List<Vehicle> getParkedVehicles() throws Exception {
        return Database.getAllVehicles();
    }
}
