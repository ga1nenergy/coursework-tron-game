package com.coursework.server;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nikita on 05.06.2017.
 */
public class Root {
    private static final int STATE_X_1 = 1;
    private static final int STATE_Y_1 = 1;

    private static final int STATE_X_2 = 1;
    private static final int STATE_Y_2 = 1;

    private static ArrayList<Integer> fill_array(Player player) {
        ArrayList<Integer> array = new ArrayList<>();

        array.add(player.getNumber());
        array.add(player.getX());
        array.add(player.getY());

        return array;
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Server server = new Server(4444);
        Computer computer = new Computer();
        ArrayList<Player> players;

        server.wait_for_players();

        server.send(0, "you're the left one");
        server.send(1, "you're the right one");

        String[] input;

        while(true) {
            Thread.sleep(17);

            input = server.recv_all();

            if (computer.decide(input)) {
                server.send_all("collision");
                Thread.sleep(5000);
                continue;
            }
            else {
                server.send(0, input[1]);
                server.send(1, input[0]);
            }
        }
    }

}
