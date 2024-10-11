package ui;

import model.Vector2;

// A class representing an ASCIIObject with character and position
public class AsciiObject implements Comparable<AsciiObject> {
    private String character;
    private Vector2 position;

    // EFFECTS: constructs an ASCIIObject
    public AsciiObject(String character, Vector2 position) {
        this.character = character;
        this.position = position;
    }

    // EFFECTS: returns ASCIIObject's character
    public String getCharacter() {
        return character;
    }

    // EFFECTS: returns ASCIIObject's position
    public Vector2 getPosition() {
        return position;
    }

    // EFFECTS: compares the position components between this and the other given ASCIIObject
    @Override
    public int compareTo(AsciiObject other) {
        int posY = position.getY();
        int otherPosY = other.position.getY();
        int comparisonY = Integer.compare(posY, otherPosY);

        if (comparisonY != 0) {
            return comparisonY;
        }

        int posX = position.getX();
        int otherPosX = other.position.getX();
        int comparisonX = Integer.compare(posX, otherPosX);

        return comparisonX;
    }
}
