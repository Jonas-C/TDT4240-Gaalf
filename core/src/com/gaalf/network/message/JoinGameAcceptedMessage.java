package com.gaalf.network.message;

import com.gaalf.network.data.GameData;

public class JoinGameAcceptedMessage extends Message {

    public int yourPlayerId;
    public GameData gameData;

    public JoinGameAcceptedMessage() {
    }

    public JoinGameAcceptedMessage(int yourPlayerId, GameData gameData) {
        this.yourPlayerId = yourPlayerId;
        this.gameData = gameData;
    }
}
