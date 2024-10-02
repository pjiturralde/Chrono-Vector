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
    public boolean isEqualTo(Vector2 other) {
        return pointX == other.pointX && pointY == other.pointY;
    }

    // EFFECTS: returns if pointX > other.pointX or pointY > other.pointY
    public boolean isMoreThan(Vector2 other) {
        return this.pointX > other.pointX || this.pointY > other.pointY;
    }

    // EFFECTS: returns if pointX < other.pointX or pointY < other.pointY
    public boolean isLessThan(Vector2 other) {
        return this.pointX < other.pointX || this.pointY < other.pointY;
    }

    // EFFECTS: returns if pointX >= other.pointX and pointY >= other.pointY
    public boolean isMoreThanEqualTo(Vector2 other) {
        return this.pointX >= other.pointX && this.pointY >= other.pointY;
    }

    // EFFECTS: returns if pointX <= other.pointX and pointY <= other.pointY
    public boolean isLessThanEqualTo(Vector2 other) {
        return this.pointX <= other.pointX && this.pointY <= other.pointY;
    }

    // MODIFIES: this
    // EFFECTS: rescales Vector by given scalar value
    public void rescale(int scalar) {
        this.pointX *= scalar;
        this.pointY *= scalar;
    }

    // MODIFIES: this
    // EFFECTS: sets vector points to given points
    public void setVector(int x, int y) {
        this.pointX = x;
        this.pointY = y;
    }

    // MODIFIES: this
    // EFFECTS: sets vector to given vector
    public void setVector(Vector2 other) {
        this.pointX = other.pointX;
        this.pointY = other.pointY;
    }

    // EFFECTS: returns posX
    public int getX() {
        return pointX;
    }

    // EFFECTS: returns posY
    public int getY() {
        return pointY;
    }

    // EFFECTS: returns a new Vector2 scaled by the given scalar value
    public static Vector2 scale(Vector2 v, int scalar) {
        return new Vector2(v.getX() * scalar, v.getY() * scalar);
    }
}
