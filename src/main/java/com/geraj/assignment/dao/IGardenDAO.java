package com.geraj.assignment.dao;

public interface IGardenDAO {
    void createGarden(String name, String hash);

    void findGarden(String name, String Location);
}
