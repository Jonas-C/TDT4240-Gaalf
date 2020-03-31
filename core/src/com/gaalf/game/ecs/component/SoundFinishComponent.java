package com.gaalf.game.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundFinishComponent implements Component {
    public Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/FinishSound.wav"));
}
