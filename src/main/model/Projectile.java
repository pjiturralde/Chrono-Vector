package model;

// A class representing a projectile with a position, direction, default speed, and speed
public class Projectile {
    private Vector2 cellPosition;
    private Vector2 directionVector;

    // REQUIRES: dirX and dirY can't be zero at the same time
    // EFFECTS: constructs a Projectile object
    public Projectile(int posX, int posY, int dirX, int dirY) {
        // stub
    }

    // REQUIRES: direction cannot be a zero vector
    // MODIFIES: this
    // EFFECTS: changes current direction of Projectile to the given direction
    public void changeDirection(Vector2 direction) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: moves projectile forwards depending on direction and speed
    public void moveForward() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: moves projectile backwards depending on direction and speed
    public void moveBackward() {
        // stub
    }

    // EFFECTS: returns the Projectile's position
    public Vector2 getPosition() {
        return null; // stub
    }
}
