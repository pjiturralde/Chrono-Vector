package ui;

import javax.swing.*;

import model.EventLog;
import model.Player;
import model.Event;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Front Menu Panel
public class FrontMenuPanel extends MenuPanel implements ActionListener {
    private GameApp gameApp;
    private JButton playButton;
    private JButton completedLevelsButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton quitButton;

    // EFFECTS: constructs FrontMenuPanel
    FrontMenuPanel(GameApp gameApp) {
        this.gameApp = gameApp;

        playButton = createMenuButton("Play");
        completedLevelsButton = createMenuButton("Completed Levels");
        saveButton = createMenuButton("Save");
        loadButton = createMenuButton("Load");
        quitButton = createMenuButton("Quit");

        this.add(playButton);
        this.add(completedLevelsButton);
        this.add(saveButton);
        this.add(loadButton);
        this.add(quitButton);
    }

    // MODIFIES: this
    // EFFECTS: resizes panel and components to given width and height
    @Override
    public void resizePanel(int width, int height) {
        final float PANEL_TO_BUTTON_WIDTH_RATIO = 0.8f;
        final float PANEL_TO_BUTTON_HEIGHT_RATIO = 0.166f;

        super.resizePanel(width, height);

        int buttonWidth = Math.round(width * PANEL_TO_BUTTON_WIDTH_RATIO);
        int buttonHeight = Math.round(height * PANEL_TO_BUTTON_HEIGHT_RATIO);

        resizeButton(playButton, buttonWidth, buttonHeight);
        resizeButton(completedLevelsButton, buttonWidth, buttonHeight);
        resizeButton(saveButton, buttonWidth, buttonHeight);
        resizeButton(loadButton, buttonWidth, buttonHeight);
        resizeButton(quitButton, buttonWidth, buttonHeight);
    }

    // MODIFIES: this
    // EFFECTS: resizes given button
    public void resizeButton(JButton button, int width, int height) {
        button.setPreferredSize(new Dimension(width, height));
        Image buttonImage = new ImageIcon("src/resources/images/Menu Button.png").getImage().getScaledInstance(width,
                height, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(buttonImage));
    }

    // EFFECTS: creates and returns a menu button
    public JButton createMenuButton(String name) {
        final int BUTTON_WIDTH = 400;
        final int BUTTON_HEIGHT = 100;

        JButton button = new JButton(name);

        Image buttonImage = new ImageIcon("src/resources/images/Menu Button.png").getImage()
                .getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);

        ImageIcon buttonIcon = new ImageIcon(buttonImage);

        button.setIcon(buttonIcon);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setHorizontalTextPosition(JLabel.CENTER);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(230, 230, 230));
        button.setFont(new Font("DialogInput", Font.PLAIN, 18));
        button.addActionListener(this);

        return button;
    }

    // MODIFIES: this, gameApp
    // EFFECTS: diables and enables completedLevelButtons based on
    // gameApp.getPlayer().getCompletedLevelStats()
    public void refreshCompletedLevelButtons() {
        Player player = gameApp.getPlayer();
        CompletedLevelSelectMenuPanel completedLevelSelectMenuPanel = gameApp
                .getCompletedLevelSelectMenuPanel();

        for (int i = 0; i < player.getCompletedLevelStats().size(); i++) {
            JButton completedLevelButton = completedLevelSelectMenuPanel.getCompletedLevelButtons()[i];
            if (!completedLevelButton.isVisible()) {
                completedLevelButton.setVisible(true);
                completedLevelButton.setEnabled(true);
            }
        }

        for (int i = player.getCompletedLevelStats().size(); i < gameApp.getLevels().size(); i++) {
            JButton completedLevelButton = completedLevelSelectMenuPanel.getCompletedLevelButtons()[i];
            if (completedLevelButton.isVisible()) {
                completedLevelButton.setVisible(false);
                completedLevelButton.setEnabled(false);
            }
        }
    }

    // MODIFIES: this, gameApp
    // EFFECTS: handles buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            gameApp.setMenuCardLayout("Level Select");
            gameApp.enableBackButton();
            gameApp.setPreviousMenu("Main Menu");
        } else if (e.getSource() == completedLevelsButton) {
            gameApp.setMenuCardLayout("Completed Level Select");
            gameApp.enableBackButton();
            gameApp.setPreviousMenu("Main Menu");

            if (gameApp.getPlayer().getCompletedLevelStats().size() > 0) {
                refreshCompletedLevelButtons();
            }
        } else if (e.getSource() == saveButton) {
            gameApp.savePlayer();
        } else if (e.getSource() == loadButton) {
            gameApp.loadPlayer();
        } else if (e.getSource() == quitButton) {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString() + "\n\n");
            }
            System.exit(0);
        }
    }
}
