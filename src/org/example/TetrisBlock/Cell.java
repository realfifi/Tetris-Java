package org.example.TetrisBlock;

public class Cell {
    private final int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell() {
        this(0,0);
    }

    public int[] get() {
        return new int[] {x, y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
