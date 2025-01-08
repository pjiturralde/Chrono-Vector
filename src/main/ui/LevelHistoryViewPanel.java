package ui;

import javax.swing.*;

import model.Level;
import model.LevelStats;
import model.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Level History View Panel
public class LevelHistoryViewPanel extends MenuPanel implements ActionListener {
    private static final int MOVES_THEN_TIME_SORT = 0;
    private static final int TIME_THEN_MOVES_SORT = 1;
    private static final int ATTEMPT_SORT = 2;

    private JPanel scrollPanel;
    private JScrollPane scrollPane;
    private JButton clearButton;
    private JLabel levelHistoryLabel;
    private JLabel levelNameLabel;
    private JComboBox<String> sortByBox;
    private JCheckBox topAndBottomCheckBox;
    private GameApp gameApp;
    private Level selectedLevel;

    // EFFECTS: constructs LevelHistoryViewPanel
    LevelHistoryViewPanel(GameApp gameApp) {
        this.gameApp = gameApp;

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.setPreferredSize(new Dimension(500, 600));

        scrollPanel = createScrollPanel();

        levelHistoryLabel = createLevelHistoryLabel();

        scrollPanel.add(levelHistoryLabel);

        scrollPane = createScrollPane(scrollPanel);

        topAndBottomCheckBox = createTopAndBottomCheckBox();

        JLabel topAndBottomLabel = createLabel("Top and bottom results only:");

        String[] sortOptions = { "Moves then Time", "Time then Moves", "Attempt" };
        sortByBox = new JComboBox<String>(sortOptions);
        sortByBox.addActionListener(this);

        JLabel sortLabel = createLabel("Sort by:");

        levelNameLabel = createLevelNameLabel();

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);

