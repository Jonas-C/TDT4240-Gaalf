package com.gaalf.view.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MapPackDropdown extends SelectBox<String> {
    public MapPackDropdown(Skin skin) {
        super(skin, "mapPack");
    }

    @Override
    protected void onShow(Actor selectBoxList, boolean below) {
        selectBoxList.getColor().a = 1;
    }

    @Override
    protected void onHide(Actor selectBoxList) {
        selectBoxList.getColor().a = 0;
        selectBoxList.remove();
    }
}
