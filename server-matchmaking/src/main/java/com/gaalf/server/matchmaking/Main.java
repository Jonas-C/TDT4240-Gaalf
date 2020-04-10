package com.gaalf.server.matchmaking;

import com.esotericsoftware.kryonet.Server;
import com.gaalf.network.KryoMessageRegister;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server kryoServer = new Server();
        KryoMessageRegister.registerMessages(kryoServer.getKryo());

        MatchmakingServer matchmakingServer = new MatchmakingServer();

        kryoServer.addListener(new ConnectionListener(matchmakingServer));
        kryoServer.bind(7000);
        kryoServer.start();

        System.out.println("Matchmaking server started");
    }
}
