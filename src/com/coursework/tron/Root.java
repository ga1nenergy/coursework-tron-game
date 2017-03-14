package com.coursework.tron;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

/**
 * Created by Nikita on 12.03.2017.
 */
public class Root extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int SCALE = 1;
    public static final String NAME = "Tron";

    private JFrame frame;

    public boolean running = false;
    public int tickCount = 0;

    private BufferedImage field = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels;/* = ((DataBufferInt) field.getRaster().getDataBuffer()).getData();*/

    private Player player1;
    private GameObject player2;
    //private BufferedImage field;

    public Root(){
        setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

        frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try {
            field = ImageIO.read(Root.class.getResource("tron_field.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //init();
    }

    private void init() {
        //showGreetinMessage();
        player1 = new Player("player1");
        try {
            field = ImageIO.read(Root.class.getResource("tron_field.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //pixels = ((DataBufferInt) field.getRaster().getDataBuffer()).getData();

    }

    public synchronized void start() {
        init();
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    public void run() {
        this.addKeyListener(player1);

        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D/60D;

        int frames = 0;
        int ticks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = false;
            while(delta >= 1)
            {
                //ticks++;
                //tick();
                delta -= 1;
                update();
                shouldRender = true;
            }

            /*try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            if (shouldRender)
            {
                //frames++;
                //player1.move();
                render();
            }

            /*if(System.currentTimeMillis() - lastTimer >= 1000){
                lastTimer += 1000;
                System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }*/
        }
    }

    private void update()
    {
        player1.move();
        if (player1.checkCollision(field)) {
            player1.moveTo(player1.getOrig_state_x(), player1.getOrig_state_y());
        }
    }

    public void tick(){
        /*tickCount++;

        for (int i = 0; i < pixels.length; i++){
            pixels[i] = i + tickCount;
        }*/
    }

    private void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(field, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(player1.getImage(), player1.getX(), player1.getY(), null);

        g.dispose();
        bs.show();
    }

    /*private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("IM HERE");
            player1.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player1.keyPressed(e);
            System.out.println("IM HERE");
        }
    }*/


    public static void main(String[] args){
        Root game = new Root();
        game.start();
    }

}
