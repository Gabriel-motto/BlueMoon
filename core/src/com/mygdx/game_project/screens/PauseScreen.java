package com.mygdx.game_project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import static com.mygdx.game_project.constants.Constant.*;

public class PauseScreen {
    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Minecraftia-Regular.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont font;
    private Label lblRecordTitle, lblRecordCount, lblVolume;
    private LabelStyle lblRecordTitleStyle, lblRecordCountStyle, lblVolumeStyle;
    private Button btnIncrVol, btnDecVol, btnReturn;
    private ButtonStyle btnIncrVolStyle, btnDecVolStyle, btnReturnStyle;
    private TextureAtlas btnTexure = new TextureAtlas("Buttons\\Buttons.atlas");
    private Skin buttonSkin;
    private Stage stage;

    public PauseScreen(Preferences prefs, ExtendViewport viewport) {
        stage = new Stage(viewport);

        parameter.size = 24;
        font = generator.generateFont(parameter);

        lblRecordTitleStyle = new LabelStyle();
        lblRecordTitleStyle.font = font;
        lblRecordTitleStyle.fontColor = Color.WHITE;
        lblRecordTitle = new Label("Enemies Killed", lblRecordTitleStyle);
        lblRecordTitle.setPosition(WORLD_WIDTH - 150*PPU, WORLD_HEIGHT - 120*PPU);
        stage.addActor(lblRecordTitle);

        parameter.size = 16;
        font = generator.generateFont(parameter);

        lblRecordCountStyle = new LabelStyle();
        lblRecordCountStyle.font = font;
        lblRecordCountStyle.fontColor = Color.RED;
        lblRecordCount = new Label("", lblRecordCountStyle);
        lblRecordCount.setPosition(WORLD_WIDTH - 150*PPU, WORLD_HEIGHT - 140*PPU);
        stage.addActor(lblRecordCount);

        parameter.size = 12;
        font = generator.generateFont(parameter);

        buttonSkin = new Skin();
        buttonSkin.addRegions(btnTexure);
        btnIncrVolStyle = new ButtonStyle();
        btnIncrVolStyle.up = buttonSkin.getDrawable("smallButtonUp");
        btnIncrVolStyle.down = buttonSkin.getDrawable("smallButtonDown");
        btnIncrVol = new Button(btnIncrVolStyle);
        btnIncrVol.setBounds(150*PPU, WORLD_HEIGHT - 130*PPU, 64, 64);
        stage.addActor(btnIncrVol);

        btnDecVolStyle = new ButtonStyle();
        btnDecVolStyle.up = buttonSkin.getDrawable("smallButtonUp");
        btnDecVolStyle.down = buttonSkin.getDrawable("smallButtonDown");
        btnDecVol = new Button(btnDecVolStyle);
        btnDecVol.setBounds(200*PPU, WORLD_HEIGHT - 130*PPU, 64, 64);
        stage.addActor(btnDecVol);

        btnReturnStyle = new ButtonStyle();
        btnReturnStyle.up = buttonSkin.getDrawable("smallButtonUp");
        btnReturnStyle.down = buttonSkin.getDrawable("smallButtonDown");
        btnReturn = new Button(btnReturnStyle);
        btnReturn.setBounds(WORLD_WIDTH - 40*PPU, WORLD_HEIGHT - 40*PPU, 64, 64);
        stage.addActor(btnReturn);

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
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getCamera().position.x = WORLD_WIDTH / 2f;
        stage.getCamera().update();
        stage.getViewport().apply();
        stage.act();
        stage.draw();
        Gdx.input.setInputProcessor(stage);
    }
    public void dispose() {
        stage.dispose();
    }
}
