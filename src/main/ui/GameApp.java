package ui;

import java.util.Comparator;
import java.util.LinkedList;
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.Level;
import model.Projectile;
import model.Wall;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.Player;
import model.LevelStats;

// Game application
public class GameApp extends JFrame implements ActionListener, KeyListener {
    private static final String JSON_STORE = "./data/player.json";
    private static final String GAME_TITLE = "Chrono Vector";
    private static final int MOVES_THEN_TIME_SORT = 0;
    private static final int TIME_THEN_MOVES_SORT = 1;
    private static final int ATTEMPT_SORT = 2;

    private LinkedList<Level> levels;
    private Level currentLevel;
    private Player player;
    private boolean inGame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JButton[] levelButtons;
    private JButton[] completedLevelButtons;
    private JButton backButton;
    private JLabel levelHistory;

    private GameplayPanel gameplayPanel;
    private JLayeredPane layeredGamePane;
    private JPanel mainScreenPanel;
    private JPanel inGameMenuPanel;
    private JLabel inGameMenuLabel;

    private String previousMenu;
    private Level selectedLevel;
    private JComboBox sortByBox;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    // EFFECTS: handles user input
    @Override
    public void keyReleased(KeyEvent e) {
        if (inGame) {
            switch (e.getKeyChar()) {
                case 'w':
                    movePlayer(0, -1);
                    break;
                case 'a':
                    movePlayer(-1, 0);
                    break;
                case 's':
                    movePlayer(0, 1);
                    break;
                case 'd':
                    movePlayer(1, 0);
                    break;
            }
        }
    }

    // EFFECTS: runs the Game application
    public GameApp() {
        init();
    }

    // MODIFIES: this
    // EFFECTS: moves player and updates level accordingly
    private void movePlayer(int x, int y) {
        player.move(x, y);
        currentLevel.updateMovesTaken();
        currentLevel.checkPlayerMovement(x, y);
        currentLevel.checkCollision(player, x, y);

        if (currentLevel.getMovesTaken() == 1) {
            currentLevel.startTime();
        }

        checkLostOrWon();
    }

