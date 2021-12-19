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
    private TypeRacerSession trSession;

    public Server(int port, Map<String, WebSocket> connections) {
        super(new InetSocketAddress(port));
        this.connections = connections;
        this.trSession = new TypeRacerSession();
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
        if (!reason.equals("playerAlreadyExist"))
            removePlayerFromSession(conn);

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(message);
        if (!trSession.isGameStarted()) {
            switch (message) {
                case "sair":
                    removePlayerFromSession(conn);
                    break;
                case "iniciar":
                    startGame();
                    break;

                default:
                    break;
            }
        } else if (trSession.isGameStarted()) {
            String playerId = getPlayerId(conn.getResourceDescriptor());
            trSession.verifyAnswer(message, playerId);
            String avaiableWords = "palavras disponiveis:\n" + trSession.getPlayerAvaiableWords(playerId);
            conn.send(avaiableWords);
            if (trSession.isThereAWinner())
                endGame();

        }
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
        this.trSession.addPlayerToSession(newPlayer);

        String newPlayerMessage = "\n" + newPlayer + " entrou." + "\nNumero de jogadores: " + getConnectionsNumber();

        broadcast(newPlayerMessage);
        conn.send("Você entrou, Bem vindo " + newPlayer + "!");
        System.out.println(newPlayerMessage);
    }

    private void removePlayerFromSession(WebSocket conn) {
        String playerId = getPlayerId(conn.getResourceDescriptor());
        connections.remove(playerId);
        trSession.removePlayerFromSessionById(playerId);
        broadcast("\n" + playerId + " saiu." + "\nNumero de jogadores: "
                + getConnectionsNumber());
        System.out.println("\n" + playerId + " saiu." + "\nNumero de jogadores: "
                + getConnectionsNumber());
    }

    private void startGame() {
        broadcast(
                "inicio de jogo!\nO primeiro jogador a escrever corretamente das seguintes palavras será o ganhador.\n"
                        + trSession.getWords());
        System.out.println("Inicio de jogo");
        trSession.loadWordListToAllPlayers();
        trSession.changeGameSession(true);
    }

    private void endGame() {
        System.out.println("Fim de jogo");
        broadcast("Jogo finalizado!");
        trSession.changeGameSession(false);

    }
}
