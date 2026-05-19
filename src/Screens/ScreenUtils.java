package Screens;

import javax.swing.*;
import java.awt.*;

public class ScreenUtils {

    public static JButton makeButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static JLabel makeTitle(String text, Color color) {
        JLabel title = new JLabel(text, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 64));
        title.setForeground(color);
        return title;
    }

    public static JTextArea makeText(String text) {
        JTextArea txt = new JTextArea(text);
        txt.setForeground(Color.WHITE);
        txt.setFont(new Font("Courier New", Font.PLAIN, 14));
        txt.setOpaque(false);
        txt.setBorder(BorderFactory.createEmptyBorder());
        txt.setEditable(false);
        return txt;
    }

    public static JPanel makeGradient(Color topColor, Color botColor) {
        JPanel gradient = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0,0, topColor, 0, getHeight(), botColor));
                g2.fillRect(0,0, getWidth(), getHeight());
            }
        };
        gradient.setOpaque(true);


        return gradient;
    }
}
