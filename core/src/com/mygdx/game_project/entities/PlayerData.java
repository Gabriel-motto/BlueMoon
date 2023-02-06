package com.mygdx.game_project.entities;

public final class PlayerData {
    private final float speed, dmg, armor, hp;

    public PlayerData(float speed, float dmg, float armor, float hp) {
        this.speed = speed;
        this.dmg = dmg;
        this.armor = armor;
        this.hp = hp;
    }

    //region $Getters

    public float getSpeed() {
        return speed;
    }

    public float getDmg() {
        return dmg;
    }

    public float getArmor() {
        return armor;
    }

    public float getHp() {
        return hp;
    }


    //endregion
}
