package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TiledCollisions {
    public boolean isDoorCreated = false;
    public TiledCollisions(World world, MapObjects objects) {
        for (MapObject object : objects) {
            //Gdx.app.log("INFO", "Name: " + object.getProperties().get("x", Float.class));
            Shape shape = null;
            Body body;
            BodyDef def = new BodyDef();
            Fixture fixture;
            def.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(def);

            if (object instanceof PolylineMapObject || object instanceof PolygonMapObject) {
                if (object instanceof PolylineMapObject) shape = createPoly((PolylineMapObject) object);
                if (object instanceof PolygonMapObject) {
                    Gdx.app.log("INFO", "Created doors");
                    shape = createPoly((PolygonMapObject) object);
                    isDoorCreated = true;
                }
            } else {
                continue;
            }

            fixture = body.createFixture(shape, 1.0f);
            if (isDoorCreated) fixture.setUserData(this);
            shape.dispose();
        }
    }

    private ChainShape createPoly(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / 10.65f, vertices[i * 2 + 1] / 10.65f);
        }
        ChainShape chainShape = new ChainShape();
        chainShape.createChain(worldVertices);

        return chainShape;
    }

    private PolygonShape createPoly(PolygonMapObject polygon) {
        float[] vertices = polygon.getPolygon().getTransformedVertices();
        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = vertices[i] / 10.65f;
        }

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(worldVertices);

        return polygonShape;
    }
}
