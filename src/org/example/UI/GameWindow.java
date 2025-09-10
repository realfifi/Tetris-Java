package org.example.UI;

import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow(String title, int width, int height) {
        super(title);
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

}

