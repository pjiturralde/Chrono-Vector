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

    private JLabel levelHistory;
    private JComboBox<String> sortByBox;
    private JCheckBox topAndBottomCheckBox;
    private Player player;
    private Level selectedLevel;

    // EFFECTS: constructs LevelHistoryViewPanel
    LevelHistoryViewPanel(Player player) {
        this.player = player;
        selectedLevel = null;

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.setPreferredSize(new Dimension(500, 600));

        JPanel scrollPanel = createScrollPanel();

        levelHistory = createLevelHistory();

        scrollPanel.add(levelHistory);

        JScrollPane scrollPane = createScrollPane(scrollPanel);

        topAndBottomCheckBox = new JCheckBox();
        topAndBottomCheckBox.setOpaque(false);

        JLabel topAndBottomLabel = new JLabel("Top and bottom results only:");
        topAndBottomLabel.setForeground(new Color(230, 230, 230));
        topAndBottomLabel.setFont(new Font("DialogInput", Font.PLAIN, 13));

        String[] sortOptions = {"Moves then Time", "Time then Moves", "Attempts"};
        sortByBox = new JComboBox<String>(sortOptions);

        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setForeground(new Color(230, 230, 230));
        sortLabel.setFont(new Font("DialogInput", Font.PLAIN, 13));

        this.add(topAndBottomLabel);
        this.add(topAndBottomCheckBox);
        this.add(sortLabel);
        this.add(sortByBox);
        this.add(scrollPane);
    }

    // EFFECTS: resets sortByBox
    public void resetSortByBox() {
        sortByBox.setSelectedIndex(0);
    }

    // EFFECTS: sets up levelHistory and returns it
    public JLabel createLevelHistory() {
        levelHistory = new JLabel();
        levelHistory.setForeground(new Color(230, 230, 230));
        levelHistory.setFont(new Font("DialogInput", Font.PLAIN, 15));

        return levelHistory;
    }

    // EFFECTS: sets up scrollPanel and returns it
    public JPanel createScrollPanel() {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        scrollPanel.setPreferredSize(new Dimension(500, 10000));
        scrollPanel.setOpaque(false);

        return scrollPanel;
    }

    // EFFECTS: sets up scrollPane and returns it
    public JScrollPane createScrollPane(JPanel scrollPanel) {
        JScrollPane scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new CustomScrollBar("src\\resources\\images\\Vertical Scroll Bar.PNG"));
        verticalBar.setPreferredSize(new Dimension(26,10));
        verticalBar.setUnitIncrement(25);
        scrollPane.setPreferredSize(new Dimension(490, 550));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        return scrollPane;
    }

    // EFFECTS: sets the text of LevelHistory
    public void setText(String text) {
        levelHistory.setText(text);
    }

    // MODIFIES: this
    // EFFECTS: sets this.player to given player
    public void setPlayer(Player player) {
        this.player = player;
    }

    // MODIFIES: this
    // EFFECTS: sets selectedLevel to given level
    public void setSelectedLevel(Level level) {
        selectedLevel = level;
    }

    // REQUIRES: selectedLevel != null && player != null
    // EFFECTS: returns a string of all selectedLevel's LevelStats
    public String listAllLevels() {
        String string = "<html>";
    
        for (LevelStats stats : player.getCompletedLevelStats().get(selectedLevel.getLevelIndex())) {
            string += "<br>Attempt #" + stats.getAttemptNum() + "<br>Moves taken: "
                    + stats.getLeastMovesTaken()
                    + " moves" + "<br>Time taken: " + stats.getLeastTimeTaken() + " seconds<br>";
        }
        string += "<html>";

        return string;
    }

    // REQUIRES: selectedLevel != null && player != null
    // EFFECTS: returns a string of all top and bottom LevelStats of selectedLevel
    public String listTopAndBottomLevels() {
        LevelStats statsTop = player.getCompletedLevelStats().get(selectedLevel.getLevelIndex()).get(0);
        LevelStats statsBottom = player.getCompletedLevelStats().get(selectedLevel.getLevelIndex()).get(player.getCompletedLevelStats().get(selectedLevel.getLevelIndex()).size()-1);

        String string = "<html><br>Attempt #" + statsTop.getAttemptNum() + "<br>Moves taken: "
        + statsTop.getLeastMovesTaken()
        + " moves" + "<br>Time taken: " + statsTop.getLeastTimeTaken() + " seconds<br>";

        if (statsTop != statsBottom) {
            string += "<br>Attempt #" + statsBottom.getAttemptNum() + "<br>Moves taken: "
            + statsBottom.getLeastMovesTaken()
            + " moves" + "<br>Time taken: " + statsBottom.getLeastTimeTaken() + " seconds<br>";
        }
        string += "<html>";

        return string;
    }

    // EFFECTS: handles buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sortByBox) {
            String selected = (String) sortByBox.getSelectedItem();
            if (selected.equals("Moves then Time")) {
                player.sortCompletedlevelStats(selectedLevel.getLevelIndex(), MOVES_THEN_TIME_SORT);
            } else if (selected.equals("Time then Moves")) {
                player.sortCompletedlevelStats(selectedLevel.getLevelIndex(), TIME_THEN_MOVES_SORT);
            } else if (selected.equals("Attempts")) {
                player.sortCompletedlevelStats(selectedLevel.getLevelIndex(), ATTEMPT_SORT);
            }
    
            if (topAndBottomCheckBox.isSelected()) {
                levelHistory.setText(listTopAndBottomLevels());
            } else {
                levelHistory.setText(listAllLevels());
            }
        } else if (e.getSource() == topAndBottomCheckBox) {
            if (topAndBottomCheckBox.isSelected()) {
                levelHistory.setText(listTopAndBottomLevels());
            } else {
                levelHistory.setText(listAllLevels());
            }
        }
    }
}
