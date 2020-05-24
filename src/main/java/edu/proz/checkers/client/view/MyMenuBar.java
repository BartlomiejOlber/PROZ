package edu.proz.checkers.client.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MyMenuBar extends JMenuBar {

    private JMenu gameOptions;
    private JMenuItem exit;

    private JMenu help;
    private JMenuItem keyboardShortcuts;
    private JMenuItem aboutTheGame;

    public MyMenuBar() {
        gameOptions = new JMenu("Game options");
        this.add(gameOptions);
        exit = new JMenuItem("Exit");
        gameOptions.add(exit);

        help = new JMenu("Help");
        this.add(help);
        keyboardShortcuts = new JMenuItem("Keyboard shortcuts");
        aboutTheGame = new JMenuItem("About the game");
        help.add(keyboardShortcuts);
        help.addSeparator();
        help.add(aboutTheGame);
    }

    public void setActionListener(ActionListener object) {
        this.exit.addActionListener(object);
        this.keyboardShortcuts.addActionListener(object);
        this.aboutTheGame.addActionListener(object);
    }

    public void setKeyboardShortcuts() {
        this.exit.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
        this.keyboardShortcuts.setAccelerator(KeyStroke.getKeyStroke("ctrl K"));
        this.aboutTheGame.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
    }


    public JMenuItem getExit() {
        return this.exit;
    }

    public JMenuItem getKeyboardShortcuts() {
        return this.keyboardShortcuts;
    }
}

