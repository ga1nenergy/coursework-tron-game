package com.coursework.tron;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Nikita on 12.03.2017.
 */

public class Player extends GameObject implements KeyListener {
    private int number;

    Player(int number, String name, int x, int y, Color color) {
        super(name, x, y, color);

        this.number = number;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: moveUp(); break;
            case KeyEvent.VK_S: moveDown(); break;
            case KeyEvent.VK_A: moveLeft(); break;
            case KeyEvent.VK_D: moveRight(); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public int getNumber() {
        return number;
    }
}
