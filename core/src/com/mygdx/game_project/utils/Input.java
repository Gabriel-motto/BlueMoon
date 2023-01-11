package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game_project.entities.Player;

public class Input {

    public enum direction {
        UP, DOWN, RIGHT, LEFT
    }
    public static direction dir = direction.LEFT;
    public static void updateInput(float delta, Player player) {
        int horizontalForce = 0;
        int verticalForce = 0;

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.J)) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
                player.getBody().setBullet(true);
                player.getBody().applyLinearImpulse(new Vector2(-100f, 0), player.getBody().getWorldCenter(), true);
            } else {
                player.getBody().setBullet(false);
                horizontalForce--;
            }
            dir = direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.L)) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
                player.getBody().setBullet(true);
                player.getBody().applyLinearImpulse(new Vector2(100f, 0), player.getBody().getWorldCenter(), true);
            } else {
                player.getBody().setBullet(false);
                horizontalForce++;
            }
            dir = direction.RIGHT;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.I)) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
                player.getBody().setBullet(true);
                player.getBody().applyLinearImpulse(new Vector2(0, 100f), player.getBody().getWorldCenter(), true);
            } else {
                verticalForce++;
                player.getBody().setBullet(false);
            }
            dir = direction.UP;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.K)) {
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
                player.getBody().setBullet(true);
                player.getBody().applyLinearImpulse(new Vector2(0, -100f), player.getBody().getWorldCenter(), true);
            } else {
                player.getBody().setBullet(false);
                verticalForce--;
            }
            dir = direction.DOWN;
        }
        if (!Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            player.getBody().setLinearVelocity(horizontalForce * 5f, verticalForce * 5f);
        }
        System.out.println(player.getBody().getPosition().x*32*1.5+35 + " : " + player.getBody().getPosition().y*32*1.5f+25);
        //System.out.println(player.getPosition().x + " : " + player.getPosition().y);
        player.setPosition(new Vector2(player.getBody().getPosition().x * 48 + 35, player.getBody().getPosition().y * 48 - 25));
    }
}
