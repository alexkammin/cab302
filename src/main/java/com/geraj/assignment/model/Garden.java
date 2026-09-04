package com.geraj.assignment.model;

import java.util.ArrayList;
import java.util.Objects;

public class Garden {
    private String name;
    private String location;
    private Double temperature;
    private Double precipitation;
    private Integer atmosphericHumidity;
    private Account owner;
//    private ArrayList<GardenPlot> gardenPlots;

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
}
