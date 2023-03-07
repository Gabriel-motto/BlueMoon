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

public class RaidBoss extends CreateHitbox {

    private float dmg, hp, armor, atkSpeed, interval, time;
    private Body body;
    private boolean alive;
    private TextureAtlas bossAtlas = new TextureAtlas("Boss\\Boss.atlas");
    private TextureRegion actualFrame;
    private HashMap<String, Animation<TextureAtlas.AtlasRegion>> animation;
    public enum state {
        STARTING,
        IDLE,
        HARDEN,
        ATTACK,
        BEAM,
        DEAD
    }
    private state currentState;

    public RaidBoss(World world, Vector2 position) {
        super(world, position, 100, 100, 0, true, true,
                false, category.COLLISION.bits(), 3);
        fixture.setUserData(this);
        this.hp = 50;
        this.armor = 1;
        this.dmg = super.dmg;
        this.atkSpeed = 3;
        this.body = super.body;
        this.alive = true;
        this.currentState = state.IDLE;
        animate();
    }

    public void animate() {
        switch (currentState) {
            case STARTING:
                animation.put("starting", new Animation<>(.3f,
                        bossAtlas.findRegion("BossStarting1"),
                        bossAtlas.findRegion("BossStarting2"),
                        bossAtlas.findRegion("BossStarting3"),
                        bossAtlas.findRegion("BossStarting4"),
                        bossAtlas.findRegion("BossStarting5"),
                        bossAtlas.findRegion("BossStarting6"),
                        bossAtlas.findRegion("BossStarting7"),
                        bossAtlas.findRegion("BossStarting8")));
                break;

            case IDLE:
                animation.put("idle", new Animation<>(.3f,
                        bossAtlas.findRegion("BossIdle1"),
                        bossAtlas.findRegion("BossIdle2"),
                        bossAtlas.findRegion("BossIdle3"),
                        bossAtlas.findRegion("BossIdle4")));
                break;

            case HARDEN:
                animation.put("harden", new Animation<>(.3f,
                        bossAtlas.findRegion("BossHarden1"),
                        bossAtlas.findRegion("BossHarden2"),
                        bossAtlas.findRegion("BossHarden3"),
                        bossAtlas.findRegion("BossHarden4"),
                        bossAtlas.findRegion("BossHarden5"),
                        bossAtlas.findRegion("BossHarden6"),
                        bossAtlas.findRegion("BossHarden7"),
                        bossAtlas.findRegion("BossHarden8"),
                        bossAtlas.findRegion("BossHarden9"),
                        bossAtlas.findRegion("BossHarden10")));
                break;

            case ATTACK:
                animation.put("attack", new Animation<>(.3f,
                        bossAtlas.findRegion("BossAttack1"),
                        bossAtlas.findRegion("BossAttack2"),
                        bossAtlas.findRegion("BossAttack3"),
                        bossAtlas.findRegion("BossAttack4"),
                        bossAtlas.findRegion("BossAttack5"),
                        bossAtlas.findRegion("BossAttack6"),
                        bossAtlas.findRegion("BossAttack7"),
                        bossAtlas.findRegion("BossAttack8"),
                        bossAtlas.findRegion("BossAttack9")));
                break;

            case BEAM:
                animation.put("beam", new Animation<>(.3f,
                        bossAtlas.findRegion("BossBeam1"),
                        bossAtlas.findRegion("BossBeam2"),
                        bossAtlas.findRegion("BossBeam3"),
                        bossAtlas.findRegion("BossBeam4"),
                        bossAtlas.findRegion("BossBeam5"),
                        bossAtlas.findRegion("BossBeam6"),
                        bossAtlas.findRegion("BossBeam7")));
                break;

            case DEAD:
                animation.put("death", new Animation<>(.3f,
                        bossAtlas.findRegion("BossDeath1"),
                        bossAtlas.findRegion("BossDeath2"),
                        bossAtlas.findRegion("BossDeath3"),
                        bossAtlas.findRegion("BossDeath4"),
                        bossAtlas.findRegion("BossDeath5"),
                        bossAtlas.findRegion("BossDeath6"),
                        bossAtlas.findRegion("BossDeath7"),
                        bossAtlas.findRegion("BossDeath8"),
                        bossAtlas.findRegion("BossDeath9"),
                        bossAtlas.findRegion("BossDeath10"),
                        bossAtlas.findRegion("BossDeath11"),
                        bossAtlas.findRegion("BossDeath12"),
                        bossAtlas.findRegion("BossDeath13"),
                        bossAtlas.findRegion("BossDeath14")));
                break;
        }
    }

    public void draw(Batch batch) {
        switch (currentState) {
            case STARTING:

                break;

            case IDLE:
                idleBehaviour();
                break;

            case HARDEN:
                hardenBehaviour();
                break;

            case ATTACK:
                attackBehaviour();
                break;

            case BEAM:
                beamBehaviour();
                break;

            case DEAD:
                deadBehaviour();
                break;
        }
    }

    public void drawStates(Batch batch, boolean isLoop, String frame) {
        time += Gdx.graphics.getDeltaTime();

        actualFrame = animation.get(frame).getKeyFrame(time, isLoop);
        batch.draw(actualFrame, );
    }

    public void update(float delta) {
        interval += delta;
        switch (currentState) {
            case STARTING:
                startingBehaviour();
                break;

            case IDLE:
                idleBehaviour();
                break;

            case HARDEN:
                hardenBehaviour();
                break;

            case ATTACK:
                attackBehaviour();
                break;

            case BEAM:
                beamBehaviour();
                break;

            case DEAD:
                deadBehaviour();
                break;
        }
    }

    public void startingBehaviour() {

    }

    public void idleBehaviour() {

    }

    public void hardenBehaviour() {

    }

    public void attackBehaviour() {

    }

    public void beamBehaviour() {

    }

    public void deadBehaviour() {

    }

    @Override
    public void onHit(Object object) {
        if (object instanceof Float) {
            hp -= (float) object / armor;
        }
    }

    //region $Setters&Getters

    public float getDmg() {
        return dmg;
    }

    public void setDmg(float dmg) {
        this.dmg = dmg;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getAtkSpeed() {
        return atkSpeed;
    }

    public void setAtkSpeed(float atkSpeed) {
        this.atkSpeed = atkSpeed;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public state getCurrentState() {
        return currentState;
    }

    public void setCurrentState(state currentState) {
        this.currentState = currentState;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    //endregion
}
