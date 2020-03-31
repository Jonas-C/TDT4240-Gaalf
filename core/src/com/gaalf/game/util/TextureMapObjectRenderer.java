package com.gaalf.game.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import static com.gaalf.game.constants.B2DConstants.*;

public class TextureMapObjectRenderer extends OrthogonalTiledMapRenderer {

    public TextureMapObjectRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    @Override
    public void renderObject(MapObject object){
        if(object instanceof TextureMapObject){
            TextureMapObject textureObject= (TextureMapObject) object;
            batch.draw(textureObject.getTextureRegion(), textureObject.getX() / PPM, textureObject.getY() / PPM, textureObject.getOriginX(), textureObject.getOriginY(),
                    textureObject.getTextureRegion().getRegionWidth(), textureObject.getTextureRegion().getRegionHeight(), textureObject.getScaleX() / PPM, textureObject.getScaleY() / PPM, 360 - textureObject.getRotation());
        }
    }
}
