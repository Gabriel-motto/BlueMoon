package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Input {
    public static void updateInput(float delta, Body body) {
        int horizontalForce = 0;
        int verticalForce = 0;

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.J)) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
                body.setBullet(true);
                body.applyLinearImpulse(new Vector2(-100f, 0), body.getWorldCenter(), true);
            } else {
                body.setBullet(false);
                horizontalForce--;
            }
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.L)) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
                body.setBullet(true);
                body.applyLinearImpulse(new Vector2(100f, 0), body.getWorldCenter(), true);
            } else {
                body.setBullet(false);
                horizontalForce++;
            }
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.I)) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
                body.setBullet(true);
                body.applyLinearImpulse(new Vector2(0, 100f), body.getWorldCenter(), true);
            } else {
                verticalForce++;
                body.setBullet(false);
            }
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.K)) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
                body.setBullet(true);
                body.applyLinearImpulse(new Vector2(0, -100f), body.getWorldCenter(), true);
            } else {
                body.setBullet(false);
                verticalForce--;
            }
        }
        if (!Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            body.setLinearVelocity(horizontalForce * 5f, verticalForce * 5f);
        }
    }
}
