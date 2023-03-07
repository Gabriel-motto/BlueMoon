package com.mygdx.game_project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.utils.CreateHitbox;

public class Bullets extends CreateHitbox{
    Vector2 position;
    private float radius, width, height, damping, dmg, speed;
    private Body body;
    private boolean alive = true;
    private boolean moved = false;

    public Bullets(World world, Vector2 position, float radius, float dmg, float speed) {
        super(world, position, radius / 32, 0, 1.0f, category.NO_COLLISION.bits(), dmg);
        this.speed = speed;
        fixture.setUserData(this);
        this.position = position;
        this.radius = radius;
        this.body = super.body;
        this.dmg = dmg;
        body.setBullet(true);
    }

    public Bullets(World world, Vector2 position, int width, int height, float damping, float dmg, float speed) {
        super(world, position, width, height, 0, false, false, false, category.NO_COLLISION.bits(), dmg);
        this.speed = speed;
        fixture.setUserData(this);
        this.position = position;
        this.width = width;
        this.height = height;
        this.damping = damping;
        this.body = super.body;
        this.dmg = dmg;
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

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
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

    //endregion
}
