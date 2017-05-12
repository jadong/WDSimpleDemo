package com.dong.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zengwendong on 17/3/17.
 */
public class CommandServer implements Runnable {

    private static final String TAG = "CommandServer";
    private final int mPort;
    private boolean isRunning;
    private ServerSocket serverSocket;

    private HandlerMessageListener handlerMessageListener;

    public void setHandlerMessageListener(HandlerMessageListener handlerMessageListener) {
        this.handlerMessageListener = handlerMessageListener;
    }

    public CommandServer(int port) {
        mPort = port;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void start() {
        isRunning = true;
        new Thread(this).start();
    }

    public void stop() {
        isRunning = false;
    }

    PrintWriter writer = null;
    BufferedReader bufferedReader = null;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(mPort);
            System.out.println("服务端启动成功...");
            while (isRunning) {
                Socket socket = serverSocket.accept();
                System.out.println("来自客户端连接:" + socket.getInetAddress());
                readMessage(socket);
                sendMessage(socket);
            }

            if (null != writer) {
                writer.close();
            }

            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Socket socket) throws IOException {
        writer = new PrintWriter(socket.getOutputStream());
        String string = "隔壁老孙来访";
        writer.println(string);
        writer.flush();
    }

    private void readMessage(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message = bufferedReader.readLine();
        if (handlerMessageListener != null) {
            handlerMessageListener.receiveMessage(message);
        }
        System.out.println("handlerMessage: " + message);
    }

    public interface HandlerMessageListener {
        void receiveMessage(String message);
    }

}
