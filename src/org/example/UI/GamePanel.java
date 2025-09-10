package org.example.UI;

import org.example.CustomColors;
import org.example.GameManager;
import org.example.TetrisBlock.Block;
import org.example.TetrisBlock.Cell;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GamePanel extends JPanel {

    private final GameManager gameManager;

    public GamePanel(GameManager gameManager, int width, int height) {
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
        setBackground(CustomColors.GAME_PANEL_BACKGROUND_COLOR);
        setBorder(new EmptyBorder(GameManager.PADDING, GameManager.PADDING, GameManager.PADDING, GameManager.PADDING));
        addKeyListener(gameManager);

        this.gameManager = gameManager;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        // Move the coordinates by padding
        g.translate(GameManager.PADDING, GameManager.PADDING);

        final int tileSize = GameManager.CELL_SIZE;
        final Color[][] fallenMatrix = gameManager.getFallenMatrix();
        final Block currentBlock = gameManager.getCurrentBlock();

        for(int row = 0; row < GameManager.HEIGHT; ++row) {
            for(int col = 0; col < GameManager.WIDTH; ++col) {
                // Draw the fallen pieces
                if(fallenMatrix[row][col] != null) {
                    g.setColor(fallenMatrix[row][col]);
                    g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                    g.setStroke(new BasicStroke(5));
                    g.setColor(CustomColors.GAME_PANEL_BACKGROUND_COLOR);
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

            g.setStroke(new BasicStroke(3));
            g.setColor(CustomColors.GAME_PANEL_BACKGROUND_COLOR.brighter());
            g.drawRect(x * tileSize + 3, y * tileSize + 3, tileSize - 6, tileSize - 6);
        }

        currentBlock.draw(g);
    }
}
