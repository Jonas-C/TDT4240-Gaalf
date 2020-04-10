package com.gaalf.game.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.gaalf.GaalfGame;
import com.gaalf.game.GameObservable;
import com.gaalf.game.GameObserver;
import com.gaalf.game.enums.GameEvent;

import java.util.ArrayList;

public class ShotInputHandler implements InputProcessor, GameObservable {

    private ArrayList<GameObserver> gameObservers;

    private Vector2 touchPos;

    public ShotInputHandler() {
        gameObservers = new ArrayList<>();
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
        notifyObservers(GameEvent.TOUCH_DOWN, touchPos);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        notifyObservers(GameEvent.TOUCH_UP, touchPos);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touchPos.set(screenX, GaalfGame.V_HEIGHT - screenY);
        notifyObservers(GameEvent.TOUCH_DRAG, touchPos);
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

    @Override
    public void addListener(GameObserver observer) {
        gameObservers.add(observer);
    }

    @Override
    public void removeListener(GameObserver observer) {
        gameObservers.remove(observer);

    }

    @Override
    public void notifyObservers(GameEvent gameEvent, Object obj) {
        for(GameObserver observer : gameObservers){
            observer.onReceiveEvent(gameEvent, obj);
        }
    }
}
