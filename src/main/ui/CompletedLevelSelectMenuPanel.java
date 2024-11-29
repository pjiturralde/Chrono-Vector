package ui;

import javax.swing.*;

import model.Level;
import model.LevelStats;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Completed Level Select Menu Panel
public class CompletedLevelSelectMenuPanel extends MenuPanel implements ActionListener {
    private GameApp gameApp;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;
    private JButton[] completedLevelButtons;

    // EFFECTS: constructs CompletedLevelSelectMenuPanel
    CompletedLevelSelectMenuPanel(GameApp gameApp) {
        this.gameApp = gameApp;

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        completedLevelButtons = new JButton[gameApp.getLevels().size()];

        scrollPanel = new JPanel();
        scrollPanel.setPreferredSize(new Dimension(490, 1000));
        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        scrollPanel.setOpaque(false);

        for (Level level : gameApp.getLevels()) {
            JButton levelButton = createLevelButton(level);
            scrollPanel.add(levelButton);
        }

        scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new CustomScrollBar("src/resources/images/Vertical Scroll Bar.PNG"));
        verticalBar.setPreferredSize(new Dimension(26, 10));
        verticalBar.setUnitIncrement(50);
        scrollPane.setPreferredSize(new Dimension(490, 580));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        this.add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: resizes panel and components to given width and height
    @Override
    public void resizePanel(int width, int height) {
        final float PANEL_TO_BUTTON_WIDTH_RATIO = 0.2f;

        final float PANEL_TO_PANE_WIDTH_RATIO = 0.98f;
        final float PANEL_TO_PANE_HEIGHT_RATIO = 0.96f;

        super.resizePanel(width, height);

        int buttonWidth = Math.round(width * PANEL_TO_BUTTON_WIDTH_RATIO);
        int buttonHeight = buttonWidth;

        for (JButton completedLevelButton : completedLevelButtons) {
            completedLevelButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            Image buttonImage = new ImageIcon("src/resources/images/Level Button.png").getImage()
                    .getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
            completedLevelButton.setIcon(new ImageIcon(buttonImage));
        }

        int paneWidth = Math.round(width * PANEL_TO_PANE_WIDTH_RATIO);
        int paneHeight = Math.round(height * PANEL_TO_PANE_HEIGHT_RATIO);

        scrollPane.setPreferredSize(new Dimension(paneWidth, paneHeight));

        scrollPanel.setPreferredSize(new Dimension(paneWidth, Math.round(1000 * 1.6f)));
        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, Math.round(width * 0.02f), Math.round(width * 0.02f)));
    }

    // EFFECTS: creates a button to select a Level and returns it
    public JButton createLevelButton(Level level) {
        JButton levelButton = new JButton(Integer.toString(level.getLevelIndex() + 1));
        ImageIcon originalIcon = new ImageIcon("src/resources/images/Level Button.png");

        Image resizedImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        ImageIcon levelIcon = new ImageIcon(resizedImage);
        levelButton.addActionListener(this);
        levelButton.setPreferredSize(new Dimension(100, 100));
        levelButton.setIcon(levelIcon);
        levelButton.setHorizontalTextPosition(JLabel.CENTER);
        levelButton.setVerticalTextPosition(JLabel.CENTER);
        levelButton.setBackground(new Color(0, 0, 44));
        levelButton.setContentAreaFilled(false);
        levelButton.setBorderPainted(false);
        levelButton.setForeground(new Color(230, 230, 230));
        levelButton.setFont(new Font("DialogInput", Font.PLAIN, 15));

        completedLevelButtons[level.getLevelIndex()] = levelButton;
        levelButton.setVisible(false);
        levelButton.setEnabled(false);

        return levelButton;
    }

    // EFFECTS: returns completedLevelButtons
    public JButton[] getCompletedLevelButtons() {
        return completedLevelButtons;
    }

    // MODIFIES: this, gameApp
    // EFFECTS: handles buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        int levelIndex = 0;

        for (JButton button : completedLevelButtons) {
            if (e.getSource() == button) {
                Level level = gameApp.getLevels().get(levelIndex);
                LevelHistoryViewPanel levelHistoryViewPanel = gameApp.getLevelHistoryViewPanel();

                gameApp.setMenuCardLayout("Level History");
                gameApp.setPreviousMenu("Completed Level Select");
                String levelHistory = "<html>";

                for (LevelStats stats : gameApp.getPlayer().getCompletedLevelStats().get(level.getLevelIndex())) {
                    levelHistory += "<br>Attempt #" + stats.getAttemptNum() + "<br>Moves taken: "
                            + stats.getLeastMovesTaken()
                            + " moves" + "<br>Time taken: " + stats.getLeastTimeTaken() + " seconds<br>";
                }
                levelHistory += "<html>";

                levelHistoryViewPanel.setSelectedLevel(level);
                levelHistoryViewPanel.setText(levelHistory, level.getName());
                levelHistoryViewPanel.resetSortByBox();

                break;
            }
            levelIndex++;
        }
    }
}
