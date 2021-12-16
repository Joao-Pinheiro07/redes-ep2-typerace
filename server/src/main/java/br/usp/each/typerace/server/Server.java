package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server extends WebSocketServer {

    private final Map<String, WebSocket> connections;

    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        if (!isPlayerAlreadyExist(conn)) {
            addPlayerToSession(conn);
        } else {
            conn.send("Esse usuário já existe!\n Tente entrar com outro nome!\n");
            conn.close(1000, "playerAlreadyExist");
        }
        return;
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        if (!reason.equals("playerAlreadyExist")) {
            connections.remove(getPlayerId(conn.getResourceDescriptor()));
            broadcast("\n" + getPlayerId(conn.getResourceDescriptor()) + " saiu." + "\nNumero de jogadores: "
                    + getConnectionsNumber());
            System.out.println("\n" + getPlayerId(conn.getResourceDescriptor()) + " saiu." + "\nNumero de jogadores: "
                    + getConnectionsNumber());
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        // TODO: Implementar
    }

    @Override
    public void onStart() {
        System.out.println("Servidor iniciado!");
    }

    public int getConnectionsNumber() {
        return connections.size();
    }

    public boolean isPlayerAlreadyExist(WebSocket conn) {
        String playerId = getPlayerId(conn.getResourceDescriptor());

        if (connections.containsKey(playerId))
            return true;
        return false;
    }

    public String getPlayerId(String playerDescription) {
        Matcher matcher = Pattern.compile("/client=(?<client>.+)").matcher(playerDescription);

        if (matcher.find())
            return matcher.group(1);
        return "";
    }

    private void addPlayerToSession(WebSocket conn) {
        String newPlayer = getPlayerId(conn.getResourceDescriptor());
        this.connections.put(newPlayer, conn);
        String newPlayerMessage = "\n" + newPlayer + " entrou." + "\nNumero de jogadores: " + getConnectionsNumber();

        broadcast(newPlayerMessage);
        conn.send("Você entrou, Bem vindo " + newPlayer + "!");
        System.out.println(newPlayerMessage);
    }
}
