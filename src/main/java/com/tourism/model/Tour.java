package com.tourism.model;

public class Tour {
    private String id;
    private String name;
    private String country;
    private String city;
    private double price;
    private int duration;
    private String hotel;
    private String type;
    private int availableSpots;
    private double rating;

    public Tour() {}

    public Tour(String id, String name, String country, String city,
                double price, int duration, String hotel, String type,
                int availableSpots, double rating) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.price = price;
        this.duration = duration;
        this.hotel = hotel;
        this.type = type;
        this.availableSpots = availableSpots;
        this.rating = rating;
    }

    // Геттеры и сеттеры
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getHotel() { return hotel; }
    public void setHotel(String hotel) { this.hotel = hotel; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getAvailableSpots() { return availableSpots; }
    public void setAvailableSpots(int availableSpots) { this.availableSpots = availableSpots; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public double getTotalPrice() {
        return price * duration;
    }
}