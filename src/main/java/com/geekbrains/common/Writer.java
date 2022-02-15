package com.geekbrains.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Writer implements Runnable {

    private DataOutputStream out;

    public Writer(DataOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        BufferedReader stringReader = new BufferedReader(
                new InputStreamReader(System.in));
        String outMessage=null;

        while (true) {
            try {
                outMessage = stringReader.readLine();
                out.writeUTF(outMessage);
            } catch (IOException e) {
                System.out.printf("Подключение закрыто");
            }
            if (outMessage.equals("/end")) {
                break;
            }
        }

    }


}
