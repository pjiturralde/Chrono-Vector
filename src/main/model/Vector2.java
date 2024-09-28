package model;

// A class representing a Vector object with two coordinates
public class Vector2 {
    private int pointX;
    private int pointY;

    // EFFECTS: constructs a Vector2 object
    public Vector2(int x, int y) {
        this.pointX = x;
        this.pointY = y;
    }

    // MODIFIES: this
    // EFFECTS: adds this Vector2 and other Vector2 together
    public void add(Vector2 other) {
        this.pointX += other.pointX;
        this.pointY += other.pointY;
    }

    // MODIFIES: this
    // EFFECTS: subtracts other Vector2 from this Vector2
    public void subtract(Vector2 other) {
        this.pointX -= other.pointX;
        this.pointY -= other.pointY;
    }
    
    // EFFECTS: returns if this Vector2 and other Vector2 have equal coordinates
    public boolean isEqual(Vector2 other) {
        return pointX == other.pointX && pointY == other.pointY;
    }

    // EFFECTS: returns a new Vector2 scaled by the given scalar value
    public void scale(int scalar) {
        this.pointX *= scalar;
        this.pointY *= scalar;
    }

    // MODIFIES: this
    // EFFECTS: sets vector points to given points
    public void setVector(int x, int y) {
        this.pointX = x;
        this.pointY = y;
    }

    // EFFECTS: returns posX
    public int getX() {
        return pointX;
    }

    // EFFECTS: returns posY
    public int getY() {
        return pointY;
    }
}
