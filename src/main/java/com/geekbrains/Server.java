package com.geekbrains;

import com.geekbrains.common.Reader;
import com.geekbrains.common.Writer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер запущен");
            //Ожидаем клиента
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Thread readerThread = new Thread(new Reader(in, "Client"));
            readerThread.start();
            Thread writerThread = new Thread(new Writer(out));
            writerThread.start();

            while (readerThread.isAlive() && writerThread.isAlive()) ;

            if (readerThread.isAlive())
                readerThread.interrupt();

            if (writerThread.isAlive())
                writerThread.interrupt();

        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                System.out.println("Закрываю подключения");
                in.close();
                out.close();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
