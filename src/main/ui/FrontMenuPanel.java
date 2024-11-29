package ui;

import javax.swing.*;

import model.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Front Menu Panel
public class FrontMenuPanel extends MenuPanel implements ActionListener {
    private GameApp gameApp;
    private JButton playButton;
    private JButton completedLevelsButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;

    // EFFECTS: constructs FrontMenuPanel
    FrontMenuPanel(GameApp gameApp) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: resizes panel and components to given width and height
    @Override
    public void resizePanel(int width, int height) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: resizes given button
    public void resizeButton(JButton button, int width, int height) {
        // stub
    }

    // EFFECTS: creates and returns a menu button
    public JButton createMenuButton(String name) {
        return null; // stub
    }

    // MODIFIES: this, gameApp
    // EFFECTS: handles buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        // stub
    }
}
