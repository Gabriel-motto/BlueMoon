package com.mygdx.game_project.entities;

public final class PlayerData {
    public static float speed, atk, armor, hp, atkSpeed;
    public static int mapCount;

    public static void setData(float speed, float atk, float armor, float hp, float atkSpeed) {
        PlayerData.speed = speed;
        PlayerData.atk = atk;
        PlayerData.armor = armor;
        PlayerData.hp = hp;
        PlayerData.atkSpeed = atkSpeed;
    }
}
