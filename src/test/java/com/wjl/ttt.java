package com.wjl;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class ttt {

    @Test
    public void client() throws IOException {
        Socket socket=new Socket("localhost",6668);
        OutputStream outToServer = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);

        out.writeUTF("Hello from " + socket.getLocalSocketAddress());
        InputStream inFromServer = socket.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        System.out.println("服务器响应： " + in.readUTF());
        socket.close();

    }

}
