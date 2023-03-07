package com.mygdx.game_project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game_project.entities.Player;
import com.mygdx.game_project.utils.UICreator;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.mygdx.game_project.constants.Constant.*;

public class UIScreen {
    private static TextureAtlas hpBarAtlas = new TextureAtlas("HpBar/hpBar.atlas");
    private static Image hpBar;
    private static Label lblAtk, lblSpeed, lblAtkSpeed, lblArmor;
    private static Button btnSettings;
    private static TextureAtlas btnTexure = new TextureAtlas("UIPack\\UIPack.atlas");
    private static Skin skin;
    private static Vector2 startCoord = new Vector2((WORLD_WIDTH)-75*PPU, (WORLD_HEIGHT)-25*PPU);
    private static float xSeparation = 30*PPU;
    private static float ySeparation = 20*PPU;
    private static DecimalFormat df = new DecimalFormat("##.##");

    public static void initUI(Stage stage) {
        df.setRoundingMode(RoundingMode.DOWN);

        skin = new Skin();
        skin.addRegions(btnTexure);
        skin.addRegions(hpBarAtlas);

        btnSettings = UICreator.createImageButton(new Vector2(startCoord.x +30*PPU, startCoord.y - 15*PPU),
                64, 64, skin, "Slot", "settings", stage);

        hpBar = UICreator.createImage(new Vector2(startCoord.x, startCoord.y - ySeparation * 2),
                64*PPU, 16*PPU, skin, "hp10", stage);

        lblAtk = UICreator.createLabel("Atk", 12, Color.WHITE,
                new Vector2(startCoord.x, startCoord.y - ySeparation * 3), stage);

        lblSpeed = UICreator.createLabel("Speed", 12, Color.WHITE,
                new Vector2(startCoord.x, startCoord.y - ySeparation * 4), stage);

        lblAtkSpeed = UICreator.createLabel("Atk Speed", 12, Color.WHITE,
                new Vector2(startCoord.x, startCoord.y - ySeparation * 5), stage);

        lblArmor = UICreator.createLabel("Armor", 12, Color.WHITE,
                new Vector2(startCoord.x, startCoord.y - ySeparation * 6), stage);

        btnSettings.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                super.touchUp(event, x, y, pointer, button);
                GameScreen.state = GameScreen.State.PAUSED;
            }
        });
    }

    public static void updateUI(Player player) {
        lblAtk.setText("Atk " + df.format(player.getDmg()));
        lblSpeed.setText("Speed " + df.format(player.getSpeed()));
        lblAtkSpeed.setText("AtkSpeed " + df.format(player.getAtkSpeed()));
        lblArmor.setText("Armor " + df.format(player.getArmor()));

        switch ((int) Math.ceil(player.getHp())) {
            case 1:
                hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp1")));
                break;

            case 2:
                hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp2")));
                break;

            case 3:
                hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp3")));
                break;

            case 4:
                hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp4")));
                break;

            case 5:
                hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp5")));
                break;

            case 6:
                hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp6")));
                break;

            case 7:
                hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp7")));
                break;

            case 8:
                hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp8")));
                break;

            case 9:
                hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp9")));
                break;

            case 10:
                hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp10")));
                break;
        }
    }
}
