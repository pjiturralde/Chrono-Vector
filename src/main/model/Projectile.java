package model;

// A class representing a projectile with a position, direction, default speed, and speed
public class Projectile {
    private Vector2 cellPosition;
    private Vector2 directionVector;

    // REQUIRES: dirX and dirY can't be zero at the same time
    // EFFECTS: constructs a Projectile object
    public Projectile(int posX, int posY, int dirX, int dirY) {
        this.cellPosition = new Vector2(posX, posY);
        this.directionVector = new Vector2(dirX, dirY);
    }

    // REQUIRES: direction cannot be a zero vector
    // MODIFIES: this
    // EFFECTS: changes current direction of Projectile to the given direction
    public void changeDirection(int dirX, int dirY) {
        this.directionVector = new Vector2(dirX, dirY);
    }

    // MODIFIES: this
    // EFFECTS: moves projectile forwards depending on direction and speed
    public void moveForward() {
        cellPosition.add(directionVector);
    }

    // MODIFIES: this
    // EFFECTS: moves projectile backwards depending on direction and speed
    public void moveBackward() {
        cellPosition.subtract(directionVector);
    }

    // EFFECTS: returns the Projectile's position
    public Vector2 getPosition() {
        return cellPosition;
    }

    // EFFECTS: returns the Projectile's direction vector
    public Vector2 getDirection() {
        return directionVector;
    }
    
    // for a linear projectile you should specify a start and end point.

    // for a bouncing projectile no need.
}
