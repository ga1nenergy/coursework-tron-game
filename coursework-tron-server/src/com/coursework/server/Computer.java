package com.coursework.server;

import java.util.ArrayList;

/**
 * Created by Nikita on 06.06.2017.
 */

public class Computer {
    private int WIDTH = 640;
    private int HEIGHT = 480;

    private  ArrayList<Player> players;

    public Computer() {
        players = new ArrayList<>();

        int STATE_Y_1 = HEIGHT / 2;
        int STATE_X_1 = (WIDTH - 20) / 2 - 20;
        players.add(new Player(0, STATE_X_1, STATE_Y_1));
        int STATE_X_2 = (WIDTH - 20) / 2 + 20;
        int STATE_Y_2 = HEIGHT / 2;
        players.add(new Player(1, STATE_X_2, STATE_Y_2));
    }

    public ArrayList compute(String[] input) {
        for (int i = 0; i < 2; i++) {
            String[] arr = input[i].split(" ");
            players.get(Integer.parseInt(arr[0])).moveTo(Integer.parseInt(arr[1]),
                                                         Integer.parseInt(arr[2]));
        }

        for (Player player : players) {
            player.move();
        }
        return players;
    }

    public boolean decide(String[] strings) {
        String[] string;
        boolean decision = false;

        for (int i = 0; i < strings.length; i++) {
            string = strings[i].split(" ");

            if (string[1].equalsIgnoreCase("collision")) {
                decision = true;
                break;
            }
        }

        return decision;
    }

}
