package ui;

import java.util.LinkedList;
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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

// Game application
public class GameApp extends JFrame implements ActionListener, KeyListener, ComponentListener {
    private static final String JSON_STORE = "./data/player.json";
    private static final String GAME_TITLE = "Chrono Vector";

    private Timer resizeTimer;

    private LinkedList<Level> levels;
    private Level currentLevel;
    private Player player;
    private boolean inGame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel menuCardPanel;
    private CardLayout menuCardLayout;

    private JButton backButton;

    private GameplayPanel gameplayPanel;
    private JLayeredPane layeredGamePane;
    private MainScreenPanel mainScreenPanel;
    private InGameMenuPanel inGameMenuPanel;

    private String previousMenu;

    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel southPanel;

    private JLabel titleLabel;

    private FrontMenuPanel frontMenuPanel;
    private LevelSelectMenuPanel levelSelectMenuPanel;
    private CompletedLevelSelectMenuPanel completedLevelSelectMenuPanel;
    private LevelHistoryViewPanel levelHistoryViewPanel;

    // EFFECTS: runs the Game application
    public GameApp() {
        init();
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

        titleLabel = createTitleLabel();

        initializeMenuCardPanel();

        backButton = createBackButton();

        initializeMainScreenPanel();

        initializeInGameUI();

        this.setTitle(GAME_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1920, 1080);
        this.setLayout(new BorderLayout());
        this.addKeyListener(this);
        this.addComponentListener(this);
        this.setMinimumSize(new Dimension(800, 800));
        this.setVisible(true);

        toMainMenu();
    }

    // EFFECTS: creates back button and returns it
    public JButton createBackButton() {
        JButton button = new JButton("Back");
        button.setPreferredSize(new Dimension(100, 100));
        button.setVisible(false);
        button.setEnabled(false);
        button.addActionListener(this);

        button.setHorizontalTextPosition(JLabel.CENTER);
        button.setVerticalTextPosition(JLabel.CENTER);

        ImageIcon originalBackIcon = new ImageIcon("src/resources/images/Button.png");

        Image resizedBackImage = originalBackIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        ImageIcon backIcon = new ImageIcon(resizedBackImage);

        button.setIcon(backIcon);
        button.setBackground(new Color(0, 0, 44));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(230, 230, 230));
        button.setFont(new Font("DialogInput", Font.PLAIN, 15));

