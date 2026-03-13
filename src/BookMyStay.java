import java.util.HashMap;

/**
 * Book My Stay - Hotel Booking System
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates how room availability can be
 * managed using a centralized HashMap instead of separate variables.
 *
 * @author Rashi
 * @version 3.1
 */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes room availability
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Method to get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Method to display inventory
    public void displayInventory() {

        System.out.println("\n===== Current Room Inventory =====");

        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : " + inventory.get(roomType) + " available");
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Room Inventory System =====");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory();

        // Check availability
        int singleRooms = inventory.getAvailability("Single Room");
        System.out.println("\nAvailable Single Rooms: " + singleRooms);

        // Update inventory after booking
        inventory.updateAvailability("Single Room", singleRooms - 1);

        System.out.println("\nAfter booking one Single Room:");

        // Display updated inventory
        inventory.displayInventory();
    }
}