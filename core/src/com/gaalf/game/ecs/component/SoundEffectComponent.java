package com.gaalf.game.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class SoundEffectComponent implements Component {
    public ArrayList<Sound> sounds = new ArrayList<Sound>();
    public Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/JumpSound.wav"));
}
