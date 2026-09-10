package com.geraj.assignment.model;

import java.util.ArrayList;

/**
 * A simple model class representing a Garden with width, length, ph, light, nutriments, salinity, texture, depth, soil humidity, contributors and plants.
 */
public class GardenPlot {
    private double width;
    private double length;
    private double ph;
    private int light;
    private int nutriments;
    private int salinity;
    private int texture;
    private double depth;
    private int soilHumidity;
    private ArrayList<Account> contributors;
//    private ArrayList<Plant> plants;

    /**
     * Constructs a new Garden with the specified width, length, ph, light, nutriments, salinity, texture, depth, soil humidity, contributors and plants.
     * @param width The width of the garden.
     * @param length The length of the garden.
     * @param ph The ph of the garden.
     * @param light The light of the garden.
     * @param nutriments The nutriments of the garden.
     * @param salinity The salinity of the garden.
     * @param texture The texture of the garden.
     * @param depth The depth of the garden.
     * @param soilHumidity The soil humidity of the garden.
     */
    public GardenPlot (double width,
            double length,
            double ph,
            int light,
            int nutriments,
            int salinity,
            int texture,
            double depth,
            int soilHumidity) {
        this.length = length;
        this.ph = ph;
        this.light = light;
        this.nutriments = nutriments;
        this.salinity = salinity;
        this.texture = texture;
        this.depth = depth;
        this.soilHumidity = soilHumidity;
    }

    /**
     * Gets the width of the garden.
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the garden.
     * @param width the new width of the garden
     */
    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getNutriments() {
        return nutriments;
    }

    public void setNutriments(int nutriments) {
        this.nutriments = nutriments;
    }

    public int getSalinity() {
        return salinity;
    }

    public void setSalinity(int salinity) {
        this.salinity = salinity;
    }

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public int getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(int soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public ArrayList<Account> getContributors() {
        return contributors;
    }

    public void addContributor(Account contributor) {
        this.contributors.add(contributor);
    }

    public void removeContributor(Account contributor) {
        this.contributors.remove(contributor);
    }
}
