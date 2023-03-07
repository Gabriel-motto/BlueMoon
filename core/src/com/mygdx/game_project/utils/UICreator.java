package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.*;

public class UICreator {
    private static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Minecraftia-Regular.ttf"));
    private static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private static BitmapFont font;
    public static Button createButton(Vector2 pos, float width, float height, Skin skin, String drawable, Stage stage) {
        ButtonStyle buttonStyle = new ButtonStyle();
        buttonStyle.up = skin.getDrawable(drawable);
        Button button = new Button(buttonStyle);
        button.setBounds(pos.x, pos.y, width, height);
        stage.addActor(button);
        return button;
    }
    public static TextButton createTextButton(String text, int textSize, Vector2 pos, float width, float height, Skin skin, String drawable, Stage stage) {
        parameter.size = textSize;
        font = generator.generateFont(parameter);

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable(drawable);
        TextButton textButton = new TextButton(text, textButtonStyle);
        textButton.setBounds(pos.x, pos.y, width, height);
        stage.addActor(textButton);
        return textButton;
    }
    public static ImageButton createImageButton(Vector2 pos, float width, float height, Skin skin, String drawable, String drawable2, Stage stage) {
        ImageButtonStyle imageButtonStyle = new ImageButtonStyle();
        imageButtonStyle.up = skin.getDrawable(drawable);
        imageButtonStyle.imageUp = skin.getDrawable(drawable2);
        imageButtonStyle.imageUp.setMinWidth(width-10);
        imageButtonStyle.imageUp.setMinHeight(height-10);
        ImageButton imageButton = new ImageButton(imageButtonStyle);
        imageButton.setBounds(pos.x, pos.y, width, height);
        stage.addActor(imageButton);
        return imageButton;
    }
    public static Label createLabel(String text, int textSize, Color fontColor, Vector2 pos, Stage stage) {
        parameter.size = textSize;
        font = generator.generateFont(parameter);

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = fontColor;
        Label label = new Label(text, labelStyle);
        label.setPosition(pos.x, pos.y);
        stage.addActor(label);
        return label;
    }
    public static Image createImage(Vector2 pos, float width, float height, Skin skin, String drawable, Stage stage) {
        Image image = new Image(skin, drawable);
        image.setBounds(pos.x, pos.y, width, height);
        stage.addActor(image);
        return image;
    }
}
