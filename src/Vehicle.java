import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vehicle {
    private String plateNumber;
    private LocalDateTime entryTime;

    public Vehicle(String plateNumber) {
        this.plateNumber = plateNumber.toUpperCase();
        this.entryTime = LocalDateTime.now();
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public String getFormattedEntryTime() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return entryTime.format(fmt);
    }

    @Override
    public String toString() {
        return String.format("%-12s | %s", plateNumber, getFormattedEntryTime());
    }
}
