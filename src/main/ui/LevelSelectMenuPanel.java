package ui;

import javax.swing.*;

import model.Level;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Level Select Menu Panel
public class LevelSelectMenuPanel extends MenuPanel implements ActionListener {
    private GameApp gameApp;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;
    private JButton[] levelButtons;
    private LevelDisplayPanel[] displayPanels;
    private JLayeredPane[] levelLayeredPanes;

    // EFFECTS: constructs LevelSelectMenuPanel
    LevelSelectMenuPanel(GameApp gameApp) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: resizes panel and components to given width and height
    @Override
    public void resizePanel(int width, int height) {
        // stub
    }

    // EFFECTS: creates a button to select a Level and returns it
    public JButton createLevelButton(Level level) {
        return null; // stub
    }

    // EFFECTS: sets up scrollPane and returns it
    public JScrollPane createScrollPane(JPanel scrollPanel) {
        return null; // stub
    }

    // MODIFIES: this, gameApp
    // EFFECTS: handles buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        // stub
    }
}
