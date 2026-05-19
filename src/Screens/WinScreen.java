package Screens;

import javax.swing.*;

import java.awt.*;

import static Screens.ScreenUtils.*;

/**
 * Shown when the player collects the key, opens the door, and walks through it.
 * Offers "Play again" for another run or Exit to quit.
 */
public class WinScreen extends JFrame {

    /**
     * Builds and shows the win screen immediately on construction.
     */
    public WinScreen() {
        super("Locked");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel gradient = makeGradient(new Color( 10, 40, 15), new Color(5, 25, 10));
        gradient.add(buildContent());
        setContentPane(gradient);

        setVisible(true);
    }

    /**
     * Assembles the inner panel with title, win message, and play-again/exit buttons.
     *
     * @return the assembled content panel
     */
    private JPanel buildContent(){
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension( 460, 400));

        JLabel title = makeTitle("You escaped", new Color( 50, 200, 80));
        title.setBounds(0, 30, 460, 65);
        panel.add(title);

        JTextArea text = makeText(
                "Against all odds, you navigated the treacherous cave," +
                        "\nfound the hidden key and unlocked the door to" +
                        "\nfreedom. The darkness has been conquered." +

                        "\n\nWell done."
        );
        text.setBounds(40, 110, 520, 180);
        panel.add(text);

        JButton again = makeButton("Play again", new Color(40, 150, 60));
        again.setBounds(130,  280, 220, 55);
        again.addActionListener(e -> {
            dispose();
            new GameScreen();
        });
        panel.add(again);

        JButton exit = makeButton("Exit", new Color(30, 50, 35));
        exit.setBounds(130, 340, 220, 40);
        exit.addActionListener(e -> System.exit(0));
        panel.add(exit);

        return panel;
    }
}
