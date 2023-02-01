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

import java.util.HashMap;
import java.util.Random;

import static com.mygdx.game_project.constants.Constant.PPU;

public class Enemy extends CreateHitbox {
    Vector2 position;
    private float width, height, speed, dmg, armor, hp, maxHp;
    private Body body;
    private boolean alive = true;
    private TextureAtlas goblinAtlas = new TextureAtlas("Enemies\\Goblin\\Goblin.atlas");
    private TextureAtlas wraithAtlas = new TextureAtlas("Enemies\\Wraith\\Wraith.atlas");
    private TextureAtlas mummyAtlas = new TextureAtlas("Enemies\\Mummy\\Mummy.atlas");
    private HashMap<String, Animation<TextureAtlas.AtlasRegion>> animation;
    private TextureRegion actualFrame;
    private float time;
    public enum states {
        SLEEP,
        HOSTILE,
        AVOID;
    }
    private states currentState;
    public Enemy(World world, Vector2 position, int width, int height, float speed, float dmg, float armor, float hp) {
        super(world, position, width, height, 10f, false, true, false, category.COLLISION.bits(), dmg);
        fixture.setUserData(this);
        this.position = position;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.dmg = dmg;
        this.armor = armor;
        this.hp = hp;
        this.maxHp = hp;
        this.body = super.body;
        int rand = (int)Math.floor(Math.random() * 1);
        switch (rand) {
            case 0:
                this.currentState = states.SLEEP;
                break;

            case 1:
                this.currentState = states.HOSTILE;

                break;

            case 2:
                this.currentState = states.AVOID;
                break;
        }
        animate();
    }
    public void animate() {
        animation = new HashMap<>();
        switch (currentState) {
            case HOSTILE:
                animation.put("idle", new Animation<>(.3f,
                        goblinAtlas.findRegion("goblin-idle(1)"),
                        goblinAtlas.findRegion("goblin-idle(2)"),
                        goblinAtlas.findRegion("goblin-idle(3)"),
                        goblinAtlas.findRegion("goblin-idle(4)")));
                animation.put("attackLeft", new Animation<>(.3f,
                        goblinAtlas.findRegion("goblin-attack(1)"),
                        goblinAtlas.findRegion("goblin-attack(2)"),
                        goblinAtlas.findRegion("goblin-attack(3)"),
                        goblinAtlas.findRegion("goblin-attack(4)")));
                animation.put("attackRight", new Animation<>(.3f,
                        goblinAtlas.findRegion("goblin-attack(5)"),
                        goblinAtlas.findRegion("goblin-attack(6)"),
                        goblinAtlas.findRegion("goblin-attack(7)"),
                        goblinAtlas.findRegion("goblin-attack(8)")));
                animation.put("runLeft", new Animation<>(.3f,
                        goblinAtlas.findRegion("goblin-run(1)"),
                        goblinAtlas.findRegion("goblin-run(2)"),
                        goblinAtlas.findRegion("goblin-run(3)"),
                        goblinAtlas.findRegion("goblin-run(4)")));
                animation.put("runRight", new Animation<>(.3f,
                        goblinAtlas.findRegion("goblin-run(5)"),
                        goblinAtlas.findRegion("goblin-run(6)"),
                        goblinAtlas.findRegion("goblin-run(7)"),
                        goblinAtlas.findRegion("goblin-run(8)")));
                animation.put("deathLeft", new Animation<>(.3f,
                        goblinAtlas.findRegion("goblin-death(1)"),
                        goblinAtlas.findRegion("goblin-death(2)"),
                        goblinAtlas.findRegion("goblin-death(3)"),
                        goblinAtlas.findRegion("goblin-death(4)")));
                animation.put("deathRight", new Animation<>(.3f,
                        goblinAtlas.findRegion("goblin-death(5)"),
                        goblinAtlas.findRegion("goblin-death(6)"),
                        goblinAtlas.findRegion("goblin-death(7)"),
                        goblinAtlas.findRegion("goblin-death(8)")));
                break;

            case AVOID:
                break;

            case SLEEP:
                animation.put("idle", new Animation<>(.3f,
                        wraithAtlas.findRegion("wraith-idle(1)"),
                        wraithAtlas.findRegion("wraith-idle(2)"),
                        wraithAtlas.findRegion("wraith-idle(3)"),
                        wraithAtlas.findRegion("wraith-idle(4)")));
                animation.put("attackLeft", new Animation<>(.3f,
                        wraithAtlas.findRegion("wraith-attack(1)"),
                        wraithAtlas.findRegion("wraith-attack(2)"),
                        wraithAtlas.findRegion("wraith-attack(3)"),
                        wraithAtlas.findRegion("wraith-attack(4)")));
                animation.put("attackRight", new Animation<>(.3f,
                        wraithAtlas.findRegion("wraith-attack(5)"),
                        wraithAtlas.findRegion("wraith-attack(6)"),
                        wraithAtlas.findRegion("wraith-attack(7)"),
                        wraithAtlas.findRegion("wraith-attack(8)")));
                animation.put("runLeft", new Animation<>(.3f,
                        wraithAtlas.findRegion("wraith-run(1)"),
                        wraithAtlas.findRegion("wraith-run(2)"),
                        wraithAtlas.findRegion("wraith-run(3)"),
                        wraithAtlas.findRegion("wraith-run(4)")));
                animation.put("runRight", new Animation<>(.3f,
                        wraithAtlas.findRegion("wraith-run(5)"),
                        wraithAtlas.findRegion("wraith-run(6)"),
                        wraithAtlas.findRegion("wraith-run(7)"),
                        wraithAtlas.findRegion("wraith-run(8)")));
                animation.put("deathLeft", new Animation<>(.3f,
                        wraithAtlas.findRegion("wraith-death(1)"),
                        wraithAtlas.findRegion("wraith-death(2)"),
                        wraithAtlas.findRegion("wraith-death(3)"),
                        wraithAtlas.findRegion("wraith-death(4)")));
                animation.put("deathRight", new Animation<>(.3f,
                        wraithAtlas.findRegion("wraith-death(5)"),
                        wraithAtlas.findRegion("wraith-death(6)"),
                        wraithAtlas.findRegion("wraith-death(7)"),
                        wraithAtlas.findRegion("wraith-death(8)")));
                break;
        }
    }
    public void draw(Batch batch) {
        time += Gdx.graphics.getDeltaTime();
        if (body.getLinearVelocity().x > 0) {
            actualFrame = animation.get("runRight").getKeyFrame(time, true);
            batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*PPU, height*PPU);
        }
        if (body.getLinearVelocity().x < 0) {
            actualFrame = animation.get("runLeft").getKeyFrame(time, true);
            batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*PPU, height*PPU);
        }
        if (body.getLinearVelocity().x == 0) {
            actualFrame = animation.get("idle").getKeyFrame(time, true);
            batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*PPU, height*PPU);
        }
    }
    @Override
    public void onHit(float dmg) {
        hp -= dmg/armor;
        Gdx.app.log("INFO", "Enemy hp: " + hp);
        if (hp <= 0) setAlive(false);
    }

    public void updateBehavior(float delta, Player player) {
        if (isAlive()) {
            switch (currentState) {
                case HOSTILE:
                    Vector2 enemyDir = new Vector2((player.getBody().getPosition().x * 32 - body.getPosition().x * 32), (player.getBody().getPosition().y * 32 - body.getPosition().y * 32)).nor();

                    body.setLinearVelocity(new Vector2(speed * enemyDir.x, speed * enemyDir.y));
                    break;

                case AVOID:

                    break;

                case SLEEP:
                    float playerDistanceX = (player.getBody().getPosition().x - body.getPosition().x) > 0 ? (player.getBody().getPosition().x - body.getPosition().x) : -(player.getBody().getPosition().x - body.getPosition().x);
                    float playerDistanceY = (player.getBody().getPosition().y - body.getPosition().y) > 0 ? (player.getBody().getPosition().y - body.getPosition().y) : -(player.getBody().getPosition().y - body.getPosition().y);
                    if (playerDistanceX + playerDistanceY < 5 || hp < maxHp) currentState = states.HOSTILE;
                    break;

                default:
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

    //endregion
}
