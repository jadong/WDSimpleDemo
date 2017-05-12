package test;

import com.dong.server.CommandServer;

/**
 * Created by zengwendong on 17/3/17.
 */
public class SocketServer {

    public static void main(String[] args) {
        CommandServer commandServer = new CommandServer(8088);
        commandServer.start();
    }

}
