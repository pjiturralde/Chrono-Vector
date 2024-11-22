package ui;

import javax.swing.*;

import model.Level;
import model.Player;
import model.Projectile;
import model.Wall;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameplayPanel extends JPanel implements ActionListener{
    private static final int SQUARE_WIDTH = 50;
    private static final int START_DRAW_POINT_X = 1920/3 + 100;
    private static final int START_DRAW_POINT_Y = 280;

    Timer timer;
    Level currentLevel;
    Player player;

    // EFFECTS: constructs GameplayPanel
    GameplayPanel(Player player) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets current level to given level
    public void setCurrentLevel(Level level) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this.player to given player
    public void setPlayer(Player player) {
        // stub
    }

    // EFFECTS: paints level and player onto screen
    public void paint(Graphics g) {
        // stub
    }

    // EFFECTS: repaints every frame
    @Override
    public void actionPerformed(ActionEvent e) {
        // stub
    }
}
