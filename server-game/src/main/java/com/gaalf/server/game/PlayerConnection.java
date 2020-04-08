package com.gaalf.server.game;

import com.esotericsoftware.kryonet.Connection;
import com.gaalf.network.data.PlayerData;

public class PlayerConnection extends Connection {

    public PlayerData playerData;
    public boolean hasJoined;

    public PlayerConnection() {
        super();
        playerData = new PlayerData();
        hasJoined = false;
    }
}
