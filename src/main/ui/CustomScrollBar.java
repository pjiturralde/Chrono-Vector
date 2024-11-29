package ui;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

// Custom Scroll Bar
public class CustomScrollBar extends BasicScrollBarUI {
    private Image thumbImage;

    // EFFECTS: constructs custom scroll bar with given image path
    public CustomScrollBar(String imagePath) {
        thumbImage = new ImageIcon(imagePath).getImage();
    }

    // MODIFIES: this
    // EFFECTS: paints scroll bar track
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(new Color(0, 0, 44));
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    // MODIFIES: this
    // EFFECTS: paints scroll bar thumb
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbImage != null) {
            g.drawImage(thumbImage, thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, null);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates scroll bar decrease button
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return hiddenButton();
    }

    // MODIFIES: this
    // EFFECTS: creates scroll bar increase button
    @Override
    protected JButton createIncreaseButton(int orientation) {
        return hiddenButton();
    }

    // EFFECTS: returns button with zero width and zero height
    private JButton hiddenButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}