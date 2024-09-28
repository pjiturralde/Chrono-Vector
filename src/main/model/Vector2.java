package model;

// A class representing a Vector object with two coordinates
public class Vector2 {
    private int posX;
    private int posY;

    // EFFECTS: constructs a Vector2 object
    public Vector2(int posX, int posY) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds this Vector2 and other Vector2 together
    public void Add(Vector2 other) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: subtracts other Vector2 from this Vector2
    public void Subtract(Vector2 other) {
        // stub
    }
    
    // EFFECTS: returns if this Vector2 and other Vector2 have equal coordinates
    public boolean isEqual(Vector2 other) {
        return false; // stub
    }

    // EFFECTS: returns a new Vector2 scaled by the given scalar value
    public Vector2 Scale(int scalar) {
        return null; // stub
    }

    // EFFECTS: returns posX
    public int getX() {
        return -1; // stub
    }

    // EFFECTS: returns posY
    public int getY() {
        return -1; // stub
    }
}
