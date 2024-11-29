package com.fps.pg;
import static com.fps.pg.GameInput.StartScreenInputProcessor;
import static com.fps.pg.Methods.files;
import static com.fps.pg.Methods.loadModel;
import static com.fps.pg.Methods.print;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.GL20;


public class StartScreen implements Screen {

    public PlayGround game;
    public GameWorld world;

    public StartScreen(PlayGround game){

        this.game=game;

        Gdx.input.setCursorCatched(true);

        world=new GameWorld();

        world.addObject("floor_basic");

    }

    @Override
    public void show() {
        StartScreenInputProcessor();
    }

    @Override
    public void render(float delta) {
        
        world.render(delta);

    }

    @Override
    public void resize(int width, int height) {
        world.resize(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }
}
