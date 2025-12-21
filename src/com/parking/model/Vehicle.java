package com.parking.model;

public class Vehicle {
    private String plate;
    private String entryTime;

    public Vehicle(String plate, String entryTime) {
        this.plate = plate;
        this.entryTime = entryTime;
    }

    public String getPlate() {
        return plate;
    }

    public String getEntryTime() {
        return entryTime;
    }
}
