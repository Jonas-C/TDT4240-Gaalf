package com.gaalf.game.ecs;

import com.badlogic.ashley.core.Entity;
import com.gaalf.game.enums.ECSEvent;

public interface ECSObserver {
    void onEventReceived(ECSEvent event, Entity entity);
}
