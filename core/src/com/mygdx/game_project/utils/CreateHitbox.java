package com.mygdx.game_project.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CreateHitbox {
    public static Body createBox(World world, Vector2 position, int width, int height, float damping, boolean isStatic, boolean cantRotate) {
        Body body;
        BodyDef def = new BodyDef();
        PolygonShape shape = new PolygonShape();

        if (isStatic) def.type = BodyDef.BodyType.StaticBody;
        else def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(position.x / 32f, position.y / 32f);
        def.fixedRotation = cantRotate;
        body = world.createBody(def);

        shape.setAsBox(width / 2f / 32f, height / 2f / 32f);

        body.createFixture(shape, 1.0f);
        body.setLinearDamping(damping);
        shape.dispose();

        return body;
    }

    public static Body createCircle(World world, Vector2 position, float radius, float damping, float bounce) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x / 32f, position.y / 32f);

        Body body = world.createBody(bodyDef);
        body.setLinearDamping(damping);

        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = bounce;

        Fixture fixture = body.createFixture(fixtureDef);
        circle.dispose();

        return  body;
    }
}
