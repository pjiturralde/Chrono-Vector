package model;

// A class representing a Player object
public class Player {
    private Vector2 position;

    // EFFECTS: constructs a Player object
    public Player(int posX, int posY) {
        this.position = new Vector2(posX, posY);
    }

    // REQUIRES: (dirX, dirY) must be normalized eg. (1,0), (0,1), (-1, 0), or (0,-1)
    // MODIFIES: this
    // EFFECTS: moves player one cell in the given direction
    public void move(int dirX, int dirY) {
        this.position.add(new Vector2(dirX, dirY));
    }

    // EFFECTS: returns Player's position
    public Vector2 getPosition() {
        return position;
    }
}
