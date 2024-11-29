package ui;

import javax.swing.*;

import java.awt.*;

// Level Display Panel
public class LevelDisplayPanel extends JPanel {
    private JLabel label;
    private JPanel levelDisplayPanel;
    private int width;
    private int height;
    private Image image;

    // EFFECTS: constructs LevelDisplayPanel
    LevelDisplayPanel(String levelName, int width, int height) {
        image = new ImageIcon("src/resources/images/Level Frame.png").getImage().getScaledInstance(width, height,
                Image.SCALE_SMOOTH);

        this.width = width;
        this.height = height;

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(0, 0, 44));

        label = new JLabel();
        label.setPreferredSize(new Dimension(WIDTH, HEIGHT / 3));
        label.setForeground(new Color(230, 230, 230));
        label.setFont(new Font("DialogInput", Font.PLAIN, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setText(levelName);

        levelDisplayPanel = new JPanel();
        levelDisplayPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT * (2 / 3)));
        levelDisplayPanel.setOpaque(false);

        this.add(levelDisplayPanel, BorderLayout.CENTER);
        this.add(label, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: changes the dimensions of this
    public void setDimension(int width, int height) {
        this.width = width;
        this.height = height;

        levelDisplayPanel.setPreferredSize(new Dimension(width, height * (2 / 3)));
        label.setPreferredSize(new Dimension(width, height / 3));
    }

    // EFFECTS: paints level and player onto screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, width, height, this);
    }
}
