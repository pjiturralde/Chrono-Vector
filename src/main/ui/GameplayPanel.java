package ui;

import javax.swing.*;

import model.Level;
import model.Player;
import model.Projectile;
import model.Wall;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Gameplay panel
public class GameplayPanel extends JPanel implements ActionListener {
    private static final int SQUARE_WIDTH = 50;
    private static final int START_DRAW_POINT_X = 1920 / 3 + 100;
    private static final int START_DRAW_POINT_Y = 280;

    Timer timer;
    Level currentLevel;
    Player player;

    // EFFECTS: constructs GameplayPanel
    GameplayPanel(Player player) {
        this.currentLevel = null;
        this.player = player;
        this.setBackground(new Color(0, 0, 44));
        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: sets current level to given level
    public void setCurrentLevel(Level level) {
        this.currentLevel = level;
    }

    // MODIFIES: this
    // EFFECTS: sets this.player to given player
    public void setPlayer(Player player) {
        this.player = player;
    }

    // EFFECTS: paints level and player onto screen
    public void paint(Graphics g) {
        if (currentLevel != null) {
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;

            g2D.setPaint(Color.WHITE);
            for (Wall w : currentLevel.getWalls()) {
                g2D.fillRect(START_DRAW_POINT_X + w.getStartPoint().getX() * SQUARE_WIDTH,
                        START_DRAW_POINT_Y + w.getStartPoint().getY() * SQUARE_WIDTH,
                        (w.getEndPoint().getX() - w.getStartPoint().getX()) * SQUARE_WIDTH + SQUARE_WIDTH,
                        (w.getEndPoint().getY() - w.getStartPoint().getY()) * SQUARE_WIDTH + SQUARE_WIDTH);
            }

            g2D.setPaint(Color.ORANGE);
            for (Projectile p : currentLevel.getProjectiles()) {
                g2D.fillRect(START_DRAW_POINT_X + p.getPosition().getX() * SQUARE_WIDTH + 10,
                        START_DRAW_POINT_Y + p.getPosition().getY() * SQUARE_WIDTH + 10, SQUARE_WIDTH - 20,
                        SQUARE_WIDTH - 20);
            }

            g2D.setPaint(new Color(0, 204, 68));
            g2D.fillRect(START_DRAW_POINT_X + currentLevel.getGoalPosition().getX() * SQUARE_WIDTH,
                    START_DRAW_POINT_Y + currentLevel.getGoalPosition().getY() * SQUARE_WIDTH, SQUARE_WIDTH,
                    SQUARE_WIDTH);

            g2D.setPaint(Color.WHITE);
            g2D.fillRect(START_DRAW_POINT_X + player.getPosition().getX() * SQUARE_WIDTH + 10,
                    START_DRAW_POINT_Y + player.getPosition().getY() * SQUARE_WIDTH + 10, SQUARE_WIDTH - 20,
                    SQUARE_WIDTH - 20);
        }
    }

    // EFFECTS: repaints every frame
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
