package com.gaalf.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.GaalfGame;
import com.gaalf.presenter.BaseGamePresenter;
import com.gaalf.presenter.GamePresenter;

public class GameView extends BaseGameView {
    Texture texture = new Texture("badlogic.jpg");

    public GameView(SpriteBatch batch, BaseGamePresenter presenter) {
    super(batch, presenter);
    TextButton playButton = new TextButton("Playing", getSkin());
        playButton.addListener(new ChangeListener() {
        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            System.out.println("hihi");
        }
    });
    getTable().add(playButton).padTop(50);
    getTable().row();
    Sprite sprite = new Sprite(texture);
    Image image = new Image(sprite);
//    getTable().add(image);
    addActor(table);

    }

//    @Override
//    public void render(float delta){
//        super.render(delta);
//        System.out.println("ye");
//        getCamera().update();
//        getBatch().setProjectionMatrix(getCamera().combined);
//        getBatch().begin();
//        getBatch().draw(texture, GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT);
//        getBatch().end();
//    }

    @Override
    public void render(float delta){
//        getCamera().update();
//        getBatch().setProjectionMatrix(getCamera().combined);
//        getBatch().begin();
//        getBatch().draw(texture, GaalfGame.V_WIDTH / 2, GaalfGame.V_HEIGHT / 2);
//        getBatch().end();
        super.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
