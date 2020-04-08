package com.gaalf.server.matchmaking;

import com.esotericsoftware.kryonet.Server;
import com.gaalf.network.KryoMessageRegister;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server kryoServer = new Server();
        KryoMessageRegister.registerMatchmakingMessages(kryoServer.getKryo());

        MatchmakingServer matchmakingServer = new MatchmakingServer();

        kryoServer.addListener(new ConnectionListener(matchmakingServer));
        kryoServer.bind(7000);
        kryoServer.start();

        System.out.println("Matchmaking server started");

//        Client client = new Client();
//        client.getKryo().register(TestMessage.class);
//        client.start();
//        client.connect(5000, "localhost", 7000);
//        client.sendTCP(new TestMessage("helå"));
//        System.out.println("Client connected and sent message");
//
//        Thread.sleep(100);
//        client.stop();
//
//        kryoServer.stop();
    }
}
