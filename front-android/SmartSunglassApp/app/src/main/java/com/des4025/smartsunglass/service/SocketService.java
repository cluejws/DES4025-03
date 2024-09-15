package com.des4025.smartsunglass.service;

import com.des4025.smartsunglass.util.ConfigUtil;

import java.io.IOException;
import java.net.Socket;

public class SocketService {

    private static Socket socket;

    public static void initSocket() throws IOException {
        socket = new Socket(ConfigUtil.SOCKET_HOST, ConfigUtil.SOCKET_PORT);
    }

    public static Socket getSocket() throws IOException {
        return socket;
    }
}
