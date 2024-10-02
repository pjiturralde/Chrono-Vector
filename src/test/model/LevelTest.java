package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.ArrayList;

public class LevelTest {
    private Level l1;
    private Level l2;

    @BeforeEach
    void runBefore() {
        l1 = new Level("level X", 1, 0);
        l2 = new Level("level Y", 0, -1);
    }

    @Test
    void addProjectileTest() {
        Projectile p1 = new Projectile(0, 0, 1, 0, 1);
        Projectile p2 = new Projectile(2, 3, 1, 1, 1);

        l1.addProjectile(p1);
        l1.addProjectile(p2);

        LinkedList<Projectile> projectileList = l1.getProjectiles();
        assertEquals(projectileList.getFirst(), p1);
        assertEquals(projectileList.getLast(), p2);
    }

    @Test
    void addWallTest() {
        Wall w1 = new Wall(0, 0, 1, 1);
        Wall w2 = new Wall(3, 3, 1, 2);

        l1.addWall(w1);
        l1.addWall(w2);

        ArrayList<Wall> wallList = l1.getWalls();
        assertEquals(wallList.getFirst(), w1);
        assertEquals(wallList.getLast(), w2); 
    }

    @Test
    void checkPlayerMovementTest() {
        Projectile p1 = new Projectile(0, 0, 1, 0, 1);
        Projectile p2 = new Projectile(2, 3, 1, 1, 1);

        l1.addProjectile(p1);
        l1.addProjectile(p2);

        LinkedList<Projectile> projectileList = l1.getProjectiles();

        l1.checkPlayerMovement(1, 0);
        
        assertEquals(projectileList.getFirst().getPosition().getX(), 1);
        assertEquals(projectileList.getFirst().getPosition().getY(), 0);

        assertEquals(projectileList.getLast().getPosition().getX(), 3);
        assertEquals(projectileList.getLast().getPosition().getY(), 4);
    }

    @Test
    void checkCollisionTest() {
        Player player = new Player(1, 1);

        Wall wall = new Wall(1, 1, 1, 1);
        l1.addWall(wall);

        l1.checkCollision(player, 1, 0); // we check collision after player has moved and pass their past moveDir

        Vector2 position = player.getPosition();
        assertEquals(position.getX(), 0);
        assertEquals(position.getY(), 1);

        Projectile projectile = new Projectile(0, 1, 1, 0, 1);
        l1.addProjectile(projectile);

        position = player.getPosition();
        assertEquals(position.getX(), 0);
        assertEquals(position.getY(), 1);

        l1.checkCollision(player, 1, 0);

        assertTrue(l1.isLevelLost());
    }

    @Test
    void getProjectilesTest() {
        Projectile p1 = new Projectile(0, 0, 1, 0, 100);
        Projectile p2 = new Projectile(2, 3, 1, 1, 100);

        l1.addProjectile(p1);
        l1.addProjectile(p2);

        LinkedList<Projectile> projectileList = l1.getProjectiles();

        assertEquals(projectileList.getFirst(), p1);
        assertEquals(projectileList.getLast(), p2);
    }

    @Test
    void getWallsTest() {
        Wall w1 = new Wall(0, 0, 1, 1);
        Wall w2 = new Wall(3, 3, 1, 2);

        l1.addWall(w1);
        l1.addWall(w2);

        ArrayList<Wall> wallList = l1.getWalls();
        assertEquals(wallList.getFirst(), w1);
        assertEquals(wallList.getLast(), w2); 
    }

    @Test
    void isLevelLostTest() {
        assertFalse(l1.isLevelLost());
    }

    @Test
    void moveAllProjectilesTest() {
        Projectile p1 = new Projectile(0, 0, 1, 1, 100);
        Projectile p2 = new Projectile(2, 3, 1, 0, 100);

        l1.addProjectile(p1);
        l1.addProjectile(p2);

        l1.moveAllProjectiles(1);

        int pointX = p1.getPosition().getX();
        int pointY = p1.getPosition().getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 1);

        pointX = p2.getPosition().getX();
        pointY = p2.getPosition().getY();

        assertEquals(pointX, 3);
        assertEquals(pointY, 3);
    }

    @Test
    void resetTest() {
        Projectile p1 = new Projectile(0, 0, 1, 0, 100);
        Projectile p2 = new Projectile(2, 3, 1, 1, 100);

        l1.addProjectile(p1);
        l1.addProjectile(p2);

        l1.moveAllProjectiles(1);

        int pointX = p1.getPosition().getX();
        int pointY = p1.getPosition().getY();

        assertEquals(pointX, 1);
        assertEquals(pointY, 0);

        pointX = p2.getPosition().getX();
        pointY = p2.getPosition().getY();

        assertEquals(pointX, 3);
        assertEquals(pointY, 4);

        l1.reset();

        pointX = p1.getPosition().getX();
        pointY = p1.getPosition().getY();

        assertEquals(pointX, 0);
        assertEquals(pointY, 0);

        pointX = p2.getPosition().getX();
        pointY = p2.getPosition().getY();

        assertEquals(pointX, 2);
        assertEquals(pointY, 3);
    }
}
