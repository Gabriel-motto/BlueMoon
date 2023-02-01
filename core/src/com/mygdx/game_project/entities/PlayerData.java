package com.mygdx.game_project.entities;

import com.badlogic.gdx.math.Vector2;

public class PlayerData {
    public static Vector2 position;
    public static float speed, dmg, armor, hp;

    public static void saveData(Vector2 position, float speed, float dmg, float armor, float hp) {
        PlayerData.position = position;
        PlayerData.speed = speed;
        PlayerData.dmg = dmg;
        PlayerData.armor = armor;
        PlayerData.hp = hp;
    }
}
