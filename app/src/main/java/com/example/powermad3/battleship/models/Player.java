package com.example.powermad3.battleship.models;

import java.util.ArrayList;

public class Player {
    ArrayList<Ship> ships;
    ArrayList<Guess> guesses;
    char board[][];

    public Player(ArrayList<Ship> ships, ArrayList<Guess> guesses, char[][] board) {
        this.ships = ships;
        this.guesses = guesses;
        this.board = board;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public ArrayList<Guess> getGuesses() {
        return guesses;
    }

    public void setGuesses(ArrayList<Guess> guesses) {
        this.guesses = guesses;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }
}
