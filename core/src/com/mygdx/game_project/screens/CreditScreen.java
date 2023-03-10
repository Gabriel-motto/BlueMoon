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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game_project.MainClass;
import com.mygdx.game_project.utils.UICreator;

import static com.mygdx.game_project.constants.Constant.WORLD_HEIGHT;
import static com.mygdx.game_project.constants.Constant.WORLD_WIDTH;

public class CreditScreen implements Screen {
    final MainClass mainClass;
    private Stage stage;
    private Image board;
    private TextButton btnMainMenu;
    private Label title1, title2, title3, title4, parag1, parag2, parag3, parag4;
    private Skin skin;
    private TextureAtlas texures = new TextureAtlas("UIPack\\UIPack.atlas");

    /**
     * Se inicializan todos los elementos de la pantalla de creditos
     * @param prefs record de enemigos matados
     * @param viewport
     * @param mainClass
     */
    public CreditScreen(Preferences prefs, ExtendViewport viewport, final MainClass mainClass) {
        this.mainClass = mainClass;
        I18NBundle lang = I18NBundle.createBundle(Gdx.files.internal("Locale/Locale"));

        stage = new Stage(viewport);

        skin = new Skin();
        skin.addRegions(texures);

        board = UICreator.createImage(new Vector2(10f,5f), WORLD_WIDTH-20f, WORLD_HEIGHT-10f,
                skin, "WidePanel", stage);

        btnMainMenu = UICreator.createTextButton("Main menu", 20, new Vector2(board.getX()+board.getWidth()/2-100f, board.getX()+25),
                200, 64, skin, "Big Wood 2 Btn", stage);
        btnMainMenu.getLabelCell().padTop(20);

        title1 = UICreator.createLabel(lang.get("cred.title1"), 24, Color.BLACK,
                new Vector2(board.getX()+50f, board.getY()+board.getHeight()-100f), stage);

        title2 = UICreator.createLabel(lang.get("cred.title2"), 24, Color.BLACK,
                new Vector2(board.getX()+50f, board.getY()+board.getHeight()-220f), stage);

        title3 = UICreator.createLabel(lang.get("cred.title3"), 24, Color.BLACK,
                new Vector2(board.getX()+board.getWidth()/2+50f, board.getY()+board.getHeight()-100f), stage);

        title4 = UICreator.createLabel(lang.get("cred.title4"), 24, Color.BLACK,
                new Vector2(board.getX()+board.getWidth()/2+50f, board.getY()+board.getHeight()-220f), stage);

        parag1 = UICreator.createLabel(lang.get("cred.parag1"), 16, Color.WHITE,
                new Vector2(board.getX()+50f, board.getY()+board.getHeight()-150f), stage);
        parag1.setWrap(true);
        parag1.setWidth((board.getWidth()/2)-100);

        parag2 = UICreator.createLabel(lang.get("cred.parag2"), 16, Color.WHITE,
                new Vector2(board.getX()+50f, board.getY()+board.getHeight()-310f), stage);
        parag2.setWrap(true);
        parag2.setWidth((board.getWidth()/2)-100);

        parag3 = UICreator.createLabel(lang.get("cred.parag3"), 16, Color.WHITE,
                new Vector2(board.getX()+board.getWidth()/2+50f, board.getY()+board.getHeight()-150f), stage);
        parag3.setWrap(true);
        parag3.setWidth((board.getWidth()/2)-100);

        parag4 = UICreator.createLabel(lang.get("cred.parag4"), 16, Color.WHITE,
                new Vector2(board.getX()+board.getWidth()/2+50f, board.getY()+board.getHeight()-300f), stage);
        parag4.setWrap(true);
        parag4.setWidth((board.getWidth()/2)-100);

        btnMainMenu.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(200);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                super.touchUp(event, x, y, pointer, button);
                MainMenuScreen.credits = false;
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
    }
}
