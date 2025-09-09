package org.example.UI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsPanel extends JPanel {

    private class LableBundle {
        private JLabel title;
        private JLabel value;

        public LableBundle(String title, int value) {
            this.title = createCustomLabel();
        }
    }

    private final Statistics statistics;
    private final JLabel score;
    private final JLabel lines;
    private final JLabel time;
    private final JLabel level;
    private final Font font;

    public StatisticsPanel(Statistics statistics, int width, int height) {
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/fonts/upheavtt.ttf"))
                    .deriveFont(Font.PLAIN, 24);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.statistics = statistics;
        score = createCustomLabel();
        time = createCustomLabel();
        lines = createCustomLabel();
        level = createCustomLabel();

        updateStats();

        add(score);
        add(lines);
        add(time);
        add(level);
    }

    public JLabel createCustomLabel() {
        JLabel label = new JLabel();
        label.setFont(font);
        label.setForeground(Color.WHITE);
        return label;
    }

    public void updateStats() {
        score.setText("Score: " + statistics.getScore());
        lines.setText("Lines: " + statistics.getLines());
        time.setText("Time: " + statistics.getTimeElapsed());
        level.setText("Level: " + statistics.getLevel());
    }


}
