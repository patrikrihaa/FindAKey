package Screens;

import javax.swing.*;
import java.awt.*;

import static Screens.ScreenUtils.*;

/**
 * The main menu shown when the game first launches.
 * Displays the title, story text, controls, and two buttons: Start and Exit.
 */
public class StartScreen extends JFrame {

    /**
     * Builds and shows the start screen immediately on construction.
     */
    public StartScreen() {
        super("Locked");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

       JPanel gradient = makeGradient(new Color(15, 15, 28), new Color(28, 28, 58));
        gradient.add(buildContent());
        setContentPane(gradient);

        setVisible(true);
    }

    /**
     * Puts together the inner panel with title, description, controls info, and action buttons.
     * Uses a null layout so everything is positioned manually.
     *
     * @return the assembled content panel
     */
    private JPanel buildContent() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(460, 400));

        JLabel title = makeTitle("Locked", new Color(32, 147, 209));
        title.setBounds(0, 30, 460, 65);
        panel.add(title);

        JTextArea text = makeText(
                "Deep inside a dark cave a locked door stands between" +
                        "\nyou and freedom. Find the hidden key somewhere in" +
                        "\nthe darkness, make your way past the obstacles and" +
                        "\nspikes, and unlock the door to escape." +

                        "\n\nControls:" +
                        "\nA / D — move left / right" +
                        "\nSPACE — jump" +
                        "\nE — interact with door"
        );
        text.setBounds(40, 110, 520, 180);
        panel.add(text);

        JButton start = makeButton("Start Game", new Color(32, 147, 209));
        start.setBounds(130, 280, 220, 55);
        start.addActionListener(e -> {
            dispose();
            new GameScreen();
        });
        panel.add(start);

        JButton exit = makeButton("Exit", new Color(50, 55, 75));
        exit.setBounds(130, 340, 220, 40);
        exit.addActionListener(e -> System.exit(0));
        panel.add(exit);

        return panel;
    }
}