package com.example.powermad3.battleship.models;

import java.util.ArrayList;

public class Ship {
    private int health;
    private ArrayList<Point> locations;
    private boolean alive;
    private String name;

    public Ship(int health, ArrayList<Point> locations, boolean alive, String name) {
        this.health = health;
        this.locations = locations;
        this.alive = alive;
        this.name = name;
    }

    public Ship(int health, Point initial, boolean alive, String name,char[][] board) {
        this.health = health;
        this.locations = buildPointList(board);
        this.alive = alive;
        this.name = name;
    }

    private ArrayList<Point> buildPointList(char[][] board){
        ArrayList<Point> list= new ArrayList<>();

        return list;
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public ArrayList<Point> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Point> locations) {
        this.locations = locations;
    }

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
