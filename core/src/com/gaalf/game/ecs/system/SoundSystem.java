package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gaalf.game.ecs.component.SoundComponent;

public class SoundSystem extends IteratingSystem {

    private ComponentMapper<SoundComponent> soundMapper;

    public SoundSystem(){
        super(Family.all(SoundComponent.class).get());
        soundMapper = ComponentMapper.getFor(SoundComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SoundComponent soundComponent = soundMapper.get(entity);

        if (soundComponent.shouldBePlayed){
            soundComponent.sound.play();
            soundComponent.shouldBePlayed = false;
        }
    }
}
