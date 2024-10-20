package ui;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import model.Level;
import model.Projectile;
import model.Vector2;
import model.Wall;
import model.Player;
import model.LevelStats;

// Game application
public class GameApp {
    private LinkedList<Level> levels;
    private Level currentLevel;
    private ArrayList<AsciiObject> currentAsciiMap;
    private Player player;
    private Scanner input;
    private boolean inGame;

    // EFFECTS: runs the Game application
    public GameApp() {
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runGame() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            if (!inGame) {
                displayMenu();
                command = promptUser();

                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    processMenuCommand(command);
                }
            } else {
                displayCurrentAsciiMap();
                displayInGameMenu();
                command = promptUser();
                processGameCommand(command);
            }
        }

        System.out.println("\nExited application");
    }

    // EFFECTS: gets and returns user input
    private String promptUser() {
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        return command;
    }

    // MODIFIES: this
    // EFFECTS: processes user command for the main menu
    private void processMenuCommand(String command) {
        if (command.equals("p")) {
            displayLevels();
            command = promptUser();
            char charCommand = command.charAt(0);

            if (command.length() == 1 && Character.isDigit(charCommand) && Character.getNumericValue(charCommand) > 0
                    && Character.getNumericValue(charCommand) <= levels.size()) {
                int index = Character.getNumericValue(charCommand) - 1;

                currentLevel = levels.get(index);
                inGame = true;
                player.setPosition(currentLevel.getStartPosition());
                createCurrentAsciiMap();
            } else if (!command.equals("q")) {
                System.out.println("Selection not valid...");
            }
        } else if (command.equals("k")) {
            displayCompletedLevels();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays completed levels
    private void displayCompletedLevels() {
        if (player.getCompletedLevelStats().size() > 0) {
            for (LevelStats stats : player.getCompletedLevelStats()) {
                System.out.println(stats.getName() + ":");
                System.out.println("Least moves taken: " + stats.getLeastMovesTaken() + " moves");
                System.out.println("Least time taken: " + stats.getLeastTimeTaken() + " seconds\n");

            }
        } else {
            System.out.println("You have no completed levels");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for in game
    private void processGameCommand(String command) {
        if (command.equals("w")) {
            movePlayer(0, -1);
        } else if (command.equals("a")) {
            movePlayer(-1, 0);
        } else if (command.equals("s")) {
            movePlayer(0, 1);
        } else if (command.equals("d")) {
            movePlayer(1, 0);
        } else if (command.equals("q")) {
            inGame = false;
            currentLevel.reset();
            currentLevel = null;
        } else {
            System.out.println("Selection not valid...");
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
    //          and proceeds accordingly
    private void checkLostOrWon() {
        if (currentLevel.lost()) {
            inGame = false;

            displayCurrentAsciiMap();
            System.out.println("\nYou lost the level");

            currentLevel.reset();
            currentLevel = null;
        } else if (currentLevel.completed()) {
            currentLevel.endTime();
            if (player.hasCompletedLevel(currentLevel)) {
                player.updateCompletedLevelStats(currentLevel);
            } else {
                player.addCompletedLevelStats(currentLevel);
                System.out.println(currentLevel.getMovesTaken());
                System.out.println(currentLevel.getTimeTaken());
            }

            inGame = false;

            displayCurrentAsciiMap();
            System.out.println("Gratz you completed the level!");

            currentLevel.reset();
            currentLevel = null;
        }
    }

    // EFFECTS: displays in game menu to user
    private void displayInGameMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tw -> go up");
        System.out.println("\ta -> go left");
        System.out.println("\ts -> go down");
        System.out.println("\td -> go left");
        System.out.println("\tq -> go back to menu");
    }

    // EFFECTS: displays main menu to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tp -> play");
        System.out.println("\tk -> completed levels");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays level options to user
    private void displayLevels() {
        System.out.println("\nSelect a level");

        for (int i = 0; i < levels.size(); i++) {
            System.out.println((i + 1) + " -> " + levels.get(i).getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes game
    private void init() {
        this.currentAsciiMap = new ArrayList<AsciiObject>();
        this.levels = new LinkedList<Level>();
        this.input = new Scanner(System.in);
        this.inGame = false;
        this.player = new Player();

        constructAllLevels();
    }

    // MODIFIES: this
    // EFFECTS: constructs all levels
    private void constructAllLevels() {
        constructLevel1();
    }

    // MODIFIES: this
    // EFFECTS: constructs level 1
    private void constructLevel1() {
        Level level1 = new Level("level 1", 4, 9, 4, 1, 8, 10, 0, -1);
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

    // REQUIRES: createCurrentAsciiMap() must be called first
    // MODIFIES: this
    // EFFECTS: prints ascii art of level
    @SuppressWarnings("methodlength")
    private void displayCurrentAsciiMap() {
        displayLevelInformation();

        String mapRow = "";
        int prevPosX = -1;
        int prevPosY = 0;

        for (AsciiObject obj : currentAsciiMap) {
            int posX = obj.getPosition().getX();
            int posY = obj.getPosition().getY();
            String character = obj.getPosition().equals(player.getPosition()) ? " A" : " " + obj.getCharacter();

            if (posY > prevPosY) {
                prevPosY = posY;
                prevPosX = -1;

                System.out.println(mapRow);
                mapRow = "";
            }

            if (!character.equals("A") && player.getPosition().getY() == obj.getPosition().getY()
                    && player.getPosition().getX() < obj.getPosition().getX()
                    && player.getPosition().getX() > prevPosX) {
                int difference1 = player.getPosition().getX() - prevPosX;
                int difference2 = posX - player.getPosition().getX();
                mapRow += "  ".repeat(difference1 - 1) + " A" + "  ".repeat(difference2 - 1) + character;
            } else {
                int difference = posX - prevPosX;
                mapRow += "  ".repeat(difference - 1) + character;
            }
            prevPosX = posX;
            if (obj.equals(currentAsciiMap.get(currentAsciiMap.size() - 1))) {
                System.out.println(mapRow + "\n");
            }
        }
    }

    // EFFECTS: displays important level information
    private void displayLevelInformation() {
        System.out.println("\nPlayer = A\n");

        Vector2 timeDir = currentLevel.getTimeDirection();
        String timeDirString;
        if (timeDir.getX() == 0) {
            timeDirString = timeDir.getY() == 1 ? "down" : "up";
        } else {
            timeDirString = timeDir.getX() == 1 ? "right" : "left";
        }

        System.out.println("Time vector = " + timeDirString + "\n");
    }

    // REQUIRES: currentLevel != null
    // MODIFIES: this
    // EFFECTS: generates sorted list of AsciiObjects
    private void createCurrentAsciiMap() {
        currentAsciiMap.clear();

        currentAsciiMap.add(new AsciiObject("S", currentLevel.getStartPosition()));
        currentAsciiMap.add(new AsciiObject("E", currentLevel.getGoalPosition()));

        createProjectileAscii();
        createWallAscii();

        Collections.sort(currentAsciiMap);
    }

    // EFFECTS: adds Projectiles from currentLevel to currentAsciiMap as
    // AsciiObjects
    private void createProjectileAscii() {
        ArrayList<Projectile> projectiles = currentLevel.getProjectiles();
        Iterator<Projectile> iterator = projectiles.iterator();

        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();

            AsciiObject obj = new AsciiObject("O", projectile.getPosition());
            currentAsciiMap.add(obj);
        }
    }

    // EFFECTS: adds Walls from currentLevel to currentAsciiMap as AsciiObjects
    private void createWallAscii() {
        ArrayList<Wall> walls = currentLevel.getWalls();
        Iterator<Wall> iterator = walls.iterator();

        while (iterator.hasNext()) {
            Wall wall = iterator.next();

            int startX = wall.getStartPoint().getX() <= wall.getEndPoint().getX() ? wall.getStartPoint().getX()
                    : wall.getEndPoint().getX();
            int endX = wall.getStartPoint().getX() <= wall.getEndPoint().getX() ? wall.getEndPoint().getX()
                    : wall.getStartPoint().getX();

            for (int x = startX; x <= endX; x++) {
                int startY = wall.getStartPoint().getY() <= wall.getEndPoint().getY() ? wall.getStartPoint().getY()
                        : wall.getEndPoint().getY();
                int endY = wall.getStartPoint().getY() <= wall.getEndPoint().getY() ? wall.getEndPoint().getY()
                        : wall.getStartPoint().getY();

                for (int y = startY; y <= endY; y++) {
                    AsciiObject obj = new AsciiObject("#", new Vector2(x, y));
                    currentAsciiMap.add(obj);
                }
            }
        }
    }
}
