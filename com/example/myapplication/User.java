package com.example.myapplication;
import java.net.Socket;

import java.util.ArrayList;
import java.io.*;
import static com.example.myapplication.Server.userList;

public class User extends ConnectedDevice implements Serializable{
    static ArrayList<Message> messagesin = new ArrayList<>();
    static ArrayList<Message> messagesout = new ArrayList<>();  
    private static final long serialVersionUID = 6529685098267757690L;
    private String userId;
    private String username;
    private  ArrayList<String>pmMessages = new ArrayList<>();
    public User(Socket socket, String username, String userId) throws IOException{
    super(socket);
    this.username = username;
    this.userId = userId;
    }

        @Override
        public synchronized void run(){

            while (true){
                try{
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);   
                Message msg = (Message) objectInputStream.readObject();
                messagesin.add(msg);
                System.out.println("Messages in = " + messagesin.size());
                System.out.println("Taked message from  " + msg.sender + " to " + msg.receiver);    
                messageWorker(msg);
                    
                 

                        
                }catch (Exception e){
                    System.out.println("Errror in User");
                    System.out.println(e);
                    try{
                        socket.close();
                        System.out.println("user disconnected");
                        userList.remove(this);
                        for (User currentUser : userList){
                            try{
                                
                                OutputStream outputStream = currentUser.getUserSocket().getOutputStream();
                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                                objectOutputStream.writeObject(userList);
                                System.out.println("Sended userlist" + userList.get(0).getUsername());
                            }catch(Exception a){
                                System.out.println("Error in sending userlist");
                                System.out.println(a);
                            }
                        }    
                        break;
                        }catch (Exception a){
                            System.out.println(a);
                        }
                }
                        
               

                    
            }
        }
        

        
            public synchronized void messageWorker(Message msg)
            {
                Thread thread = new Thread(){
                @Override
                public void run(){
                   System.out.println("Create New Thread  " + Thread.currentThread());
                    try{
                       
                        
                
                        
                        for(User user : userList){
                                if(user.getUserId().equals(msg.receiver)){
                                    
                                    OutputStream outputStream = user.socket.getOutputStream();
                                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                                    objectOutputStream.writeObject(msg);
                                    messagesout.add(msg);
                                    System.out.println("Messages out = " + messagesout.size());
                                    System.out.println("Sended in thread - " + Thread.currentThread());
                                    System.out.println("Sended messege from " + msg.sender + " to " + user.getUserId() + "    " + user.socket);
                                    System.out.println(msg + "      " + msg.msg + "     ");
                                
                                }else{
                                    System.out.println(msg.receiver + " is not "  + user.getUserId());
                                }
                        }
                    }catch (Exception x ){
                        System.out.println(x);
                    }
                }
            
            };
            thread.start();
        }


        public void setUserId(String userId){
            this.userId = userId;
        }

        public String getUserId(){
            return this.userId;
        }

        public Socket getUserSocket(){
            return this.socket;
        }

        public String getUsername(){
            return this.username;
        }



}
