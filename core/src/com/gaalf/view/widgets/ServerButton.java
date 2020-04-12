package com.gaalf.view.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class ServerButton extends Button {

    private Label serverNameLabel;
    private Label playersLabel;
    private Table infoTable;
    private TextButton.TextButtonStyle style;

    public ServerButton (String serverName, int currentPlayers, int maxPlayers, Skin skin) {
        this(serverName, currentPlayers, maxPlayers, skin.get(TextButton.TextButtonStyle.class));
        setSkin(skin);
    }

    public ServerButton (String serverName, int currentPlayers, int maxPlayers, TextButton.TextButtonStyle style) {
        super();
        setStyle(style);
        this.style = style;
        this.infoTable = new Table();
        this.serverNameLabel = new Label(serverName, new Label.LabelStyle(style.font, style.fontColor));
        this.serverNameLabel.setAlignment(Align.center);
        this.playersLabel = new Label(currentPlayers + "/" + maxPlayers + " players", new Label.LabelStyle(style.font, style.fontColor));
        infoTable.add(serverNameLabel).padRight(30);
        infoTable.add(playersLabel);
        infoTable.pad(10);
        add(infoTable).expand().fill();
        setSize(getPrefWidth(), getPrefHeight());
    }

    public void setStyle (ButtonStyle style) {
        if (style == null) throw new NullPointerException("style cannot be null");
        if (!(style instanceof TextButton.TextButtonStyle)) throw new IllegalArgumentException("style must be a TextButtonStyle.");
        super.setStyle(style);
        this.style = (TextButton.TextButtonStyle)style;
        if (serverNameLabel != null) {
            TextButton.TextButtonStyle textButtonStyle = (TextButton.TextButtonStyle)style;
            Label.LabelStyle labelStyle = serverNameLabel.getStyle();
            labelStyle.font = textButtonStyle.font;
            labelStyle.fontColor = textButtonStyle.fontColor;
            serverNameLabel.setStyle(labelStyle);
            playersLabel.setStyle(labelStyle);
        }
    }

    public TextButton.TextButtonStyle getStyle () {
        return style;
    }

    public void draw (Batch batch, float parentAlpha) {
        Color fontColor;
        if (isDisabled() && style.disabledFontColor != null)
            fontColor = style.disabledFontColor;
        else if (isPressed() && style.downFontColor != null)
            fontColor = style.downFontColor;
        else if (isChecked() && style.checkedFontColor != null)
            fontColor = (isOver() && style.checkedOverFontColor != null) ? style.checkedOverFontColor : style.checkedFontColor;
        else if (isOver() && style.overFontColor != null)
            fontColor = style.overFontColor;
        else
            fontColor = style.fontColor;
        if (fontColor != null) serverNameLabel.getStyle().fontColor = fontColor;
        super.draw(batch, parentAlpha);
    }
}
