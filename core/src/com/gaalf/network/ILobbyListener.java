package com.gaalf.network;

import com.gaalf.network.data.PlayerData;

public interface ILobbyListener {
    /**
     * Called when another player joins the multiplayer game.
     * @param playerData the data for the player which joined
     */
    void playerJoined(PlayerData playerData);

    void playerLeft(int playerId);

    void onGameStarted();
}
