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
        v1.Add(v2);
        assertEquals(v1.getX(), 1);
        assertEquals(v1.getY(), 2);
    }

    @Test
    void subtractionTest() {
        v1.Subtract(v2);
        assertEquals(v1.getX(), -1);
        assertEquals(v1.getY(), -2);
    }

    @Test
    void isEqualTest() {
        boolean isEqual = v1.isEqual(v3);
        assertTrue(isEqual);

        isEqual = v1.isEqual(v2);
        assertFalse(isEqual);
    }

    @Test
    void scaleTest() {
        v2.Scale(2);
        assertEquals(v2.getX(), 2);
        assertEquals(v2.getY(), 4);
    }
}
