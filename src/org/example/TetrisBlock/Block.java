package org.example.TetrisBlock;

import org.example.GameManager;
import org.example.UI.GamePanel;

import java.awt.*;

public abstract class Block {
    protected static final int TILE_COUNT = 4;
    private static final int POSSIBLE_ROTATIONS = 4;

    private int x, y;
    protected Cell[][] cells;
    protected int currentRotation;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        cells = new Cell[TILE_COUNT][POSSIBLE_ROTATIONS];
        currentRotation = 0;
        initCells();
    }

    public Block() {
        this(GameManager.WIDTH / 2 - 2, 0);
    }

    protected abstract void initCells();

    public Cell[] getCurrentRotation() {
        return cells[currentRotation];
    }

    public Cell[] getNextRotation() {
        int nextRotation = (currentRotation + 1) % POSSIBLE_ROTATIONS;
        return cells[nextRotation];
    }

    public void rotate() {
        currentRotation = (currentRotation + 1) % POSSIBLE_ROTATIONS;
    }

    public void moveDown() {
        y++;
    }

    public void moveHorizontal(int dx) {
        x += dx;
    }



    public int getX() { return x; }
    public int getY() { return y; }
    public abstract Color getColor();

    public void draw(Graphics2D g) {

       for(Cell cell : cells[currentRotation]) {
           int drawX = (x + cell.getX()) * GameManager.TILE_SIZE;
           int drawY = (y + cell.getY()) * GameManager.TILE_SIZE;

           g.setColor(getColor());
           g.fillRect(drawX, drawY, GameManager.TILE_SIZE, GameManager.TILE_SIZE);
           g.setStroke(new BasicStroke(5));
           g.setColor(GamePanel.BACKGROUND_COLOR);
           g.drawRect(drawX, drawY, GameManager.TILE_SIZE, GameManager.TILE_SIZE);
       }
    }
}
