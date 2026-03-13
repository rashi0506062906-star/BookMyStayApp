import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay - Hotel Booking System
 * Use Case 5: Booking Request (First-Come-First-Served)
 * Demonstrates booking request intake using Queue.
 *
 * @author Rashi
 * @version 5.1
 */

// Reservation class representing a booking request
class Reservation {

    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
        System.out.println("-----------------------------");
    }
}

// Booking request queue manager
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.guestName);
    }

    // Display all queued requests
    public void displayRequests() {

        System.out.println("\n===== Booking Requests in Queue =====");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay - Booking Request Queue =====");

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Amit", "Single Room");
        Reservation r2 = new Reservation("Priya", "Double Room");
        Reservation r3 = new Reservation("Rahul", "Suite Room");

        // Add requests to queue (FIFO order)
        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        // Display queued booking requests
        queue.displayRequests();

        System.out.println("All requests stored in arrival order (FIFO).");
        System.out.println("Room allocation will occur in a later stage.");
    }
}