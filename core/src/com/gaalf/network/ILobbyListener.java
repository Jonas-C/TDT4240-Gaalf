package com.gaalf.network;

import com.gaalf.network.data.PlayerData;

public interface ILobbyListener {
    /**
     * Called when another player joins the multiplayer game lobby.
     * @param playerData the data for the player which joined
     */
    void playerJoined(PlayerData playerData);

    /**
     * Called when a player leaves the lobby.
     * @param playerId the id of the player who left the lobby
     */
    void playerLeft(int playerId);

    /**
     * Called when someone changed a setting in the lobby.
     * @param selectedMapPack the currently selected map pack
     */
    void lobbyStateChanged(int selectedMapPack);

    /**
     * Called when someone started the game.
     * @param mapPack an identifier for the map pack of the game which is starting
     */
    void onGameStarted(String mapPack);
}
