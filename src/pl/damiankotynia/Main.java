package pl.damiankotynia;

import pl.damiankotynia.connector.Connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        int port = 4444;
        Connector connector = new Connector(port);


    }
}
