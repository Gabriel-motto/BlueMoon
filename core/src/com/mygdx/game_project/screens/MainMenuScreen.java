package com.mygdx.game_project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game_project.MainClass;
import static com.mygdx.game_project.constants.Constant.*;

public class MainMenuScreen implements Screen {
    final MainClass mainClass;
    private OrthographicCamera camera;
    private TextButton textButton;
    private TextButton.TextButtonStyle textButtonStyle;
    public MainMenuScreen(MainClass mainClass) {
        this.mainClass = mainClass;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.fontColor = Color.WHITE;
        textButton = new TextButton("Start", textButtonStyle);
        textButton.setSize(50,10);
        textButton.setPosition(100,100);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);

        camera.update();
        mainClass.batch.setProjectionMatrix(camera.combined);

        mainClass.batch.begin();
        mainClass.font.draw(mainClass.batch, "Welcome!!! ", camera.position.x-100, camera.position.y);
        mainClass.font.draw(mainClass.batch, "Tap anywhere to begin!", camera.position.x-100, camera.position.y-100);
        textButton.draw(mainClass.batch, 1);
        mainClass.batch.end();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (textButton.isPressed()) System.out.println("boton");
                return true;
            }
        });
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
