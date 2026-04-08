import java.io.*;
import java.util.*;

// Booking class
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    String customerName;
    int roomNumber;

    public Booking(String customerName, int roomNumber) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
    }

    public String toString() {
        return "Customer: " + customerName + ", Room: " + roomNumber;
    }
}

// Inventory class
class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<Integer, Boolean> rooms;

    public Inventory(int totalRooms) {
        rooms = new HashMap<>();
        for (int i = 1; i <= totalRooms; i++) {
            rooms.put(i, true); // true = available
        }
    }

    public void bookRoom(int roomNumber) {
        rooms.put(roomNumber, false);
    }

    public boolean isAvailable(int roomNumber) {
        return rooms.getOrDefault(roomNumber, false);
    }

    public String toString() {
        return rooms.toString();
    }
}

// Wrapper class for persistence
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Booking> bookings;
    Inventory inventory;

    public SystemState(List<Booking> bookings, Inventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

// Main Application
public class BookMyStayApp {

    private static final String FILE_NAME = "hotel_data.ser";

    List<Booking> bookings;
    Inventory inventory;

    public BookMyStayApp() {
        // Try to load previous state
        loadState();
    }

    // Add booking
    public void addBooking(String name, int roomNumber) {
        if (inventory.isAvailable(roomNumber)) {
            Booking booking = new Booking(name, roomNumber);
            bookings.add(booking);
            inventory.bookRoom(roomNumber);
            System.out.println("Booking successful!");
        } else {
            System.out.println("Room not available!");
        }
    }

    // Save state to file
    public void saveState() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            SystemState state = new SystemState(bookings, inventory);
            oos.writeObject(state);

            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load state from file
    public void loadState() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            bookings = state.bookings;
            inventory = state.inventory;

            System.out.println("System state restored successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
            initializeNewSystem();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Corrupted data. Starting fresh.");
            initializeNewSystem();
        }
    }

    // Initialize new system
    private void initializeNewSystem() {
        bookings = new ArrayList<>();
        inventory = new Inventory(5); // Example: 5 rooms
    }

    // Display bookings
    public void displayBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
    }

    // Main method
    public static void main(String[] args) {

        BookMyStayApp app = new BookMyStayApp();

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n1. Book Room");
            System.out.println("2. View Bookings");
            System.out.println("3. Save & Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.next();
                    System.out.print("Enter room number: ");
                    int room = sc.nextInt();
                    app.addBooking(name, room);
                    break;

                case 2:
                    app.displayBookings();
                    break;

                case 3:
                    app.saveState();
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);

        sc.close();
    }
}