package model;
import java.util.LinkedList;

// A class representing a Player object with a position and completedLevels list
public class Player {
    private Vector2 position;
    private LinkedList<Level> completedLevels;

    // EFFECTS: constructs a Player object
    public Player(int posX, int posY) {
        this.position = new Vector2(posX, posY);
        this.completedLevels = new LinkedList<Level>();
    }

    // EFFECTS: constructs a Player object
    public Player() {
        this(0, 0);
    }

    // REQUIRES: (dirX, dirY) must be normalized eg. (1,0), (0,1), (-1, 0), or (0,-1)
    // MODIFIES: this
    // EFFECTS: moves player one cell in the given direction
    public void move(int dirX, int dirY) {
        this.position.add(new Vector2(dirX, dirY));
    }

    // EFFECTS: returns Player's position
    public Vector2 getPosition() {
        return position;
    }

    // EFFECTS: sets Player's position
    public void setPosition(Vector2 position) {
        this.position.setVector(position);
    }

    // EFFECTS: returns Player's completed Levels
    public LinkedList<Level> getCompletedLevels() {
        return completedLevels;
    }

    // EFFECTS: adds a completed level to list of completed levels
    public void addCompletedLevel(Level level) {
        completedLevels.add(level);
    }

    // EFFECTS: returns if Player has completed level
    public boolean hasCompletedLevel(Level level) {
        return completedLevels.contains(level);
    }
}
