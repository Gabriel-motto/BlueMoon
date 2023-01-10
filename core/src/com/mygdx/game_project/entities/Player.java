package com.mygdx.game_project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.utils.CreateHitbox;

public class Player {
    // Base stats
    Vector2 position;
    private float width, height, speed, strenght, armor, hp;
    private Body body;

    TextureAtlas playerTextureAtlas = new TextureAtlas("Player/Samurai.atlas");
    TextureRegion actualFrame;
    Animation<TextureAtlas.AtlasRegion> playerSprite;
    float time;

    public Player(World world, Vector2 position, float width, float height, float speed, float strenght, float armor, float hp) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.strenght = strenght;
        this.armor = armor;
        this.hp = hp;
        body = CreateHitbox.createBox(world, position, (int)width, (int)height, 10, false, true);
        playerSprite = new Animation<>(.2f,
                playerTextureAtlas.findRegion("samurai-idle"),
                playerTextureAtlas.findRegion("samurai-idle2"),
                playerTextureAtlas.findRegion("samurai-idle3"),
                playerTextureAtlas.findRegion("samurai-idle4"));
    }

    public void draw(Batch batch) {
        time += Gdx.graphics.getDeltaTime();
        actualFrame = playerSprite.getKeyFrame(time, true);
        batch.draw(actualFrame, position.x, position.y, width, height);
    }

    public void updateDraw(float delta) {

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
