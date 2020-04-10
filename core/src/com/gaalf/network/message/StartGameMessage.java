package com.gaalf.network.message;

public class StartGameMessage extends Message {

    public String mapPack;

    public StartGameMessage() {
    }

    public StartGameMessage(String mapPack) {
        this.mapPack = mapPack;
    }
}
