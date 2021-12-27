package com.example.myapplication;

public class Main {
    public static void main(String[] args) throws Exception {
       for (int i = 0; i < 10; i++){
           new FakeClient(i);
       }
    }
}
