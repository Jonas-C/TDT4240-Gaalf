package com.gaalf.game.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;

public class SoundComponent implements Component {
    public Sound sound;

    public void setSound(Sound sound){
        this.sound = sound;
    }

    public Sound getSound(){return sound;}
}
