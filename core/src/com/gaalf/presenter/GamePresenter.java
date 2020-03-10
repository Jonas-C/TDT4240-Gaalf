package com.gaalf.presenter;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
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
import com.gaalf.view.BaseView;
import com.gaalf.view.GameView;

public class GamePresenter extends BaseGamePresenter {

    private BaseGameView view;
    Engine engine;
    World world;


    public GamePresenter(final GaalfGame game){
        super(game);
        view = new GameView(game.getBatch(), this);
        world = new World(new Vector2(-9.81f, 0), true);
        engine = new Engine();
        cam = new OrthographicCamera();

        RenderingSystem renderingSystem = new RenderingSystem(game.getBatch());
        TextureComponent textureComponent = new TextureComponent();
        Texture texture = new Texture("badlogic.jpg");
        textureComponent.texture = new TextureRegion(texture, 0, 0, texture.getHeight(), texture.getWidth());
        engine.addSystem(renderingSystem);
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.pos.set(GaalfGame.V_WIDTH / 2, GaalfGame.V_HEIGHT / 2);
        transformComponent.scale.set(1, 1);
        transformComponent.rotation = 0f;
        transformComponent.visible = true;

        Entity e = new Entity();
        e.add(transformComponent);
        e.add(textureComponent);

        engine.addEntity(e);

    }

    public void update(float delta){
        engine.update(delta);
        getView().update(delta);
    }


    @Override
    public BaseGameView getView() {
        return view;
    }
}
