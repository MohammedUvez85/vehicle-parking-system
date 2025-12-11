import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ParkingLot lot = new ParkingLot(10, 20.0);

        while (true) {
            System.out.println("\n===== VEHICLE PARKING MANAGEMENT SYSTEM =====");
            System.out.println("1. Park Vehicle");
            System.out.println("2. View Parked Vehicles");
            System.out.println("3. View Available Slots");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter Vehicle Number: ");
                    String p = sc.nextLine();
                    try {
                        lot.parkVehicle(p);
                    } catch (ParkingFullException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2":
                    List<Vehicle> list = lot.getParkedVehicles();
                    if (list.isEmpty()) {
                        System.out.println("No vehicles currently parked.");
                    } else {
                        System.out.println("\nPlate Number  | Entry Time");
                        System.out.println("--------------------------------------------");
                        for (Vehicle v : list) {
                            System.out.println(v);
                        }
                    }
                    break;

                case "3":
                    System.out.println("Available Slots: " + lot.getAvailableSlots());
                    break;

                case "4":
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice! Try again.");
            }
        }
    }
}
