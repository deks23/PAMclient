package pl.damiankotynia.connector;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Connector {

    private Socket socket;
    private int port;
    private ServerSocket listener;

    public Connector(int port) {
        this.port = port;
        try{
            createSocket();
        }catch(IOException e){

        }
    }

    private void createSocket() throws IOException{

        listener = new ServerSocket(port);
        Scanner scan = new Scanner(System.in);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    String qwe;
                    qwe = scan.nextLine();
                    out.println(qwe);
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }


}
