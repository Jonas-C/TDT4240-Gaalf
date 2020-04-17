package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.gaalf.presenter.BaseGamePresenter;

public class GameView extends BaseGameView {

    private final String TAG = GameView.class.getSimpleName();

    public GameView(SpriteBatch batch, final BaseGamePresenter presenter) {
        super(batch, presenter);
        Array<TextButton> pauseButtons = new Array<>();
        pauseButtons.add(resumeButton, createExitLevelSelectButton(), createExitMainMenuButton());
        pauseWindow = createModal("Pause", pauseButtons, false);

        Array<TextButton> extraClearedButtons = new Array<>();
        extraClearedButtons.add(nextLevelButton, createExitLevelSelectButton(), createExitMainMenuButton());
        levelClearedWindow = createModal("Level cleared!", extraClearedButtons, true);


        addActor(table);
        addActor(pauseWindow);
        addActor(levelClearedWindow);
    }

    @Override
    public void render(float delta){
        super.draw();
    }

    @Override
    public void hide() {

    }
}
