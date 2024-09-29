package model;

import java.util.LinkedList;
import java.util.ArrayList;

// A class representing a Level object
public class Level {
    private String name;
    private Vector2 timeDirection;
    private LinkedList<Projectile> projectileList;
    private ArrayList<Wall> wallList;

    // REQUIRES: either timeDirX or timeDirY has to be 0 and
    //           Vector2(timeDirX, timeDirY) needs to have a magnitude of exactly 1
    //           eg. (0,1), (1,0), (-1, 0), ...
    // EFFECTS: constructs a Level object with an empty projectileList and empty wallList
    public Level(String name, int timeDirX, int timeDirY) {
        // stub
    }

    // REQUIRES: either moveDirX or moveDirY has to be 0 and
    //           Vector2(timeDirX, timeDirY) needs to have a magnitude of exactly 1
    //           eg. (0,1), (1,0), (-1, 0), ...
    // EFFECTS: checks if player moves in the same direction as timeDirection
    public void checkPlayerMovement(int moveDirX, int moveDirY) {
        // stub
    }

    // REQUIRES: timeScale must be either 1, 0, or -1
    // EFFECTS: calls either moveForward or moveBackward on every projectile based on given timeScale
    // MODIFIES: this
    public void moveAllProjectiles(int timeScale) {
        // stub
    }
}
