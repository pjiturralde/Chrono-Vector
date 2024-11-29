package ui;

import javax.swing.*;

import model.Level;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// In Game Menu Panel
public class InGameMenuPanel extends JPanel implements ActionListener {
    private GameApp gameApp;
    private JLabel inGameMenuLabel;
    private JPanel bottomPanel;
    private JButton retryButton;
    private JButton levelsButton;

    // MODIFIES: this
    // EFFECTS: constructs InGameMenuPanel
    InGameMenuPanel(GameApp gameApp) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets text of inGameMenuLabel to given text
    public void setText(String text) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: resizes panel to given width and height
    public void resizePanel(int width, int height) {
        // stub
    }

    // MODIFIES: this, gameApp
    // EFFECTS: handles buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        // stub
    }
}
