import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double cost;

    public Reservation(String reservationId, String guestName, String roomType, double cost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Cost: ₹" + cost;
    }
}

// Validator class
class BookingValidator {

    private static final Set<String> VALID_ROOM_TYPES =
            new HashSet<>(Arrays.asList("Single", "Double", "Suite"));

    // Validate input
    public static void validate(String reservationId, String guestName,
                                String roomType, double cost) throws InvalidBookingException {

        if (reservationId == null || reservationId.trim().isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty.");
        }

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!VALID_ROOM_TYPES.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type. Allowed: Single, Double, Suite.");
        }

        if (cost <= 0) {
            throw new InvalidBookingException("Cost must be greater than zero.");
        }
    }
}

// Booking History
class BookingHistory {
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingHistory history = new BookingHistory();

        while (true) {
            try {
                System.out.println("\n--- Booking Menu ---");
                System.out.println("1. Confirm Booking");
                System.out.println("2. View Booking History");
                System.out.println("3. Exit");

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
                        String room = sc.nextLine();

                        System.out.print("Enter Cost: ");
                        double cost = sc.nextDouble();

                        // ✅ Validation (Fail-Fast)
                        BookingValidator.validate(id, name, room, cost);

                        // If valid → create reservation
                        Reservation r = new Reservation(id, name, room, cost);
                        history.addReservation(r);

                        System.out.println("Booking confirmed successfully!");
                        break;

                    case 2:
                        List<Reservation> list = history.getAllReservations();
                        if (list.isEmpty()) {
                            System.out.println("No bookings found.");
                        } else {
                            System.out.println("\n--- Booking History ---");
                            for (Reservation res : list) {
                                System.out.println(res);
                            }
                        }
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid menu choice!");
                }

            } catch (InvalidBookingException e) {
                // ✅ Custom exception handling
                System.out.println("Booking Failed: " + e.getMessage());

            } catch (InputMismatchException e) {
                // ✅ Invalid input type handling
                System.out.println("Invalid input type! Please enter correct values.");
                sc.nextLine(); // clear buffer

            } catch (Exception e) {
                // ✅ Safety net (graceful failure)
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}