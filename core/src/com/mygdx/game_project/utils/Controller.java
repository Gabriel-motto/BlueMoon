package com.mygdx.game_project.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Controller extends Touchpad {
    private static Skin touchpadSkin;
    private static TouchpadStyle touchpadStyle;
    private static TextureAtlas touchpadAtlas = new TextureAtlas("Touchpad/Touchpad.atlas");

    public Controller(float deadzoneRadius) {
        super(deadzoneRadius, getTouchpadStyle());
        setBounds(100,100,touchpadAtlas.findRegion("Touchpad").getRegionWidth(), touchpadAtlas.findRegion("Touchpad").getRegionHeight());
    }

    public static TouchpadStyle getTouchpadStyle() {
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", touchpadAtlas.findRegion("Touchpad"));
        touchpadSkin.add("touchKnob", touchpadAtlas.findRegion("SmallKnob"));

        touchpadStyle = new TouchpadStyle();
        touchpadStyle.background = (Drawable) touchpadAtlas.findRegion("touchBackground");
        touchpadStyle.knob = (Drawable) touchpadAtlas.findRegion("touchKnob");
        return touchpadStyle;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isTouched()) {

        }
    }
}