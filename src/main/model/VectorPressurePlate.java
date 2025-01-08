package model;

// A class representing a VectorPressurePlate object that extends PressurePlate with storedTimeDirection, previousTimeDirection, and level
// If pressed it will change the given Level's timeDirection to the storedTimeDirection
public class VectorPressurePlate extends PressurePlate {
    private Vector2 storedTimeDirection;
    private Vector2 previousTimeDirection;
    private Level level;

    // EFFECTS: constructs a VectorPressurePlate object
    public VectorPressurePlate(Vector2 position, Vector2 storedTimeDirection, Level level) {
        super(position);
        this.storedTimeDirection = new Vector2(storedTimeDirection.getX(), storedTimeDirection.getY());
        this.level = level;
    }

    // REQUIRES: isPressed() == false
    // MODIFIES: this, level
    // EFFECTS: sets level's timeDirection to storedTimeDirection and stores old
    // timeDirection in previousTimeDirection
    @Override
    public void activateEffects() {
        super.activateEffects();
        previousTimeDirection = level.getTimeDirection();
        level.setTimeDirection(storedTimeDirection);
    }

    // REQUIRES: isPressed() == true
    // MODIFIES: this, level
    // EFFECTS: undoes the effects of the pressure plate when pressed
    @Override
    protected void undoEffects() {
        super.undoEffects();
        level.setTimeDirection(previousTimeDirection);
    }
}
