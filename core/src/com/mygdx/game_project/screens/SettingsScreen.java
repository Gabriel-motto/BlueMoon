package com.mygdx.game_project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game_project.MainClass;
import com.mygdx.game_project.utils.Sounds;
import com.mygdx.game_project.utils.Sounds.*;
import com.mygdx.game_project.utils.UICreator;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.mygdx.game_project.constants.Constant.*;

public class SettingsScreen implements Screen {
    private ImageButton btnIncrVol, btnDecVol, btnReturn;
    private TextButton btnMainMenu;
    private Label lblVolume;
    private Image board;
    private TextureAtlas texures = new TextureAtlas("UIPack\\UIPack.atlas");
    private Skin skin;
    private Stage stage;
    private DecimalFormat df = new DecimalFormat("##.##");

    /**
     * Se inicializan todos los elementos de la pantalla de opciones
     * @param prefs record de enemigos matados
     * @param viewport
     * @param mainClass
     */
    public SettingsScreen(Preferences prefs, ExtendViewport viewport, final MainClass mainClass, final boolean isMainMenu) {
        I18NBundle lang = I18NBundle.createBundle(Gdx.files.internal("Locale/Locale"));
        df.setRoundingMode(RoundingMode.DOWN);

        stage = new Stage(viewport);
        //stage.getCamera().position.x = (WORLD_WIDTH) / 2f;
        skin = new Skin();
        skin.addRegions(texures);

        Gdx.app.log("INFO/SCREEN",Gdx.graphics.getWidth() + " : " + Gdx.graphics.getHeight());
        Gdx.app.log("INFO/PPU", "" + PPU);
        Gdx.app.log("INFO/WHWW", WORLD_WIDTH + " : " + WORLD_HEIGHT);

        board = UICreator.createImage(new Vector2(50f,40f), WORLD_WIDTH-100f, WORLD_HEIGHT-80f,
                skin, "WidePanel", stage);

        UICreator.createLabel(lang.get("settings.enemies"), 24, Color.WHITE,
                new Vector2(board.getX()+board.getWidth()-320f, board.getY()+board.getHeight()-150f), stage);

        UICreator.createLabel(String.format(""+prefs.getInteger("enemiesKilled")),32, Color.RED,
                new Vector2(board.getX()+board.getWidth()-220f, board.getY()+board.getHeight()-250f), stage);

        UICreator.createLabel(lang.get("settings.volume"), 32, Color.WHITE,
                new Vector2(board.getX()+150, board.getY()+board.getHeight()-125f), stage);

        btnDecVol = UICreator.createImageButton(new Vector2(board.getX()+100, board.getY() + 180),
                48, 48, skin, "Slot", "Decrease", stage);

        lblVolume = UICreator.createLabel(df.format(Sounds.volume), 32, Color.WHITE,
                            new Vector2(board.getX()+200, board.getY() + 180), stage);

        btnIncrVol = UICreator.createImageButton(new Vector2(board.getX()+300, board.getY() + 180),
                48, 48, skin, "Slot", "Increase", stage);

        btnReturn = UICreator.createImageButton(new Vector2(board.getX()+board.getWidth()-70f, board.getY()+board.getHeight()-70f),
                32, 32, skin, "Btn Frame", "Cross Icon", stage);

        btnMainMenu = UICreator.createTextButton("Main menu", 20, new Vector2(board.getX()+125, board.getX()+50),
                200, 64, skin, "Big Wood 2 Btn", stage);
        btnMainMenu.getLabelCell().padTop(20);

        btnDecVol.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(200);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                super.touchUp(event, x, y, pointer, button);
                if (Sounds.volume > .1f) Sounds.volume -= .1f;
                MainClass.music.setVolume(Sounds.volume);
                lblVolume.setText(df.format(Sounds.volume));
            }
        });
        btnIncrVol.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(200);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                super.touchUp(event, x, y, pointer, button);
                if (Sounds.volume < 1) Sounds.volume += .1f;
                MainClass.music.setVolume(Sounds.volume);
                lblVolume.setText(df.format(Sounds.volume));
            }
        });

        btnReturn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(200);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                super.touchUp(event, x, y, pointer, button);
                if (isMainMenu) {
                    MainMenuScreen.pause = false;
                } else {
                    GameScreen.state = GameScreen.State.RUNNING;
                }
            }
        });

        btnMainMenu.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(200);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                super.touchUp(event, x, y, pointer, button);
                GameScreen.state = GameScreen.State.RUNNING;
                mainClass.setScreen(new MainMenuScreen(mainClass));
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
