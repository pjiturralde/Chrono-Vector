package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Vector2Test {
    private Vector2 v1;
    private Vector2 v2;
    private Vector2 v3;

    @BeforeEach
    void runBefore() {
        v1 = new Vector2(0, 0);
        v2 = new Vector2(1, 2);
        v3 = new Vector2(0, 0);
    }

    @Test
    void additionTest() {
        v1.add(v2);
        assertEquals(v1.getX(), 1);
        assertEquals(v1.getY(), 2);
    }

    @Test
    void setVectorTest() {
        v1.setVector(2, 2);
        assertEquals(v1.getX(), 2);
        assertEquals(v1.getY(), 2);

        Vector2 vector = new Vector2(0, 0);

        v1.setVector(vector);
        assertEquals(v1.getX(), 0);
        assertEquals(v1.getY(), 0);
    }

    @Test
    void subtractionTest() {
        v1.subtract(v2);
        assertEquals(v1.getX(), -1);
        assertEquals(v1.getY(), -2);
    }

    @Test
    void equalsTest() {
        boolean isEqual = v1.equals(v3);
        assertTrue(isEqual);

        isEqual = v1.equals(v2);
        assertFalse(isEqual);

        isEqual = v1.equals(v1);
        assertTrue(isEqual);

        Level level = new Level(null, 0, 0, 3, 2, 100, 100, 0, 1);
        isEqual = v1.equals(level);
        assertFalse(isEqual);
    }

    @Test
    void rescaleTest() {
        v2.rescale(2);
        assertEquals(v2.getX(), 2);
        assertEquals(v2.getY(), 4);
    }

    @Test
    void scaleTest() {
        Vector2 newVector = Vector2.scale(v2, 2);
        assertEquals(newVector.getX(), 2);
        assertEquals(newVector.getY(), 4);
    }
}
