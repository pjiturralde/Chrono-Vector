package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;

// A class representing a Level object
public class Level {
    private String name;
    private boolean levelLost;
    private Vector2 timeDirection;
    private LinkedList<Projectile> projectileList;
    private ArrayList<Wall> wallList;
    private Vector2 startPosition;
    private Vector2 goalPosition;

    // REQUIRES: either timeDirX or timeDirY has to be 0 and
    //           Vector2(timeDirX, timeDirY) needs to have a magnitude of exactly 1
    //           eg. (0,1), (1,0), (-1, 0), ...
    // EFFECTS: constructs a Level object with an empty projectileList and empty wallList
    public Level(String name, int startPosX, int startPosY, int goalPosX, int goalPosY, int timeDirX, int timeDirY) {
        this.name = name;
        this.startPosition = new Vector2(startPosX, startPosY);
        this.goalPosition = new Vector2(goalPosX, goalPosY);
        this.timeDirection = new Vector2(timeDirX, timeDirY);
        this.projectileList = new LinkedList<Projectile>();
        this.wallList = new ArrayList<Wall>();
        this.levelLost = false;
    }

    // MODIFIES: this
    // EFFECT: adds Projectile object to list of Projectiles
    public void addProjectile(Projectile p) {
        projectileList.addLast(p);
    }

    // EFFECT: adds Projectile object to list of Projectiles
    public void addProjectile(int startPosX, int startPosY, int dirX, int dirY, int range, boolean isBouncy) {
        projectileList.addLast(new Projectile(startPosX, startPosY, dirX, dirY, range, isBouncy));
    }

    // MODIFIES: this
    // EFFECT: adds Wall object to list of Walls
    public void addWall(Wall w) {
        wallList.addLast(w);
    }

    // REQUIRES: either moveDirX or moveDirY has to be 0 and
    //           Vector2(timeDirX, timeDirY) needs to have a magnitude of exactly 1
    //           eg. (0,1), (1,0), (-1, 0), ...
    // EFFECTS: checks if player moves in the same direction as timeDirection
    public void checkPlayerMovement(int moveDirX, int moveDirY) {
        Vector2 playerMovementDir = new Vector2(moveDirX, moveDirY);
        Vector2 oppositeTimeDir = Vector2.scale(timeDirection, -1);

        if (playerMovementDir.isEqualTo(timeDirection)) {
            moveAllProjectiles(1);
        } else if (playerMovementDir.isEqualTo(oppositeTimeDir)) {
            moveAllProjectiles(-1);
        }
    }

    // REQUIRES: timeScale must be either 1, or -1
    // MODIFIES: this
    // EFFECTS: calls either moveForward or moveBackward on every projectile based on given timeScale
    public void moveAllProjectiles(int timeScale) {
        Iterator<Projectile> iterator = projectileList.iterator();

        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();

            if (timeScale == 1) {
                projectile.moveForward();
            } else {
                projectile.moveBackward();
            }   
        }
    }

    // REQUIRES: moveDirX and moveDirY can't both be zero and as a vector has to take the form
    //           (0, 1), (1, 0), (0, -1), or (-1, 0)
    // MODIFIES: Player p
    // EFFECTS: checks if given player collides with anything in the Level
    //          if player collides with wall move him backo nce
    //          if player collides with projectile its a loss
    public void checkCollision(Player p, int moveDirX, int moveDirY) {
        Iterator<Wall> iterator1 = wallList.iterator();

        boolean hasHitWall = false;

        while (iterator1.hasNext() && !hasHitWall) {
            Wall wall = iterator1.next();

            Vector2 wallStartPoint = wall.getStartPoint();  
            Vector2 wallEndPoint = wall.getEndPoint();

            if ((p.getPosition().isLessThanEqualTo(wallStartPoint) && 
                p.getPosition().isMoreThanEqualTo(wallEndPoint)) || 
                (p.getPosition().isLessThanEqualTo(wallEndPoint) && 
                p.getPosition().isMoreThanEqualTo(wallStartPoint))) {
                p.move(-moveDirX, -moveDirY);
                hasHitWall = true;
            }
        }
        
        Iterator<Projectile> iterator2 = projectileList.iterator();

        boolean hasHitProjectile = false;

        while (iterator2.hasNext() && !hasHitProjectile) {
            Projectile projectile = iterator2.next();

            Vector2 projectilePos = projectile.getPosition();

            if (p.getPosition().isEqualTo(projectilePos)) {
                levelLost = true;
                hasHitProjectile = true;
            }
        }
        // come back to this method
    }

    // MODIFIES: this
    // EFFECTS: resets the Level
    public void reset() {
        Iterator<Projectile> iterator = projectileList.iterator();

        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.reset();
        }
        // don't forget once pressure plates added to reset them as well
    }

    // EFFECTS: returns Level's list of Projectiles
    public LinkedList<Projectile> getProjectiles() {
        return projectileList;
    }

    // EFFECTS: returns Level's list of Walls
    public ArrayList<Wall>  getWalls() {
        return wallList;
    }

    // EFFECTS: returns Level's start position
    public Vector2 getStartPosition() {
        return startPosition;
    }

    // EFFECTS: returns Level's goal position
    public Vector2 getGoalPosition() {
        return goalPosition;
    }

    // EFFECTS: returns if the Level has been lost
    public boolean isLevelLost() {
        return levelLost;
    }
}