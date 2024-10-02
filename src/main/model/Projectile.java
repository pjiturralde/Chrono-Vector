package model;

// A class representing a projectile with a position, direction, default speed, and speed
public class Projectile {
    private Vector2 position;
    private Vector2 startPosition;
    private Vector2 endPosition;
    private Vector2 directionVector;
    private Vector2 startDirection;
    private boolean isBouncy;

    // REQUIRES: dirX and dirY can't be zero at the same time and
    //           either dirX or dirY has to be 0 or both need to have the same magnitude
    //           range > 0
    // EFFECTS: constructs a Projectile object and calculates the endPosition
    public Projectile(int startPosX, int startPosY, int dirX, int dirY, int range, boolean isBouncy) {
        this.position = new Vector2(startPosX, startPosY);
        this.startPosition = new Vector2(startPosX, startPosY);
        this.endPosition = new Vector2(startPosX + dirX * range, startPosY + dirY * range);
        this.directionVector = new Vector2(dirX, dirY);
        this.startDirection = new Vector2(dirX, dirY);
        this.isBouncy = isBouncy;
    }

    // REQUIRES: dirX and dirY can't be zero at the same time and
    //           either dirX or dirY has to be 0 or both need to have the same magnitude
    //           range > 0
    // EFFECTS: constructs a Projectile object, calculates the endPosition and sets isBouncy to false
    public Projectile(int startPosX, int startPosY, int dirX, int dirY, int range) {
        this(startPosX, startPosY, dirX, dirY, range, false);
    }

    // REQUIRES: direction cannot be a zero vector
    // MODIFIES: this
    // EFFECTS: changes current direction of Projectile to the given direction
    public void changeDirection(int dirX, int dirY) {
        this.directionVector.setVector(dirX, dirY);
    }

    // MODIFIES: this
    // EFFECTS: moves Projectile forwards depending on direction and speed
    //          if isBouncy then Projectile will bounce when it goes past its endpoint and
    //          if not isBouncy then it will return to its start position
    public void moveForward() {
        position.add(directionVector);
        if (isPastEndOrStart()) {
            if (isBouncy) {
                directionVector.rescale(-1);
                position.add(Vector2.scale(directionVector, 2));
            } else {
                position.setVector(startPosition);
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: moves Projectile backwards depending on direction and speed
    //          if isBouncy then Projectile will bounce when it goes past its startpoint and
    //          if not isBouncy then it will return to its end position
    public void moveBackward() {
        position.subtract(directionVector);
        if (isPastEndOrStart()) {
            if (isBouncy) {
                directionVector.rescale(-1);
                position.subtract(Vector2.scale(directionVector, 2));
            } else {
                position.setVector(endPosition);
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: sets the Projectile's position and direction to default
    public void reset() {
        this.directionVector.setVector(startDirection);
        this.position.setVector(startPosition);
    }

    // EFFECTS: returns the Projectile's position
    public Vector2 getPosition() {
        return position;
    }

    // EFFECTS: returns the Projectile's direction vector
    public Vector2 getDirection() {
        return directionVector;
    }

    // MODIFIES: this
    // EFFECTS: returns if Projectile is past end or start position
    private boolean isPastEndOrStart() {
        return (position.isMoreThan(endPosition) && position.isMoreThan(startPosition)) || (position.isLessThan(endPosition) && position.isLessThan(startPosition));
    }
}
