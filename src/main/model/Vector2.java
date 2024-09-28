package model;

// A class representing a Vector object with two coordinates
public class Vector2 {
    private int posX;
    private int posY;

    // EFFECTS: constructs a Vector2 object
    public Vector2(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    // MODIFIES: this
    // EFFECTS: adds this Vector2 and other Vector2 together
    public void Add(Vector2 other) {
        this.posX += other.posX;
        this.posY += other.posY;
    }

    // MODIFIES: this
    // EFFECTS: subtracts other Vector2 from this Vector2
    public void Subtract(Vector2 other) {
        this.posX -= other.posX;
        this.posY -= other.posY;
    }
    
    // EFFECTS: returns if this Vector2 and other Vector2 have equal coordinates
    public boolean isEqual(Vector2 other) {
        return posX == other.posX && posY == other.posY;
    }

    // EFFECTS: returns a new Vector2 scaled by the given scalar value
    public void Scale(int scalar) {
        this.posX *= scalar;
        this.posY *= scalar;
    }

    // EFFECTS: returns posX
    public int getX() {
        return posX;
    }

    // EFFECTS: returns posY
    public int getY() {
        return posY;
    }
}
