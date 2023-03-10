package com.mygdx.game_project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game_project.MainClass;
import com.mygdx.game_project.utils.Sounds;
import com.mygdx.game_project.utils.UICreator;

import static com.mygdx.game_project.constants.Constant.*;

public class MainMenuScreen implements Screen {
    // region $Vars
    final MainClass mainClass;
    private OrthographicCamera camera;
    private Stage stage;
    private ExtendViewport viewport;
    private TextureAtlas texures = new TextureAtlas("UIPack\\UIPack.atlas");
    private Skin skin;
    private TextButton btnStart, btnSettings, btnCredits;
    private Image board;
    private Preferences prefs = Gdx.app.getPreferences("Scores");
    private SettingsScreen pauseScreen;
    private CreditScreen creditScreen;
    public static boolean pause, credits;

    // endregion

    /**
     * Se inicializan todos los elementos de la pantalla principal
     * @param mainClass
     */
    public MainMenuScreen(final MainClass mainClass) {
        this.mainClass = mainClass;
        pause = false;
        credits = false;

        I18NBundle lang = I18NBundle.createBundle(Gdx.files.internal("Locale/Locale"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        stage = new Stage(viewport);

        pauseScreen = new SettingsScreen(prefs, viewport, mainClass, true);
        creditScreen = new CreditScreen(prefs, viewport, mainClass);

        skin = new Skin();
        skin.addRegions(texures);

        board = UICreator.createImage(new Vector2(10f,5f), WORLD_WIDTH-20f, WORLD_HEIGHT-10f,
                skin, "WidePanel", stage);

        UICreator.createLabel("BLUEMOON", 70, Color.BLUE,
                new Vector2(board.getX()+board.getWidth()/2-50f, board.getY()+board.getHeight()/2-40f), stage);

        btnStart = UICreator.createTextButton(lang.get("mainmenu.start"), 20, new Vector2(board.getX()+130, board.getX()+300),
                200, 64, skin, "Big Dark Btn", stage);
        btnStart.getLabelCell().padTop(20);

        btnSettings = UICreator.createTextButton(lang.get("mainmenu.settings"), 20, new Vector2(board.getX()+130, board.getX()+200),
                200, 64, skin, "Big Dark Btn", stage);
        btnSettings.getLabelCell().padTop(20);

        btnCredits = UICreator.createTextButton(lang.get("mainmenu.creds"), 20, new Vector2(board.getX()+130, board.getX()+100),
                200, 64, skin, "Big Dark Btn", stage);
        btnCredits.getLabelCell().padTop(20);

        btnStart.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(200);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mainClass.setScreen(new GameScreen(mainClass, true, -1));
            }
        });

        btnSettings.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(200);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pause = true;
            }
        });

        btnCredits.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(200);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                credits = true;
            }
        });
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        camera.update();
        mainClass.batch.setProjectionMatrix(camera.combined);

        if (pause) {
            pauseScreen.render(Gdx.graphics.getDeltaTime());
        } else {
            if (credits) {
                creditScreen.render(Gdx.graphics.getDeltaTime());
            } else {
                Gdx.input.setInputProcessor(stage);
                stage.getCamera().update();
                stage.getViewport().apply();
                stage.act(delta);
                stage.draw();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        stage.getViewport().update(width,height);
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
        Gdx.input.setInputProcessor(null);
    }
}
