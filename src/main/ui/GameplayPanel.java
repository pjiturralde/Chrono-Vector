package ui;

import javax.swing.*;

import model.Projectile;
import model.Wall;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Gameplay panel
public class GameplayPanel extends JPanel implements ActionListener {
    private int squareWidth = 50;
    private int startPointX = 1920 / 3;
    private int startPointY = 280;

    private GameApp gameApp;
    private Timer timer;

    // EFFECTS: constructs GameplayPanel
    GameplayPanel(GameApp gameApp) {
        this.gameApp = gameApp;
        this.setBackground(new Color(0, 0, 44));
        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: resizes panel and components to given width and height
    public void resizePanel(int width, int height) {
        final float SCREEN_TO_SQUARE_WIDTH_RATIO = 0.05f;
        final float SCREEN_TO_START_DRAW_POINT_X_RATIO = 0.5f;
        final float SCREEN_TO_START_DRAW_POINT_Y_RATIO = 0.2f;

        squareWidth = Math.round(height * SCREEN_TO_SQUARE_WIDTH_RATIO);
        startPointX = Math.round(width * SCREEN_TO_START_DRAW_POINT_X_RATIO
                - (gameApp.getCurrentLevel().getSize().getX() + 1) * squareWidth / 2);
        startPointY = Math.round(height * SCREEN_TO_START_DRAW_POINT_Y_RATIO);

        this.setBounds(0, 0, width, height);
        revalidate();
        repaint();
    }

    // EFFECTS: paints level and player onto screen
    public void paint(Graphics g) {
        if (gameApp.getCurrentLevel() != null) {
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;

            paintWalls(g2D);
            paintProjectiles(g2D);
            paintGoal(g2D);
            paintPlayer(g2D);
        }
    }

    // EFFECTS: paints player onto this panel
    public void paintPlayer(Graphics2D g2D) {
        g2D.setPaint(Color.WHITE);
        g2D.fillRect(startPointX + gameApp.getPlayer().getPosition().getX() * squareWidth + 10,
                startPointY + gameApp.getPlayer().getPosition().getY() * squareWidth + 10, squareWidth - 20,
                squareWidth - 20);
    }

    // EFFECTS: paints goal onto this panel
    public void paintGoal(Graphics2D g2D) {
        g2D.setPaint(new Color(0, 204, 68));
        g2D.fillRect(startPointX + gameApp.getCurrentLevel().getGoalPosition().getX() * squareWidth,
                startPointY + gameApp.getCurrentLevel().getGoalPosition().getY() * squareWidth, squareWidth,
                squareWidth);
    }

    // EFFECTS: paints projectiles onto this panel
    public void paintProjectiles(Graphics2D g2D) {
        g2D.setPaint(Color.ORANGE);
        for (Projectile p : gameApp.getCurrentLevel().getProjectiles()) {
            g2D.fillRect(startPointX + p.getPosition().getX() * squareWidth + 10,
                    startPointY + p.getPosition().getY() * squareWidth + 10, squareWidth - 20,
                    squareWidth - 20);
        }
    }

    // EFFECTS: paints walls onto this panel
    public void paintWalls(Graphics2D g2D) {
        g2D.setPaint(Color.WHITE);
        for (Wall w : gameApp.getCurrentLevel().getWalls()) {
            g2D.fillRect(startPointX + w.getStartPoint().getX() * squareWidth,
                    startPointY + w.getStartPoint().getY() * squareWidth,
                    (w.getEndPoint().getX() - w.getStartPoint().getX()) * squareWidth + squareWidth,
                    (w.getEndPoint().getY() - w.getStartPoint().getY()) * squareWidth + squareWidth);
        }
    }

    // EFFECTS: repaints every frame
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
