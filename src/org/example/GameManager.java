package org.example;

import org.example.TetrisBlock.Block;
import org.example.TetrisBlock.Blocks.*;
import org.example.TetrisBlock.Cell;
import org.example.UI.GamePanel;
import org.example.UI.GameWindow;
import org.example.UI.Statistics;
import org.example.UI.StatisticsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameManager implements KeyListener {
    public static final Color BACKGROUND_COLOR = new Color(20, 20, 30);
    public static final int TOTAL_BLOCKS_COUNT = 7;
    public static final int TILE_SIZE = 32; // px
    public static final int HEIGHT = 20; // tiles
    public static final int WIDTH = 10; // tiles
    private static final int WINDOW_WIDTH = WIDTH * TILE_SIZE; // px
    private static final int WINDOW_HEIGHT = HEIGHT * TILE_SIZE; //px

    private int gameSpeed;
    private boolean isPlaying;
    private final GameWindow window;
    private final GamePanel gamePanel;
    private final StatisticsPanel statsPanel;

    private Block currentBlock;
    private final Color[][] fallenMatrix;

    private final Statistics statistics;

    public GameManager() {
        isPlaying = true;
        window = new GameWindow("Tetris", WINDOW_WIDTH, WINDOW_HEIGHT);
        gamePanel = new GamePanel(this, WINDOW_WIDTH, WINDOW_HEIGHT);
        gamePanel.addKeyListener(this);

        currentBlock = getRandomBlock();
        fallenMatrix = new Color[GameManager.HEIGHT][GameManager.WIDTH];
        gameSpeed = 20;

        statistics = new Statistics();
        statsPanel = new StatisticsPanel(statistics, WINDOW_WIDTH, WINDOW_HEIGHT);

        JPanel holderPanel = new JPanel(new FlowLayout());
        holderPanel.setBackground(BACKGROUND_COLOR);

        holderPanel.add(gamePanel);
        holderPanel.add(statsPanel);
        window.add(holderPanel);
        window.pack();
        window.setVisible(true);
    }

    public void start() {
        long lastTime = System.currentTimeMillis();
        long lastSecondTime = System.currentTimeMillis();

        while (isPlaying) {
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastTime >= 10_000 / gameSpeed) {
                update();
                lastTime = currentTime;
            }

            if(currentTime - lastSecondTime >= 1000) {
                statistics.incremenetTime();
                lastSecondTime = currentTime;
            }

            gamePanel.repaint();
        }
    }

    private void update() {
        if(canMoveDown()) {
            currentBlock.moveDown();
        }
        else {
            fillFallenMatrix();
            checkIfRowsFull();

            if(currentBlock.getY() <= 1) {
                isPlaying = false;
                return;
            }

            currentBlock = getRandomBlock();
        }

        statsPanel.updateStats();
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
        int deletedRows = 0;

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
                deletedRows++;
            }
        }

        if(deletedRows > 0) {
            awardPoints(deletedRows);
        }

        int newLevel = (statistics.getLines() / 10) + 1;
        if(newLevel > statistics.getLevel()) {
            statistics.incrementLevel();
            gameSpeed++;
        }
    }

    private void deleteRow(int nRow) {
        for(int row = nRow; row > 0; --row) {
            fallenMatrix[row] = fallenMatrix[row-1];
        }

        fallenMatrix[0] = new Color[WIDTH];

        statistics.incrementLines();
    }

    private void awardPoints(final int rows) {
        switch(rows) {
            case 1 -> statistics.addScore(40 * statistics.getLevel());
            case 2 -> statistics.addScore(100 * statistics.getLevel());
            case 3 -> statistics.addScore(300 * statistics.getLevel());
            case 4 -> statistics.addScore(1200 * statistics.getLevel());
        }
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
