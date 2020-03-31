package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Sound;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.game.ecs.component.SoundEffectComponent;
import com.gaalf.game.ecs.component.SoundFinishComponent;

public class SoundSystem extends IteratingSystem {

    private ComponentMapper<SoundEffectComponent> soundMapper;
    private ComponentMapper<SoundFinishComponent> soundFinishMapper;

    public SoundSystem(){
        super(Family.all().one(SoundEffectComponent.class, SoundFinishComponent.class).get());
        soundMapper = ComponentMapper.getFor(SoundEffectComponent.class);
        soundFinishMapper = ComponentMapper.getFor(SoundFinishComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SoundEffectComponent soundComponent = soundMapper.get(entity);
        SoundFinishComponent soundFinishComponent = soundFinishMapper.get(entity);

        if (soundComponent != null){
            soundComponent.sound.play();
            entity.remove(SoundEffectComponent.class);
        }

        if (soundFinishComponent != null){
            soundFinishComponent.sound.play();
            entity.remove(SoundFinishComponent.class);
        }

    }
}
