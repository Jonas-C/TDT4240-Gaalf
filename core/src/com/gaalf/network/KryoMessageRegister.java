package com.gaalf.network;

import com.esotericsoftware.kryo.Kryo;
import com.gaalf.network.message.*;

public class KryoMessageRegister {
    public static void registerMatchmakingMessages(Kryo kryo) {
        kryo.register(TestMessage.class);
        kryo.register(AvailableGameServersRequestMessage.class);
        kryo.register(AvailableGameServersResponseMessage.class);
    }

    public static void registerGameServerMessages(Kryo kryo) {
        kryo.register(PlayerJoinServerMessage.class);
        kryo.register(BallHitMessage.class);
    }
}
