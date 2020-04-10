package com.gaalf.server.matchmaking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.network.data.ServerAddress;
import com.gaalf.network.message.AvailableGameServersResponseMessage;
import com.gaalf.network.message.GameServerStatusMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchmakingServer {

    private static final Logger log = LoggerFactory.getLogger(MatchmakingServer.class);

    private Server kryoServer;
    private List<ServerAddress> gameServers;

    public MatchmakingServer(Server kryoServer) {
        this.kryoServer = kryoServer;
        gameServers = new ArrayList<>();
        gameServers.add(new ServerAddress("mchyll.no", 7001));
        gameServers.add(new ServerAddress("mchyll.no", 7002));
        gameServers.add(new ServerAddress("mchyll.no", 7003));
        gameServers.add(new ServerAddress("mchyll.no", 7004));
        gameServers.add(new ServerAddress("mchyll.no", 7005));
    }

    public void availableGameServersRequest(Connection connection) {
        connection.sendTCP(new AvailableGameServersResponseMessage(getAvailableGameServers()));
    }

    public List<GameServerSpecification> getAvailableGameServers() {
        List<GameServerSpecification> servers = new ArrayList<>();

        for (ServerAddress serverAddress : gameServers) {
            log.debug("Checking server " + serverAddress);
            try (GameServerStatusClient statusClient = new GameServerStatusClient(serverAddress)) {
                GameServerStatusMessage status = statusClient.getStatus();
                log.debug("Server is online, {}/{} players online, game started: {}",
                        status.connectedPlayers, status.maxPlayers, status.gameStarted);

                if (!status.gameStarted && status.connectedPlayers < status.maxPlayers) {
                    servers.add(new GameServerSpecification(
                            serverAddress, status.connectedPlayers, status.maxPlayers));
                }
            } catch (IOException e) {
                log.trace("IOException", e);
                log.debug("I assume server is down, skipping");
                // Server is most likely down, skip it
            }
        }

        return servers;
    }
}