        this.add(clearButton);
        this.add(levelNameLabel);
        this.add(topAndBottomLabel);
        this.add(topAndBottomCheckBox);
        this.add(sortLabel);
        this.add(sortByBox);
        this.add(scrollPane);
    }

    // EFFECTS: creates top and bottom checkbox then returns it
    public JCheckBox createTopAndBottomCheckBox() {
        JCheckBox checkbox = new JCheckBox();
        checkbox.setOpaque(false);
        checkbox.addActionListener(this);

        return checkbox;
    }

    // MODIFIES: this
    // EFFECTS: resizes panel and components to given width and height
    @Override
    public void resizePanel(int width, int height) {
        final float PANEL_TO_PANE_WIDTH_RATIO = 0.98f;
        final float PANEL_TO_PANE_HEIGHT_RATIO = 0.7f;

        super.resizePanel(width, height);

        int paneWidth = Math.round(width * PANEL_TO_PANE_WIDTH_RATIO);
        int paneHeight = Math.round(height * PANEL_TO_PANE_HEIGHT_RATIO);

        scrollPane.setPreferredSize(new Dimension(paneWidth, paneHeight));
    }

    // EFFECTS: creates level name label and returns it
    public JLabel createLevelNameLabel() {
        JLabel label = new JLabel();
        label.setForeground(new Color(230, 230, 230));
        label.setFont(new Font("DialogInput", Font.PLAIN, 16));
        label.setPreferredSize(new Dimension(490, 30));
        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
    }

    // EFFECTS: creates customized label and returns it
    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(230, 230, 230));
        label.setFont(new Font("DialogInput", Font.PLAIN, 14));

        return label;
    }

    // MODIFIES: this
    // EFFECTS: resets sortByBox
    public void resetSortByBox() {
        sortByBox.setSelectedIndex(0);
    }

    // EFFECTS: sets up levelHistory and returns it
    public JLabel createLevelHistoryLabel() {
        levelHistoryLabel = new JLabel();
        levelHistoryLabel.setForeground(new Color(230, 230, 230));
        levelHistoryLabel.setFont(new Font("DialogInput", Font.PLAIN, 15));

        return levelHistoryLabel;
    }

    // EFFECTS: sets up scrollPanel and returns it
    public JPanel createScrollPanel() {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        // scrollPanel.setPreferredSize(new Dimension(500, 10000));
        scrollPanel.setOpaque(false);

        return scrollPanel;
    }

    // EFFECTS: sets up scrollPane and returns it
    public JScrollPane createScrollPane(JPanel scrollPanel) {
        JScrollPane scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new CustomScrollBar("src/resources/images/Vertical Scroll Bar.PNG"));
        verticalBar.setPreferredSize(new Dimension(26, 10));
        verticalBar.setUnitIncrement(25);
        scrollPane.setPreferredSize(new Dimension(490, 500));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        return scrollPane;
    }

    // MODIFIES: this
    // EFFECTS: sets the text of LevelHistory
    public void setText(String history, String levelName) {
        levelHistoryLabel.setText(history);
        levelNameLabel.setText(levelName);
    }

    // MODIFIES: this
    // EFFECTS: sets selectedLevel to given level
    public void setSelectedLevel(Level level) {
        selectedLevel = level;
    }

    // REQUIRES: selectedLevel != null && player != null
    // EFFECTS: returns a string of all selectedLevel's LevelStats
    public String listLevelHistory() {
        Player player = gameApp.getPlayer();

        String levelHistory = "<html>";

        for (LevelStats stats : player.getCompletedLevelStats().get(selectedLevel.getLevelIndex())) {
            levelHistory += "<br>Attempt #" + stats.getAttemptNum() + "<br>Moves taken: "
                    + stats.getLeastMovesTaken()
                    + " moves" + "<br>Time taken: " + stats.getLeastTimeTaken() + " seconds<br>";
        }
        levelHistory += "<html>";

        return levelHistory;
    }

    // REQUIRES: selectedLevel != null && player != null
    // EFFECTS: returns a string of all top and bottom LevelStats of selectedLevel
    public String listTopAndBottomLevels() {
        Player player = gameApp.getPlayer();

        LevelStats statsTop = player.getCompletedLevelStats().get(selectedLevel.getLevelIndex()).get(0);
        LevelStats statsBottom = player.getCompletedLevelStats().get(selectedLevel.getLevelIndex())
                .get(player.getCompletedLevelStats().get(selectedLevel.getLevelIndex()).size() - 1);

        String levelHistory = "<html><br>Attempt #" + statsTop.getAttemptNum() + "<br>Moves taken: "
                + statsTop.getLeastMovesTaken()
                + " moves" + "<br>Time taken: " + statsTop.getLeastTimeTaken() + " seconds<br>";

        if (statsTop != statsBottom) {
            levelHistory += "<br>Attempt #" + statsBottom.getAttemptNum() + "<br>Moves taken: "
                    + statsBottom.getLeastMovesTaken()
                    + " moves" + "<br>Time taken: " + statsBottom.getLeastTimeTaken() + " seconds<br>";
        }
        levelHistory += "<html>";

        return levelHistory;
    }

    // MODIFIES: this
    // EFFECTS: checks if topAndBottomCheckBox is selected and proceeds accordingly
    public void checkTopAndBottomCheckBox() {
        if (topAndBottomCheckBox.isSelected()) {
            levelHistoryLabel.setText(listTopAndBottomLevels());
        } else {
            levelHistoryLabel.setText(listLevelHistory());
        }
    }

    // MODIFIES: this
    // EFFECTS: handles buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        Player player = gameApp.getPlayer();

        if (e.getSource() == sortByBox) {
            String selected = (String) sortByBox.getSelectedItem();
            if (selected.equals("Moves then Time")) {
                player.sortCompletedlevelStats(selectedLevel.getLevelIndex(), MOVES_THEN_TIME_SORT);
            } else if (selected.equals("Time then Moves")) {
                player.sortCompletedlevelStats(selectedLevel.getLevelIndex(), TIME_THEN_MOVES_SORT);
            } else if (selected.equals("Attempt")) {
                player.sortCompletedlevelStats(selectedLevel.getLevelIndex(), ATTEMPT_SORT);
            }

            checkTopAndBottomCheckBox();
        } else if (e.getSource() == topAndBottomCheckBox) {
            checkTopAndBottomCheckBox();
        } else if (e.getSource() == clearButton) {
            player.clearLevelHistory(selectedLevel.getLevelIndex());
            levelHistoryLabel.setText("");
        }
    }
}
