package com.mygdx.game_project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game_project.utils.CreateHitbox;
import com.mygdx.game_project.utils.Input;

public class Player extends Actor {
    // Base stats
    Vector2 position;
    private float width, height, speed, strength, armor, hp;
    private Body body;

    TextureAtlas playerTextureAtlas = new TextureAtlas("Player/Samurai.atlas");
    TextureRegion actualFrame;
    Animation<TextureAtlas.AtlasRegion> playerSpriteLeft;
    Animation<TextureAtlas.AtlasRegion> playerSpriteRight;
    Animation<TextureAtlas.AtlasRegion> playerSpriteUp;
    Animation<TextureAtlas.AtlasRegion> playerSpriteDown;
    float time;

    public Player(World world, Vector2 position, float width, float height, float speed, float strength, float armor, float hp) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.strength = strength;
        this.armor = armor;
        this.hp = hp;
        body = CreateHitbox.createBox(world, position, (int)width, (int)height, 10, false, true);
        playerSpriteLeft = new Animation<>(.3f,
                playerTextureAtlas.findRegion("samurai-idle-left"),
                playerTextureAtlas.findRegion("samurai-idle-left2"),
                playerTextureAtlas.findRegion("samurai-idle-left3"),
                playerTextureAtlas.findRegion("samurai-idle-left4"));
        playerSpriteRight = new Animation<>(.3f,
                playerTextureAtlas.findRegion("samurai-idle-right"),
                playerTextureAtlas.findRegion("samurai-idle-right2"),
                playerTextureAtlas.findRegion("samurai-idle-right3"),
                playerTextureAtlas.findRegion("samurai-idle-right4"));
        playerSpriteUp = new Animation<>(.3f,
                playerTextureAtlas.findRegion("samurai-run-up"),
                playerTextureAtlas.findRegion("samurai-run-up2"),
                playerTextureAtlas.findRegion("samurai-run-up3"),
                playerTextureAtlas.findRegion("samurai-run-up4"));
        playerSpriteDown = new Animation<>(.3f,
                playerTextureAtlas.findRegion("samurai-run-down"),
                playerTextureAtlas.findRegion("samurai-run-down2"),
                playerTextureAtlas.findRegion("samurai-run-down3"),
                playerTextureAtlas.findRegion("samurai-run-down4"));
    }

    float dir = 0;
    public void draw(Batch batch) {
        time += Gdx.graphics.getDeltaTime();
        if (Input.dir == Input.direction.LEFT) {
            actualFrame = playerSpriteLeft.getKeyFrame(time, true);
            batch.draw(actualFrame, position.x, position.y, 60, 60);
            dir = 0;
        }
        if (Input.dir == Input.direction.RIGHT) {
            actualFrame = playerSpriteRight.getKeyFrame(time, true);
            batch.draw(actualFrame, position.x, position.y, 60, 60);
            dir = 1;
        }
        if (Input.dir == Input.direction.UP) {
            actualFrame = playerSpriteUp.getKeyFrame(time, true);
            batch.draw(actualFrame, position.x, position.y, 60, 60);
        }
        if (Input.dir == Input.direction.DOWN) {
            actualFrame = playerSpriteDown.getKeyFrame(time, true);
            batch.draw(actualFrame, position.x, position.y, 60, 60);
        }
        if (dir == 0) Input.dir = Input.direction.LEFT;
        else Input.dir = Input.direction.RIGHT;
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

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
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
