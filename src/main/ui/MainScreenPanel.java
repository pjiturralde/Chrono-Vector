package ui;

import javax.swing.*;

import model.Vector2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// Main Screen Panel
public class MainScreenPanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private Vector2[] smallParticles = new Vector2[100];
    private Vector2[] mediumParticles = new Vector2[100];
    Random ran;
    Timer timer;

    // MODIFIES: this
    // EFFECTS: constructs Main Screen Panel
    MainScreenPanel() {
        this.setLayout(new BorderLayout());
        ran = new Random();

        for (int i = 0; i < smallParticles.length; i++) {
            int randomY = ran.nextInt(SCREEN_HEIGHT + 1);
            int randomX = ran.nextInt(SCREEN_WIDTH + 1);
            smallParticles[i] = new Vector2(randomX, randomY);
        }

        for (int i = 0; i < mediumParticles.length; i++) {
            int randomY = ran.nextInt(SCREEN_HEIGHT + 1);
            int randomX = ran.nextInt(SCREEN_WIDTH + 1);
            mediumParticles[i] = new Vector2(randomX, randomY);
        }

        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    // EFFECTS: paints level and player onto screen
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setPaint(Color.GREEN);
        for (Vector2 p : smallParticles) {
            g2D.fillRect(p.getX(), p.getY(), 1, 1);
        }

        g2D.setPaint(new Color(80, 200, 120));
        for (Vector2 p : mediumParticles) {
            g2D.fillRect(p.getX(), p.getY(), 3, 3);
        }
    }

    // MODIFIES: this
    // EFFECTS: repaints every frame and handles particle position
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();

        for (Vector2 p : smallParticles) {
            if (p.getX() > SCREEN_WIDTH) {
                int randomY = ran.nextInt(SCREEN_HEIGHT + 1);
                p.setVector(-2, randomY);
            } else {
                p.setVector(p.getX() + 3, p.getY());
            }
        }

        for (Vector2 p : mediumParticles) {
            if (p.getX() > SCREEN_WIDTH) {
                int randomY = ran.nextInt(SCREEN_HEIGHT + 1);
                p.setVector(0, randomY);
            } else {
                p.setVector(p.getX() + 3, p.getY());
            }
        }
    }
}
