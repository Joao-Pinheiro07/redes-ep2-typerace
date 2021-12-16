package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        // TODO: Implementar
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
        // TODO: Implementar
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (!reason.equals("playerAlreadyExist")) {
            System.out.println("Saindo da sessão");
        }
    }

    @Override
    public void onError(Exception ex) {
        // TODO: Implementar
    }
}
