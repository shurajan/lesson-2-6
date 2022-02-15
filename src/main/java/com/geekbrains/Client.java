package com.geekbrains;

import com.geekbrains.common.Reader;
import com.geekbrains.common.Writer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private final static String SERVER_ADDRESS = "localhost";
    private final static int SERVER_PORT = 8080;

    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;


    public static void main(String[] args) {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Thread readerThread = new Thread(new Reader(in, "Server"));
            readerThread.start();
            Thread writerThread = new Thread(new Writer(out));
            writerThread.start();

            while (readerThread.isAlive() && writerThread.isAlive()) ;

            if (readerThread.isAlive())
                readerThread.interrupt();

            if (writerThread.isAlive())
                writerThread.interrupt();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Закрываю подключения");
                in.close();
                out.close();
                socket.close();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
