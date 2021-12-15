package br.usp.each.typerace.server;

import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        WebSocketServer server = new Server(8080, new HashMap<>());
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        ServerMain main = new ServerMain(server);

        main.init();

        String command = "vazio!";

        while(!command.equals("fechar")) {
            command = bf.readLine();
        }

        server.stop();

        System.out.println("Servidor finalizado.");

    }

}
