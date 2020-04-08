package com.gaalf.server.matchmaking;

import com.gaalf.network.GameServerSpecification;

import java.util.ArrayList;
import java.util.List;

public class MatchmakingServer {

    private List<String> gameServers;

    public MatchmakingServer() {
        gameServers = new ArrayList<>();
        gameServers.add("gaalf.mchyll.no:7001");
    }

    public List<GameServerSpecification> getAvailableGameServers() {
        List<GameServerSpecification> servers = new ArrayList<>();

        for (String serverHost : gameServers) {
            GameServerSpecification gameServerEntry = new GameServerSpecification();
            gameServerEntry.host = serverHost;
            // TODO poll configured game servers for status
            gameServerEntry.players = 0;
            servers.add(gameServerEntry);
        }

        return servers;
    }
}
