package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.gaalf.presenter.BaseGamePresenter;

public class MPGameView extends BaseGameView {
    private final String TAG = GameView.class.getSimpleName();

    public MPGameView(SpriteBatch batch, final BaseGamePresenter presenter) {
        super(batch, presenter);


        Array<TextButton> extraClearedButtons = new Array<>();
        extraClearedButtons.add(nextLevelButton, createExitMainMenuButton());
        levelClearedWindow = createModal("Level cleared!", extraClearedButtons, true);

        Array<TextButton> pauseButtons = new Array<>();
        pauseButtons.add(resumeButton, createExitMainMenuButton());
        pauseWindow = createModal("Pause", pauseButtons, false);

        addActor(table);
        addActor(pauseWindow);
        addActor(levelClearedWindow);
    }

    @Override
    public void render(float delta){
        super.draw();
    }

}
