package ui;

import javax.swing.*;

import model.Vector2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// Main Screen Panel
public class MainScreenPanel extends JPanel implements ActionListener {
    private int screenWidth;
    private int screenHeight;
    private Vector2[] smallParticles = new Vector2[100];
    private Vector2[] mediumParticles = new Vector2[100];
    private Random ran;
    private Timer timer;

    // EFFECTS: constructs MainScreenPanel
    MainScreenPanel() {
        this.setLayout(new BorderLayout());

        screenWidth = 1920;
        screenHeight = 1080;

        ran = new Random();

        for (int i = 0; i < smallParticles.length; i++) {
            int randomY = ran.nextInt(screenHeight + 1);
            int randomX = ran.nextInt(screenWidth + 1);
            smallParticles[i] = new Vector2(randomX, randomY);
        }

        for (int i = 0; i < mediumParticles.length; i++) {
            int randomY = ran.nextInt(screenHeight + 1);
            int randomX = ran.nextInt(screenWidth + 1);
            mediumParticles[i] = new Vector2(randomX, randomY);
        }

        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: resizes panel to given width and height
    public void resizePanel(int width, int height) {
        for (Vector2 p : smallParticles) {
            double screenYRatio = (double) p.getY() / screenHeight;
            double screenXRatio = (double) p.getX() / screenWidth;

            int particleWidth = (int) Math.round(width * screenXRatio);
            int particleHeight = (int) Math.round(height * screenYRatio);

            p.setVector(particleWidth, particleHeight);
        }

        for (Vector2 p : mediumParticles) {
            double screenYRatio = (double) p.getY() / screenHeight;
            double screenXRatio = (double) p.getX() / screenWidth;

            int particleWidth = (int) Math.round(width * screenXRatio);
            int particleHeight = (int) Math.round(height * screenYRatio);

            p.setVector(particleWidth, particleHeight);
        }

        screenWidth = width;
        screenHeight = height;

        revalidate();
        repaint();
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
            if (p.getX() > screenWidth) {
                int randomY = ran.nextInt(screenHeight + 1);
                p.setVector(-2, randomY);
            } else {
                p.setVector(p.getX() + 3, p.getY());
            }
        }

        for (Vector2 p : mediumParticles) {
            if (p.getX() > screenWidth) {
                int randomY = ran.nextInt(screenHeight + 1);
                p.setVector(0, randomY);
            } else {
                p.setVector(p.getX() + 3, p.getY());
            }
        }
    }
}