        return button;
    }

    // MODIFIES: this
    // EFFECTS: initializes main screen panel and its components
    public void initializeMainScreenPanel() {
        centerPanel = new JPanel();
        northPanel = new JPanel();
        southPanel = new JPanel();

        centerPanel.setPreferredSize(new Dimension(300, 300));
        northPanel.setPreferredSize(new Dimension(300, 300));
        southPanel.setPreferredSize(new Dimension(300, 120));

        centerPanel.setBackground(new Color(0, 0, 44));
        northPanel.setBackground(new Color(0, 0, 44));
        southPanel.setBackground(new Color(0, 0, 44));

        northPanel.setLayout(new BorderLayout());
        northPanel.add(titleLabel);

        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        southPanel.add(backButton);

        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        centerPanel.add(menuCardPanel);

        mainScreenPanel = new MainScreenPanel();

        mainScreenPanel.add(centerPanel, BorderLayout.CENTER);
        mainScreenPanel.add(northPanel, BorderLayout.NORTH);
        mainScreenPanel.add(southPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes menuCardPanel and its components
    public void initializeMenuCardPanel() {
        menuCardLayout = new CardLayout();
        menuCardPanel = new JPanel(menuCardLayout);
        menuCardPanel.setPreferredSize(new Dimension(500, 600));

        levelSelectMenuPanel = new LevelSelectMenuPanel(this);
        completedLevelSelectMenuPanel = new CompletedLevelSelectMenuPanel(this);
        levelHistoryViewPanel = new LevelHistoryViewPanel(this);
        frontMenuPanel = new FrontMenuPanel(this);

        menuCardPanel.add(frontMenuPanel, "Main Menu");
        menuCardPanel.add(levelSelectMenuPanel, "Level Select");
        menuCardPanel.add(completedLevelSelectMenuPanel, "Completed Level Select");
        menuCardPanel.add(levelHistoryViewPanel, "Level History");
    }

    // MODIFIES: this
    // EFFECTS: initializes panels related to gameplay
    public void initializeInGameUI() {
        layeredGamePane = new JLayeredPane();
        layeredGamePane.setPreferredSize(new Dimension(1920, 1080));

        gameplayPanel = new GameplayPanel(this);
        gameplayPanel.setBounds(0, 0, 1920, 1080);

        inGameMenuPanel = new InGameMenuPanel(this);
        inGameMenuPanel.setBounds(1920 / 2 - 700 / 2, 300, 700, 500);
        inGameMenuPanel.setVisible(false);
        inGameMenuPanel.setEnabled(false);

        layeredGamePane.add(inGameMenuPanel, JLayeredPane.PALETTE_LAYER);
        layeredGamePane.add(gameplayPanel, JLayeredPane.DEFAULT_LAYER);
    }

    // EFFECTS: creates title label and returns it
    public JLabel createTitleLabel() {
        ImageIcon originalIcon = new ImageIcon("src/resources/images/Game Title Icon.png");

        Image resizedImage = originalIcon.getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH);

        ImageIcon gameIcon = new ImageIcon(resizedImage);

        JLabel label = new JLabel();
        label.setIcon(gameIcon);
        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
    }

    // MODIFIES: this
    // EFFECTS: enables back button and makes it visible
    public void enableBackButton() {
        backButton.setVisible(true);
        backButton.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: disables back button and makes it invisible
    public void disableBackButton() {
        backButton.setVisible(false);
        backButton.setEnabled(false);
    }

    // EFFECTS: returns levelHistoryViewPanel
    public LevelHistoryViewPanel getLevelHistoryViewPanel() {
        return levelHistoryViewPanel;
    }

    // EFFECTS: returns completedLevelSelectMenuPanel
    public CompletedLevelSelectMenuPanel getCompletedLevelSelectMenuPanel() {
        return completedLevelSelectMenuPanel;
    }

    // EFFECTS: returns levels
    public LinkedList<Level> getLevels() {
        return levels;
    }

    // MODIFIES: this
    // EFFECTS: shows the menu panel specified by the given name
    public void setMenuCardLayout(String name) {
        menuCardLayout.show(menuCardPanel, name);
    }

    // MODIFIES: this
    // EFFECTS: sets previousMenu to given string
    public void setPreviousMenu(String previousMenu) {
        this.previousMenu = previousMenu;
    }

    // MODIFIES: this
    // EFFECTS: sets currentLevel to given level
    public void setCurrentLevel(Level level) {
        this.currentLevel = level;
    }

    // MODIFIES: this
    // EFFECTS: sets inGame to given bool
    public void setInGame(boolean bool) {
        this.inGame = bool;
    }

    // EFFECTS: returns currentLevel
    public Level getCurrentLevel() {
        return currentLevel;
    }

    // EFFECTS: returns player
    public Player getPlayer() {
        return player;
    }

    // EFFECTS: returns gameplayPanel;
    public GameplayPanel getGameplayPanel() {
        return gameplayPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    // MODIFIES: this
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
            inGameMenuPanel.setText("YOU LOST");

            currentLevel.reset();
        } else if (currentLevel.completed()) {
            currentLevel.endTime();
            if (player.getCompletedLevelStats().size() > currentLevel.getLevelIndex()) {
                player.addCompletedLevelStats(currentLevel,
                        player.getCompletedLevelStats().get(currentLevel.getLevelIndex()).size() + 1);
            } else {
                player.addCompletedLevelStats(currentLevel, 1);
            }

            inGame = false;

            inGameMenuPanel.setVisible(true);
            inGameMenuPanel.setEnabled(true);
            inGameMenuPanel.setText("<html>YOU WON<br>" + "Completed in " + currentLevel.getTimeTaken() + " seconds<br>"
                    + "You took " + currentLevel.getMovesTaken() + " moves" + "<html>");

            currentLevel.reset();
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    // MODIFIES: this
    // EFFECTS: resizes UI components when gameApp is resized
    @Override
    public void componentResized(ComponentEvent e) {
        final long DELAY = 50;

        final float FRAME_TO_MENU_PANEL_HEIGHT_RATIO = 0.555f;
        final float MENU_PANEL_HEIGHT_TO_WIDTH_RATIO = 0.833f;

        int menuPanelHeight = Math.round(this.getHeight() * FRAME_TO_MENU_PANEL_HEIGHT_RATIO);
        int menuPanelWidth = Math.round(menuPanelHeight * MENU_PANEL_HEIGHT_TO_WIDTH_RATIO);

        if (resizeTimer != null && resizeTimer.isRunning()) {
            resizeTimer.stop();
        }

        resizeTimer = new Timer((int) DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resizeMainPanels();

                resizeNorthAndSouthPanels();

                resizeBackButton();

                resizeTitleLabel();

                menuCardPanel.setPreferredSize(new Dimension(menuPanelWidth, menuPanelHeight));

                resizeTimer.stop();
            }
        });

        resizeTimer.setRepeats(false);
        resizeTimer.start();
    }

    // MODIFIES: this
    // EFFECTS: resizes north and south panels based on gameApp screen dimensions
    public void resizeNorthAndSouthPanels() {
        final float FRAME_TO_NORTH_PANEL_HEIGHT_RATIO = 0.276f;
        final float FRAME_TO_SOUTH_PANEL_HEIGHT_RATIO = 0.111f;

        int northPanelHeight = Math.round(this.getHeight() * FRAME_TO_NORTH_PANEL_HEIGHT_RATIO);
        int southPanelHeight = Math.round(this.getHeight() * FRAME_TO_SOUTH_PANEL_HEIGHT_RATIO);

        northPanel.setPreferredSize(new Dimension(0, northPanelHeight));
        southPanel.setPreferredSize(new Dimension(0, southPanelHeight));
    }

    // MODIFIES: this
    // EFFECTS: resizes titleLabel based on gameApp screen dimensions
    public void resizeTitleLabel() {
        final float FRAME_TO_TITLE_LABEL_HEIGHT_RATIO = 0.370f;
        final float TITLE_LABEL_HEIGHT_TO_WIDTH_RATIO = 1.25f;

        int titleLabelHeight = Math.round(this.getHeight() * FRAME_TO_TITLE_LABEL_HEIGHT_RATIO);
        int titleLabelWidth = Math.round(titleLabelHeight * TITLE_LABEL_HEIGHT_TO_WIDTH_RATIO);

        titleLabel.setPreferredSize(new Dimension(titleLabelWidth, titleLabelHeight));

        ImageIcon originalTitleIcon = new ImageIcon("src/resources/images/Game Title Icon.png");

        Image resizedTitleImage = originalTitleIcon.getImage().getScaledInstance(titleLabelWidth, titleLabelHeight,
                Image.SCALE_SMOOTH);

        titleLabel.setIcon(new ImageIcon(resizedTitleImage));
    }

    // MODIFIES: this
    // EFFECTS: resizes backButton based on gameApp screen dimensions
    public void resizeBackButton() {
        final float FRAME_TO_BACK_BUTTON_HEIGHT_RATIO = 0.0926f;

        int backButtonHeight = Math.round(this.getHeight() * FRAME_TO_BACK_BUTTON_HEIGHT_RATIO);
        int backButtonWidth = backButtonHeight;

        backButton.setPreferredSize(new Dimension(backButtonWidth, backButtonHeight));

        ImageIcon originalBackIcon = new ImageIcon("src/resources/images/Button.png");

        Image resizedBackImage = originalBackIcon.getImage().getScaledInstance(backButtonWidth, backButtonHeight,
                Image.SCALE_SMOOTH);

        backButton.setIcon(new ImageIcon(resizedBackImage));
    }

    // MODIFIES: this
    // EFFECTS: resizes main panels based on gameApp screen dimensions
    public void resizeMainPanels() {
        final float FRAME_TO_MENU_PANEL_HEIGHT_RATIO = 0.555f;
        final float MENU_PANEL_HEIGHT_TO_WIDTH_RATIO = 0.833f;

        final float FRAME_TO_IN_GAME_MENU_PANEL_HEIGHT_RATIO = 0.555f;
        final float FRAME_TO_IN_GAME_MENU_PANEL_WIDTH_RATIO = 0.364f;

        int screenHeight = this.getHeight();
        int screenWidth = this.getWidth();

        int inGameMenuPanelHeight = Math.round(this.getHeight() * FRAME_TO_IN_GAME_MENU_PANEL_HEIGHT_RATIO);
        int inGameMenuPanelWidth = Math.round(this.getWidth() * FRAME_TO_IN_GAME_MENU_PANEL_WIDTH_RATIO);

        int menuPanelHeight = Math.round(this.getHeight() * FRAME_TO_MENU_PANEL_HEIGHT_RATIO);
        int menuPanelWidth = Math.round(menuPanelHeight * MENU_PANEL_HEIGHT_TO_WIDTH_RATIO);

        frontMenuPanel.resizePanel(menuPanelWidth, menuPanelHeight);
        levelSelectMenuPanel.resizePanel(menuPanelWidth, menuPanelHeight);
        completedLevelSelectMenuPanel.resizePanel(menuPanelWidth, menuPanelHeight);
        levelHistoryViewPanel.resizePanel(menuPanelWidth, menuPanelHeight);
        mainScreenPanel.resizePanel(screenWidth, screenHeight);

        if (currentLevel != null) {
            gameplayPanel.resizePanel(screenWidth, screenHeight);
        }

        inGameMenuPanel.resizePanel(inGameMenuPanelWidth, inGameMenuPanelHeight);
    }

    @Override
    public void componentMoved(ComponentEvent e) {

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

    // EFFECTS: handles back button in main menu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            menuCardLayout.show(menuCardPanel, previousMenu);

            if (previousMenu.equals("Main Menu")) {
                disableBackButton();
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
    public void savePlayer() {
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
    public void loadPlayer() {
        try {
            player = jsonReader.read();
            // TODO: set player for all panels (if needed)
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
