package com.gaalf.network.message;

import com.gaalf.network.data.GameServerSpecification;

import java.util.ArrayList;
import java.util.List;

public class AvailableGameServersResponseMessage extends Message {

    public List<GameServerSpecification> servers;

    public AvailableGameServersResponseMessage() {
        servers = new ArrayList<>();
    }

    public AvailableGameServersResponseMessage(List<GameServerSpecification> servers) {
        this.servers = servers;
    }
}
