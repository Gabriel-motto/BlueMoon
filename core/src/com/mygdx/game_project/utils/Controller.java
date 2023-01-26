package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Controller extends Actor {
    private static Skin touchpadSkin;
    private static Touchpad.TouchpadStyle touchpadStyle;
    private static Touchpad touchpad;
    public Controller() {

    }

    public Touchpad createTouchpad() {
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("Touchpad/Joystick.png"));
        touchpadSkin.add("touchKnob", new Texture("Touchpad/SmallHandle.png"));

        Gdx.app.log("INFO", "Creating touchpad");

        touchpadStyle = new Touchpad.TouchpadStyle();
        Drawable D_background = touchpadSkin.getDrawable("touchBackground");
        Drawable D_knob = touchpadSkin.getDrawable("touchKnob");

        D_background.setMinHeight(40);
        D_background.setMinWidth(40);

        D_knob.setMinHeight(35);
        D_knob.setMinWidth(35);

        touchpadStyle.background = D_background;
        touchpadStyle.knob = D_knob;

        touchpad = new Touchpad(5, touchpadStyle);
        touchpad.setBounds(50, 50, 125, 125);

        return touchpad;
    }
}