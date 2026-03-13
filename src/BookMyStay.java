import java.util.*;

/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Demonstrates safe room allocation and prevention of double booking.
 *
 * @author Rashi
 * @version 6.1
 */

// Reservation request
class Reservation {

    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service
class InventoryService {

    private HashMap<String, Integer> inventory;

    InventoryService() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}

// Booking Service
class BookingService {

    private Queue<Reservation> requestQueue;
    private HashMap<String, Set<String>> allocatedRooms;
    private InventoryService inventory;
    private int roomCounter = 1;

    BookingService(InventoryService inventory) {
        this.inventory = inventory;
        requestQueue = new LinkedList<>();
        allocatedRooms = new HashMap<>();
    }

    // Add request to queue
    public void addRequest(Reservation r) {
        requestQueue.add(r);
        System.out.println("Booking request received for " + r.guestName);
    }

    // Generate unique room ID
    private String generateRoomID(String roomType) {
        return roomType.replace(" ", "") + "-" + (roomCounter++);
    }

    // Process requests
    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation r = requestQueue.poll();

            System.out.println("\nProcessing booking for " + r.guestName);

            int available = inventory.getAvailability(r.roomType);

            if (available > 0) {

                String roomID = generateRoomID(r.roomType);

                allocatedRooms.putIfAbsent(r.roomType, new HashSet<>());
                allocatedRooms.get(r.roomType).add(roomID);

                inventory.decrementRoom(r.roomType);

                System.out.println("Reservation Confirmed!");
                System.out.println("Guest: " + r.guestName);
                System.out.println("Room Type: " + r.roomType);
                System.out.println("Assigned Room ID: " + roomID);

            } else {

                System.out.println("Reservation Failed. No rooms available for " + r.roomType);
            }
        }
    }

    public void displayAllocatedRooms() {

        System.out.println("\nAllocated Rooms:");

        for (String roomType : allocatedRooms.keySet()) {

            System.out.println(roomType + " -> " + allocatedRooms.get(roomType));
        }
    }
}

// Main class
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Room Allocation =====");

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Add booking requests
        bookingService.addRequest(new Reservation("Amit", "Single Room"));
        bookingService.addRequest(new Reservation("Priya", "Double Room"));
        bookingService.addRequest(new Reservation("Rahul", "Single Room"));
        bookingService.addRequest(new Reservation("Neha", "Suite Room"));

        // Process bookings
        bookingService.processBookings();

        // Show results
        bookingService.displayAllocatedRooms();
        inventory.displayInventory();
    }
}