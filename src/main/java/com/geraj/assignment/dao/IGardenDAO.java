package com.geraj.assignment.dao;

import com.geraj.assignment.model.Garden;

import java.util.ArrayList;

public interface IGardenDAO {
    void createGarden(Garden garden);

    ArrayList<Garden> findGardens(String searchName, String searchLocation);
}
