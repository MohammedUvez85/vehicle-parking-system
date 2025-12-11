import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private int capacity;
    private double ratePerHour;
    private List<Vehicle> parked;

    public ParkingLot(int capacity, double ratePerHour) {
        this.capacity = capacity;
        this.ratePerHour = ratePerHour;
        this.parked = new ArrayList<>();
    }

    // YOUR FEATURE: PARKING VEHICLE
    public void parkVehicle(String plate) throws ParkingFullException {
        if (parked.size() >= capacity) {
            throw new ParkingFullException("Parking Lot is Full! Capacity = " + capacity);
        }
        Vehicle v = new Vehicle(plate);
        parked.add(v);
        System.out.println("Vehicle Parked: " + plate.toUpperCase() +  " at " + v.getFormattedEntryTime());
    }

    public List<Vehicle> getParkedVehicles() {
        return parked;
    }

    public int getAvailableSlots() {
        return capacity - parked.size();
    }
}