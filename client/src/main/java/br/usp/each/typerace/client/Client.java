package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Client extends WebSocketClient {

    public static final String YELLOW = "\033[0;36m";
    public static final String RESET = "\033[0m";

    public Client(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        // TODO: Implementar
    }

    @Override
    public void onMessage(String message) {
        System.out.println(YELLOW + message +
                RESET);
        // TODO: Implementar
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (!reason.equals("playerAlreadyExist")) {
            System.out.println("Saindo da sessão");
            System.exit(0);
        }
    }

    @Override
    public void onError(Exception ex) {
        // TODO: Implementar
    }
}
