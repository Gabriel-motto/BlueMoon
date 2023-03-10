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
import com.mygdx.game_project.screens.EndScreen;
import com.mygdx.game_project.screens.GameScreen;
import com.mygdx.game_project.utils.CreateHitbox;
import com.mygdx.game_project.utils.Input;
import com.mygdx.game_project.utils.Sounds;

import static com.mygdx.game_project.constants.Constant.*;

public class Player extends CreateHitbox {
    // Base stats
    Vector2 position;
    private float speed, width, height, atk, armor, hp, atkSpeed;
    private int[] stats = new int[3];
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
    private Sounds hitSound, chestSound;

    public Player(World world, Camera camera, MainClass mainClass) {
        super(world, PLAYER_INIT_POS, 50,50,10,false,true,true,category.PLAYER_NO_COLL.bits(), 1, false);
        fixture.setUserData(this);
        this.camera = camera;
        this.mainClass = mainClass;
        this.position = PLAYER_INIT_POS;
        this.width = 50;
        this.height = 50;
        this.speed = 5;
        this.atk = 3;
        this.armor = 3;
        this.hp = 10;
        this.atkSpeed = 1;
        this.body = super.body;
        hitSound = new Sounds("Sounds\\Hit1.mp3");
        chestSound = new Sounds("Sounds\\Chest.mp3");
        animate();
    }
    public Player(World world, Vector2 position, float width, float height, float speed, float atk, float armor, float hp, Camera camera, MainClass mainClass, float atkSpeed) {
        super(world, position, width, height, 10, false, true, true, category.PLAYER_NO_COLL.bits(), atk, false);
        fixture.setUserData(this);
        this.camera = camera;
        this.mainClass = mainClass;
        this.position = position;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.atk = atk;
        this.armor = armor;
        this.hp = hp;
        this.body = super.body;
        this.atkSpeed = atkSpeed;
        hitSound = new Sounds("Sounds\\Hit1.mp3");
        chestSound = new Sounds("Sounds\\Chest.mp3");
        animate();
    }

    /**
     * Inicializa las animaciones
     */
    public void animate() {
        playerSpriteLeft = new Animation<>(.3f,
                playerTextureAtlas.findRegion("samurai-idle-left"),
                playerTextureAtlas.findRegion("samurai-idle-left2"),
                playerTextureAtlas.findRegion("samurai-idle-left3"),
                playerTextureAtlas.findRegion("samurai-idle-left4"));
        playerSpriteRight = new Animation<>(.3f,
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

    /**
     * Dibuja el jugador
     * @param batch
     */
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

        for (Bullets bullet : Input.getBullets()) {
            Gdx.app.log("INFO", "bulletdraw");
            batch.draw(playerTextureAtlas.findRegion("shuriken"), bullet.getBody().getWorldCenter().x * 32 * PPU - bullet.getWidth() * PPU / 2, bullet.getBody().getWorldCenter().y * 32 * PPU - bullet.getHeight() * PPU / 2,
                    16 * PPU, 16 * PPU);
        }

//        Gdx.app.log("INFO/POSITION",position.x*PPU + " : " + position.y*PPU);
//        Gdx.app.log("INFO/BODYPOS", "" + body.getPosition());
    }

    @Override
    public void onHit(Object object) {
        if (object instanceof Float && (float) object > 0) {
            hp -= (float) object /armor;
            Gdx.app.log("INFO","Player hp: " + hp);
            if (hp <= 0) {
                alive = false;
                GameScreen.state = GameScreen.State.END;
            }
            Gdx.input.vibrate(200);
            hitSound.play(false);
        }
        if (object instanceof String && object.equals("door")) {
            // Gdx.app.log("INFO", "Hit");
            if (GameScreen.mapRoute.equals("Maps\\BossMap.tmx")) {
                mainClass.setScreen(new GameScreen(mainClass, true, -1));
            }
            else {
                PlayerData.mapCount++;
                mainClass.setScreen(new GameScreen(mainClass, false, (int)(Math.random() * 3)));
            }
        }
        if (object instanceof Objects) {
            powerUp((Objects) object);
            chestSound.play(false);
            Gdx.app.log("STATS", "Hp: " + hp + " Atk: " + atk + " Speed: " + speed + " AtkSpeed: " + atkSpeed + " Armor: " + armor);
        }
    }

    /**
     * Aumenta una o mas stats dependiendo de la rareza del objeto
     * @param object Rareza del objeto
     */
    public void powerUp(Objects object) {
        stats[0] = (int) (Math.random() * 5);
        stats[1] = -1;
        stats[2] = -1;

        switch (stats[0]) {
            case 0:
                speed += .3f;
                break;

            case 1:
                atk += .5f;
                break;

            case 2:
                armor += .5f;
                break;

            case 3:
                if (Math.ceil(hp) < 10) hp++;
                else armor += .5f;
                break;

            case 4:
                if (atkSpeed > .1f) atkSpeed -= .1f;
                else atk += .5f;
                break;
        }
        if (object.getRareness() == Objects.Rareness.EXOTIC) {
            stats[1] = (int) (Math.random() * 5);
            switch (stats[1]) {
                case 0:
                    speed += .3f;
                    break;

                case 1:
                    atk += .5f;
                    break;

                case 2:
                    armor += .5f;
                    break;

                case 3:
                    if (Math.ceil(hp) < 10) hp++;
                    else armor += .5f;
                    break;

                case 4:
                    if (atkSpeed > .1f) atkSpeed -= .1f;
                    else atk += .5f;
                    break;
            }
        }
        if (object.getRareness() == Objects.Rareness.EXOTIC || object.getRareness() == Objects.Rareness.RARE) {
            stats[2] = (int) (Math.random() * 5);
            switch (stats[2]) {
                case 0:
                    speed += .3f;
                    break;

                case 1:
                    atk += .5f;
                    break;

                case 2:
                    armor += .5f;
                    break;

                case 3:
                    if (Math.ceil(hp) < 10) hp++;
                    else armor += .5f;
                    break;

                case 4:
                    if (atkSpeed > .1f) atkSpeed -= .1f;
                    else atk += .5f;
                    break;
            }
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

    public float getAtk() {
        return atk;
    }

    public void setAtk(float atk) {
        this.atk = atk;
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

    public float getAtkSpeed() {
        return atkSpeed;
    }

    public void setAtkSpeed(float atkSpeed) {
        this.atkSpeed = atkSpeed;
    }

    public int[] getStats() {
        return stats;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    //endregion
}
