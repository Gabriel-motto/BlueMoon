package com.mygdx.game_project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game_project.screens.GameScreen;
import com.mygdx.game_project.screens.MainMenuScreen;
import com.mygdx.game_project.utils.Sounds;

public class MainClass extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public static Music music;

    @Override
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        music = Gdx.audio.newMusic(Gdx.files.internal("Sounds//Fief-A-Good-Inn.mp3"));
        music.setVolume(Sounds.volume);
        music.setLooping(true);
        music.play();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose () {
        batch.dispose();
        font.dispose();
        music.dispose();
    }
}
