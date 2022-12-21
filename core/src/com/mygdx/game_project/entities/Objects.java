package com.mygdx.game_project.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.utils.CreateHitbox;

public class Objects {
    Vector2 position;
    private float width, height, radius;

    public Objects(World world, Vector2 position, float width, float height, float radius) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.radius = radius;
        CreateHitbox.createCircle(world, position, radius / 32, .5f, 1f).setBullet(true);
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

    //endregion
}
