package com.coursework.server;

/**
 * Created by Nikita on 06.06.2017.
 */
public class Player {
    private int x, y;
    private int number;
    private int velocity = 2;

    enum Direction {UP, DOWN, LEFT, RIGHT};

    Direction direction;

    public Player(){
        number = 0;
        x = 0;
        y = 0;
        direction = Direction.UP;
    }

    public Player(int number, int x, int y) {
        this.number = number;
        this.x = x;
        this.y = y;
        direction = Direction.UP;
    }

    public void move() {
        switch (direction)
        {
            case UP: x += 0; y -= velocity; break;
            case DOWN: x += 0; y += velocity; break;
            case LEFT: x -= velocity; y += 0; break;
            case RIGHT: x += velocity; y += 0; break;
        }
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumber() {
        return number;
    }

    public Direction getDirection() {return direction;}

    public void setDirection(Direction direction) {this.direction = direction;}
}
