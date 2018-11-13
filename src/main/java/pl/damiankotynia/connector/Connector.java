package pl.damiankotynia.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector {


    private int port;
    private ServerSocket listener;
    private int clientNumber;

    public Connector(int port) {
        this.port = port;
        clientNumber = 0;
        try {
            listener = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            try {
                Socket socket = listener.accept();
                clientNumber++;
                new Thread(new Connection(socket, clientNumber)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
