package model;

import java.util.Iterator;
import java.util.ArrayList;

// A class representing a Level object with name, levelLost, levelComplete, 
// size, timeDirection, projectileList, wallList, startPosition, movesTaken, timeStarted,
// timeTaken, leastMovesTaken, and leastTimeTaken components
public class Level {
    private String name;
    private boolean levelLost;
    private boolean levelComplete;
    private Vector2 size;
    private Vector2 timeDirection;
    private ArrayList<Projectile> projectileList;
    private ArrayList<Wall> wallList;
    private Vector2 startPosition;
    private Vector2 goalPosition;
    private int movesTaken;
    private long timeStarted;
    private double timeTaken;

    private int leastMovesTaken;
    private double leastTimeTaken;

    // REQUIRES: either timeDirX or timeDirY has to be 0 and
    // Vector2(timeDirX, timeDirY) needs to have a magnitude of exactly 1
    // eg. (0,1), (1,0), (-1, 0), ...
    // EFFECTS: constructs a Level object with an empty projectileList and empty
    // wallList
    public Level(String name, int startPosX, int startPosY, int goalPosX,
            int goalPosY, int sizeX, int sizeY, int timeDirX, int timeDirY) {
        this.name = name;
        this.startPosition = new Vector2(startPosX, startPosY);
        this.goalPosition = new Vector2(goalPosX, goalPosY);
        this.timeDirection = new Vector2(timeDirX, timeDirY);
        this.size = new Vector2(sizeX, sizeY);
        this.projectileList = new ArrayList<Projectile>();
        this.wallList = new ArrayList<Wall>();
        this.levelLost = false;
        this.levelComplete = false;
        this.movesTaken = 0;
        this.timeStarted = 0;
        this.timeTaken = 0;
        this.leastMovesTaken = -1;
        this.leastTimeTaken = -1;
    }

    // REQUIRES: position of Projectile must be in bounds of the Level size
    // MODIFIES: this
    // EFFECT: adds Projectile object to list of Projectiles
    public void addProjectile(Projectile p) {
        projectileList.add(p);
    }

    // REQUIRES: position of Projectile must be in bounds of the Level size
    // MODIFIES: this
    // EFFECT: adds Projectile object to list of Projectiles
    public void addProjectile(int startPosX, int startPosY, int dirX, int dirY, int range, boolean isBouncy) {
        Projectile p = new Projectile(startPosX, startPosY, dirX, dirY, range, isBouncy);
        projectileList.add(p);
    }

    // REQUIRES: position of Wall must be in bounds of the Level size
    // MODIFIES: this
    // EFFECT: adds Wall object to list of Walls
    public void addWall(Wall w) {
        wallList.add(w);
    }

    // REQUIRES: either moveDirX or moveDirY has to be 0 and
    // Vector2(timeDirX, timeDirY) needs to have a magnitude of exactly 1
    // eg. (0,1), (1,0), (-1, 0), ...
    // EFFECTS: checks if player moves in the same direction as timeDirection
    public void checkPlayerMovement(int moveDirX, int moveDirY) {
        Vector2 playerMovementDir = new Vector2(moveDirX, moveDirY);
        Vector2 oppositeTimeDir = Vector2.scale(timeDirection, -1);

        if (playerMovementDir.equals(timeDirection)) {
            moveAllProjectiles(1);
        } else if (playerMovementDir.equals(oppositeTimeDir)) {
            moveAllProjectiles(-1);
        }
    }

    // REQUIRES: timeScale must be either 1, or -1
    // MODIFIES: this
    // EFFECTS: calls either moveForward or moveBackward on every projectile based
    // on given timeScale
    public void moveAllProjectiles(int timeScale) {
        for (Projectile projectile : projectileList) {
            if (timeScale == 1) {
                projectile.moveForward();
            } else {
                projectile.moveBackward();
            }
        }
    }

    // REQUIRES: moveDirX and moveDirY can't both be zero and as a vector has to
    // take the form
    // (0, 1), (1, 0), (0, -1), or (-1, 0)
    // MODIFIES: Player p, this
    // EFFECTS: checks if given player collided with anything in the Level
    public void checkCollision(Player p, int moveDirX, int moveDirY) {
        checkWallCollision(p, moveDirX, moveDirY);
        checkProjectileCollision(p, moveDirX, moveDirY);

        if (p.getPosition().equals(goalPosition)) {
            levelComplete = true;
        }
    }

