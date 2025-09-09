package org.example.UI;

import javax.swing.*;

public class GameWindow extends JFrame {
    public GameWindow(String title, int width, int height) {
        setSize(width, height);
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }
}

