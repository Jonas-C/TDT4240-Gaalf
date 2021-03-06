package com.gaalf.game.util;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import static com.gaalf.game.constants.B2DConstants.PPM;

public class TiledObjectUtil {

    public static Body parseTiledObjectLayer(World world, MapObject object){
        if((object instanceof PolylineMapObject)){
            Shape shape;
            Body body;
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            shape = createPolyLine((PolylineMapObject) object);
            bdef.position.set(((PolylineMapObject) object).getPolyline().getOriginX() / PPM,
                    ((PolylineMapObject) object).getPolyline().getOriginY() / PPM);

            body = world.createBody(bdef);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            body.setUserData(object.getProperties().get("type"));
            //System.out.println(body.getUserData());
            body.createFixture(fixtureDef);
            shape.dispose();
            return body;
        } else {
            throw new IllegalArgumentException("Object must be a Polyline!");
        }
    }

    private static ChainShape createPolyLine(PolylineMapObject polyLine){
        float[] vertices = polyLine.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for(int i = 0; i < worldVertices.length; i++){
            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i*2 + 1] / PPM);
        }

        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }
}
