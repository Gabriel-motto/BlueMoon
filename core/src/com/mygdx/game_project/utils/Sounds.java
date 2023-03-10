package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

    public static float volume = .5f;
    private Sound sound;

    /**
     * Inicializa el sonido
     * @param path direccion del sonido en los archivos
     */
    public Sounds(String path) {
        sound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    /**
     * Reproduce el sonido asignado
     * @param loop
     */
    public void play(boolean loop) {
        long id = sound.play(volume);
        sound.setLooping(id, loop);
    }

    public void dispose() {
        sound.dispose();
    }
}
