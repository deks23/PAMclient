package pl.damiankotynia;

import pl.damiankotynia.connector.Connection;
import pl.damiankotynia.connector.Connector;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        int port = 4444;
        new Thread(new Connector(port)).start();

    }
}
