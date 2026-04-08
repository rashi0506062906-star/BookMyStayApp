import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isCancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        this.isCancelled = true;
    }

    @Override
    public String toString() {
        return "ID: " + reservationId +
                ", Guest: " + guestName +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Status: " + (isCancelled ? "Cancelled" : "Active");
    }
}

// Inventory Manager
class InventoryManager {
    private Map<String, Integer> roomInventory = new HashMap<>();

    public InventoryManager() {
        roomInventory.put("Single", 2);
        roomInventory.put("Double", 2);
        roomInventory.put("Suite", 1);
    }

    public boolean allocateRoom(String roomType) {
        int count = roomInventory.getOrDefault(roomType, 0);
        if (count > 0) {
            roomInventory.put(roomType, count - 1);
            return true;
        }
        return false;
    }

    public void releaseRoom(String roomType) {
        roomInventory.put(roomType, roomInventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + roomInventory);
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              Map<String, Reservation> reservationMap,
                              InventoryManager inventoryManager) {

        if (!reservationMap.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        Reservation res = reservationMap.get(reservationId);

        if (res.isCancelled()) {
            System.out.println("Cancellation failed: Already cancelled.");
            return;
        }

        // Push room ID for rollback tracking
        rollbackStack.push(res.getRoomId());

        // Restore inventory
        inventoryManager.releaseRoom(res.getRoomType());

        // Mark cancelled
        res.cancel();

        System.out.println("Booking cancelled successfully. Room " + res.getRoomId() + " released.");
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        InventoryManager inventory = new InventoryManager();
        CancellationService cancellationService = new CancellationService();

        Map<String, Reservation> reservationMap = new HashMap<>();

        int roomCounter = 1;

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Book Room");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Reservations");
            System.out.println("4. View Inventory");
            System.out.println("5. View Rollback Stack");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type (Single/Double/Suite): ");
                    String roomType = sc.nextLine();

                    if (!inventory.allocateRoom(roomType)) {
                        System.out.println("Booking failed: No rooms available.");
                        break;
                    }

                    String roomId = "R" + roomCounter++;
                    Reservation res = new Reservation(id, name, roomType, roomId);

                    reservationMap.put(id, res);

                    System.out.println("Booking successful! Room allocated: " + roomId);
                    break;

                case 2:
                    System.out.print("Enter Reservation ID to cancel: ");
                    String cancelId = sc.nextLine();

                    cancellationService.cancelBooking(cancelId, reservationMap, inventory);
                    break;

                case 3:
                    if (reservationMap.isEmpty()) {
                        System.out.println("No reservations found.");
                    } else {
                        for (Reservation r : reservationMap.values()) {
                            System.out.println(r);
                        }
                    }
                    break;

                case 4:
                    inventory.displayInventory();
                    break;

                case 5:
                    cancellationService.showRollbackStack();
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