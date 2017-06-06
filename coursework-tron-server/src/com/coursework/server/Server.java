package com.coursework.server;

/**
 * Created by Nikita on 05.06.2017.
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    ArrayList<ObjectInputStream> in;
    ArrayList<ObjectOutputStream> out;

    ServerSocket server;
    ArrayList<Socket> players;

    public Server(int port) {
        try {
            server = new ServerSocket(9090, 0, InetAddress.getByName(null));
        } catch (IOException e) {
            System.out.println("Couldn't listen to port 9090");
            System.exit(-1);
        }

        in = new ArrayList<>();
        out = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void wait_for_players() throws IOException{
        int count = 0;

        while (count < 2) {
            try {
                System.out.print("Waiting for a client...");
                players.add(server.accept());
                System.out.println("Client connected");
            } catch (IOException e) {
                System.out.println("Can't accept");
                System.exit(-1);
            }

            out.add(new ObjectOutputStream(players.get(count).getOutputStream()));
            out.get(count).flush();
            in.add(new ObjectInputStream(players.get(count).getInputStream()));

            send(count, count+"");

            count++;
        }
    }

    public String[] recv_all() throws IOException, ClassNotFoundException {
        String[] input = new String[2];

        for (int i = 0; i < 2; i++) {
            input[i] = (String)in.get(i).readObject();
            System.out.println(i+" : "+input[i]);
        }

        return input;
    }

    public void send_all(String strings) throws IOException {
        for (int i = 0; i < 2; i++) {
            out.get(i).writeObject(strings);
            out.get(i).flush();
        }
    }

    public void  send_all(Player player) throws IOException {
        String str = new String();

        str += player.getNumber()+" ";
        str += player.getX()+" ";
        str += player.getY()+" ";
        switch (player.getDirection()) {
            case UP: str += "UP"; break;
            case DOWN: str += "DOWN"; break;
            case LEFT: str += "LEFT"; break;
            case RIGHT: str += "RIGHT"; break;
        }

        System.out.println(str);

        out.get(1 - player.getNumber()).writeObject(str);
        out.get(1 - player.getNumber()).flush();
    }

    public void send(int player, String string) throws IOException {
        out.get(player).writeObject(string);
        out.get(player).flush();
        System.out.println("to "+player+":"+string);
    }
}