    // MODIFIES: this
    // EFFECTS: checks if level has been lost or won
    // and proceeds accordingly
    private void checkLostOrWon() {
        if (currentLevel.lost()) {
            inGame = false;

            inGameMenuPanel.setVisible(true);
            inGameMenuPanel.setEnabled(true);
            inGameMenuLabel.setText("YOU LOST");

            currentLevel.reset();
        } else if (currentLevel.completed()) {
            currentLevel.endTime();
            if (player.getCompletedLevelStats().size() > currentLevel.getLevelIndex()) {
                player.addCompletedLevelStats(currentLevel, player.getCompletedLevelStats().get(currentLevel.getLevelIndex()).size() + 1);
            } else {
                player.addCompletedLevelStats(currentLevel, 1);
            }

            inGame = false;

            inGameMenuPanel.setVisible(true);
            inGameMenuPanel.setEnabled(true);
            inGameMenuLabel.setText("<html>YOU WON<br>" + "Completed in " + currentLevel.getTimeTaken() + " seconds<br>"
                    + "You took " + currentLevel.getMovesTaken() + " moves" + "<html>");

            currentLevel.reset();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes game and menu
    private void init() {
        this.levels = new LinkedList<Level>();
        this.inGame = false;
        this.player = new Player();
        this.jsonReader = new JsonReader(JSON_STORE);
        this.jsonWriter = new JsonWriter(JSON_STORE);

        constructAllLevels();

        levelButtons = new JButton[levels.size()];
        completedLevelButtons = new JButton[levels.size()];

        ImageIcon originalIcon = new ImageIcon("src\\resources\\images\\Game Title Icon.png");

        Image resizedImage = originalIcon.getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH);

        ImageIcon gameIcon = new ImageIcon(resizedImage);

        JLabel titleLabel = new JLabel();
        titleLabel.setIcon(gameIcon);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        mainScreenPanel = new MainScreenPanel();
        JPanel centerPanel = new JPanel();
        JPanel northPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel westPanel = new JPanel();

        centerPanel.setBackground(Color.red);
        northPanel.setBackground(Color.green);
        eastPanel.setBackground(Color.yellow);
        southPanel.setBackground(Color.magenta);
        westPanel.setBackground(Color.blue);

        centerPanel.setPreferredSize(new Dimension(300, 300));
        northPanel.setPreferredSize(new Dimension(300, 300));
        eastPanel.setPreferredSize(new Dimension(300, 300));
        southPanel.setPreferredSize(new Dimension(300, 120));
        westPanel.setPreferredSize(new Dimension(300, 300));

        centerPanel.setBackground(new Color(0, 0, 44));
        northPanel.setBackground(new Color(0, 0, 44));
        eastPanel.setBackground(new Color(0, 0, 44));
        southPanel.setBackground(new Color(0, 0, 44));
        westPanel.setBackground(new Color(0, 0, 44));

        mainScreenPanel.add(centerPanel, BorderLayout.CENTER);
        mainScreenPanel.add(northPanel, BorderLayout.NORTH);
        mainScreenPanel.add(eastPanel, BorderLayout.EAST);
        mainScreenPanel.add(southPanel, BorderLayout.SOUTH);
        mainScreenPanel.add(westPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(500, 600));

        JPanel frontMenuPanel = createFrontMenu();
        JPanel levelSelectScrollPane = createLevelSelectMenu();
        JPanel completedLevelSelectScrollPanel = createCompletedLevelSelectMenu();
        JPanel levelHistoryViewPanel = createLevelHistoryView();

        cardPanel.add(frontMenuPanel, "Main Menu");
        cardPanel.add(levelSelectScrollPane, "Level Select");
        cardPanel.add(completedLevelSelectScrollPanel, "Completed Level Select");
        cardPanel.add(levelHistoryViewPanel, "Level History");

        centerPanel.add(cardPanel);

        ImageIcon originalBackIcon = new ImageIcon("src\\resources\\images\\Button.png");

        Image resizedBackImage = originalBackIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        ImageIcon backIcon = new ImageIcon(resizedBackImage);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 100));
        backButton.setVisible(false);
        backButton.setEnabled(false);
        backButton.addActionListener(this);

        backButton.setHorizontalTextPosition(JLabel.CENTER);
        backButton.setVerticalTextPosition(JLabel.CENTER); 
        backButton.setIcon(backIcon);
        backButton.setBackground(new Color(0, 0, 44));
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setForeground(new Color(230, 230, 230));
        backButton.setFont(new Font("DialogInput", Font.PLAIN, 15));

        layeredGamePane = new JLayeredPane();
        layeredGamePane.setPreferredSize(new Dimension(1920, 1080));

        gameplayPanel = new GameplayPanel(player); // IMPORTANT
        gameplayPanel.setBounds(0, 0, 1920, 1080);

        inGameMenuPanel = createInGameMenu();
        inGameMenuPanel.setBounds(1920 / 2 - 700 / 2, 300, 700, 500);
        inGameMenuPanel.setVisible(false);
        inGameMenuPanel.setEnabled(false);

        layeredGamePane.add(inGameMenuPanel, JLayeredPane.PALETTE_LAYER);
        layeredGamePane.add(gameplayPanel, JLayeredPane.DEFAULT_LAYER);

        this.setTitle(GAME_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1920, 1080);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.addKeyListener(this);
        this.setVisible(true);

        northPanel.setLayout(new BorderLayout());
        northPanel.add(titleLabel);

        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        southPanel.add(backButton);

        toMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: switches content pane to menu
    public void toMainMenu() {
        this.setContentPane(mainScreenPanel);
        revalidate();
        requestFocus();
    }

    // MODIFIES: this
    // EFFECTS: switches content pane to gameplay
    public void toGameplay() {
        inGame = true;
        this.setContentPane(layeredGamePane);
        revalidate();
        requestFocus();
    }

