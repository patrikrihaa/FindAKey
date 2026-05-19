package Screens;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {

    public StartScreen() {
        super("Find a Key");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel wrapper = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, new Color(15, 15, 28),
                        0, getHeight(), new Color(28, 28, 58)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        wrapper.setOpaque(true);
        wrapper.add(buildContent());

        setContentPane(wrapper);
        setVisible(true);
    }

    private JPanel buildContent() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(460, 400));

        JLabel title = new JLabel("Find a Key", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 54));
        title.setForeground(new Color(32, 147, 209));
        title.setBounds(0, 30, 460, 65);
        panel.add(title);

        JTextArea info = new JTextArea(
                "Your goal is to find a key and unlock \n" + "a door."
        );
        info.setEditable(false);
        info.setOpaque(false);
        info.setForeground(new Color(200, 200, 215));
        info.setFont(new Font("Courier New", Font.PLAIN, 13));
        info.setBorder(BorderFactory.createEmptyBorder());
        info.setBounds(70, 115, 320, 140);
        panel.add(info);

        JButton start = makeButton("Start Game", new Color(32, 147, 209));
        start.setBounds(130, 278, 200, 50);
        start.addActionListener(e -> {dispose(); new GameScreen(); });
        panel.add(start);

        JButton exit = makeButton("Exit", new Color(150, 35, 35));
        exit.setBounds(130, 340, 200, 38);
        exit.addActionListener(e -> System.exit(0));
        panel.add(exit);

        return panel;
    }

    private JButton makeButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 15));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}