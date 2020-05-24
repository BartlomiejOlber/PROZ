package edu.proz.checkers.client.view;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Class that makes it possible to add menu bar in our gui frame
 */
public class MyMenuBar extends JMenuBar {

    private JMenu gameOptions;
    private JMenuItem exit;

    private JMenu help;
    private JMenuItem keyboardShortcuts;
    private JMenuItem aboutTheGame;

    private final String gameOptionsText = "Game options";
    private final String exitText = "Exit";
    private final String helpText = "Help";
    private final String keyboardShortcutsText = "Keyboard shortcuts";
    private final String aboutTheGameText = "About the game";

    private final String exitKeyStroke = "alt F4";
    private final String keyboardShortcutsKeyStroke = "ctrl K";
    private final String aboutTheGameKeyStroke = "ctrl A";

    /**
     * Constructor that creates menu items and adds them to the bar.
     */
    public MyMenuBar() {
        gameOptions = new JMenu(gameOptionsText);
        this.add(gameOptions);
        exit = new JMenuItem(exitText);
        gameOptions.add(exit);

        help = new JMenu(helpText);
        this.add(help);
        keyboardShortcuts = new JMenuItem(keyboardShortcutsText);
        aboutTheGame = new JMenuItem(aboutTheGameText);
        help.add(keyboardShortcuts);
        help.addSeparator();
        help.add(aboutTheGame);
    }

    /**
     * Method that assigns the action listener to all the options in menu bar.
     * @param object Object that will be added as action listener.
     */
    public void setActionListener(ActionListener object) {
        this.exit.addActionListener(object);
        this.keyboardShortcuts.addActionListener(object);
        this.aboutTheGame.addActionListener(object);
    }

    /**
     * Method that assigns keyboard shortcuts to all the options in menu bar.
     */
    public void setKeyboardShortcuts() {
        this.exit.setAccelerator(KeyStroke.getKeyStroke(exitKeyStroke));
        this.keyboardShortcuts.setAccelerator(KeyStroke.getKeyStroke(keyboardShortcutsKeyStroke));
        this.aboutTheGame.setAccelerator(KeyStroke.getKeyStroke(aboutTheGameKeyStroke));
    }

    /**
     * Method that returns menu item that represents exit option.
     * @return Menu item that represents exit option.
     */
    public JMenuItem getExit() {
        return this.exit;
    }

    /**
     * Method that returns menu item that represents get keyboard shortcuts option.
     * @return Menu item that represents get keyboard shortcuts option.
     */
    public JMenuItem getKeyboardShortcuts() {
        return this.keyboardShortcuts;
    }
}

