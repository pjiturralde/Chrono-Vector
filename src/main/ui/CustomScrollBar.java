package ui;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

// Custom Scroll Bar
public class CustomScrollBar extends BasicScrollBarUI {
    private Image thumbImage;

    // EFFECTS: constructs custom scroll bar with given image path
    public CustomScrollBar(String imagePath) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: paints scroll bar track
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: paints scroll bar thumb
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: creates scroll bar decrease button
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: creates scroll bar increase button
    @Override    
    protected JButton createIncreaseButton(int orientation) {
        return null; // stub
    }

    // EFFECTS: returns button with zero width and zero height
    private JButton hiddenButton() {
        return null; // stub
    }
}
