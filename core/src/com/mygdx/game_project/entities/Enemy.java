package com.mygdx.game_project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.states.BaseStateClass;
import com.mygdx.game_project.utils.CreateHitbox;

public class Enemy extends CreateHitbox {
    Vector2 position;
    private float width, height, speed, dmg, armor, hp, maxHp;
    private Body body;
    private boolean alive = true;
    public enum states {
        SLEEP,
        HOSTILE,
        AVOID;
    }
    private states currentState;
    public Enemy(World world, Vector2 position, int width, int height, float speed, float dmg, float armor, float hp, states currentState) {
        super(world, position, width, height, 10f, false, false, false, category.COLLISION.bits(), dmg);
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
        this.currentState = currentState;
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
