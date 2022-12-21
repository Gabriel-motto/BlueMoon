package com.mygdx.game_project.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class TiledCollisions {

    public static void parseTiledObject(World world, MapObjects objects) {
        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = createPoly((PolylineMapObject) object);
            } else {
                continue;
            }

            Body body;
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(def);
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
}
