package com.gaalf.game.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.GaalfGame;
import static com.gaalf.game.constants.B2DConstants.*;

public class B2dDebugUtil {

    public static void createWalls(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((GaalfGame.V_WIDTH /PPM) / 2, 0);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body bc = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((GaalfGame.V_WIDTH / PPM) / 2, 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        bc.createFixture(fixtureDef);

        bodyDef = new BodyDef();
        bodyDef.position.set((GaalfGame.V_WIDTH / PPM) / 2, (GaalfGame.V_HEIGHT / PPM) - 0.01f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bc = world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox((GaalfGame.V_WIDTH / PPM) / 2, 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        bc.createFixture(fixtureDef);

        bodyDef = new BodyDef();
        bodyDef.position.set(0.01f, (GaalfGame.V_HEIGHT / PPM) / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bc = world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(0.01f, (GaalfGame.V_HEIGHT / PPM) / 2);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        bc.createFixture(fixtureDef);

        bodyDef = new BodyDef();
        bodyDef.position.set(GaalfGame.V_WIDTH / PPM, (GaalfGame.V_HEIGHT / PPM) / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bc = world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(0.01f, (GaalfGame.V_HEIGHT / PPM) / 2);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        bc.createFixture(fixtureDef);
    }
}
