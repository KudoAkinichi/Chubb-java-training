package model;

public class User {
    private String name;
    private String destination;

    public User(String name, String destination) {
        this.name = name;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }
}
