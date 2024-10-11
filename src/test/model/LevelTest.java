package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LevelTest {
    private Level l1;

    @BeforeEach
    void runBefore() {
        l1 = new Level("level X", 1, 1, 5, 5, 100, 100, 1, 0);
    }

    @Test
    void addProjectileTest() {
        Projectile p1 = new Projectile(0, 0, 1, 0, 1);
        Projectile p2 = new Projectile(2, 3, 1, 1, 1);

        l1.addProjectile(p1);
        l1.addProjectile(p2);

        ArrayList<Projectile> projectileList = l1.getProjectiles();
        assertEquals(projectileList.get(0), p1);
        assertEquals(projectileList.get(1), p2);

        l1.addProjectile(5, 8, 1, 0, 3, false);

        Projectile px = l1.getProjectiles().get(2);
        assertEquals(px.getPosition().getX(), 5);
        assertEquals(px.getPosition().getY(), 8);
        assertEquals(px.getDirection().getX(), 1);
        assertEquals(px.getDirection().getY(), 0);
    }

    @Test
    void addWallTest() {
        Wall w1 = new Wall(0, 0, 1, 1);
        Wall w2 = new Wall(3, 3, 1, 2);

        l1.addWall(w1);
        l1.addWall(w2);

        ArrayList<Wall> wallList = l1.getWalls();
        assertEquals(wallList.get(0), w1);
        assertEquals(wallList.get(1), w2); 
    }

    @Test
    void checkPlayerMovementTest() {
        Projectile p1 = new Projectile(0, 0, 1, 0, 1);
        Projectile p2 = new Projectile(2, 3, 1, 1, 1);

        l1.addProjectile(p1);
        l1.addProjectile(p2);

        ArrayList<Projectile> projectileList = l1.getProjectiles();

        l1.checkPlayerMovement(1, 0);
        
        assertEquals(projectileList.get(0).getPosition().getX(), 1);
        assertEquals(projectileList.get(0).getPosition().getY(), 0);

        assertEquals(projectileList.get(1).getPosition().getX(), 3);
        assertEquals(projectileList.get(1).getPosition().getY(), 4);

        l1.checkPlayerMovement(-1, 0);

        assertEquals(projectileList.get(0).getPosition().getX(), 0);
        assertEquals(projectileList.get(0).getPosition().getY(), 0);

        assertEquals(projectileList.get(1).getPosition().getX(), 2);
        assertEquals(projectileList.get(1).getPosition().getY(), 3);
    }

    @SuppressWarnings("methodlength")
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

        assertTrue(l1.lost());

        Player player2 = new Player(5, 5);

        l1.checkCollision(player2, 1, 0);

        assertTrue(l1.completed());

        Player player3 = new Player(2, 2);

        Projectile projectile2 = new Projectile(8, 8, 1, 0, 5);

        wall = new Wall(2,2,2,2);
        l1.addWall(wall);

        l1.checkCollision(player3, -1, 0);

        position = projectile2.getPosition();

        assertEquals(position.getX(), 8);
        assertEquals(position.getY(), 8);
    }

    @Test
    void getProjectilesTest() {
        Projectile p1 = new Projectile(0, 0, 1, 0, 100);
        Projectile p2 = new Projectile(2, 3, 1, 1, 100);

        l1.addProjectile(p1);
        l1.addProjectile(p2);

        ArrayList<Projectile> projectileList = l1.getProjectiles();

        assertEquals(projectileList.get(0), p1);
        assertEquals(projectileList.get(1), p2);
    }

    @Test
    void getWallsTest() {
        Wall w1 = new Wall(0, 0, 1, 1);
        Wall w2 = new Wall(3, 3, 1, 2);

        l1.addWall(w1);
        l1.addWall(w2);

        ArrayList<Wall> wallList = l1.getWalls();
        assertEquals(wallList.get(0), w1);
        assertEquals(wallList.get(1), w2); 
    }

    @Test
    void isLevelLostTest() {
        assertFalse(l1.lost());
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

        l1.moveAllProjectiles(-1);

        pointX = p1.getPosition().getX();
        pointY = p1.getPosition().getY();

        assertEquals(pointX, 0);
        assertEquals(pointY, 0);

        pointX = p2.getPosition().getX();
        pointY = p2.getPosition().getY();

        assertEquals(pointX, 2);
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

    @Test
    void getStartPositionTest() {
        Vector2 startPos = l1.getStartPosition();

        assertEquals(startPos.getX(), 1);
        assertEquals(startPos.getY(), 1);
    }

    @Test
    void getGoalPositionTest() {
        Vector2 startPos = l1.getGoalPosition();

        assertEquals(startPos.getX(), 5);
        assertEquals(startPos.getY(), 5);
    }

    @Test
    void getTimeDirectionTest() {
        Vector2 direction = l1.getTimeDirection();

        assertEquals(direction.getX(), 1);
        assertEquals(direction.getY(), 0);
    }

    @Test
    void completedTest() {
        boolean completed = l1.completed();

        assertFalse(completed);
    }

    @Test
    void getSizeTest() {
        Vector2 size = l1.getSize();

        assertEquals(size.getX(), 100);
        assertEquals(size.getY(), 100);
    }

    @Test
    void getNameTest() {
        String name = l1.getName();

        assertTrue(name.equals("level X"));
    }
}
