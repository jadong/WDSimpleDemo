package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by zengwendong on 17/3/17.
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {
        try {
            Socket socket = new Socket("172.19.84.43", 8088);
            //Socket socket = new Socket("127.0.0.1", 8088);
            System.out.println("客户端启动成功...");
            PrintWriter write = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readLine = "8888888";
            write.println(readLine);
            write.flush();
            System.out.println("write---"+readLine);
            while (!"end".equals(readLine = in.readLine())) {
                System.out.println("Server:" + readLine);
            }
            write.close();
            in.close();
            socket.close();
            System.out.println("end---");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
