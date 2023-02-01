package com.mygdx.game_project.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.utils.CreateHitbox;

public class Objects extends CreateHitbox {
    private Vector2 position;
    private float width, height;
    private Body body;
    public enum rareness {
        COMMON,
        RARE,
        EXOTIC;
    }
    public Objects(World world, Vector2 position, float width, float height) {
        super(world, position, width, height, 0, true, true, false, category.COLLISION.bits(), 0);

    }

    @Override
    public void onHit(float dmg) {

    }
}
