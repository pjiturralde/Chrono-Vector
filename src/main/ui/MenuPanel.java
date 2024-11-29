package ui;

import javax.swing.*;

import java.awt.*;

// Menu Panel
public class MenuPanel extends JPanel {
    private int width = 500;
    private int height = 600;
    private Image image;

    // EFFECTS: constructs MenuPanel
    MenuPanel() {
        image = new ImageIcon("src/resources/images/Menu Frame.png").getImage().getScaledInstance(width, height,
                Image.SCALE_SMOOTH);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 16));
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(new Color(0, 0, 44));
    }

    // MODIFIES: this
    // EFFECTS: resizes panel to given width and height
    public void resizePanel(int width, int height) {
        final double FRAME_TO_VGAP_RATIO = 0.025;

        this.width = width;
        this.height = height;

        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, (int) Math.round(height * FRAME_TO_VGAP_RATIO)));
        revalidate();
        repaint();
    }

    // EFFECTS: paints level and player onto screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, width, height, this);
    }
}
