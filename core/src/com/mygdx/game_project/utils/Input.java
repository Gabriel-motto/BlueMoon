package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Bullets;
import com.mygdx.game_project.entities.Player;
import java.util.ArrayList;
import static com.mygdx.game_project.constants.Constant.*;

public class Input extends InputAdapter{
    public enum direction {
        UP, DOWN, RIGHT, LEFT
    }
    public static direction dir = direction.LEFT;
    public static boolean isTouchpad = false;
    private World world;
    private Player player;
    private ArrayList<Enemy> enemies;
    private Stage stage;

    public Input(World world, Player player, ArrayList<Enemy> enemies, Stage stage) {
        this.world = world;
        this.player = player;
        this.enemies = enemies;
        this.stage = stage;

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     *
     * @param delta Tiempo en segundos desde el último render.
     * @param player Jugador al que se le aplica las fuerzas para el movimiento.
     */
    public static void movementInput(float delta, Player player, Touchpad touchpad) {
        float horizontalForce = 0;
        float verticalForce = 0;

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

        if (touchpad.isTouched()) {
            horizontalForce = touchpad.getKnobPercentX();
            verticalForce = touchpad.getKnobPercentY();

            Gdx.app.log("KNOBPER", touchpad.getKnobPercentX() + " : " + touchpad.getKnobPercentY());

            player.getBody().setLinearVelocity(horizontalForce * player.getSpeed(), verticalForce * player.getSpeed());
            isTouchpad = true;
            if (touchpad.getKnobPercentY() < .5f && touchpad.getKnobPercentY() > -.5f) {
                if (touchpad.getKnobPercentX() < 0) dir = direction.LEFT;
                if (touchpad.getKnobPercentX() > 0) dir = direction.RIGHT;
            }
            if (touchpad.getKnobPercentX() < .5f && touchpad.getKnobPercentX() > -.5f) {
                if (touchpad.getKnobPercentY() < 0) dir = direction.DOWN;
                if (touchpad.getKnobPercentY() > 0) dir = direction.UP;
            }
        }

        // Gdx.app.log("INFO","" + horizontalForce);

        // Gdx.app.log("INFO", "Vel: " + horizontalForce * 5f + " : " + verticalForce * 5f);
        // System.out.println(player.getBody().getPosition().x*32*1.5+35 + " : " + player.getBody().getPosition().y*32*1.5f+25);
        // System.out.println(player.getPosition().x + " : " + player.getPosition().y);

//        Gdx.app.log("INFO",player.getBody().getPosition().x + " : " + player.getBody().getPosition().y);
//        Gdx.app.log("INFO",player.getBody().getWorldCenter().x*32 + " : " + player.getBody().getWorldCenter().y*32);

        player.setPosition(new Vector2(player.getBody().getWorldCenter().x*32, player.getBody().getWorldCenter().y*32));
    }

    private static ArrayList<Bullets> bullets = new ArrayList<>();
    private static ArrayList<Bullets> delBullets = new ArrayList<>();
    private static Vector2 bulletDir;
    private static Enemy closestEnemy;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isTouchpad = false;
        return true;
    }

    private static float timer = 0f;

    /**
     *
     * @param delta Tiempo en segundos desde el último render.
     * @param player Jugador que ataca.
     * @param world Mundo en el que se crean los ataques.
     */
    public static void atackInput(float delta, final Player player, float atkSpeed, ArrayList<Enemy> enemies, final World world) {
        timer += delta;
        if (timer > atkSpeed) {
            timer -= atkSpeed;
            getClosestEnemy(enemies, player);
            if ((Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) && !enemies.isEmpty()) {
                atack(player, atkSpeed, enemies, world);
            }
            for (int i = 0; i < 1; i++) {
                if (Gdx.input.isTouched(i) && Gdx.input.getX(i) > Gdx.graphics.getWidth()/2) {
                    atack(player, atkSpeed, enemies, world);
                }
            }
        }

        //Gdx.app.log("INFO",String.format(enemy.getPosition().x + " : " + enemy.getPosition().y));
    }

    public static void atack(final Player player, float atkSpeed, ArrayList<Enemy> enemies, final World world) {
        bullets.add(new Bullets(world, player.getPosition(), 7, player.getDmg(), 10));

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

    private static float distanceClosestEnemy;
    public static void getClosestEnemy(ArrayList<Enemy> enemies, Player player) {
        float distanceX;
        float distanceY;
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isFocusable()) {
                distanceX = (enemies.get(i).getBody().getWorldCenter().x - player.getBody().getWorldCenter().x) > 0 ? (enemies.get(i).getBody().getWorldCenter().x - player.getBody().getWorldCenter().x) : -(enemies.get(i).getBody().getWorldCenter().x - player.getBody().getWorldCenter().x);
                distanceY = (enemies.get(i).getBody().getWorldCenter().y - player.getBody().getWorldCenter().y) > 0 ? (enemies.get(i).getBody().getWorldCenter().y - player.getBody().getWorldCenter().y) : -(enemies.get(i).getBody().getWorldCenter().y - player.getBody().getWorldCenter().y);

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
            } else {
                for (int j = 0; j < enemies.size(); j++) {
                    if (enemies.get(j).isFocusable()) {
                        closestEnemy = enemies.get(j);
                    }
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
    public static void deleteEnemies(World world, ArrayList<Enemy> enemies, Preferences prefs) {
        delEnemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) {
                prefs.putInteger("enemiesKilled", prefs.getInteger("enemiesKilled")+1);
                delEnemies.add(enemy);
            }
        }
        for (Enemy delEnemy : delEnemies) {
            world.destroyBody(delEnemy.getBody());
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

    public static ArrayList<Enemy> getDelEnemies() {
        return delEnemies;
    }

    public static void setDelEnemies(ArrayList<Enemy> delEnemies) {
        Input.delEnemies = delEnemies;
    }

    //endregion
}
