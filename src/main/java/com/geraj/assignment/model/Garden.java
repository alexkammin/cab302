package com.geraj.assignment.model;

import java.util.Objects;
import java.util.List;

public class Garden {
    private String name;
    private String location;
    private Double temperature;
    private Double precipitation;
    private Integer atmosphericHumidity;
    private Account owner;
    // Overall garden dimensions; null for records created before US17.
    private Integer id;
    private Double width;
    private Double length;
    private int planterBoxCount;
    private List<Integer> planterBoxIds = List.of();

    /** Creates a validated US17 garden. Each empty box receives a stable database ID on save. */
    public static Garden create(String name, String location, double width, double length,
                                int planterBoxCount, Account owner) {
        if (!GardenValidator.validate(name, location, Double.toString(width),
                Double.toString(length), Integer.toString(planterBoxCount)).isEmpty()) {
            throw new IllegalArgumentException("Invalid garden details");
        }
        if (owner == null) throw new IllegalArgumentException("Sign in before creating a garden");
        Garden garden = new Garden(name.trim(), location.trim(), null, null, null, owner);
        garden.width = width;
        garden.length = length;
        garden.planterBoxCount = planterBoxCount;
        return garden;
    }

    public Integer getId() { return id; }
    public Double getWidth() { return width; }
    public Double getLength() { return length; }
    public int getPlanterBoxCount() { return planterBoxCount; }
    public List<Integer> getPlanterBoxIds() { return planterBoxIds; }

    /** Restores database identity and layout, including legacy gardens without dimensions. */
    public void restoreLayout(int id, Double width, Double length, List<Integer> boxIds) {
        this.id = id;
        this.width = width;
        this.length = length;
        this.planterBoxIds = List.copyOf(boxIds);
        this.planterBoxCount = boxIds.size();
    }

    /** The existing owner relationship gives the creator admin access to this garden. */
    public boolean isAdmin(Account account) {
        return account != null && owner != null && owner.getName().equals(account.getName());
    }

    public Garden(String name,
                  String location,
                  Double temperature,
                  Double precipitation,
                  Integer atmosphericHumidity,
                  Account owner) {
        this.name = Objects.requireNonNull(name, "Garden name cannot be null");
        this.location = Objects.requireNonNull(location, "Garden location cannot be null");
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.atmosphericHumidity = atmosphericHumidity;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public Integer getAtmosphericHumidity() {
        return atmosphericHumidity;
    }

    public void setAtmosphericHumidity(int atmosphericHumidity) {
        this.atmosphericHumidity = atmosphericHumidity;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public String toString() {
        return getName() + " - " + getLocation();
    }
}
