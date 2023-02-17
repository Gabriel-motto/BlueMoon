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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game_project.utils.UICreator;

import static com.mygdx.game_project.constants.Constant.*;

public class PauseScreen implements Screen {
    private ImageButton btnIncrVol, btnDecVol, btnReturn;
    private Image board;
    private TextureAtlas texures = new TextureAtlas("UIPack\\UIPack.atlas");
    private Skin skin;
    private Stage stage;

    public PauseScreen(Preferences prefs, ExtendViewport viewport) {
        stage = new Stage(viewport);
        //stage.getCamera().position.x = (WORLD_WIDTH) / 2f;
        skin = new Skin();
        skin.addRegions(texures);

        Gdx.app.log("INFO/SCREEN",Gdx.graphics.getWidth() + " : " + Gdx.graphics.getHeight());
        Gdx.app.log("INFO/PPU", "" + PPU);
        Gdx.app.log("INFO/WHWW", WORLD_WIDTH + " : " + WORLD_HEIGHT);

        board = UICreator.createImage(new Vector2(50f,40f), WORLD_WIDTH-100f, WORLD_HEIGHT-80f,
                skin, "WidePanel", stage);

        UICreator.createLabel("Enemies Killed", 24, Color.WHITE,
                new Vector2(board.getX()+board.getWidth()-300f, board.getY()+board.getHeight()-150f), stage);

        UICreator.createLabel(String.format(""+prefs.getInteger("enemiesKilled")),32, Color.RED,
                new Vector2(board.getX()+board.getWidth()-220f, board.getY()+board.getHeight()-250f), stage);

        btnDecVol = UICreator.createImageButton(new Vector2(board.getX()+100, board.getY() + 180),
                48, 48, skin, "Slot", "Decrease", stage);

        btnIncrVol = UICreator.createImageButton(new Vector2(board.getX()+300, board.getY() + 180),
                48, 48, skin, "Slot", "Increase", stage);

        Gdx.app.log("INFO/BOARD", board.getX() + " : " + board.getY());

        btnReturn = UICreator.createImageButton(new Vector2(board.getX()+board.getWidth()-70f, board.getY()+board.getHeight()-70f),
                32, 32, skin, "Btn Frame", "Cross Icon", stage);

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

        stage.act(delta);
        stage.draw();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
