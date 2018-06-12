package com.example.powermad3.battleship.models;

public class Guess {
    Point point;
    String result;

    public Guess(Point point, String result) {
        this.point = point;
        this.result = result;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
