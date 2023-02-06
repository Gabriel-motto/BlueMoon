package com.mygdx.game_project.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class CreateHitbox {
    protected Body body;
    protected Fixture fixture;
    protected BodyDef bodyDef;
    protected float dmg;
    protected enum category {
        NO_COLLISION((short)-1),
        COLLISION((short)1),
        NEUTRAL((short)0);

        private final short bits;
        category(short bits) {
            this.bits = bits;
        }
        public short bits() {
            return bits;
        }
    }
    public CreateHitbox(World world, Vector2 position, float width, float height, float damping, boolean isStatic, boolean cantRotate, boolean isPlayer, short group, float dmg) {
        bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();

        if (isStatic) bodyDef.type = BodyDef.BodyType.StaticBody;
        else bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(position.x / 32f, position.y / 32f);
        bodyDef.fixedRotation = cantRotate;
        body = world.createBody(bodyDef);

        shape.setAsBox(width / 2f / 32f, height / 2f / 32f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.groupIndex = group;

        if (isPlayer) fixture = body.createFixture(fixtureDef);
        else fixture = body.createFixture(fixtureDef);
        body.setLinearDamping(damping);
        shape.dispose();

        this.dmg = dmg;
    }

    public CreateHitbox(World world, Vector2 position, float radius, float damping, float bounce, short group, float dmg) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x / 32f, position.y / 32f);

        body = world.createBody(bodyDef);
        body.setLinearDamping(damping);

        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = bounce;
        fixtureDef.filter.groupIndex = group;

        fixture = body.createFixture(fixtureDef);
        circle.dispose();

        this.dmg = dmg;
    }

    public abstract void onHit(Object object);
}
