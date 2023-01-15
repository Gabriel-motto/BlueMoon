package com.mygdx.game_project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.utils.CreateHitbox;

public class Objects extends CreateHitbox{
    Vector2 position;
    private float radius, width, height;
    private Body body;

    private boolean alive = true;

    public Objects(World world, Vector2 position, float radius) {
        super(world, position, radius / 32, .5f, 1.0f);
        fixture.setUserData(this);
        this.position = position;
        this.radius = radius;
        this.body = super.body;
        body.setBullet(true);
    }

    public Objects(World world, Vector2 position, int width, int height, float radius) {
        super(world, position, width, height, 0.5f, false, false, false);
        fixture.setUserData("object");
        this.position = position;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.body = super.body;
    }

    @Override
    public void onHit() {
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

    //endregion
}
