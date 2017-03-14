package com.coursework.tron;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Nikita on 12.03.2017.
 */
public class GameObject extends GameModel {
    private String name;
    private double velocity = 2; //pixels per update
    private enum Direction {UP, DOWN, LEFT, RIGHT};
    private Direction direction = Direction.UP;

    GameObject() {
        super();
        name = "NO NAME";
    }

    GameObject(String name, Color color) {
        super(0, 0, 10, 10, color);
        this.name = name;
    }

    GameObject(String name, String path)
    {
        super(path);
        this.name = name;
    }

    public void move() {
        switch (direction)
        {
            case UP: super.x += 0; super.y -= velocity; break;
            case DOWN: super.x += 0; super.y += velocity; break;
            case LEFT: super.x -= velocity; super.y += 0; break;
            case RIGHT: super.x += velocity; super.y += 0; break;
        }
        //System.out.println("(" + x + " " + y + ")");
    }

    public void moveTo(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean checkCollision(BufferedImage image) {
        //BufferedImage object = (BufferedImage) getImage();
        boolean ifCollided = false;
        //System.out.println(image.getRGB(getX(), getY()));

        switch (direction) {
            case RIGHT:
                if (Color.black.getRGB() != image.getRGB(getX() + 1, getY()))
                    ifCollided = true;
                /*System.out.println("im here!"); */break;
            case LEFT:
                if (Color.black.getRGB() != image.getRGB(getX() - 1, getY()))
                    ifCollided = true; break;
            case UP:
                if (Color.black.getRGB() != image.getRGB(getX() , getY() - 1))
                    ifCollided = true; break;
            case DOWN:
                if (Color.black.getRGB() != image.getRGB(getX(), getY() + 1))
                    ifCollided =  true; break;
        }
        //System.out.println(ifCollided);
        if (ifCollided)
            System.out.println("collision!");
        return ifCollided;
    }

    /*public void changeDirection(Direction direction)
    {
        this.direction = direction;
    }*/
    public void moveUp() {direction = Direction.UP;}
        //System.out.println("UP");}
    public void moveDown() {direction = Direction.DOWN;}
        //System.out.println("DOWN");}
    public void moveLeft() {direction = Direction.LEFT;}
        //System.out.println("LEFT");}
    public void moveRight() {direction = Direction.RIGHT;}
        //System.out.println("RIGHT");}

    public String getName() {return name;}
    public Direction getDirection() {
        return direction;
    }
}
