package com.geekbrains.common;

import java.io.DataInputStream;
import java.io.IOException;


public class Reader implements Runnable {
    private DataInputStream in;
    private String party;

    public Reader(DataInputStream in, String party) {
        this.in = in;
        this.party = party;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readUTF();
                if (message.equals("/end")) {
                    System.out.println(party + " закрыл подключение");
                    break;
                }
                System.out.println("........" + message);
            }
        } catch (IOException e) {
            System.out.println("Подключение закрыто");
        }
    }

}
