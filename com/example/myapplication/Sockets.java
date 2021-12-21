package com.example.myapplication;
import java.io.*;
import java.net.Socket;
import java.lang.String;



public class Sockets extends Thread{
    private Socket socket;
    
    public Sockets (Socket socket) throws IOException {
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        
        try {

            while (true) {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Message msg = (Message) objectInputStream.readObject();
                for (Sockets vr : Server.serverList) {
                    if(vr == this){
                        continue;
                    }
                vr.send(msg);
                }
            }

        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            
            e.printStackTrace();
        }
    }

    private void send(Message msg) {
        try {
            System.out.println("SEND " + String.valueOf(msg) );
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(msg);
            System.out.println("SENDED " + msg.msg );
        } catch (IOException e) {
            System.out.println("ERROR IN SENDING" + e);
        }
    }
}