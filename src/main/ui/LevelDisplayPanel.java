package ui;

import javax.swing.*;

import java.awt.*;

// Level Display Panel
public class LevelDisplayPanel extends JPanel{
    private static final int WIDTH = 380;
    private static final int HEIGHT = 500;
    private Image image;

    // EFFECTS: constructs MenuPanel
    LevelDisplayPanel(String levelName) {
        image = new ImageIcon("src\\resources\\images\\Level Frame.png").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(0, 0, 44));

        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(WIDTH, HEIGHT / 3));
        label.setForeground(new Color(230, 230, 230));
        label.setFont(new Font("DialogInput", Font.PLAIN, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setText(levelName);

        JPanel levelDisplayPanel = new JPanel();
        levelDisplayPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT * (2 / 3)));
        levelDisplayPanel.setOpaque(false);

        this.add(levelDisplayPanel, BorderLayout.CENTER);
        this.add(label, BorderLayout.SOUTH);
    }

    // EFFECTS: paints level and player onto screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this);
    }
}
