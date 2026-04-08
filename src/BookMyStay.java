import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory (Critical Resource)
class Inventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public Inventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    // 🔒 Critical Section (Thread Safe)
    public synchronized boolean allocateRoom(String roomType, String guestName) {
        int available = rooms.getOrDefault(roomType, 0);

        if (available > 0) {
            System.out.println(guestName + " is booking " + roomType + " room...");

            // simulate delay (race condition scenario)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}

            rooms.put(roomType, available - 1);

            System.out.println("✅ Booking SUCCESS for " + guestName + " (" + roomType + ")");
            return true;
        } else {
            System.out.println("❌ Booking FAILED for " + guestName + " (" + roomType + " not available)");
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + rooms);
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {

    private Queue<BookingRequest> queue;
    private Inventory inventory;

    public BookingProcessor(Queue<BookingRequest> queue, Inventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // 🔒 Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                request = queue.poll();
            }

            // Process booking
            inventory.allocateRoom(request.roomType, request.guestName);
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        // Shared queue
        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        // Add multiple requests (simulate multiple users)
        bookingQueue.add(new BookingRequest("Alice", "Single"));
        bookingQueue.add(new BookingRequest("Bob", "Single"));
        bookingQueue.add(new BookingRequest("Charlie", "Single")); // extra request
        bookingQueue.add(new BookingRequest("David", "Double"));
        bookingQueue.add(new BookingRequest("Eve", "Suite"));
        bookingQueue.add(new BookingRequest("Frank", "Suite")); // extra request

        Inventory inventory = new Inventory();

        // Multiple threads
        Thread t1 = new BookingProcessor(bookingQueue, inventory);
        Thread t2 = new BookingProcessor(bookingQueue, inventory);
        Thread t3 = new BookingProcessor(bookingQueue, inventory);

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {}

        // Final state
        System.out.println("\n--- Final System State ---");
        inventory.displayInventory();
    }
}