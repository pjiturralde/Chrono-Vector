package model;

// A class representing a LevelStats object with leastMovesTaken, 
// leastTimeTaken, and levelNum components
public class LevelStats {

    // EFFECTS: constructs a LevelStats object
    public LevelStats(String name, int leastMovesTaken, double leastTimeTaken) {
        // stub
    }

    // EFFECTS: constructs a LevelStats object with
    // negative leastTimeTaken and negative levelNum
    public LevelStats(String name) {
        // stub
    }

    // REQUIRES: Level name must be the same as LevelStats name
    // MODIFIES: this
    // EFFECTS: compares Level movesTaken and timeTaken and updates if
    // either are less than their LevelStats counterpart
    public void update(Level level) {
        // stub
    }

    // EFFECTS: returns LevelStats' name
    public String getName() {
        return null; // stub
    }

    // EFFECTS: returns LevelStats' leastMovesTaken
    public int getLeastMovesTaken() {
        return -1; // stub
    }

    // EFFECTS: returns LevelStats' leastTimeTaken
    public double getLeastTimeTaken() {
        return -1; // stub
    }
}
