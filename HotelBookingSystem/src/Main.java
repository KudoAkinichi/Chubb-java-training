import model.*;
import service.Invoice;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // User info
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your destination: ");
        String destination = sc.nextLine();
        User user = new User(name, destination);

        // Create sample hotels and rooms
        List<Room> rooms1 = Arrays.asList(
            new Room("101", "Single", 1000),
            new Room("102", "Double", 1500),
            new Room("103", "Suite", 2500)
        );
        Hotel hotel1 = new Hotel("Grand Horizon", rooms1);

        List<Room> rooms2 = Arrays.asList(
            new Room("201", "Single", 900),
            new Room("202", "Double", 1400)
        );
        Hotel hotel2 = new Hotel("Seaside Resort", rooms2);

        List<Hotel> hotels = Arrays.asList(hotel1, hotel2);

        // Select hotel
        System.out.println("\nAvailable Hotels:");
        for (int i = 0; i < hotels.size(); i++) {
            System.out.println((i+1) + ". " + hotels.get(i).getName());
        }
        System.out.print("Select a hotel (1/2): ");
        int hotelChoice = sc.nextInt();
        Hotel selectedHotel = hotels.get(hotelChoice - 1);

        // Show available rooms
        System.out.println("\nAvailable Rooms:");
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : selectedHotel.getRooms()) {
            if (room.isAvailable()) {
                availableRooms.add(room);
                System.out.println(room.getRoomNumber() + " - " + room.getType() + " - $" + room.getPrice());
            }
        }

        // Select room
        System.out.print("Enter room number to book: ");
        sc.nextLine(); // clear buffer
        String roomNum = sc.nextLine();
        Room roomToBook = selectedHotel.getAvailableRoomByNumber(roomNum);

        if (roomToBook == null) {
            System.out.println("Room not available or invalid room number.");
        } else {
            roomToBook.book();
            Invoice.printInvoice(user, selectedHotel, roomToBook);
        }

        sc.close();
    }
}
