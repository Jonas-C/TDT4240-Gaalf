package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gaalf.game.ecs.ECSObserver;
import com.gaalf.game.ecs.component.WaterComponent;
import com.gaalf.game.enums.ECSEvent;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.manager.SettingsManager;

public class SoundSystem extends IteratingSystem implements ECSObserver {
    private SettingsManager settingsManager;
    private ComponentMapper<SoundComponent> soundMapper;

    public SoundSystem(SettingsManager settingsManager){
        super(Family.all(SoundComponent.class).get());
        soundMapper = ComponentMapper.getFor(SoundComponent.class);
        this.settingsManager = settingsManager;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SoundComponent soundComponent = soundMapper.get(entity);
        if (settingsManager.soundIsEnabled){
            if (soundComponent.shouldBePlayed){
                soundComponent.sound.play(settingsManager.soundVolume);
                soundComponent.shouldBePlayed = false;
            }
        }
    }

    @Override
    public void onEventReceived(ECSEvent event, Entity entity) {
        if(event == ECSEvent.BALL_FIRED){
            SoundComponent soundComponent = soundMapper.get(entity);
            soundComponent.shouldBePlayed = true;
        }
    }
}
