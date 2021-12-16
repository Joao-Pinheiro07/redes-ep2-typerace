package br.usp.each.typerace.server;

import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.util.HashMap;

public class ServerMain {

    private WebSocketServer server;

    public ServerMain(WebSocketServer server) {
        this.server = server;
    }

    public void init() {
        System.out.println("Iniciando servidor...");
        server.start();
        // TODO: Implementar
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println(WordList.getWordList());
        // WebSocketServer server = new Server(8080, new HashMap<>());

        // ServerMain main = new ServerMain(server);

        // main.init();

    }

}
