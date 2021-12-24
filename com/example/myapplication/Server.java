package com.example.myapplication;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    //public static LinkedList<ConnectedDevice> connectedDevices = new LinkedList<>();
    public static ArrayList<User> userList = new ArrayList<>();
    public static ReentrantLock userListMutex = new ReentrantLock();
    
     
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(4004);
        System.out.println("Opend");
            try {   
                while(true){
                    Socket socket = server.accept();
                    System.out.println("new connection " + socket);
                    try{
                      new ConnectedDevice(socket);
                    } catch (IOException e){
                        System.out.println("Error in server main");
                        socket.close();
                    }
                }
            
            }finally {
                server.close();
                System.out.println("Closed");
            }
            
    
                

    }


}



  