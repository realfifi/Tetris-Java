package org.example.UI;

import org.example.CustomColors;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LabelBundle extends JPanel {
    private static final int MARGIN_BOTTOM = 12;

    private final JLabel titleLabel;
    private final JLabel valueLabel;
    private Font font;

    public LabelBundle(String title, int value) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initFont();

        titleLabel = new JLabel(title);
        titleLabel.setFont(font.deriveFont(Font.PLAIN, 24));
        titleLabel.setForeground(CustomColors.SECONDARY_TEXT);

        valueLabel = new JLabel(Integer.toString(value));
        valueLabel.setFont(font.deriveFont(Font.PLAIN, 32));
        valueLabel.setForeground(CustomColors.MAIN_TEXT);

        add(titleLabel);
        add(valueLabel);
        add(Box.createVerticalStrut(MARGIN_BOTTOM));
    }

    private void initFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/fonts/upheavtt.ttf"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(int value) {
        valueLabel.setText(Integer.toString(value));
    }
}