package com.mygdx.game_project.entities;

public final class PlayerData {
    public static float speed, dmg, armor, hp, atkSpeed;

    public static void setData(float speed, float dmg, float armor, float hp, float atkSpeed) {
        PlayerData.speed = speed;
        PlayerData.dmg = dmg;
        PlayerData.armor = armor;
        PlayerData.hp = hp;
        PlayerData.atkSpeed = atkSpeed;
    }
}
