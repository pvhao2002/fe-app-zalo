package com.author.toan.remote;

import androidx.lifecycle.MutableLiveData;

import com.author.toan.routes.ChatClient;
import com.author.toan.viewmodels.ChatViewModel;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketClient {
    private static Socket mSocket;
    private static SocketClient instance;

    private SocketClient() {
        try {
            mSocket = IO.socket("https://zalo.herokuapp.com");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static SocketClient getInstance() {
        if (instance == null) {
            instance = new SocketClient();
        }
        return instance;
    }

    public Socket getSocket() {
        return mSocket;
    }
}
