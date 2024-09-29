package model;

public class Wall {
    private Vector2 size;
    private Vector2 position;

    // EFFECTS: constructs a Wall object
    public Wall(int posX, int posY, int sizeX, int sizeY) {
        this.position = new Vector2(posX, posY);
        this.size = new Vector2(sizeX, sizeY);
    }

    // EFFECTS: returns size vector
    public Vector2 getSize() {
        return size; // stub
    }

    // EFFECTS: returns position vector
    public Vector2 getPosition() {
        return position; // stub
    }
}
