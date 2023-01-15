package com.mygdx.game_project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.utils.CreateHitbox;

public class Enemy extends CreateHitbox{
    Vector2 position;
    private float width, height, speed, strenght, armor, hp;
    private Body body;

    public Enemy(World world, Vector2 position, int width, int height, float speed, float strenght, float armor, float hp) {
        super(world, position, width, height, 10f, false, false, false);
        fixture.setUserData(this);
        this.position = position;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.strenght = strenght;
        this.armor = armor;
        this.hp = hp;
        this.body = super.body;
    }

    @Override
    public void onHit() {
        Gdx.app.log("INFO", "Enemy hp: " + hp);
        //hp -= 1/armor;
    }

    //region $setters&getters

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getStrenght() {
        return strenght;
    }

    public void setStrenght(float strenght) {
        this.strenght = strenght;
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    //endregion
}
