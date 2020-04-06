package com.gaalf.game.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.gaalf.GaalfGame;

import java.util.Observable;

public class ShotInputHandler extends Observable implements InputProcessor {

    private Vector2 touchPos;

    public ShotInputHandler() {
        touchPos = new Vector2();
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        setChanged();
        notifyObservers("touchDown");
        clearChanged();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        setChanged();
        notifyObservers("touchUp");
        clearChanged();
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touchPos.set(screenX, GaalfGame.V_HEIGHT - screenY);
        setChanged();
        notifyObservers(touchPos);
        clearChanged();
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
