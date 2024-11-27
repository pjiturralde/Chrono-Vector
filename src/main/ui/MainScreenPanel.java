package ui;

import javax.swing.*;

import model.Vector2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// Gameplay panel
public class MainScreenPanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private Vector2[] smallParticles = new Vector2[100];
    private Vector2[] mediumParticles = new Vector2[100];
    Random ran;
    Timer timer;

    // EFFECTS: constructs MenuPanel
    MainScreenPanel() {
        // stub
    }

    // EFFECTS: paints level and player onto screen
    public void paint(Graphics g) {
        // stub
    }

    // EFFECTS: repaints every frame and handles particle position
    @Override
    public void actionPerformed(ActionEvent e) {
        // stub
    }
}