    // REQUIRES: moveDirX and moveDirY can't both be zero and as a vector has to
    // take the form
    // (0, 1), (1, 0), (0, -1), or (-1, 0)
    // MODIFIES: Player p, this
    // EFFECTS: if player collides with wall move him back once and move projectiles
    // back and decrement movesTaken
    private void checkWallCollision(Player p, int moveDirX, int moveDirY) {
        Iterator<Wall> iterator = wallList.iterator();
        boolean hasHitWall = false;

        while (iterator.hasNext() && !hasHitWall) {
            Wall wall = iterator.next();
            Vector2 wallStartPoint = wall.getStartPoint();
            Vector2 wallEndPoint = wall.getEndPoint();

            if ((p.getPosition().lessThanEqualTo(wallStartPoint)
                    && p.getPosition().moreThanEqualTo(wallEndPoint))
                    || (p.getPosition().lessThanEqualTo(wallEndPoint)
                            && p.getPosition().moreThanEqualTo(wallStartPoint))) {
                p.move(-moveDirX, -moveDirY);

                Vector2 playerMovementDir = new Vector2(moveDirX, moveDirY);

                if (playerMovementDir.equals(timeDirection)) {
                    moveAllProjectiles(-1);
                } else if (playerMovementDir.equals(Vector2.scale(timeDirection, -1))) {
                    moveAllProjectiles(1);
                }

                movesTaken--;
                hasHitWall = true;
            }
        }
    }

    // REQUIRES: moveDirX and moveDirY can't both be zero and as a vector has to
    // take the form
    // (0, 1), (1, 0), (0, -1), or (-1, 0)
    // MODIFIES: Player p, this
    // EFFECTS: if player collides with projectile sets levelLost to true
    private void checkProjectileCollision(Player p, int moveDirX, int moveDirY) {
        Iterator<Projectile> iterator = projectileList.iterator();
        boolean hasHitProjectile = false;

        while (iterator.hasNext() && !hasHitProjectile) {
            Projectile projectile = iterator.next();
            Vector2 projectilePos = projectile.getPosition();

            if (p.getPosition().equals(projectilePos)) {
                levelLost = true;
                hasHitProjectile = true;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the Level
    public void reset() {
        for (Projectile projectile : projectileList) {
            projectile.reset();
        }

        levelLost = false;
        levelComplete = false;

        levelLost = false;

        movesTaken = 0;
        timeStarted = 0;
        timeTaken = 0;
    }

    // REQUIRES: endTimer() must be called before
    // EFFECTS: updates the high score
    public void updateHighScore() {
        if (movesTaken < leastMovesTaken || leastMovesTaken < 0) {
            leastMovesTaken = movesTaken;
        }
        if (timeTaken < leastTimeTaken || leastTimeTaken < 0) {
            leastTimeTaken = timeTaken;
        }
    }

    // EFFECTS: increases movesTaken by 1
    public void updateMovesTaken() {
        movesTaken++;
    }

    // EFFECTS: starts stopwatch via timeStarted
    public void startTime() {
        timeStarted = System.nanoTime();
    }

    // EFFECTS: ends stopwatch and sets it to timeTaken
    public void endTime() {
        timeTaken = (double) (System.nanoTime() - timeStarted) / 1000000000;
    }

    // EFFECTS: returns number of moves taken in Level
    public int getMovesTaken() {
        return movesTaken;
    }

    // EFFECTS: returns leastTimeTaken rounded to 2 decimal places
    public double getLeastTimeTaken() {
        return (double) Math.round(leastTimeTaken * 100) / 100;
    }

    // EFFECTS: returns leastMovesTaken
    public int getLeastMovesTaken() {
        return leastMovesTaken;
    }

    // EFFECTS: returns Level's list of Projectiles
    public ArrayList<Projectile> getProjectiles() {
        return projectileList;
    }

    // EFFECTS: returns Level's list of Walls
    public ArrayList<Wall> getWalls() {
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

    // EFFECTS: returns Level's time direction
    public Vector2 getTimeDirection() {
        return timeDirection;
    }

    // EFFECTS: returns if the Level has been lost
    public boolean lost() {
        return levelLost;
    }
    // EFFECTS: returns if the Level has been completed
    public boolean completed() {
        return levelComplete;
    }

    // EFFECTS: returns Level's size
    public Vector2 getSize() {
        return size;
    }

    // EFFECTS: returns Level's name
    public String getName() {
        return name;
    }
}