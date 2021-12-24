package com.example.myapplication;
import java.io.*;
import java.net.Socket;
import java.lang.String;
import java.lang.Throwable;
import static com.example.myapplication.Server.userList;


public class ConnectedDevice extends Thread{
    protected Socket socket;
    public ConnectedDevice (Socket socket) throws IOException {
        this.socket = socket;
        
        start();
    }

    @Override
    public void run() {
        
        try {

           
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Message msg = (Message) objectInputStream.readObject();
                System.out.println(msg.msg);
                User user = new User(socket, msg.sender, "Id#" + userList.size());
                userList.add(user);
                System.out.println("created new username " + user.getUsername() + " Id = " + user.getUserId());
                try{
                    OutputStream outputStream = user.getUserSocket().getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(user.getUserId());
                }catch (Exception e){
                    System.out.println("Error in sending userId " + e);
                }
                
                
                for (User currentUser : userList){
                    try{
                        
                        OutputStream outputStream = currentUser.getUserSocket().getOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(userList);
                        System.out.println("Sended userlist" + userList.get(0).getUsername());
                        }catch(Exception e){
                            System.out.println("Error in sending userlist");
                            System.out.println(e);
                        }
                    }
                
                
            

        } catch (Throwable e) {
            System.out.println("Error in connected device");
            e.printStackTrace();
        }
    }

    
}
////////cawabanga