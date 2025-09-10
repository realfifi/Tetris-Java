package org.example.UI;

import org.example.CustomColors;
import org.example.GameManager;
import org.example.TetrisBlock.Block;
import org.example.TetrisBlock.Cell;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class InformationPanel extends JPanel {
    private final Statistics statistics;
    private final LabelBundle scoreLabel;
    private final LabelBundle highscoreLabel;
    private final LabelBundle timeLabel;
    private final LabelBundle linesLabel;
    private final LabelBundle levelLabel;
    private final NextBlockWindow nextBlockWindow;

    public InformationPanel(final Statistics statistics, final Block nextBlock, int width, int height) {
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(GameManager.PADDING, GameManager.PADDING, GameManager.PADDING, GameManager.PADDING));


        this.statistics = statistics;
        scoreLabel = new LabelBundle("Score", 0);
        highscoreLabel = new LabelBundle("Highscore", 0);
        timeLabel = new LabelBundle("Time elapsed", 0);
        linesLabel = new LabelBundle("Lines collapsed", 0);
        levelLabel = new LabelBundle("Current level", 1);

        updateStats();

        add(scoreLabel);
        add(highscoreLabel);
        add(timeLabel);
        add(linesLabel);
        add(levelLabel);

        nextBlockWindow = new NextBlockWindow(nextBlock);
        add(nextBlockWindow);
    }


    public void updateStats() {
        scoreLabel.setValue(statistics.getScore());
        highscoreLabel.setValue(statistics.getScore());
        timeLabel.setValue((int) statistics.getTimeElapsed());
        linesLabel.setValue(statistics.getLines());
        levelLabel.setValue(statistics.getLevel());
    }

    public void updateNextBlock(final Block nextBlock) {
        nextBlockWindow.setNextBlock(nextBlock);
    }

    class NextBlockWindow extends JPanel {
        private Block nextBLock;

        public NextBlockWindow(final Block nextBLock) {
            setBackground(CustomColors.GAME_PANEL_BACKGROUND_COLOR);

            this.nextBLock = nextBLock;
        }

        public void setNextBlock(final Block nextBlock) {
            this.nextBLock = nextBlock;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for(Cell cell : nextBLock.getCurrentRotation()) {

                int x = getWidth() / 2 - 2 * GameManager.CELL_SIZE + cell.getX() * GameManager.CELL_SIZE;
                int y = getHeight() / 2 - 2 * GameManager.CELL_SIZE + cell.getY() * GameManager.CELL_SIZE;

                g.setColor(nextBLock.getColor());
                g.fillRect(x, y, GameManager.CELL_SIZE, GameManager.CELL_SIZE);
                ((Graphics2D) g ).setStroke(new BasicStroke(5));
                g.setColor(CustomColors.GAME_PANEL_BACKGROUND_COLOR);
                g.drawRect(x, y, GameManager.CELL_SIZE, GameManager.CELL_SIZE);
            }
        }
    }
}
