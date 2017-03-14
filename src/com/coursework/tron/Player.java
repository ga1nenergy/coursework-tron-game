package com.coursework.tron;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Nikita on 12.03.2017.
 */

public class Player extends GameObject implements KeyListener {
    private int orig_state_x, orig_state_y;

    Player(String name) {
        super(name, Color.white);
        orig_state_x = (Root.WIDTH - 20)/2 - 20;
        orig_state_y = (Root.HEIGHT)/2;
        moveTo(orig_state_x, orig_state_y);
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

    public int getOrig_state_x() {
        return orig_state_x;
    }

    public int getOrig_state_y() {
        return orig_state_y;
    }
}
