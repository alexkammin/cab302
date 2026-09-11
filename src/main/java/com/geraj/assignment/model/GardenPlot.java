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
        this.width = width;
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
     * Gets the width of the garden plot.
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the garden plot.
     * @param width the new width of the garden plot
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Gets the length of the garden plot.
     * @return the length
     */
    public double getLength() {
        return length;
    }

    /**
     * Sets the length of the garden plot.
     * @param length the new length of the garden plot
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Gets the pH of the garden plot.
     * @return the pH
     */
    public double getPh() {
        return ph;
    }

    /**
     * Sets the pH of the garden plot.
     * @param ph the new pH of the garden plot
     */
    public void setPh(double ph) {
        this.ph = ph;
    }

    /**
     * Gets the light of the garden plot.
     * @return the light
     */
    public int getLight() {
        return light;
    }

    /**
     * Sets the light of the garden plot.
     * @param light the new light of the garden plot
     */
    public void setLight(int light) {
        this.light = light;
    }

    /**
     * Gets the nutriments of the garden plot.
     * @return the nutriments
     */
    public int getNutriments() {
        return nutriments;
    }

    /**
     * Sets the nutriments of the garden plot.
     * @param nutriments the new nutriments of the garden plot
     */
    public void setNutriments(int nutriments) {
        this.nutriments = nutriments;
    }

    /**
     * Gets the salinity of the garden plot.
     * @return the salinity
     */
    public int getSalinity() {
        return salinity;
    }

    /**
     * Sets the salinity of the garden plot.
     * @param salinity the new salinity of the garden plot
     */
    public void setSalinity(int salinity) {
        this.salinity = salinity;
    }

    /**
     * Gets the texture of the garden plot.
     * @return the texture
     */
    public int getTexture() {
        return texture;
    }

    /**
     * Sets the texture of the garden plot.
     * @param texture the new texture of the garden plot
     */
    public void setTexture(int texture) {
        this.texture = texture;
    }

    /**
     * Gets the depth of the garden plot.
     * @return the depth
     */
    public double getDepth() {
        return depth;
    }

    /**
     * Sets the depth of the garden plot.
     * @param depth the new depth of the garden plot
     */
    public void setDepth(double depth) {
        this.depth = depth;
    }

    /**
     * Gets the soil humidity of the garden plot.
     * @return the soil humidity
     */
    public int getSoilHumidity() {
        return soilHumidity;
    }

    /**
     * Sets the soil humidity of the garden plot.
     * @param soilHumidity the new soil humidity of the garden plot
     */
    public void setSoilHumidity(int soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    /**
     * Gets the list of contributors assigned to the garden plot.
     * @return the list of contributors
     */
    public ArrayList<Account> getContributors() {
        return contributors;
    }

    /**
     * Adds a contributor to the list of contributors assigned to the garden plot.
     * @param contributor the contributor to add
     */
    public void addContributor(Account contributor) {
        this.contributors.add(contributor);
    }

    /**
     * Removes a contributor from the list of contributors assigned to the garden plot.
     * @param contributor the contributor to remove
     */
    public void removeContributor(Account contributor) {
        this.contributors.remove(contributor);
    }
}
