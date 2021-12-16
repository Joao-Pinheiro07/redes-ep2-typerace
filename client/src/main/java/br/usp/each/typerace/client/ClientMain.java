package br.usp.each.typerace.client;

import org.java_websocket.client.WebSocketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class ClientMain {

    private WebSocketClient client;

    public ClientMain(WebSocketClient client) {
        this.client = client;
    }

    public void init(String idCliente) throws InterruptedException {
        System.out.println("Iniciando cliente: " + idCliente);
        client.connectBlocking();
        // TODO: Implementar
    }

    public static void main(String[] args) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        /*
         * FIXME: Remover essas strings fixas
         * Como podemos fazer para que o cliente receba um parâmetro indicando a qual
         * servidor
         * ele deve se conectar e o seu ID?
         */
        String removeMe = "ws://localhost:8080";

        WebSocketClient client;

        try {

            while (true) {
                System.out.println("Digite seu nome:");
                String name = bf.readLine();

                if (name.isEmpty()) {
                    System.out.println("Nome vazio! Por favor digite um nome válido!");
                    continue;
                }

                client = new Client(new URI(removeMe + "/client=" + name));
                ClientMain main = new ClientMain(client);

                main.init(name);

                if (client.isOpen())
                    break;
            }

        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
