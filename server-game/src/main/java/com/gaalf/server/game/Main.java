package com.gaalf.server.game;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.gaalf.network.KryoMessageRegister;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Please supply a port number to bind");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        MinLogAdapter.registerSLF4J();

        Server kryoServer = new Server() {
            @Override
            protected Connection newConnection() {
                return new PlayerConnection();
            }
        };
        KryoMessageRegister.registerMessages(kryoServer.getKryo());

        GameServer gameServer = new GameServer(kryoServer);

        kryoServer.addListener(new ConnectionListener(gameServer));
        kryoServer.bind(port);
        kryoServer.start();

        log.info("Game server started on port " + port);
    }
}
