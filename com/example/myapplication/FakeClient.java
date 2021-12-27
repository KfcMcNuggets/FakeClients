package com.example.myapplication;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
public class FakeClient  extends Thread{

String name;
String id;
    public FakeClient(int i){
        this.name = "Ddoser" + i;
        start();
    }
 
    
    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", 4004);
            System.out.println("SUCCES create");
            
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(new Message(name,"nothing", "hello server", "server"));




            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            id = (String) objectInputStream.readObject();



            ObjectInputStream objectInputStream2 = new ObjectInputStream(inputStream);
            ArrayList<User> userList = (ArrayList<User>) objectInputStream2.readObject();


            
            Thread.sleep(11100000);







        } catch (Exception e) {
        System.out.println("ERRROR" + e);}
    }
}
