package model;

// An abstract class representing a PressurePlate object with position, isPressed bool, and stored steps
public abstract class PressurePlate {
    private Vector2 position;
    private boolean isPressed;
    private int storedSteps;

    // EFFECTS: constructs an PressurePlate object
    public PressurePlate(Vector2 position) {
        this.position = position;
        this.isPressed = false;
        this.storedSteps = 0;
    }

    // MODIFES: this
    // EFFECTS: increments storedSteps by 1
    public void incrementSteps() {
        storedSteps++;
    }

    // MODIFIES: this
    // EFFECTS: decrements storedSteps by 1
    // if storedSteps is less than 0 then undo the effects of the PressurePlate
    public void decrementSteps() {
        storedSteps--;
        if (storedSteps < 0) {
            undoEffects();
        }
    }

    // EFFECTS: returns position
    public Vector2 getPosition() {
        return position;
    }

    // EFFECTS: returns isPressed
    public boolean isPressed() {
        return isPressed;
    }

    // MODIFIES: this
    // EFFECTS: activates PressurePlate's effects
    public void activateEffects() {
        isPressed = true;
    }

    // MODIFIES: this
    // EFFECTS: undoes PressurePlate's effects
    protected void undoEffects() {
        isPressed = false;
        storedSteps = 0;
    }
}
