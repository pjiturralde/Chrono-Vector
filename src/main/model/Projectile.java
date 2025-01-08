package model;

// A class representing a projectile with a position, startPosition, endPosition, 
// and directionVector
public class Projectile {
    private Vector2 position;
    private Vector2 startPosition;
    private Vector2 endPosition;
    private Vector2 directionVector;

    // REQUIRES: dirX and dirY can't be zero at the same time and
    // either dirX or dirY has to be 0 or both need to have the same magnitude
    // range > 0
    // EFFECTS: constructs a Projectile object and calculates the endPosition
    public Projectile(int startPosX, int startPosY, int dirX, int dirY, int range) {
        this.position = new Vector2(startPosX, startPosY);
        this.startPosition = new Vector2(startPosX, startPosY);
        this.endPosition = new Vector2(startPosX + dirX * range, startPosY + dirY * range);
        this.directionVector = new Vector2(dirX, dirY);
    }

    // REQUIRES: dirX and dirY can't be zero at the same time and
    // either dirX or dirY has to be 0 or both need to have the same magnitude
    // range > 0
    // EFFECTS: constructs a Projectile object and calculates the endPosition
    public Projectile(Vector2 startPosition, Vector2 direction, int range) {
        this.position = new Vector2(startPosition.getX(), startPosition.getY());
        this.startPosition = new Vector2(startPosition.getX(), startPosition.getY());
        this.endPosition = new Vector2(startPosition.getX() + direction.getX() * range,
                startPosition.getY() + direction.getY() * range);
        this.directionVector = new Vector2(direction.getX(), direction.getY());
    }

    // REQUIRES: direction cannot be a zero vector
    // MODIFIES: this
    // EFFECTS: changes current direction of Projectile to the given direction
    public void changeDirection(int dirX, int dirY) {
        this.directionVector.setVector(dirX, dirY);
    }

    // MODIFIES: this
    // EFFECTS: moves Projectile forwards depending on direction and speed
    // if it hits a Wall then it will return to its start position
    public void moveForward() {
        position.add(directionVector);
        if (isPastEndOrStart()) {
            position.setVector(startPosition);
        }
    }

    // MODIFIES: this
    // EFFECTS: moves Projectile backwards depending on direction and speed
    // if it hits a Wall then it will return to its end position
    public void moveBackward() {
        position.subtract(directionVector);
        if (isPastEndOrStart()) {
            position.setVector(endPosition);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the Projectile's position to default
    public void reset() {
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
        return (position.moreThan(endPosition) && position.moreThan(startPosition))
                || (position.lessThan(endPosition) && position.lessThan(startPosition));
    }
}
