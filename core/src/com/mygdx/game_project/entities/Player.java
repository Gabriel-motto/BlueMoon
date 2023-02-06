package com.mygdx.game_project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.MainClass;
import com.mygdx.game_project.screens.GameScreen;
import com.mygdx.game_project.utils.CreateHitbox;
import com.mygdx.game_project.utils.Input;
import static com.mygdx.game_project.constants.Constant.*;

public class Player extends CreateHitbox {
    // Base stats
    Vector2 position;
    private float width, height, speed, dmg, armor, hp;
    private Body body;
    private boolean alive = true;
    TextureAtlas playerTextureAtlas = new TextureAtlas("Player/Samurai.atlas");
    TextureRegion actualFrame;
    Animation<TextureAtlas.AtlasRegion> playerSpriteLeft;
    Animation<TextureAtlas.AtlasRegion> playerSpriteRight;
    Animation<TextureAtlas.AtlasRegion> playerSpriteUp;
    Animation<TextureAtlas.AtlasRegion> playerSpriteDown;
    float time;
    private Camera camera;
    final MainClass mainClass;
    private PlayerData pd;

    public Player(World world, Camera camera, MainClass mainClass) {
        super(world, PLAYER_INIT_POS, 50,50,10,false,true,true,category.NO_COLLISION.bits(), 1);
        fixture.setUserData(this);
        this.camera = camera;
        this.mainClass = mainClass;
        this.position = PLAYER_INIT_POS;
        this.width = 50;
        this.height = 50;
        this.speed = 5;
        this.dmg = 3;
        this.armor = 3;
        this.hp = 10;
        this.body = super.body;
        animate();
    }
    public Player(World world, Vector2 position, float width, float height, float speed, float dmg, float armor, float hp, Camera camera, MainClass mainClass) {
        super(world, position, width, height, 10, false, true, true, category.NO_COLLISION.bits(), dmg);
        this.camera = camera;
        this.mainClass = mainClass;
        fixture.setUserData(this);
        this.position = position;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.dmg = dmg;
        this.armor = armor;
        this.hp = hp;
        this.body = super.body;
        animate();
    }
    public void animate() {
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
            batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*PPU, height*PPU);
            dir = 0;
        }
        if (Input.dir == Input.direction.RIGHT) {
            actualFrame = playerSpriteRight.getKeyFrame(time, true);
            batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*PPU, height*PPU);
            dir = 1;
        }
        if (Input.dir == Input.direction.UP) {
            actualFrame = playerSpriteUp.getKeyFrame(time, true);
            batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*PPU, height*PPU);
        }
        if (Input.dir == Input.direction.DOWN) {
            actualFrame = playerSpriteDown.getKeyFrame(time, true);
            batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*PPU, height*PPU);
        }
        if (dir == 0) Input.dir = Input.direction.LEFT;
        else Input.dir = Input.direction.RIGHT;

//        Gdx.app.log("INFO/POSITION",position.x*PPU + " : " + position.y*PPU);
//        Gdx.app.log("INFO/BODYPOS", "" + body.getPosition());
    }

    @Override
    public void onHit(Object object) {
        if ((float) object > 0) {
            hp -= (float) object /armor;
            Gdx.app.log("INFO","Player hp: " + hp);
            if (hp <= 0) setAlive(false);
        }
        if (object instanceof Objects) {
            Gdx.app.log("INFO", "Hit");
            pd = new PlayerData(this.speed, this.dmg, this.armor, this.hp);
            mainClass.setScreen(new GameScreen(mainClass, false));
        }
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

    public float getDmg() {
        return dmg;
    }

    public void setDmg(float dmg) {
        this.dmg = dmg;
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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    //endregion
}
