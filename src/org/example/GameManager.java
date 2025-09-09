package org.example;

import org.example.TetrisBlock.Block;
import org.example.TetrisBlock.Blocks.*;
import org.example.TetrisBlock.Cell;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameManager implements KeyListener {
    public static final int TOTAL_BLOCKS_COUNT = 7;
    public static final int TILE_SIZE = 32; // px
    public static final int HEIGHT = 20; // tiles
    public static final int WIDTH = 10; // tiles
    private static final int WINDOW_WIDTH = WIDTH * TILE_SIZE; // px
    private static final int WINDOW_HEIGHT = HEIGHT * TILE_SIZE; //px

    private boolean isPlaying;
    private final GameWindow window;
    private final GamePanel panel;

    private Block currentBlock;
    private final Color[][] fallenMatrix;

    public GameManager() {
        isPlaying = true;
        window = new GameWindow("Tetris", WINDOW_WIDTH, WINDOW_HEIGHT);
        panel = new GamePanel(this, WINDOW_WIDTH, WINDOW_HEIGHT);

        currentBlock = getRandomBlock();
        fallenMatrix = new Color[GameManager.HEIGHT][GameManager.WIDTH];

        panel.addKeyListener(this);
        window.add(panel);
        window.pack();
        window.setVisible(true);
    }

    public void start() {
        long lastUpdate = System.currentTimeMillis();
        while (isPlaying) {
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastUpdate >= 500) {
                update();
                lastUpdate = currentTime;
            }

            panel.repaint();
        }
    }

    private void update() {
        if(canMoveDown()) {
            currentBlock.moveDown();
        }
        else {
            fillFallenMatrix();
            checkIfRowsFull();

            if(currentBlock.getY() <= 0) {
                // handle Game Over
            }

            currentBlock = getRandomBlock();
        }

        panel.repaint();
    }

    public int getGhostY() {
        int ghostY = currentBlock.getY();
        while(!doesCollideAt(currentBlock.getCurrentRotation(), 0, ghostY - currentBlock.getY() + 1)) {
            ghostY++;
        }
        return ghostY;
    }

    private Block getRandomBlock() {
        int random = (int) (Math.random() * TOTAL_BLOCKS_COUNT);
        return switch(random) {
            case 0 -> new IBlock();
            case 1 -> new OBlock();
            case 2 -> new TBlock();
            case 3 -> new SBlock();
            case 4 -> new ZBlock();
            case 5 -> new LBlock();
            case 6 -> new JBlock();
            default -> new OBlock();
        };
    }

    private void fillFallenMatrix() {
        for(Cell cell : currentBlock.getCurrentRotation()) {
            int x = currentBlock.getX() + cell.getX();
            int y = currentBlock.getY() + cell.getY();

            fallenMatrix[y][x] = currentBlock.getColor();
        }
    }

    private void checkIfRowsFull() {
        for(int row = HEIGHT - 1; row >= 0; --row) {
            boolean full = true;

            for(int col = 0; col < WIDTH; ++col) {
                if(fallenMatrix[row][col] == null) {
                    full = false;
                    break;
                }
            }

            if(full) {
                deleteRow(row);
                row++;
            }
        }
    }

    private void deleteRow(int nRow) {
        for(int row = nRow; row > 0; --row) {
            fallenMatrix[row] = fallenMatrix[row-1];
        }

        fallenMatrix[0] = new Color[WIDTH];
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public Color[][] getFallenMatrix() {
        return fallenMatrix;
    }


    private boolean canMoveHorizontal(int dx) {
        return !doesCollideAt(currentBlock.getCurrentRotation(), dx, 0);
    }

    private boolean canMoveDown() {
        return !doesCollideAt(currentBlock.getCurrentRotation(), 0, 1);
    }

    private boolean canRotate() {
        return !doesCollideAt(currentBlock.getNextRotation(), 0, 0);
    }


    private boolean doesCollideAt(Cell[] rotation, int dx, int dy) {
        for (Cell cell : rotation) {
            int nextX = currentBlock.getX() + cell.getX() + dx;
            int nextY = currentBlock.getY() + cell.getY() + dy;

            if (nextX < 0 || nextX >= GameManager.WIDTH) {
                return true;
            }

            if (nextY >= GameManager.HEIGHT) {
                return true;
            }

            if (nextY >= 0 && nextX >= 0 && fallenMatrix[nextY][nextX] != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                if(canMoveHorizontal(1)) {
                    currentBlock.moveHorizontal(1);
                }
            }
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                if(canMoveHorizontal(-1)) {
                    currentBlock.moveHorizontal(-1);
                }
            }
            case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                if(canRotate()) {
                    currentBlock.rotate();
                }
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                if(canMoveDown()) {
                    currentBlock.moveDown();
                }
            }
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
