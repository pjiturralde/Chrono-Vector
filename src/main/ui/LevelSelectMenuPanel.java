package ui;

import javax.swing.*;

import model.Level;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Level Select Menu Panel
public class LevelSelectMenuPanel extends MenuPanel implements ActionListener {
    private GameApp gameApp;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;
    private JButton[] levelButtons;
    private LevelDisplayPanel[] displayPanels;
    private JLayeredPane[] levelLayeredPanes;

    // EFFECTS: constructs LevelSelectMenuPanel
    LevelSelectMenuPanel(GameApp gameApp) {
        this.gameApp = gameApp;

        levelButtons = new JButton[gameApp.getLevels().size()];
        displayPanels = new LevelDisplayPanel[gameApp.getLevels().size()];
        levelLayeredPanes = new JLayeredPane[gameApp.getLevels().size()];

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        scrollPanel = new JPanel();
        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 40));
        scrollPanel.setOpaque(false);

        for (Level level : gameApp.getLevels()) {
            JLayeredPane levelLayeredPane = new JLayeredPane();
            levelLayeredPane.setPreferredSize(new Dimension(380, 500));
            levelLayeredPanes[level.getLevelIndex()] = levelLayeredPane;

            LevelDisplayPanel displayPanel = new LevelDisplayPanel(level.getName(), 380, 500);
            displayPanel.setBounds(0, 0, 380, 500);
            displayPanels[level.getLevelIndex()] = displayPanel;

            JButton button = createLevelButton(level);

            levelLayeredPane.add(displayPanel);
            levelLayeredPane.add(button);

            scrollPanel.add(levelLayeredPane);
        }

        scrollPane = createScrollPane(scrollPanel);

        this.add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: resizes panel and components to given width and height
    @Override
    public void resizePanel(int width, int height) {
        final float PANEL_TO_BUTTON_WIDTH_RATIO = 0.76f;
        final float PANEL_TO_BUTTON_HEIGHT_RATIO = 0.8333f;

        final float PANEL_TO_PANE_WIDTH_RATIO = 0.98f;
        final float PANEL_TO_PANE_HEIGHT_RATIO = 0.97f;

        super.resizePanel(width, height);

        int buttonWidth = Math.round(width * PANEL_TO_BUTTON_WIDTH_RATIO);
        int buttonHeight = Math.round(height * PANEL_TO_BUTTON_HEIGHT_RATIO);

        for (JLayeredPane levelLayeredPane : levelLayeredPanes) {
            levelLayeredPane.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        }

        for (JButton levelButton : levelButtons) {
            levelButton.setBounds(0, 0, buttonWidth, buttonHeight);
        }

        for (LevelDisplayPanel displayPanel : displayPanels) {
            displayPanel.setBounds(0, 0, buttonWidth, buttonHeight);
            displayPanel.setDimension(buttonWidth, buttonHeight);
        }

        int paneWidth = Math.round(width * PANEL_TO_PANE_WIDTH_RATIO);
        int paneHeight = Math.round(height * PANEL_TO_PANE_HEIGHT_RATIO);

        scrollPane.setPreferredSize(new Dimension(paneWidth, paneHeight));

        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, Math.round(height * 0.06f)));
    }

    // EFFECTS: creates a button to select a Level and returns it
    public JButton createLevelButton(Level level) {
        JButton levelButton = new JButton("Level " + (level.getLevelIndex() + 1));
        levelButton.addActionListener(this);
        levelButton.setOpaque(false);
        levelButton.setContentAreaFilled(false);
        levelButton.setBorderPainted(false);
        levelButton.setBounds(0, 0, 380, 500);
        levelButtons[level.getLevelIndex()] = levelButton;

        return levelButton;
    }

    // EFFECTS: sets up scrollPane and returns it
    public JScrollPane createScrollPane(JPanel scrollPanel) {
        JScrollPane scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
        horizontalBar.setUI(new CustomScrollBar("src/resources/images/Horizontal Scroll Bar.png"));
        horizontalBar.setPreferredSize(new Dimension(10, 26));
        horizontalBar.setUnitIncrement(50);
        scrollPane.setPreferredSize(new Dimension(490, 590));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        return scrollPane;
    }

    // MODIFIES: this, gameApp
    // EFFECTS: handles buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        int levelIndex = 0;

        for (JButton button : levelButtons) {
            if (e.getSource() == button) {
                Level level = gameApp.getLevels().get(levelIndex);
                gameApp.setCurrentLevel(level);
                gameApp.getPlayer().setPosition(level.getStartPosition());
                gameApp.getGameplayPanel().resizePanel(gameApp.getWidth(), gameApp.getHeight());
                gameApp.toGameplay();

                break;
            }
            levelIndex++;
        }
    }
}
