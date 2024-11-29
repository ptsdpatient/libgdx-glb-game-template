package com.fps.pg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayGround extends Game {
    public SpriteBatch batch;
    public StartScreen startScreen;

    @Override
    public void create() {
//      Gdx.graphics.setCursor( Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursor.png")), 0, 0));
        batch=new SpriteBatch();
        startScreen=new StartScreen(this);
        setScreen(startScreen);
    }
}
