package ui;

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
    private static final int MOVES_AND_TIME_SORT = 0;
    private static final int ATTEMPT_SORT = 1;
    private static final int MOVE_SORT = 2;
    private static final int TIME_SORT = 3;

    private LinkedList<Level> levels;
    private Level currentLevel;
    private Player player;
    private boolean inGame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    JButton[] levelButtons;
    JButton[] completedLevelButtons;
    JButton backButton;
    JLabel levelHistory;

    GameplayPanel gameplayPanel;
    JLayeredPane layeredGamePane;
    JPanel menuPanel;
    JPanel inGameMenuPanel;
    JLabel inGameMenuLabel;

    String previousMenu;
    int sortBy;

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
            player.addCompletedLevelStats(currentLevel, player.getCompletedLevelStats().size() + 1);

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
        this.sortBy = MOVES_AND_TIME_SORT;

        constructAllLevels();

        levelButtons = new JButton[levels.size()];
        completedLevelButtons = new JButton[levels.size()];

        ImageIcon originalImage = new ImageIcon("src\\main\\ui\\Game Icon.png");

        Image resizedImage = originalImage.getImage().getScaledInstance(800, 400, Image.SCALE_SMOOTH);

        ImageIcon image = new ImageIcon(resizedImage);

        JLabel label = new JLabel();
        label.setIcon(image);
        label.setHorizontalAlignment(JLabel.CENTER);

        menuPanel = new JPanel();
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
        northPanel.setPreferredSize(new Dimension(300, 260));
        eastPanel.setPreferredSize(new Dimension(300, 300));
        southPanel.setPreferredSize(new Dimension(300, 140));
        westPanel.setPreferredSize(new Dimension(300, 300));

        menuPanel.setLayout(new BorderLayout());

        menuPanel.add(centerPanel, BorderLayout.CENTER);
        menuPanel.add(northPanel, BorderLayout.NORTH);
        menuPanel.add(eastPanel, BorderLayout.EAST);
        menuPanel.add(southPanel, BorderLayout.SOUTH);
        menuPanel.add(westPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel mainMenuPanel = createMainMenu();
        JScrollPane levelSelectScrollPane = createLevelSelectMenu();
        JScrollPane completedLevelSelectScrollPane = createCompletedLevelSelectMenu();
        JScrollPane levelHistoryViewPane = createLevelHistoryView();

        cardPanel.add(mainMenuPanel, "Main Menu");
        cardPanel.add(levelSelectScrollPane, "Level Select");
        cardPanel.add(completedLevelSelectScrollPane, "Completed Level Select");
        cardPanel.add(levelHistoryViewPane, "Level History");

        centerPanel.add(cardPanel);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 80));
        backButton.setVisible(false);
        backButton.setEnabled(false);
        backButton.addActionListener(this);

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
        this.getContentPane().setBackground(new Color(0, 0, 44));

        northPanel.setLayout(new BorderLayout());
        northPanel.add(label);

        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        southPanel.add(backButton);

        toMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: switches content pane to menu
    public void toMainMenu() {
        this.setContentPane(menuPanel);
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

    // MODIFIES: this
    // EFFECTS: creates main menu panel and returns it
    public JPanel createMainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        panel.setPreferredSize(new Dimension(700, 600));

        JButton playButton = new JButton("Play");
        JButton completedLevelsButton = new JButton("Completed Levels");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton quitButton = new JButton("Quit");

        playButton.setPreferredSize(new Dimension(400, 60));
        completedLevelsButton.setPreferredSize(new Dimension(400, 60));
        saveButton.setPreferredSize(new Dimension(400, 60));
        loadButton.setPreferredSize(new Dimension(400, 60));
        quitButton.setPreferredSize(new Dimension(400, 60));

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
    public JScrollPane createLevelSelectMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 40));

        for (Level level : levels) {
            JButton button = new JButton("Level " + (level.getLevelIndex() + 1));
            button.setPreferredSize(new Dimension(300, 300));
            levelButtons[level.getLevelIndex()] = button;
            panel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    currentLevel = level;
                    gameplayPanel.setCurrentLevel(level);
                    player.setPosition(level.getStartPosition());
                    toGameplay();
                }
            });
        }

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(50);
        scrollPane.setPreferredSize(new Dimension(700, 600));

        return scrollPane;
    }

    // MODIFIES: this
    // EFFECTS: creates completed level select menu scroll pane and returns it
    public JScrollPane createCompletedLevelSelectMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setPreferredSize(new Dimension(700, 10000));

        for (Level level : levels) {
            JButton button = new JButton("Level " + (level.getLevelIndex() + 1));
            button.setPreferredSize(new Dimension(100, 100));
            completedLevelButtons[level.getLevelIndex()] = button;
            button.setVisible(false);
            button.setEnabled(false);
            panel.add(button);

            button.addActionListener(new ActionListener() {
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

                    levelHistory.setText(string);
                }
            });
        }

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);
        scrollPane.setPreferredSize(new Dimension(700, 600));

        return scrollPane;
    }

    // MODIFIES: this
    // EFFECTS: creates level history view scroll pane and returns it
    public JScrollPane createLevelHistoryView() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setPreferredSize(new Dimension(700, 10000));

        levelHistory = new JLabel();
        panel.add(levelHistory);

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.setPreferredSize(new Dimension(700, 600));

        return scrollPane;
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
