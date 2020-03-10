package com.gaalf.presenter;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.GaalfGame;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.ecs.system.RenderingSystem;
import com.gaalf.view.BaseGameView;

public abstract class BaseGamePresenter extends BasePresenter implements Screen {


    public OrthographicCamera cam;
    Texture tex = new Texture("badlogic.jpg");

    public BaseGamePresenter(final GaalfGame game){
        super(game);

    }

    @Override
    public abstract BaseGameView getView();

    public abstract void update(float delta);

    @Override
    public void render(float delta){
        super.render(delta);
        update(delta);
        game.getBatch().begin();
        game.getBatch().draw(tex, 250, 250);
        game.getBatch().end();
    }

    @Override
    public void show() {
        getView().show();
    }

    @Override
    public void hide() {
        getView().hide();
    }

    @Override
    public void pause() {
        getView().pause();
    }

    @Override
    public void resume() {
        getView().resume();
    }

    @Override
    public void dispose(){
        getView().dispose();
    }
}
