package com.mygdx.game_project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game_project.entities.Player;
import com.mygdx.game_project.entities.PlayerData;
import com.mygdx.game_project.utils.Input;
import com.mygdx.game_project.utils.UICreator;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.mygdx.game_project.constants.Constant.*;

public class UIScreen {
    private static TextureAtlas hpBarAtlas = new TextureAtlas("HpBar/hpBar.atlas");
    private static Image hpBar;
    private static Label lblAtk, lblSpeed, lblAtkSpeed, lblArmor;
    private static ImageButton btnSettings;
    private static TextureAtlas btnTexure = new TextureAtlas("UIPack\\UIPack.atlas");
    private static Skin skin;
    private static Vector2 startCoord = new Vector2((WORLD_WIDTH)-85*PPU, (WORLD_HEIGHT)-40*PPU);
    private static float xSeparation = 30*PPU;
    private static float ySeparation = 20*PPU;
    private static DecimalFormat df = new DecimalFormat("##.##");
    private static I18NBundle lang = I18NBundle.createBundle(Gdx.files.internal("Locale/Locale"));
    private static float startAtk, startSpeed, startAtkSpeed, startArmor;

    /**
     * Se inicializan los elementos de la interfaz de usuario
     * @param stage
     */
    public static void initUI(Stage stage) {
        df.setRoundingMode(RoundingMode.DOWN);

        startAtk = PlayerData.atk;
        startSpeed = PlayerData.speed;
        startAtkSpeed = PlayerData.atkSpeed;
        startArmor = PlayerData.armor;

        skin = new Skin();
        skin.addRegions(btnTexure);
        skin.addRegions(hpBarAtlas);

        btnSettings = UICreator.createImageButton(new Vector2(startCoord.x +30*PPU, startCoord.y - 15*PPU),
                64, 64, skin, "Slot", "settings", stage);

        hpBar = UICreator.createImage(new Vector2(startCoord.x, startCoord.y - ySeparation * 2),
                64*PPU, 16*PPU, skin, "hp10", stage);

        lblAtk = UICreator.createLabel(lang.get("ui.atk"), 12, Color.WHITE,
                new Vector2(startCoord.x, startCoord.y - ySeparation * 3), stage);

        lblSpeed = UICreator.createLabel(lang.get("ui.speed"), 12, Color.WHITE,
                new Vector2(startCoord.x, startCoord.y - ySeparation * 4), stage);

        lblAtkSpeed = UICreator.createLabel(lang.get("ui.atkSpeed"), 12, Color.WHITE,
                new Vector2(startCoord.x, startCoord.y - ySeparation * 5), stage);

        lblArmor = UICreator.createLabel(lang.get("ui.armor"), 12, Color.WHITE,
                new Vector2(startCoord.x, startCoord.y - ySeparation * 6), stage);

        btnSettings.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(200);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                super.touchUp(event, x, y, pointer, button);
                GameScreen.state = GameScreen.State.PAUSED;
            }
        });
    }

    /**
     * Actualiza el interfaz de usuario segun lo que suceda en el juego
     * @param player
     */
    public static void updateUI(Player player) {
        lblAtk.setText(lang.get("ui.atk") + ": " + df.format(player.getAtk()));
        lblAtk.setColor(Color.WHITE);
        lblSpeed.setText(lang.get("ui.speed") + ": " + df.format(player.getSpeed()));
        lblSpeed.setColor(Color.WHITE);
        lblAtkSpeed.setText(lang.get("ui.atkSpeed") + ": " + df.format(player.getAtkSpeed()));
        lblAtkSpeed.setColor(Color.WHITE);
        lblArmor.setText(lang.get("ui.armor") + ": " + df.format(player.getArmor()));
        lblArmor.setColor(Color.WHITE);

        if (startAtk != player.getAtk()) lblAtk.setColor(Color.GREEN);
        if (startSpeed != player.getSpeed()) lblSpeed.setColor(Color.GREEN);
        if (startAtkSpeed != player.getAtkSpeed()) lblAtkSpeed.setColor(Color.GREEN);
        if (startArmor != player.getArmor()) lblArmor.setColor(Color.GREEN);

        hpBar.setDrawable(new TextureRegionDrawable(hpBarAtlas.findRegion("hp"+(int) Math.ceil(player.getHp()))));
    }
}
