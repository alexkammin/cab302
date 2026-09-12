package com.geraj.assignment.model;

public class Plant {
    private int id;
    private String commonName;
    private int growthMonths;
    private double phMax;
    private double phMin;
    private int light;
    private double minDepth;

    public Plant(String commonName,
                 int growthMonths,
                 double phMax,
                 double phMin,
                 int light,
                 double minDepth) {
        this.commonName = commonName;
        this.growthMonths = growthMonths;
        this.phMax = phMax;
        this.phMin = phMin;
        this.light = light;
        this.minDepth = minDepth;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public int getGrowthMonths() {
        return growthMonths;
    }

    public void setGrowthMonths(int growthMonths) {
        this.growthMonths = growthMonths;
    }

    public double getPhMax() {
        return phMax;
    }

    public void setPhMax(double phMax) {
        this.phMax = phMax;
    }

    public double getPhMin() {
        return phMin;
    }

    public void setPhMin(double phMin) {
        this.phMin = phMin;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public double getMinDepth() {
        return minDepth;
    }

    public void setMinDepth(double minDepth) {
        this.minDepth = minDepth;
    }
}
