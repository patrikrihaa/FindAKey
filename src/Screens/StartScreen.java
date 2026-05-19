package Screens;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {

    public StartScreen() {
        super("Locked");
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

        JLabel title = new JLabel("Locked", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 64));
        title.setForeground(new Color(32, 147, 209));
        title.setBounds(0, 30, 460, 65);
        panel.add(title);

        JTextArea info = new JTextArea(
                "Deep inside a dark cave a locked door stands between" +
                        "\nyou and freedom. Find the hidden key somewhere in" +
                        "\nthe darkness, make your way past the obstacles and" +
                        "\nspikes, and unlock the door to escape." +

                        "\n\nControls:" +
                        "\nA / D — move left / right" +
                        "\nSPACE — jump" +
                        "\nE — interact with door"
        );

        info.setEditable(false);
        info.setOpaque(false);
        info.setForeground(new Color(255, 255, 255));
        info.setFont(new Font("Courier New", Font.PLAIN, 14));
        info.setBounds(40, 110, 520, 180);
        panel.add(info);

        JButton start = makeButton("Start Game", new Color(32, 147, 209));
        start.setBounds(130, 280, 220, 55);
        start.addActionListener(e -> {dispose(); new GameScreen(); });
        panel.add(start);

        JButton exit = makeButton("Exit", new Color(50, 55, 75));
        exit.setBounds(130, 340, 220, 40);
        exit.addActionListener(e -> System.exit(0));
        panel.add(exit);

        return panel;
    }

    public JButton makeButton(String text, Color bg) {
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
}