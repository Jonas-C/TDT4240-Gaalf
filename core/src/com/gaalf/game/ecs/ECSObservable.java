package com.gaalf.game.ecs;

import com.badlogic.ashley.core.Entity;
import com.gaalf.game.enums.ECSEvent;

public interface ECSObservable {
    void addListener(ECSObserver observer);
    void unregister(ECSObserver observer);
    void notifyObservers(ECSEvent event, Entity entity);

}
