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

    // MODIFIES: this
    // EFFECT: adds Projectile object to list of Projectiles
    public void addProjectile(Projectile p) {
        // stub
    }

    // MODIFIES: this
    // EFFECT: adds Wall object to list of Walls
    public void addWall(Wall w) {
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
    // MODIFIES: this
    // EFFECTS: calls either moveForward or moveBackward on every projectile based on given timeScale
    private void moveAllProjectiles(int timeScale) {
        // stub
    }

    // EFFECTS: checks if given player collides with anything in the Level
    //          if player collides with wall move him backo nce
    //          if player collides with projectile its a loss
    public void checkCollision(Player p, int moveDirX, int moveDirY) {
        // stub
    }

    // EFFECTS: returns Level's list of Projectiles
    public LinkedList<Projectile> getProjectiles() {
        return null; // stub
    }

    // EFFECTS: returns Level's list of Walls
    public ArrayList<Wall>  getWalls() {
        return null; // stub
    }
}
