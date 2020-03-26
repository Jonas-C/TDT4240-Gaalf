package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Sound;
import com.gaalf.game.ecs.component.SoundEffectComponent;

public class SoundSystem extends IteratingSystem {

    private ComponentMapper<SoundEffectComponent> soundMapper;

    public SoundSystem(){
        super(Family.all(SoundEffectComponent.class).get());
        soundMapper = ComponentMapper.getFor(SoundEffectComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SoundEffectComponent soundComponent = soundMapper.get(entity);
        /*for (Sound sound : soundComponent.sounds){
            sound.play();
        }*/
        soundComponent.sound.play();
    }
}
