package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Objects;
import com.mygdx.game_project.entities.Player;

import java.util.ArrayList;

public class Input {
    public enum direction {
        UP, DOWN, RIGHT, LEFT
    }
    public static direction dir = direction.LEFT;

    /**
     *
     * @param delta Tiempo en segundos desde el último render.
     * @param player Jugador al que se le aplica las fuerzas para el movimiento.
     */
    public static void movementInput(float delta, Player player) {
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
        // System.out.println(player.getBody().getPosition().x*32*1.5+35 + " : " + player.getBody().getPosition().y*32*1.5f+25);
        // System.out.println(player.getPosition().x + " : " + player.getPosition().y);
        player.setPosition(new Vector2(player.getBody().getPosition().x * 48 + 35, player.getBody().getPosition().y * 48 - 25));
    }

    private static ArrayList<Objects> bullets;
    private static ArrayList<Objects> delBullets;
    /**
     *
     * @param delta Tiempo en segundos desde el último render.
     * @param player Jugador que ataca.
     * @param world Mundo en el que se crean los ataques.
     */
    public static void atackInput(float delta, Player player, Enemy enemy, World world) {
        bullets = new ArrayList<>();
        delBullets = new ArrayList<>();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.A)) {
            bullets.add(new Objects(world, player.getPosition(), 5));

            for (Objects bullet : bullets) {
                if (bullet.isAlive()) bullet.getBody().applyForce(new Vector2(50f,50f), enemy.getPosition(), true);
                //else delBullets.add(bullet);
            }
            bullets.removeAll(delBullets);
        }

        //Gdx.app.log("INFO",String.format(enemy.getPosition().x + " : " + enemy.getPosition().y));
    }

    //region $setter&getters

    public static ArrayList<Objects> getBullets() {
        return bullets;
    }

    public static void setBullets(ArrayList<Objects> bullets) {
        Input.bullets = bullets;
    }

    //endregion
}
