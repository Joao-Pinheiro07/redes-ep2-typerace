package br.usp.each.typerace.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
        if (!reason.equals("playerAlreadyExist")) {
            String playerId = getPlayerId(conn.getResourceDescriptor());
            broadcast("\n" + playerId + " saiu." + " Numero de jogadores na sessão: "
                    + getConnectionsNumber());
            System.out.println("\n" + playerId + " saiu." + " Numero de jogadores na sessão: "
                    + getConnectionsNumber());
        }

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (!trSession.isGameStarted()) {
            switch (message) {
                case "sair":
                    removePlayerFromSession(conn);
                    break;
                case "iniciar":
                    startGame(getPlayerId(conn.getResourceDescriptor()));
                    break;

                default:
                    break;
            }
        } else if (trSession.isGameStarted()) {
            String playerId = getPlayerId(conn.getResourceDescriptor());
            boolean isCorrectAnswer = trSession.verifyAnswer(message, playerId);
            String correctAnswer = "Voce acertou!\n";
            if (!isCorrectAnswer)
                correctAnswer = "Voce errou.\n";
            conn.send(correctAnswer + trSession.getPlayerAvaiableWords(playerId));
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

        String newPlayerMessage = "" + newPlayer + " entrou." + " Numero de jogadores na sessão: "
                + getConnectionsNumber();

        broadcast(newPlayerMessage);
        conn.send("Bem vindo ao Typerace, " + newPlayer
                + "!\n -Caso deseje sair da sessão antes do inicio de uma partida, digite \"sair\".\n -Caso dejese iniciar a patida para to a sessão, digite \"iniciar\".");
        System.out.println(newPlayerMessage);
    }

    private void removePlayerFromSession(WebSocket conn) {
        String playerId = getPlayerId(conn.getResourceDescriptor());
        connections.remove(playerId);
        trSession.removePlayerFromSessionById(playerId);
        conn.close();
    }

    private void startGame(String player) {
        startCoutdown(player, 5);
        broadcast(
                "Inicio de jogo!\nO primeiro jogador que escrever corretamente as seguintes palavras será o ganhador.Boa sorte!\n"
                        + trSession.getAvaiableWords());
        System.out.println("Inicio de jogo!");
        trSession.loadWordListToAllPlayers();
        trSession.startGameSession();
    }

    private void endGame() {
        trSession.endGameSession();
        System.out.println("Fim de jogo!\n\n" + trSession.getScoreBoard());
        broadcast("Fim de jogo!\n\n" + trSession.getScoreBoard());
    }

    private void startCoutdown(String player, int seconds) {
        broadcast(player + " enviou o sinal de inicio! A partida começará em:");
        for (int i = seconds; i > 1; i--) {
            broadcast(i + "...\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
