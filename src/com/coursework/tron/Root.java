package com.coursework.tron;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Nikita on 12.03.2017.
 */
public class Root extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;
    private enum State {START, RUNNING, GAMEOVER};
    State state;

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int SCALE = 1;
    public static final String NAME = "Tron";

    private JFrame frame;

    public boolean running = false;
    public int tickCount = 0;

    private BufferedImage orig_field = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage field;
    private int[] pixels;

    private Client client;
    private Player player1;
    private GameObject player2;

    public Root() throws IOException{
        setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

        client = new Client("localhost", 9090);

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
    }

    private BufferedImage cloneImage(BufferedImage src) {
        BufferedImage b = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = b.getGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();
        return b;
    }

    private void init() throws IOException, ClassNotFoundException {
        state = State.START;
        int number = Integer.parseInt(client.recv());

        switch (number) {
            case 0: player1 = new Player(number,
                                    "player1",
                                    (Root.WIDTH - 20)/2 - 20,
                                    (Root.HEIGHT)/2,
                                        Color.magenta);
                    player2 = new GameObject("player2",
                                            (Root.WIDTH - 20)/2 + 20,
                                            (Root.HEIGHT)/2,
                                                Color.orange);
                    break;
            case 1: player1 = new Player(number,
                                        "player1",
                                        (Root.WIDTH - 20)/2 +20,
                                        (Root.HEIGHT)/2,
                                            Color.orange);
                    player2 = new GameObject("player2",
                                            (Root.WIDTH - 20)/2 - 20,
                                            (Root.HEIGHT)/2,
                                                Color.magenta);
                    break;
        }

        try {
            orig_field = ImageIO.read(Root.class.getResource("tron_field.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        field = cloneImage(orig_field);
    }

    public synchronized void start() throws IOException, ClassNotFoundException {
        init();
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    public void run() {
        this.addKeyListener(player1);

        while (running){
            try {
                update();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            }
            render();
        }
    }

    private void update() throws IOException, InterruptedException, ClassNotFoundException {
        String[] string;
        //check stage
        switch (state) {
            case GAMEOVER:
                player1.moveTo(player1.getOrig_state_x(), player1.getOrig_state_y());
                player1.setOriginDirection();
                player2.moveTo(player2.getOrig_state_x(), player2.getOrig_state_y());
                field = cloneImage(orig_field);
                state = State.RUNNING;
                break;
            case RUNNING:
                client.send(player1);
                string = client.recv().split(" ");
                if (string[0].equals("collision"))
                {
                    System.out.println("new game starts in 5 sec");
                    Thread.sleep(5000);
                    state = State.GAMEOVER;
                    break;
                }
                player1.drawTrace(field);
                player2.drawTrace(field);
                player1.move();
                player2.moveTo(Integer.parseInt(string[1]), Integer.parseInt(string[2]));
                if (player1.checkCollision(field)) {
                    client.send(player1.getNumber()+" collision");
                }
                break;
            case START:
                System.out.println(client.recv());
                player1.moveTo(player1.getOrig_state_x(), player1.getOrig_state_y());
                player1.setOriginDirection();
                player2.moveTo(player2.getOrig_state_x(), player2.getOrig_state_y());
                field = cloneImage(orig_field);
                System.out.println("the game starts in 5 sec");
                Thread.sleep(5000);
                state = State.RUNNING;
                break;
        }
    }

    private void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(field, 0, 0, getWidth(), getHeight(), null);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Root game = new Root();
        game.start();
    }

}
