package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.network.data.PlayerData;
import com.gaalf.network.data.ServerAddress;
import com.gaalf.network.message.*;

import java.util.ArrayList;

public class KryoMessageRegister {
    /**
     * Registers all objects which can be send via KryoNet.
     * NB: Do not change the order or the register calls.
     * @param kryo
     */
    public static void registerMessages(Kryo kryo) {
        kryo.register(ArrayList.class);
        kryo.register(Vector2.class);

        kryo.register(GameData.class);
        kryo.register(GameServerSpecification.class);
        kryo.register(PlayerData.class);
        kryo.register(ServerAddress.class);

        kryo.register(AvailableGameServersRequestMessage.class);
        kryo.register(AvailableGameServersResponseMessage.class);
        kryo.register(BallHitMessage.class);
        kryo.register(GameServerStatusMessage.class);
        kryo.register(JoinGameAcceptedMessage.class);
        kryo.register(JoinGameRejectedMessage.class);
        kryo.register(JoinGameRequestMessage.class);
        kryo.register(LeaveGameMessage.class);
        kryo.register(PlayerJoinedMessage.class);
    }
}
