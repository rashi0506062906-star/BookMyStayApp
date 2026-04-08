import java.util.*;

// Class representing an Add-On Service
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manager class to handle Add-On Services for reservations
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> reservationServicesMap;

    public AddOnServiceManager() {
        reservationServicesMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        reservationServicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Get all services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return reservationServicesMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = reservationServicesMap.get(reservationId);
        if (services == null) return 0;

        double total = 0;
        for (AddOnService service : services) {
            total += service.getCost();
        }
        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Selected Services:");
        for (AddOnService s : services) {
            System.out.println("- " + s);
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        while (true) {
            System.out.println("\n--- Add-On Services Menu ---");
            System.out.println("1. Add Breakfast (₹500)");
            System.out.println("2. Add Airport Pickup (₹1200)");
            System.out.println("3. Add Extra Bed (₹800)");
            System.out.println("4. View Selected Services");
            System.out.println("5. Calculate Total Cost");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    manager.addService(reservationId, new AddOnService("Breakfast", 500));
                    System.out.println("Breakfast added.");
                    break;

                case 2:
                    manager.addService(reservationId, new AddOnService("Airport Pickup", 1200));
                    System.out.println("Airport Pickup added.");
                    break;

                case 3:
                    manager.addService(reservationId, new AddOnService("Extra Bed", 800));
                    System.out.println("Extra Bed added.");
                    break;

                case 4:
                    manager.displayServices(reservationId);
                    break;

                case 5:
                    double total = manager.calculateTotalCost(reservationId);
                    System.out.println("Total Add-On Cost: ₹" + total);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}