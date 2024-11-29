package ui;

import javax.swing.*;

import model.Level;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// In Game Menu Panel
public class InGameMenuPanel extends JPanel implements ActionListener {
    private GameApp gameApp;
    private JLabel inGameMenuLabel;
    private JPanel bottomPanel;
    private JButton retryButton;
    private JButton levelsButton;

    // MODIFIES: this
    // EFFECTS: constructs InGameMenuPanel
    InGameMenuPanel(GameApp gameApp) {
        this.gameApp = gameApp;
        this.setLayout(new BorderLayout());

        this.setPreferredSize(new Dimension(700, 600));

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(1000, 200));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));

        inGameMenuLabel = new JLabel();
        inGameMenuLabel.setHorizontalAlignment(JLabel.CENTER);

        retryButton = new JButton("Retry");
        levelsButton = new JButton("Levels");

        retryButton.setPreferredSize(new Dimension(200, 100));
        levelsButton.setPreferredSize(new Dimension(200, 100));

        retryButton.addActionListener(this);
        levelsButton.addActionListener(this);

        bottomPanel.add(retryButton);
        bottomPanel.add(levelsButton);

        this.add(inGameMenuLabel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: sets text of inGameMenuLabel to given text
    public void setText(String text) {
        inGameMenuLabel.setText(text);
    }

    // MODIFIES: this
    // EFFECTS: resizes panel to given width and height
    public void resizePanel(int width, int height) {
        final float PANEL_TO_BOTTOM_PANEL_WIDTH_RATIO = 1.428f;
        final float PANEL_TO_BOTTOM_PANEL_HEIGHT_RATIO = 0.33f;

        final float PANEL_TO_BUTTON_WIDTH_RATIO = 0.285f;
        final float PANEL_TO_BUTTON_HEIGHT_RATIO = 0.142f;

        final float SCREEN_TO_START_POINT_X_RATIO = 0.5f;
        final float SCREEN_TO_START_POINT_Y_RATIO = 0.2f;

        int startPointX = Math.round(gameApp.getWidth() * SCREEN_TO_START_POINT_X_RATIO);
        int startPointY = Math.round(gameApp.getHeight() * SCREEN_TO_START_POINT_Y_RATIO);

        this.setBounds(startPointX - Math.round(height * 1.166f) / 2, startPointY, Math.round(height * 1.166f), height);

        int bottomPanelHeight = Math.round(height * PANEL_TO_BOTTOM_PANEL_HEIGHT_RATIO);
        int bottomPanelWidth = Math.round(width * PANEL_TO_BOTTOM_PANEL_WIDTH_RATIO);

        bottomPanel.setPreferredSize(new Dimension(bottomPanelWidth, bottomPanelHeight));

        int buttonWidth = Math.round(width * PANEL_TO_BUTTON_WIDTH_RATIO);
        int buttonHeight = Math.round(height * PANEL_TO_BUTTON_HEIGHT_RATIO);

        retryButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        levelsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
    }

    // MODIFIES: this, gameApp
    // EFFECTS: handles buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == retryButton) {
            Level currentLevel = gameApp.getCurrentLevel();
            currentLevel.reset();
            gameApp.getPlayer().setPosition(currentLevel.getStartPosition());
            this.setVisible(false);
            this.setEnabled(false);
            gameApp.setInGame(true);
            gameApp.requestFocus();
        } else if (e.getSource() == levelsButton) {
            this.setVisible(false);
            this.setEnabled(false);
            gameApp.toMainMenu();
        }
    }
}
