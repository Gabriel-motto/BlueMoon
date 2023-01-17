package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Bullets;
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
            player.getBody().setLinearVelocity(horizontalForce * player.getSpeed(), verticalForce * player.getSpeed());
        }

        // Gdx.app.log("INFO", "Vel: " + horizontalForce * 5f + " : " + verticalForce * 5f);
        // System.out.println(player.getBody().getPosition().x*32*1.5+35 + " : " + player.getBody().getPosition().y*32*1.5f+25);
        // System.out.println(player.getPosition().x + " : " + player.getPosition().y);

        player.setPosition(new Vector2(player.getBody().getPosition().x * 48 + 35, player.getBody().getPosition().y * 48 - 25));
    }

    private static ArrayList<Bullets> bullets = new ArrayList<>();
    private static ArrayList<Bullets> delBullets = new ArrayList<>();
    private static Vector2 bulletDir;
    private static Enemy closestEnemy;
    /**
     *
     * @param delta Tiempo en segundos desde el último render.
     * @param player Jugador que ataca.
     * @param world Mundo en el que se crean los ataques.
     */
    public static void atackInput(float delta, Player player, ArrayList<Enemy> enemies, World world) {
        getClosestEnemy(enemies, player);
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.A) && !enemies.isEmpty()) {
            bullets.add(new Bullets(world, new Vector2(player.getPosition().x/1.59f, player.getPosition().y/1.41f), 5, player.getDmg(), 10));

            for (Bullets bullet : bullets) {
                if (bullet.isAlive() && !bullet.isMoved()) {
                    Gdx.app.log("POSITION", "Enemy: " + closestEnemy.getPosition().x + " : " + closestEnemy.getPosition().y);
                    Gdx.app.log("INFO", "Vel: " + ((closestEnemy.getBody().getPosition().x * 32 - bullet.getPosition().x) * 5f));

                    bulletDir = new Vector2((closestEnemy.getBody().getPosition().x * 32 - bullet.getPosition().x), (closestEnemy.getBody().getPosition().y * 32 - bullet.getPosition().y)).nor();

                    bullet.getBody().setLinearVelocity(new Vector2(bullet.getSpeed() * bulletDir.x, bullet.getSpeed() * bulletDir.y));
                    bullet.setMoved(true);
                }
            }
        }

        //Gdx.app.log("INFO",String.format(enemy.getPosition().x + " : " + enemy.getPosition().y));
    }

    private static float distanceClosestEnemy;
    public static void getClosestEnemy(ArrayList<Enemy> enemies, Player player) {
        float distanceX;
        float distanceY;
        for (int i = 0; i < enemies.size(); i++) {
            distanceX = (enemies.get(i).getBody().getPosition().x - player.getBody().getPosition().x) > 0 ? (enemies.get(i).getBody().getPosition().x - player.getBody().getPosition().x) : -(enemies.get(i).getBody().getPosition().x - player.getBody().getPosition().x);
            distanceY = (enemies.get(i).getBody().getPosition().y - player.getBody().getPosition().y) > 0 ? (enemies.get(i).getBody().getPosition().y - player.getBody().getPosition().y) : -(enemies.get(i).getBody().getPosition().y - player.getBody().getPosition().y);

//            Gdx.app.log("INFO", "xPos: " + distanceX);
//            Gdx.app.log("INFO", "yPos: " + distanceY);

            if (i == 0) {
                distanceClosestEnemy = distanceX + distanceY;
                closestEnemy = enemies.get(i);
            } else {
                if ((distanceX + distanceY) < distanceClosestEnemy) {
                    distanceClosestEnemy = distanceX + distanceY;
                    closestEnemy = enemies.get(i);
                }
            }
        }
    }

    public static void deleteBullets(World world) {
        for (Bullets bullet : bullets) {
            if (!bullet.isAlive()) {
                delBullets.add(bullet);
                world.destroyBody(bullet.getBody());
            }
        }
        bullets.removeAll(delBullets);

    }

    private static ArrayList<Enemy> delEnemies;
    public static void deleteEnemies(World world, ArrayList<Enemy> enemies, Player player) {
        delEnemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) {
                delEnemies.add(enemy);
                world.destroyBody(enemy.getBody());
                player.setDmg(player.getDmg() + 1);
            }
        }
        enemies.removeAll(delEnemies);
    }

    //region $setter&getters

    public static ArrayList<Bullets> getBullets() {
        return bullets;
    }

    public static void setBullets(ArrayList<Bullets> bullets) {
        Input.bullets = bullets;
    }

    //endregion
}
