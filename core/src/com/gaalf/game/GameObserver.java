package com.gaalf.game;

import com.gaalf.game.enums.GameEvent;

public interface GameObserver {
    void onReceiveEvent(GameEvent event, Object object);
}
