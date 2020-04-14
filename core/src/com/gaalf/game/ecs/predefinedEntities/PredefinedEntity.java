package com.gaalf.game.ecs.predefinedEntities;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gaalf.game.ecs.component.SpriteComponent;

public abstract class PredefinedEntity extends Entity {

    protected SpriteComponent addSpriteComponent(Sprite sprite){
        SpriteComponent spriteComponent = new SpriteComponent();
        spriteComponent.sprite = sprite;
        this.add(spriteComponent);
        return spriteComponent;
    }
}
