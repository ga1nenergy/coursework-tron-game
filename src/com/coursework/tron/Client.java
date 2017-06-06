package com.coursework.tron;

/**
 * Created by Nikita on 04.06.2017.
 */
import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }

    public void send(Player player) throws IOException {
        String string = new String();

        string += player.getNumber() + " ";
        string += player.getX() + " ";
        string += player.getY() + " ";
        switch (player.getDirection()) {
            case UP: string += "UP"; break;
            case DOWN: string += "DOWN"; break;
            case LEFT: string += "LEFT"; break;
            case RIGHT: string += "RIGHT"; break;
        }

        send(string);
    }

    public void send(String string) throws IOException {
        out.writeObject(string);
        out.flush();
    }

    public String recv() throws IOException, ClassNotFoundException {
        String string;

        string = (String)in.readObject();

        return string;
    }

    protected void finalize() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}
