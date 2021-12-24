package com.example.myapplication;
import java.io.*;
import java.net.Socket;
import java.lang.String;
import java.lang.Throwable;
import static com.example.myapplication.Server.userList;
import static com.example.myapplication.Server.userListMutex;


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
                userListMutex.lock();
                User user = new User(socket, msg.sender, "Id#" + userList.size());
                userList.add(user);
                userListMutex.unlock();

                System.out.println("created new username " + user.getUsername() + " Id = " + user.getUserId());
                //System.out.println("user.getUserSocket() " + user.getUserSocket());
                try {
                    //System.out.println("Before user.getUserSocket().getOutputStream();");
                    OutputStream outputStream = user.getUserSocket().getOutputStream();
                    //System.out.println("After user.getUserSocket().getOutputStream();");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(user.getUserId());
                } catch (Exception e) {
                    System.out.println("Error in sending userId " + e);
                }

                userListMutex.lock();

                for (User currentUser : userList) {
                    try {
                        OutputStream outputStream = currentUser.getUserSocket().getOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(userList);
                        System.out.println("Sended userlist" + userList.get(0).getUsername());
                    } catch(Exception e) {
                        System.out.println("Error in sending userlist");
                        System.out.println(e);
                    }
                }

                userListMutex.unlock();

        } catch (Throwable e) {
            System.out.println("Error in connected device");
            e.printStackTrace();
        }
    }

    
}
////////cawabanga