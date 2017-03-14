package com.coursework.tron;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Nikita on 12.03.2017.
 */
public abstract class GameModel {
    private Image image;
    private Color color;
    protected int x, y;
    //private int[] pixels;
    //Rectangle rectangle;

    public  GameModel() {
        image = null;
        //pixels = null;
    }

    public GameModel(String path) { //конструктор для текстур
        try {
            image = ImageIO.read(Root.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        color = null;
        //pixels = bufferedImage.getRGB(0, 0, width, height, pixels, 0, width);
    }

    public GameModel(int x, int y, int width, int height, Color color) { //конструктор для прямоугольников
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();

        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.dispose();

        this.color = color;
        //this.width = width;
        //this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {return color;}
}
