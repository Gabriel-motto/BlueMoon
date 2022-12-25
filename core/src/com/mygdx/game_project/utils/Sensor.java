package com.mygdx.game_project.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Sensor {
    public Body body, triggerBody;
    public String id;

    public Sensor(World world, String id, float x, float y) {
        this.id = id;

    }
}
