package service;

import model.User;
import model.Hotel;
import model.Room;

public class Invoice {
    public static void printInvoice(User user, Hotel hotel, Room room) {
        System.out.println("\n--- INVOICE ---");
        System.out.println("Customer Name: " + user.getName());
        System.out.println("Destination: " + user.getDestination());
        System.out.println("Hotel: " + hotel.getName());
        System.out.println("Room: " + room.getRoomNumber() + " (" + room.getType() + ")");
        System.out.println("Price: $" + room.getPrice());
        System.out.println("-------------------");
        System.out.println("Thank you for booking with us!");
    }
}
