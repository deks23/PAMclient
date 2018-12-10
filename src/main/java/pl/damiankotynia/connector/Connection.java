package pl.damiankotynia.connector;

import pl.damiankotynia.exceptions.InvalidRequestFormatException;
import pl.damiankotynia.model.Response;
import pl.damiankotynia.model.ResponseType;
import pl.damiankotynia.service.RequestExecutor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import static pl.damiankotynia.service.Utils.CONNECTION_LOGGER;

public class Connection implements Runnable {
    private Socket socket;
    private int clientNumber;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private RequestExecutor requestExecutor;
    private List<Connection> connectionList;
    private boolean running;

    public Connection(Socket socket, int client, List<Connection> connectionList) {
        System.out.println(CONNECTION_LOGGER + "New Connection");
        this.clientNumber = client;
        this.socket = socket;
        this.requestExecutor = new RequestExecutor();
        this.connectionList = connectionList;
        this.running = true;
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (running) {
            try {
                Object request = inputStream.readObject();
                System.out.println(CONNECTION_LOGGER + request.toString());
                Response response = null;
                try {
                    response = requestExecutor.executeRequest(request);
                } catch (InvalidRequestFormatException e) {
                    e.printStackTrace();
                }

                if (ResponseType.RESERVATION_COMPLETE.equals(response.getResponseType()))
                    brodcastMessage(response);
                synchronized (outputStream) {
                    outputStream.writeObject(response);
                }

            } catch (SocketException e) {
                System.out.println(CONNECTION_LOGGER + "Zerwano połączenie");
                running = !running;
                connectionList.remove(this);
            } catch (IOException e) {
                System.out.println(CONNECTION_LOGGER + "Zerwano połączenie2");
                running = !running;
                connectionList.remove(this);
            } catch (ClassNotFoundException e) {
                System.out.println(CONNECTION_LOGGER + "Niepoprawny format zapytania");
            }
        }


    }

    private void sendMessage(Response message) {
        try {
            synchronized (outputStream) {
                outputStream.writeObject(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void brodcastMessage(Response message) {
        for (Connection connection : connectionList) {
            if (!connection.equals(this))
                    connection.sendMessage(message);
        }
    }

}
