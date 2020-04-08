package com.gaalf.network.message;

import com.gaalf.network.data.PlayerData;

public class PlayerJoinedMessage extends Message {

    public PlayerData player;

    public PlayerJoinedMessage() {
        player = new PlayerData();
    }

    public PlayerJoinedMessage(PlayerData player) {
        this.player = player;
    }
}
