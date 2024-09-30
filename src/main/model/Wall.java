package model;

// A class representing a Wall object with size and position vectors
public class Wall {
    private Vector2 startPoint;
    private Vector2 endPoint;

    // EFFECTS: constructs a Wall object
    public Wall(int startPosX, int startPosY, int endPosX, int endPosY) {
        this.startPoint = new Vector2(startPosX, startPosY);
        this.endPoint = new Vector2(endPosX, endPosY);
    }

    // EFFECTS: returns start point vector
    public Vector2 getStartPoint() {
        return startPoint;
    }

    // EFFECTS: returns end point vector
    public Vector2 getEndPoint() {
        return endPoint;
    }
}
