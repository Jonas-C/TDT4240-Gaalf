package com.gaalf.view.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MapPackDropdown extends SelectBox<String> {

    private int selectedIndex;
    private Runnable onSelectedChangeCallback;

    public MapPackDropdown(Skin skin, Runnable onSelectedChangeCallback) {
        super(skin, "mapPack");
        selectedIndex = 0;
        this.onSelectedChangeCallback = onSelectedChangeCallback;
        addListener(new InternalChangeListener());
    }

    @Override
    public void setSelectedIndex(int index) {
        selectedIndex = index;
        super.setSelectedIndex(index);
    }

    @Override
    protected void onShow(Actor selectBoxList, boolean below) {
        selectBoxList.getColor().a = 1;
    }

    @Override
    protected void onHide(Actor selectBoxList) {
        selectBoxList.remove();
    }

    private class InternalChangeListener extends ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            if (selectedIndex != getSelectedIndex()) {
                selectedIndex = getSelectedIndex();
                onSelectedChangeCallback.run();
            }
        }
    }
}
