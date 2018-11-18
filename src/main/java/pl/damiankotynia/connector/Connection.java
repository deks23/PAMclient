package pl.damiankotynia.connector;

import pl.damiankotynia.exceptions.InvalidRequestFormatException;
import pl.damiankotynia.model.Request;
import pl.damiankotynia.service.RequestExecutor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import static pl.damiankotynia.service.Utils.CONNECTION_LOGGER;

public class Connection implements Runnable {
    private Socket socket;
    private int clientNumber;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private RequestExecutor requestExecutor;

    public Connection(Socket socket, int client) {
        System.out.println(CONNECTION_LOGGER + "New Connection");
        this.clientNumber = client;
        this.socket = socket;
        this.requestExecutor = new RequestExecutor();
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        //TODO bedzie trzeba dac to w petli zeby bylo polaczenie z userem
        try {
            Object request = inputStream.readObject();
            System.out.println(CONNECTION_LOGGER + request.toString());
            try {
                requestExecutor.executeRequest(request);
            } catch (InvalidRequestFormatException e) {
                e.printStackTrace();
            }
            socket.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
