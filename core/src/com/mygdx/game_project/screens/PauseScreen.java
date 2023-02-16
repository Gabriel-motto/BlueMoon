package com.mygdx.game_project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game_project.utils.UICreator;

import static com.mygdx.game_project.constants.Constant.*;

public class PauseScreen implements Screen {
    private Button btnIncrVol, btnDecVol, btnReturn;
    private TextureAtlas texures = new TextureAtlas("UIPack\\UIPack.atlas");
    private Skin skin;
    private Stage stage;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    public PauseScreen(Preferences prefs) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        stage = new Stage(viewport);
        stage.getCamera().position.x = (WORLD_WIDTH) / 2f;
        skin = new Skin();
        skin.addRegions(texures);

        Gdx.app.log("INFO/CAM", camera.position.x + " : " + camera.position.y);
        Gdx.app.log("INFO/SCREEN",Gdx.graphics.getWidth() + " : " + Gdx.graphics.getHeight());
        Gdx.app.log("INFO/PPU", "" + PPU);

        UICreator.createImage(new Vector2(50*PPU,25*PPU), (WORLD_WIDTH)-100*PPU, (WORLD_HEIGHT)-50*PPU,
                skin, "Panel 2", stage);

        UICreator.createLabel("Enemies Killed", 24, Color.WHITE,
                new Vector2((WORLD_WIDTH) - 250*PPU, (WORLD_HEIGHT) - 120*PPU), stage);

        UICreator.createLabel(String.format(""+prefs.getInteger("enemiesKilled")),32, Color.RED,
                new Vector2((WORLD_WIDTH) - 200*PPU, (WORLD_HEIGHT) - 200*PPU), stage);

//        btnIncrVol = UICreator.createButton(new Vector2(150*PPU, WORLD_HEIGHT - 130*PPU), 64, 64,
//                skin, "Btn Frame", stage);
//
//        btnDecVol = UICreator.createButton(new Vector2(200*PPU, WORLD_HEIGHT - 130*PPU), 64, 64,
//                skin, "Btn Frame", stage);

        btnReturn = UICreator.createImageButton(new Vector2(WORLD_WIDTH - 110*PPU, WORLD_HEIGHT - 65*PPU), 32, 32,
                skin, "Btn Frame", "Cross Icon", stage);

        btnReturn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                super.touchUp(event, x, y, pointer, button);
                GameScreen.state = GameScreen.State.RUNNING;
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getCamera().update();
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
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
        stage.dispose();
    }
}
