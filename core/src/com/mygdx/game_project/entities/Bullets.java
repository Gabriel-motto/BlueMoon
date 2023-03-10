package com.mygdx.game_project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.utils.CreateHitbox;

public class Bullets extends CreateHitbox{
    Vector2 position;
    private float radius, width, height, damping, dmg, speed;
    private short group;
    private Body body;
    private boolean alive = true;
    private boolean shot = false;

    public Bullets(World world, Vector2 position, float radius, float dmg, float speed, short group) {
        super(world, position, radius / 32, 0, 1.0f, group, dmg, true);
        this.speed = speed;
        fixture.setUserData(this);
        this.position = position;
        this.radius = radius;
        this.body = super.body;
        this.dmg = dmg;
        this.group = group;
        body.setBullet(true);
    }

    public Bullets(World world, Vector2 position, int width, int height, float damping, float dmg, float speed, short group) {
        super(world, position, width, height, 0, false, false, false, group, dmg, true);
        this.speed = speed;
        fixture.setUserData(this);
        this.position = position;
        this.width = width;
        this.height = height;
        this.damping = damping;
        this.body = super.body;
        this.dmg = dmg;
        this.group = group;
        body.setBullet(true);
    }

    @Override
    public void onHit(Object object) {
        Gdx.app.log("BULLET","Dead");
        setAlive(false);
    }

    //region $setter&getters

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

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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

    public float getDamping() {
        return damping;
    }

    public void setDamping(float damping) {
        this.damping = damping;
    }

    public float getDmg() {
        return dmg;
    }

    public void setDmg(float dmg) {
        this.dmg = dmg;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public short getGroup() {
        return group;
    }

    public void setGroup(short group) {
        this.group = group;
    }

    public boolean isShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    //endregion
}
