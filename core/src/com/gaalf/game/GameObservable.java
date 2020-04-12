package com.gaalf.game;

import com.gaalf.game.enums.GameEvent;

public interface GameObservable {
    void addListener(GameObserver observer);
    void removeListener(GameObserver observer);
    void notifyObservers(GameEvent gameEvent, Object obj);
}
