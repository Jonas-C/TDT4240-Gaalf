package com.gaalf.server.matchmaking;

import com.esotericsoftware.kryonet.Connection;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.network.message.AvailableGameServersResponseMessage;
import com.gaalf.network.message.GameServerStatusMessage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class MatchmakingServer {

    private List<InetSocketAddress> gameServers;

    public MatchmakingServer() {
        gameServers = new ArrayList<>();
        gameServers.add(new InetSocketAddress("gaalf.mchyll.no", 7001));
        gameServers.add(new InetSocketAddress("gaalf.mchyll.no", 7002));
        gameServers.add(new InetSocketAddress("gaalf.mchyll.no", 7003));
        gameServers.add(new InetSocketAddress("gaalf.mchyll.no", 7004));
        gameServers.add(new InetSocketAddress("gaalf.mchyll.no", 7005));
    }

    public void availableGameServersRequest(Connection connection) {
        connection.sendTCP(new AvailableGameServersResponseMessage(getAvailableGameServers()));
    }

    public List<GameServerSpecification> getAvailableGameServers() {
        List<GameServerSpecification> servers = new ArrayList<>();

        for (InetSocketAddress serverAddress : gameServers) {
            try (GameServerStatusClient statusClient = new GameServerStatusClient(serverAddress)) {
                GameServerStatusMessage status = statusClient.getStatus();
                if (status.connectedPlayers < status.maxPlayers) {
                    GameServerSpecification gameServerEntry = new GameServerSpecification();
                    gameServerEntry.host = serverAddress.toString();
                    gameServerEntry.players = status.connectedPlayers;
                    servers.add(gameServerEntry);
                }
            } catch (IOException e) {
                // Server is most likely down, skip it
            }
        }

        return servers;
    }
}
