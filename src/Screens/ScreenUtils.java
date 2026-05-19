package Screens;

import javax.swing.*;
import java.awt.*;

/**
 * Shared factory methods for building UI elements across all screens.
 * Keeps the look consistent and avoids repeating the same styling code
 * in StartScreen, DeathScreen, and WinScreen.
 *
 * @author Patrik Říha
 */
public class ScreenUtils {

    /**
     * Creates a styled button with a colored background and white text.
     *
     * @param text shown on the button
     * @param bg background color
     * @return a ready-to-use JButton with no border or focus ring
     */
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

    /**
     * Creates a large, centered title label.
     *
     * @param text title text
     * @param color text color
     * @return a JLabel sized for a 64pt bold heading
     */
    public static JLabel makeTitle(String text, Color color) {
        JLabel title = new JLabel(text, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 64));
        title.setForeground(color);
        return title;
    }

    /**
     * Creates a non-editable text area for multiline body text.
     * Background is transparent so it blends into the gradient panel behind it.
     *
     * @param text content to display
     * @return a styled, read-only JTextArea
     */
    public static JTextArea makeText(String text) {
        JTextArea txt = new JTextArea(text);
        txt.setForeground(Color.WHITE);
        txt.setFont(new Font("Courier New", Font.PLAIN, 14));
        txt.setOpaque(false);
        txt.setBorder(BorderFactory.createEmptyBorder());
        txt.setEditable(false);
        return txt;
    }

    /**
     * Creates a JPanel that paints a vertical gradient between two colors.
     * Uses GridBagLayout so content added to it is automatically centered.
     *
     * @param topColor color at the top of the gradient
     * @param botColor color at the bottom
     * @return a panel with a live-painted gradient background
     */
    public static JPanel makeGradient(Color topColor, Color botColor) {
        JPanel gradient = new JPanel(new GridBagLayout()) {

            @Override
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
