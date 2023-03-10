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
    public enum category {
        PLAYER_NO_COLL((short)-1),
        ENEMY_NO_COLL((short)-2),
        NEUTRAL((short)-1);

        private final short bits;
        category(short bits) {
            this.bits = bits;
        }
        public short bits() {
            return bits;
        }
    }

    /**
     * Creacion de una hitbox cuadrada
     * @param world mundo en el que se crea
     * @param position position en el que se crea
     * @param width ancho
     * @param height alto
     * @param damping fuerza de rozamiento
     * @param isStatic si se puede mover
     * @param cantRotate si no puede rotar
     * @param isPlayer si es jugador
     * @param group grupo con el que no colisiona
     * @param dmg daño que ejerce
     * @param isSensor si es sensor
     */
    public CreateHitbox(World world, Vector2 position, float width, float height, float damping, boolean isStatic, boolean cantRotate, boolean isPlayer, short group, float dmg, boolean isSensor) {
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
        fixtureDef.isSensor = isSensor;

        if (isPlayer) fixture = body.createFixture(fixtureDef);
        else fixture = body.createFixture(fixtureDef);
        body.setLinearDamping(damping);
        shape.dispose();

        this.dmg = dmg;
    }

    /**
     * Creacion de una hitbox redonda
     * @param world mundo en el que se crea
     * @param position posicion
     * @param radius radio
     * @param damping fuerza de rozamiento
     * @param bounce elasticidad
     * @param group grupo con el que no colisiona
     * @param dmg daño que ejerce
     * @param isSensor si es sensor
     */
    public CreateHitbox(World world, Vector2 position, float radius, float damping, float bounce, short group, float dmg, boolean isSensor) {
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
        fixtureDef.isSensor = isSensor;

        fixture = body.createFixture(fixtureDef);
        circle.dispose();

        this.dmg = dmg;
    }

    public abstract void onHit(Object object);
}
