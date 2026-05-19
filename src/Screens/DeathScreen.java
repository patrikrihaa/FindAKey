package Screens;

import javax.swing.*;
import java.awt.*;

import static Screens.ScreenUtils.*;

public class DeathScreen extends JFrame {
        public DeathScreen() {
            super("Locked");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);

            JPanel gradient = makeGradient(new Color(60,17,17), new Color(51,9,9));
            gradient.add(buildContent());
            setContentPane(gradient);

            setVisible(true);
        }

        private JPanel buildContent() {
            JPanel panel = new JPanel(null);
            panel.setOpaque(false);
            panel.setPreferredSize(new Dimension(460, 400));

            JLabel title = makeTitle("You died", new Color(255, 0, 0));
            title.setBounds(0, 30, 460, 65);
            panel.add(title);

            JTextArea text = makeText(
                    "The cave claimed another victim. You stepped on" +
                            "\na spike and your journey came to an abrupt end." +
                            "\nThe darkness is unforgiving, only the most careful" +
                            "\nexplorers make it through alive." +

                            "\n\nHint: Don't step on the spikes!"
            );
            text.setBounds(40, 120, 520, 180);
            panel.add(text);

            JButton death = makeButton("Try again", new Color(211, 94, 18));
            death.setBounds(130, 280, 220, 55);
            death.addActionListener(e -> {
                dispose();
                new GameScreen();
            });

            panel.add(death);

            JButton exit = makeButton("Exit", new Color(90, 20, 20));
            exit.setBounds(130, 340, 220, 40);
            exit.addActionListener(e -> System.exit(0));
            panel.add(exit);

            return panel;
        }
    }
