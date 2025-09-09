package org.example.UI;

import org.example.GameManager;
import org.example.TetrisBlock.Block;
import org.example.TetrisBlock.Cell;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public static final Color BACKGROUND_COLOR = new Color(30, 30, 50);

    private final GameManager gameManager;

    public GamePanel(GameManager gameManager, int width, int height) {
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
        setBackground(BACKGROUND_COLOR);

        this.gameManager = gameManager;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        final int tileSize = GameManager.TILE_SIZE;

        final Color[][] fallenMatrix = gameManager.getFallenMatrix();
        final Block currentBlock = gameManager.getCurrentBlock();

        for(int row = 0; row < GameManager.HEIGHT; ++row) {
            for(int col = 0; col < GameManager.WIDTH; ++col) {
                // Draw the fallen pieces
                if(fallenMatrix[row][col] != null) {
                    g.setColor(fallenMatrix[row][col]);
                    g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                    g.setStroke(new BasicStroke(5));
                    g.setColor(GamePanel.BACKGROUND_COLOR);
                    g.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
                }
                else {
                    // Draw the grid
                }
            }
        }


        // Draw the ghost blocks
        Color currentBlockColor = currentBlock.getColor();

        for(Cell cell : currentBlock.getCurrentRotation()) {
            int y = gameManager.getGhostY() + cell.getY();
            int x = currentBlock.getX() + cell.getX();

            g.setColor(new Color(currentBlockColor.getRed(), currentBlockColor.getGreen(), currentBlockColor.getBlue(), 70));
            g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            g.setStroke(new BasicStroke(5));
            g.setColor(GamePanel.BACKGROUND_COLOR);
            g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
        }

        currentBlock.draw(g);
    }
}
