package pl.damiankotynia.connector;

import pl.damiankotynia.model.Request;
import pl.damiankotynia.model.RequestType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class Connection implements Runnable{
    private Socket socket;
    private int clientNumber;
    private ObjectInputStream inputStream;

    public Connection(Socket socket, int client){
        System.out.println(">>>>> New Connection");
        this.clientNumber= client;
        this.socket = socket;
        try {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(){
        while(true) {
            System.out.println(clientNumber);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*try {
                Object o = inputStream.readObject();
                System.out.println("qwe");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
*/
        }
    }

}
