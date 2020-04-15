package com.gaalf.network.message;

import com.gaalf.network.data.GameData;

public class JoinLobbyAcceptedMessage extends Message {

    public int yourPlayerId;
    public GameData gameData;

    public JoinLobbyAcceptedMessage() {
    }

    public JoinLobbyAcceptedMessage(int yourPlayerId, GameData gameData) {
        this.yourPlayerId = yourPlayerId;
        this.gameData = gameData;
    }
}
