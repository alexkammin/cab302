package com.geraj.assignment.dao;

import com.geraj.assignment.model.Garden;

public interface IGardenDAO {
    void createGarden(Garden garden);

    Garden findGarden(String name, String location);
}
