package com.mygdx.game_project.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TiledCollisions {

    public static void parseTiledObject(World world, MapObjects objects) {
        for (MapObject object : objects) {
            Shape shape = null;
            Body body;
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(def);

            if (object instanceof PolylineMapObject || object instanceof PolygonMapObject) {
                if (object instanceof PolylineMapObject) shape = createPoly((PolylineMapObject) object);
                if (object instanceof PolygonMapObject) {
                    shape = createPoly((PolygonMapObject) object);
                    body.setUserData(TiledCollisions.class);
                }
            } else {
                continue;
            }

            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }

    private static ChainShape createPoly(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / 10.65f, vertices[i * 2 + 1] / 10.65f);
        }
        ChainShape chainShape = new ChainShape();
        chainShape.createChain(worldVertices);

        return chainShape;
    }

    private static PolygonShape createPoly(PolygonMapObject polygon) {
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
