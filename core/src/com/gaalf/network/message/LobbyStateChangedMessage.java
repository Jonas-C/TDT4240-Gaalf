package com.gaalf.network.message;

public class LobbyStateChangedMessage extends Message {

    public int selectedMapPack;

    public LobbyStateChangedMessage() {
    }

    public LobbyStateChangedMessage(int selectedMapPack) {
        this.selectedMapPack = selectedMapPack;
    }
}
