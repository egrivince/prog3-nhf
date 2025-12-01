package org.example;

public enum Player{
    TOP, BOTTOM;

    public Player other() {
        if(this == TOP) {
            return BOTTOM;
        }
        else {
            return TOP;
        }
    }
}
