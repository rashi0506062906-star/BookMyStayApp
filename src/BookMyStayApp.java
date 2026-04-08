import java.util.*;

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

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Cost: ₹" + cost;
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

// Reporting Service
class BookingReportService {

    public void showAllBookings(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public void generateSummary(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getCost();
        }

        System.out.println("\n--- Booking Summary ---");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        while (true) {
            System.out.println("\n--- Booking Menu ---");
            System.out.println("1. Confirm Booking");
            System.out.println("2. View Booking History");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type: ");
                    String room = sc.nextLine();

                    System.out.print("Enter Cost: ");
                    double cost = sc.nextDouble();

                    Reservation r = new Reservation(id, name, room, cost);
                    history.addReservation(r);

                    System.out.println("Booking confirmed and added to history.");
                    break;

                case 2:
                    reportService.showAllBookings(history.getAllReservations());
                    break;

                case 3:
                    reportService.generateSummary(history.getAllReservations());
                    break;

                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
