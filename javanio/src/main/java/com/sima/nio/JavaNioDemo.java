package com.sima.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by qisima on 2/12/2019 2:55 PM
 */
public class JavaNioDemo {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket clientSocket = serverSocket.accept();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String request, response;
        while((request = in.readLine()) != null){
            if ("Done".equals(request)){
                break;
            }
            response = request;
            out.println(response);
            System.out.println(response);
        }
    }
}
