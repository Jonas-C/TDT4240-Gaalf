package com.gaalf.server.matchmaking;

import com.esotericsoftware.kryonet.Server;
import com.gaalf.network.KryoMessageRegister;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        boolean local = false;
        if (args.length > 0 && args[0].equals("local")) {
            local = true;
            log.info("Starting in local environment mode");
        }

        MinLogAdapter.registerSLF4J();

        Server kryoServer = new Server();
        KryoMessageRegister.registerMessages(kryoServer.getKryo());

        MatchmakingServer matchmakingServer = new MatchmakingServer(kryoServer, local);

        kryoServer.addListener(new ConnectionListener(matchmakingServer));
        kryoServer.bind(7000);
        kryoServer.start();

        log.info("Matchmaking server started");
    }
}
