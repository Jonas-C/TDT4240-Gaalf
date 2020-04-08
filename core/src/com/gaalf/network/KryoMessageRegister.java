package com.gaalf.network;

import com.esotericsoftware.kryo.Kryo;
import com.gaalf.network.message.*;

public class KryoMessageRegister {
    public static void registerMatchmakingMessages(Kryo kryo) {
        kryo.register(AvailableGameServersRequestMessage.class);
        kryo.register(AvailableGameServersResponseMessage.class);
    }

    public static void registerGameServerMessages(Kryo kryo) {
        kryo.register(GameServerStatusMessage.class);
        kryo.register(JoinGameRequestMessage.class);
        kryo.register(JoinGameRejectedMessage.class);
        kryo.register(JoinGameAcceptedMessage.class);
        kryo.register(PlayerJoinedMessage.class);
        kryo.register(BallHitMessage.class);
        kryo.register(LeaveGameMessage.class);
    }
}
