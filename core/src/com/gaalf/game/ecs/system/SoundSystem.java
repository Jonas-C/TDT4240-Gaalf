package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.manager.SettingsManager;

public class SoundSystem extends IteratingSystem {
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
}
