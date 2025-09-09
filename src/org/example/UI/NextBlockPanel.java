package org.example.UI;

import javax.swing.*;
import java.awt.*;

public class NextBlockPanel extends JPanel {
    public NextBlockPanel(int width, int height) {
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setBackground(GamePanel.BACKGROUND_COLOR);
    }
}
