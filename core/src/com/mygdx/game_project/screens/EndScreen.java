package com.mygdx.game_project.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game_project.MainClass;

import static com.mygdx.game_project.constants.Constant.*;

public class EndScreen implements Screen {
    final MainClass mainClass;
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    public EndScreen(MainClass mainClass) {
        this.mainClass = mainClass;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
