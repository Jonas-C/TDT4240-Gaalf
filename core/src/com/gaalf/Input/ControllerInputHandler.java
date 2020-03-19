package com.gaalf.Input;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.InputProcessor;
import com.gaalf.game.ecs.component.MovementComponent;

public class ControllerInputHandler implements InputProcessor {
    private Entity playerEntity;

    private float inputXDown = 0;
    private float inputYDown = 0;

    public ControllerInputHandler() {
    }

    public void setControlledEntity(Entity player){
        this.playerEntity = player;
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
        inputXDown = screenX;
        inputYDown = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        float distanceX = inputXDown - screenX;
        float distanceY = inputYDown - screenY;
        MovementComponent movementComponent = ComponentMapper.getFor(MovementComponent.class).get(playerEntity);
        movementComponent.setForceX(distanceX);
        movementComponent.setForceY(distanceY);
        movementComponent.setMoved(true);
        inputYDown = 0;
        inputXDown = 0;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO: endre på en indikator pil.

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
