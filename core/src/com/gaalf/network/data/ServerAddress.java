package com.gaalf.network.data;

public class ServerAddress {

    private String hostname;
    private int port;

    public ServerAddress(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return hostname + ':' + port;
    }
}