    public void customizeMenuButton(JButton button, int width, int height) {
        Image buttonImage = new ImageIcon("src\\resources\\images\\Menu Button.png").getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        ImageIcon buttonIcon = new ImageIcon(buttonImage);

        button.setIcon(buttonIcon);
        button.setHorizontalTextPosition(JLabel.CENTER);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(230, 230, 230));
        button.setFont(new Font("DialogInput", Font.PLAIN, 18));
    }

    // MODIFIES: this
    // EFFECTS: creates front menu panel and returns it
    public JPanel createFrontMenu() {
        final int BUTTON_WIDTH = 400;
        final int BUTTON_HEIGHT = 100;

        JPanel panel = new MenuPanel();

        JButton playButton = new JButton("Play");
        JButton completedLevelsButton = new JButton("Completed Levels");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton quitButton = new JButton("Quit");

        playButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        completedLevelsButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        saveButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        loadButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        quitButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        customizeMenuButton(playButton, BUTTON_WIDTH, BUTTON_HEIGHT);
        customizeMenuButton(completedLevelsButton, BUTTON_WIDTH, BUTTON_HEIGHT);
        customizeMenuButton(saveButton, BUTTON_WIDTH, BUTTON_HEIGHT);
        customizeMenuButton(loadButton, BUTTON_WIDTH, BUTTON_HEIGHT);
        customizeMenuButton(quitButton, BUTTON_WIDTH, BUTTON_HEIGHT);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Level Select");
                backButton.setVisible(true);
                backButton.setEnabled(true);
                previousMenu = "Main Menu";
            }
        });

        completedLevelsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Completed Level Select");
                backButton.setVisible(true);
                backButton.setEnabled(true);
                previousMenu = "Main Menu";

                if (player.getCompletedLevelStats().size() != 0) {
                    for (int i = 0; i < player.getCompletedLevelStats().size(); i++) {
                        if (!completedLevelButtons[i].isVisible()) {
                            completedLevelButtons[i].setVisible(true);
                            completedLevelButtons[i].setEnabled(true);
                        }
                    }

                    for (int i = player.getCompletedLevelStats().size(); i < levels.size(); i++) {
                        if (completedLevelButtons[i].isVisible()) {
                            completedLevelButtons[i].setVisible(false);
                            completedLevelButtons[i].setEnabled(false);
                        }
                    }
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                savePlayer();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadPlayer();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(playButton);
        panel.add(completedLevelsButton);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(quitButton);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates in game menu panel and returns it
    public JPanel createInGameMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.setPreferredSize(new Dimension(700, 600));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(1000, 200));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));

        inGameMenuLabel = new JLabel();
        inGameMenuLabel.setPreferredSize(new Dimension(1000, 1000));
        inGameMenuLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton retryButton = new JButton("Retry");
        JButton levelsButton = new JButton("Levels");

        retryButton.setPreferredSize(new Dimension(200, 100));
        levelsButton.setPreferredSize(new Dimension(200, 100));

        retryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentLevel.reset();
                player.setPosition(currentLevel.getStartPosition());
                inGameMenuPanel.setVisible(false);
                inGameMenuPanel.setEnabled(false);
                requestFocus();
                inGame = true;
            }
        });

        levelsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inGameMenuPanel.setVisible(false);
                inGameMenuPanel.setEnabled(false);
                toMainMenu();
            }
        });

        panel.add(inGameMenuLabel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(retryButton);
        bottomPanel.add(levelsButton);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates level select menu scroll pane and returns it
    public JPanel createLevelSelectMenu() {
        JPanel mainPanel = new MenuPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 40));
        scrollPanel.setOpaque(false);

        for (Level level : levels) {
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(380, 500));

            JPanel displayPanel = new LevelDisplayPanel(level.getName());
            displayPanel.setBounds(0, 0, 380, 500);

            JButton button = new JButton("Level " + (level.getLevelIndex() + 1));
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setBounds(0, 0, 380, 500);
            levelButtons[level.getLevelIndex()] = button;

            layeredPane.add(displayPanel);
            layeredPane.add(button);

            scrollPanel.add(layeredPane);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentLevel = level;
                    gameplayPanel.setCurrentLevel(level);
                    player.setPosition(level.getStartPosition());
                    toGameplay();
                }
            });
        }

        JScrollPane scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
        horizontalBar.setUI(new CustomScrollBar("src\\resources\\images\\Horizontal Scroll Bar.png"));
        horizontalBar.setPreferredSize(new Dimension(10,26));
        horizontalBar.setUnitIncrement(50);
        scrollPane.setPreferredSize(new Dimension(490, 590));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        mainPanel.add(scrollPane);

        return mainPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates completed level select menu scroll pane and returns it
    public JPanel createCompletedLevelSelectMenu() {
        JPanel mainPanel = new MenuPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        scrollPanel.setOpaque(false);

        for (Level level : levels) {
            JButton levelButton = new JButton("Level " + (level.getLevelIndex() + 1));
            ImageIcon originalIcon = new ImageIcon("src\\resources\\images\\Level Button.png");

            Image resizedImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    
            ImageIcon levelIcon = new ImageIcon(resizedImage);
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
            scrollPanel.add(levelButton);

            levelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(cardPanel, "Level History");
                    previousMenu = "Completed Level Select";
                    String string = "<html>";

                    for (LevelStats stats : player.getCompletedLevelStats().get(level.getLevelIndex())) {
                        string += "<br>Attempt #" + stats.getAttemptNum() + "<br>Moves taken: "
                                + stats.getLeastMovesTaken()
                                + " moves" + "<br>Time taken: " + stats.getLeastTimeTaken() + " seconds<br>";
                    }
                    string += "<html>";

                    selectedLevel = level;
                    levelHistory.setText(string);
                    sortByBox.setSelectedIndex(0);
                }
            });
        }

        JScrollPane scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUI(new CustomScrollBar("src\\resources\\images\\Vertical Scroll Bar.PNG"));
        verticalBar.setPreferredSize(new Dimension(26,10));
        verticalBar.setUnitIncrement(50);
        scrollPane.setPreferredSize(new Dimension(490, 580));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        mainPanel.add(scrollPane);

        return mainPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates level history view scroll pane and returns it
    public JPanel createLevelHistoryView() {
        JPanel mainPanel = new MenuPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        mainPanel.setPreferredSize(new Dimension(500, 600));

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        scrollPanel.setPreferredSize(new Dimension(500, 10000));
        scrollPanel.setOpaque(false);

        levelHistory = new JLabel();
        levelHistory.setForeground(new Color(230, 230, 230));
        levelHistory.setFont(new Font("DialogInput", Font.PLAIN, 15));
        scrollPanel.add(levelHistory);

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

        JCheckBox topAndBottomCheckBox = new JCheckBox();
        topAndBottomCheckBox.setOpaque(false);

        JLabel topAndBottomLabel = new JLabel("Top and bottom results only:");
        topAndBottomLabel.setForeground(new Color(230, 230, 230));
        topAndBottomLabel.setFont(new Font("DialogInput", Font.PLAIN, 13));

        String[] sortOptions = {"Moves then Time", "Time then Moves", "Attempts"};
        sortByBox = new JComboBox(sortOptions);

        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setForeground(new Color(230, 230, 230));
        sortLabel.setFont(new Font("DialogInput", Font.PLAIN, 13));

        sortByBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selected = (String) sortByBox.getSelectedItem();
                if (selected.equals("Moves then Time")) {
                    player.sortCompletedlevelStats(selectedLevel.getLevelIndex(), MOVES_THEN_TIME_SORT);
                } else if (selected.equals("Time then Moves")) {
                    player.sortCompletedlevelStats(selectedLevel.getLevelIndex(), TIME_THEN_MOVES_SORT);
                } else if (selected.equals("Attempts")) {
                    player.sortCompletedlevelStats(selectedLevel.getLevelIndex(), ATTEMPT_SORT);
                }

                if (topAndBottomCheckBox.isSelected()) {
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
                    levelHistory.setText(string);
                } else {
                    String string = "<html>";

                    for (LevelStats stats : player.getCompletedLevelStats().get(selectedLevel.getLevelIndex())) {
                        string += "<br>Attempt #" + stats.getAttemptNum() + "<br>Moves taken: "
                                + stats.getLeastMovesTaken()
                                + " moves" + "<br>Time taken: " + stats.getLeastTimeTaken() + " seconds<br>";
                    }
                    string += "<html>";
    
                    levelHistory.setText(string);
                }
            }
        });
        
        topAndBottomCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (topAndBottomCheckBox.isSelected()) {
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
    
                    levelHistory.setText(string);
                } else {
                    String string = "<html>";

                    for (LevelStats stats : player.getCompletedLevelStats().get(selectedLevel.getLevelIndex())) {
                        string += "<br>Attempt #" + stats.getAttemptNum() + "<br>Moves taken: "
                                + stats.getLeastMovesTaken()
                                + " moves" + "<br>Time taken: " + stats.getLeastTimeTaken() + " seconds<br>";
                    }
                    string += "<html>";
    
                    levelHistory.setText(string);
                }
            }
        });

        mainPanel.add(topAndBottomLabel);
        mainPanel.add(topAndBottomCheckBox);
        mainPanel.add(sortLabel);
        mainPanel.add(sortByBox);
        mainPanel.add(scrollPane);

        return mainPanel;
    }

    // EFFECTS: handles back button in main menu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            cardLayout.show(cardPanel, previousMenu);

            if (previousMenu.equals("Main Menu")) {
                backButton.setVisible(false);
                backButton.setEnabled(false);
            } else if (previousMenu.equals("Completed Level Select")) {
                previousMenu = "Main Menu";
            }
        }

        repaint();
    }

    // MODIFIES: this
    // EFFECTS: constructs all levels
    private void constructAllLevels() {
        constructLevel1();
        constructLevel2();
        constructLevel3();
    }

    // MODIFIES: this
    // EFFECTS: constructs level 1
    private void constructLevel1() {
        Level level1 = new Level("level 1", 1, 4, 9, 4, 1, 8, 10, 0, -1);
        level1.addProjectile(new Projectile(7, 6, -1, 0, 6));
        level1.addProjectile(new Projectile(1, 4, 1, 0, 6));
        level1.addWall(new Wall(0, 1, 0, 9));
        level1.addWall(new Wall(8, 1, 8, 9));
        level1.addWall(new Wall(1, 1, 3, 1));
        level1.addWall(new Wall(5, 1, 7, 1));
        level1.addWall(new Wall(1, 9, 3, 9));
        level1.addWall(new Wall(5, 9, 7, 9));
        level1.addWall(new Wall(3, 0, 5, 0));
        level1.addWall(new Wall(3, 10, 5, 10));

        levels.add(level1);
    }

    private void constructLevel2() {
        Level level2 = new Level("level 2", 2, 4, 9, 4, 1, 8, 10, 0, -1);
        level2.addProjectile(new Projectile(7, 6, -1, 0, 6));
        level2.addProjectile(new Projectile(1, 4, 1, 0, 6));
        level2.addWall(new Wall(0, 1, 0, 9));
        level2.addWall(new Wall(8, 1, 8, 9));
        level2.addWall(new Wall(1, 1, 3, 1));
        level2.addWall(new Wall(5, 1, 7, 1));
        level2.addWall(new Wall(1, 9, 3, 9));
        level2.addWall(new Wall(5, 9, 7, 9));
        level2.addWall(new Wall(3, 0, 5, 0));
        level2.addWall(new Wall(3, 10, 5, 10));

        levels.add(level2);
    }

    private void constructLevel3() {
        Level level3 = new Level("level 3", 3, 4, 9, 4, 1, 8, 10, 0, -1);
        level3.addProjectile(new Projectile(7, 6, -1, 0, 6));
        level3.addProjectile(new Projectile(1, 4, 1, 0, 6));
        level3.addWall(new Wall(0, 1, 0, 9));
        level3.addWall(new Wall(8, 1, 8, 9));
        level3.addWall(new Wall(1, 1, 3, 1));
        level3.addWall(new Wall(5, 1, 7, 1));
        level3.addWall(new Wall(1, 9, 3, 9));
        level3.addWall(new Wall(5, 9, 7, 9));
        level3.addWall(new Wall(3, 0, 5, 0));
        level3.addWall(new Wall(3, 10, 5, 10));

        levels.add(level3);
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // MODIFIES: this
    // EFFECTS: saves the player to file
    private void savePlayer() {
        try {
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // MODIFIES: this
    // EFFECTS: loads player from file
    private void loadPlayer() {
        try {
            player = jsonReader.read();
            gameplayPanel.setPlayer(player);
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
