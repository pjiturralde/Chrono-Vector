package ui;

import javax.swing.*;

import java.awt.*;

// Menu Panel
public class MenuPanel extends JPanel{
    private static final int WIDTH = 500;
    private static final int HEIGHT = 600;
    private Image image;

    // EFFECTS: constructs MenuPanel
    MenuPanel() {
        image = new ImageIcon("src\\resources\\images\\Menu Frame.png").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 16));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(0, 0, 44));
    }

    // EFFECTS: paints level and player onto screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this);
    }
}
