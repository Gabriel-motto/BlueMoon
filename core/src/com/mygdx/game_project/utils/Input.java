package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Bullets;
import com.mygdx.game_project.entities.Player;
import com.mygdx.game_project.entities.RaidBoss;

import java.util.ArrayList;

public class Input extends InputAdapter{
    public enum direction {
        UP, DOWN, RIGHT, LEFT
    }
    public static direction dir = direction.LEFT;
    public static boolean isTouchpad = false;
    private static World world;
    private static Player player;
    private static ArrayList<Enemy> enemies;
    private static Stage stage;
    private static ArrayList<Bullets> bullets = new ArrayList<>();
    private static ArrayList<RaidBoss> raidBoss = new ArrayList<>();
    private static Vector2 bulletDir;
    private static Enemy closestEnemy;
    private static float timer = 0f;
    private static boolean isTouchDown = false;
    private static Sounds throwSound;

    public Input(World world, Player player, ArrayList<Enemy> enemies, ArrayList<RaidBoss> raidBoss, Stage stage) {
        Input.world = world;
        Input.player = player;
        Input.enemies = enemies;
        Input.stage = stage;
        Input.raidBoss = raidBoss;
        throwSound = new Sounds("Sounds\\Throw1.mp3");
        setInpProcessors();
    }

    /**
     * Inicializa el procesador de entrada de acciones
     */
    public void setInpProcessors() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Gestión del movimiento del jugador
     * @param delta Tiempo en segundos desde el último render.
     * @param player Jugador al que se le aplica las fuerzas de movimiento.
     */
    public static void movementInput(float delta, Player player, Touchpad touchpad) {
        float horizontalForce = 0;
        float verticalForce = 0;

        //teclado
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

        // touch
        if (touchpad.isTouched()) {
            horizontalForce = touchpad.getKnobPercentX();
            verticalForce = touchpad.getKnobPercentY();

            player.getBody().setLinearVelocity(horizontalForce * player.getSpeed(), verticalForce * player.getSpeed());
            isTouchpad = true;
            if (touchpad.getKnobPercentY() < .75f && touchpad.getKnobPercentY() > -.75f) {
                if (touchpad.getKnobPercentX() < 0) dir = direction.LEFT;
                if (touchpad.getKnobPercentX() > 0) dir = direction.RIGHT;
            }
            if (touchpad.getKnobPercentX() < .75f && touchpad.getKnobPercentX() > -.75f) {
                if (touchpad.getKnobPercentY() < 0) dir = direction.DOWN;
                if (touchpad.getKnobPercentY() > 0) dir = direction.UP;
            }
        }

        player.setPosition(new Vector2(player.getBody().getWorldCenter().x*32, player.getBody().getWorldCenter().y*32));
    }

    /**
     * Controla el tiempo en el que el jugador puede atacar
     * @param delta Tiempo en segundos desde el último render.
     * @param atkSpeed velocidad de ataque del jugador
     */
    public static void atackInput(float delta, float atkSpeed) {
        timer += delta;
        if (timer > atkSpeed) {
            timer -= atkSpeed;
            if (!enemies.isEmpty() || !raidBoss.isEmpty()) {
                attack();
            }
        }
    }

    /**
     * Crea la bala y le aplica una fuerza hacia el enemigo mas cercano
     */
    public static void attack() {
        bullets.add(new Bullets(world, player.getPosition(), 7, player.getAtk(), 10, CreateHitbox.category.PLAYER_NO_COLL.bits()));
        throwSound.play(false);

        for (Bullets bullet : bullets) {
            if (bullet.isAlive()) {
                if (!enemies.isEmpty()) {
                    getClosestEnemy(enemies, player);
                    bulletDir = new Vector2((closestEnemy.getBody().getPosition().x * 32 - bullet.getPosition().x), (closestEnemy.getBody().getPosition().y * 32 - bullet.getPosition().y)).nor();
                    if (!bullet.isShot()) {
                        bullet.getBody().setLinearVelocity(new Vector2(bullet.getSpeed() * bulletDir.x, bullet.getSpeed() * bulletDir.y));
                        bullet.setShot(true);
                    }
                }
                if (!raidBoss.isEmpty()) {
                    bulletDir = new Vector2(raidBoss.get(0).getBody().getWorldCenter().x * 32 - bullet.getPosition().x, raidBoss.get(0).getBody().getWorldCenter().y * 32 - bullet.getPosition().y).nor();
                    if (!bullet.isShot()) {
                        bullet.getBody().setLinearVelocity(new Vector2(bullet.getSpeed() * bulletDir.x, bullet.getSpeed() * bulletDir.y));
                        bullet.setShot(true);
                    }
                }
            }
        }
    }

    private static float distanceClosestEnemy;

    /**
     * Calcula el enemigo mas cercano
     * @param enemies enemigos creados en el mapa
     * @param player jugador
     */
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

    //region $setter&getters

    public static ArrayList<Bullets> getBullets() {
        return bullets;
    }

    public static void setBullets(ArrayList<Bullets> bullets) {
        Input.bullets = bullets;
    }

    //endregion
}
