package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.gaalf.presenter.BaseGamePresenter;

import java.util.HashMap;

public class MPGameView extends BaseGameView {
    private HashMap<Integer, Label> playerScoreLabels = new HashMap<>();
    private final String TAG = GameView.class.getSimpleName();

    public MPGameView(SpriteBatch batch, final BaseGamePresenter presenter) {
        super(batch, presenter);


        Array<TextButton> extraClearedButtons = new Array<>();
        extraClearedButtons.add(nextLevelButton, createExitMainMenuButton());
        levelClearedWindow = createModal("Level cleared!", extraClearedButtons);

        Array<TextButton> pauseButtons = new Array<>();
        pauseButtons.add(resumeButton, createExitMainMenuButton());
        pauseWindow = createModal("Pause", pauseButtons);

        addActor(table);
        addActor(pauseWindow);
        addActor(levelClearedWindow);
    }

    @Override
    public void render(float delta){
        super.draw();
    }

}
