package com.gaalf.server.game;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.gaalf.network.KryoMessageRegister;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Please supply a port number to bind");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        Server kryoServer = new Server() {
            @Override
            protected Connection newConnection() {
                return new PlayerConnection();
            }
        };
        KryoMessageRegister.registerGameServerMessages(kryoServer.getKryo());

        GameServer gameServer = new GameServer(kryoServer);

        kryoServer.addListener(new ConnectionListener(gameServer));
        kryoServer.bind(port);
        kryoServer.start();

        System.out.println("Game server started on port " + port);
    }
}
