package com.example.myapplication;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    public static LinkedList<Sockets> serverList = new LinkedList<>();
    
     // серверсокет
    // private static BufferedReader in; // поток чтения из сокета
    // private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(4004);
        System.out.println("Opend");
            try {   
                while(true){
                    Socket socket = server.accept();
                    System.out.println("new connection");
                    try{
                        serverList.add(new Sockets(socket));
                    } catch (IOException e){
                        
                        socket.close();
                    }
                }
            
            }finally {
                server.close();
                System.out.println("Closed");
            }
            
    
                

    }
}    