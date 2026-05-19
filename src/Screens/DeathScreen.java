package Screens;

import javax.swing.*;
import java.awt.*;

public class DeathScreen extends JFrame {
        public DeathScreen() {
            super("Locked");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);

            JPanel wrapper = new JPanel(new GridBagLayout()) {
                @Override protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setPaint(new GradientPaint(0, 0, new Color(60, 17, 17),
                            0, getHeight(), new Color(51, 9, 9)));
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

            JLabel title = new JLabel("You died", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 64));
            title.setForeground(new Color(255, 0, 0));
            title.setBounds(0, 30, 460, 65);
            panel.add(title);

            JTextArea info = new JTextArea(
                    "The cave claimed another victim. You stepped on" +
                            "\na spike and your journey came to an abrupt end." +
                            "\nThe darkness is unforgiving, only the most careful" +
                            "\nexplorers make it through alive." +

                            "\n\nHint: Don't step on the spikes!"
            );

            info.setEditable(false);
            info.setOpaque(false);
            info.setForeground(new Color(255, 255, 255));
            info.setFont(new Font("Courier New", Font.PLAIN, 14));
            info.setBorder(BorderFactory.createEmptyBorder());
            info.setBounds(40, 120, 520, 180);
            panel.add(info);

            JButton death = makeButton("Try again", new Color(211, 94, 18));
            death.setBounds(130, 280, 220, 55);
            death.addActionListener(e -> {dispose(); new GameScreen(); });
            panel.add(death);

            JButton exit = makeButton("Exit", new Color(90, 20, 20));
            exit.setBounds(130, 340, 220, 40);
            exit.addActionListener(e -> System.exit(0));
            panel.add(exit);

            return panel;
        }

        private JButton makeButton(String text, Color bg) {
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
