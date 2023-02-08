package com.mygdx.game_project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game_project.entities.Player;
import static com.mygdx.game_project.constants.Constant.*;

public class UIScreen {
    private static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Minecraftia-Regular.ttf"));
    private static FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    private static BitmapFont font;
    private static TextureAtlas hpBarAtlas = new TextureAtlas("HpBar/hpBar.atlas");
    private static Image hpBar;
    private static Label lblAtk, lblSpeed, lblAtkSpeed, lblArmor;
    private static LabelStyle lblAtkStyle, lblSpeedStyle, lblAtkSpeedStyle, lblArmorStyle;
    private static Vector2 startCoord = new Vector2((WORLD_WIDTH)-75*PPU, (WORLD_HEIGHT)-25*PPU);
    private static float xSeparation = 30*PPU;
    private static float ySeparation = 20*PPU;

    public static void initUI(Stage stage) {
        parameter.size = 12;
        font = generator.generateFont(parameter);

        hpBar = new Image(hpBarAtlas.findRegion("hp10"));
        hpBar.setBounds(startCoord.x, startCoord.y, 64*PPU, 16*PPU);
        stage.addActor(hpBar);

        lblAtkStyle = new LabelStyle();
        lblAtkStyle.font = font;
        lblAtkStyle.fontColor = Color.WHITE;
        lblAtk = new Label("Atk", lblAtkStyle);
        lblAtk.setPosition(startCoord.x, startCoord.y - ySeparation);
        stage.addActor(lblAtk);

        lblSpeedStyle = new LabelStyle();
        lblSpeedStyle.font = font;
        lblSpeedStyle.fontColor = Color.WHITE;
        lblSpeed = new Label("Speed", lblSpeedStyle);
        lblSpeed.setPosition(startCoord.x, startCoord.y - ySeparation * 2);
        stage.addActor(lblSpeed);

        lblAtkSpeedStyle = new LabelStyle();
        lblAtkSpeedStyle.font = font;
        lblAtkSpeedStyle.fontColor = Color.WHITE;
        lblAtkSpeed = new Label("Atk Speed", lblAtkSpeedStyle);
        lblAtkSpeed.setPosition(startCoord.x, startCoord.y - ySeparation * 3);
        stage.addActor(lblAtkSpeed);

        lblArmorStyle = new LabelStyle();
        lblArmorStyle.font = font;
        lblArmorStyle.fontColor = Color.WHITE;
        lblArmor = new Label("Armor", lblArmorStyle);
        lblArmor.setPosition(startCoord.x, startCoord.y - ySeparation * 4);
        stage.addActor(lblArmor);
    }

    public static void updateUI(Player player) {
        lblAtk.setText("Atk " + player.getDmg());
        lblSpeed.setText("Speed " + player.getSpeed());
        lblAtkSpeed.setText("AtkSpeed " + player.getAtkSpeed());
        lblArmor.setText("Armor " + player.getArmor());

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

    public static void dispose() {
        generator.dispose();
    }
}
