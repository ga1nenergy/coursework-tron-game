package com.coursework.tron;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Nikita on 12.03.2017.
 */
public class GameObject extends GameModel {
    private String name;
    private double velocity = 1; //pixels per update
    enum Direction {UP, DOWN, LEFT, RIGHT};
    private Direction direction = Direction.UP;
    private int orig_state_x, orig_state_y;

    GameObject() {
        super();
        name = "NO NAME";
    }

    GameObject(String name, Color color) {
        super(0, 0, 10, 10, color);
        this.name = name;
    }

    GameObject(String name, int x, int y, Color color) {
        super(x, y, 10, 10, color);
        this.name = name;
        orig_state_x = x;
        orig_state_y = y;
    }

    GameObject(String name, String path)
    {
        super(path);
        this.name = name;
    }

    public void drawTrace(BufferedImage image) {
        Graphics g = image.getGraphics();

        g.setColor(getColor());
        g.fillRect(x - 4, y - 4, 10 ,10);

        g.dispose();
    }

    public void move() {
        switch (direction)
        {
            case UP: super.x += 0; super.y -= velocity; break;
            case DOWN: super.x += 0; super.y += velocity; break;
            case LEFT: super.x -= velocity; super.y += 0; break;
            case RIGHT: super.x += velocity; super.y += 0; break;
        }
    }

    public void moveTo(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean checkCollision(BufferedImage image) {
        boolean ifCollided = false;

        switch (direction) {
            case RIGHT:
                if (Color.black.getRGB() != image.getRGB(getX() + 5, getY()))
                    ifCollided = true; break;
            case LEFT:
                if (Color.black.getRGB() != image.getRGB(getX() - 5, getY()))
                    ifCollided = true; break;
            case UP:
                if (Color.black.getRGB() != image.getRGB(getX() , getY() - 5))
                    ifCollided = true; break;
            case DOWN:
                if (Color.black.getRGB() != image.getRGB(getX(), getY() + 5))
                    ifCollided =  true; break;
        }
        //System.out.println(ifCollided);
        if (ifCollided)
            System.out.println("collision!");
        return ifCollided;
    }

    public void moveUp() {
        if (direction != Direction.DOWN)
            direction = Direction.UP;
    }
        //System.out.println("UP");}
    public void moveDown() {
        if (direction != Direction.UP)
            direction = Direction.DOWN;
    }
    public void moveLeft() {
        if (direction != Direction.RIGHT)
            direction = Direction.LEFT;
    }
    public void moveRight() {
        if (direction != Direction.LEFT)
            direction = Direction.RIGHT;
    }

    public String getName() {return name;}
    public Direction getDirection() {
        return direction;
    }

    public int getOrig_state_x() {
        return orig_state_x;
    }

    public int getOrig_state_y() {
        return orig_state_y;
    }

    public void setOriginDirection() {direction = Direction.UP;}
}
