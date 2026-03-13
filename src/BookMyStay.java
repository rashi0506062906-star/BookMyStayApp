import java.util.HashMap;

/**
 * Book My Stay - Hotel Booking System
 * Use Case 4: Room Search & Availability Check
 * Demonstrates read-only search using centralized inventory.
 *
 * @author Rashi
 * @version 4.1
 */

// Room domain model
class Room {
    String type;
    double price;

    Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    void displayDetails(int available) {
        System.out.println("Room Type : " + type);
        System.out.println("Price     : ₹" + price);
        System.out.println("Available : " + available);
        System.out.println("-----------------------------");
    }
}

// Centralized inventory
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // example unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }
}

// Search service (read-only access)
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("\n===== Available Rooms =====");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.type);

            // Defensive check to filter unavailable rooms
            if (available > 0) {
                room.displayDetails(available);
            }
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Room Search =====");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Room domain objects
        Room single = new Room("Single Room", 2000);
        Room dbl = new Room("Double Room", 3500);
        Room suite = new Room("Suite Room", 6000);

        Room[] rooms = {single, dbl, suite};

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Perform read-only search
        searchService.searchAvailableRooms(inventory, rooms);

        System.out.println("\nSearch completed. Inventory unchanged.");
    }
}