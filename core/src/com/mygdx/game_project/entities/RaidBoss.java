package com.mygdx.game_project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.utils.CreateHitbox;
import com.mygdx.game_project.utils.Sounds;

import static com.mygdx.game_project.constants.Constant.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RaidBoss extends CreateHitbox {

    private float dmg, hp, armor, atkSpeed, width, height, timer;
    private Vector2 position;
    private ArrayList<Bullets> bullets;
    private Body body;
    private boolean alive;
    private TextureAtlas bossAtlas = new TextureAtlas("Boss\\Boss.atlas");
    private TextureRegion bulletTexture = bossAtlas.findRegion("Projectile");
    private TextureRegion actualFrame;
    private HashMap<String, Animation<TextureAtlas.AtlasRegion>> animation;
    private World world;
    private Music startSound, hitSound, attackSound, deadSound, hardenSound;
    public enum state {
        STARTING,
        IDLE,
        HARDEN,
        ATTACK,
        DEAD
    }
    private state currentState;

    public RaidBoss(World world, Vector2 position, float width, float height) {
        super(world, position, width, height, 100000, false, true,
                false, category.ENEMY_NO_COLL.bits(), 3, false);
        fixture.setUserData(this);
        this.hp = 20;
        this.armor = 1;
        this.dmg = super.dmg;
        this.body = super.body;
        this.alive = true;
        this.currentState = state.STARTING;
        this.position = position;
        this.width = width;
        this.height = height;
        this.world = world;
        bullets = new ArrayList<>();
        startSound = Gdx.audio.newMusic(Gdx.files.internal("Sounds\\BossStart.mp3"));
        startSound.setLooping(false);
        startSound.setVolume(Sounds.volume);
        hitSound = Gdx.audio.newMusic(Gdx.files.internal("Sounds\\BossHit.mp3"));
        hitSound.setLooping(false);
        hitSound.setVolume(Sounds.volume);
        attackSound = Gdx.audio.newMusic(Gdx.files.internal("Sounds\\BossAttack.mp3"));
        attackSound.setLooping(false);
        attackSound.setVolume(Sounds.volume);
        deadSound = Gdx.audio.newMusic(Gdx.files.internal("Sounds\\BossDeath.mp3"));
        deadSound.setLooping(false);
        deadSound.setVolume(Sounds.volume);
        hardenSound = Gdx.audio.newMusic(Gdx.files.internal("Sounds\\BossHarden.mp3"));
        hardenSound.setLooping(false);
        hardenSound.setVolume(Sounds.volume);
        animate();
    }

    /**
     * Inicializa los sprites del Jefe Final
     */
    public void animate() {
        animation = new HashMap<>();

        animation.put("starting", new Animation<>(.5f,
                bossAtlas.findRegion("BossStarting1"),
                bossAtlas.findRegion("BossStarting2"),
                bossAtlas.findRegion("BossStarting3"),
                bossAtlas.findRegion("BossStarting4"),
                bossAtlas.findRegion("BossStarting5"),
                bossAtlas.findRegion("BossStarting6"),
                bossAtlas.findRegion("BossStarting7"),
                bossAtlas.findRegion("BossStarting8")));

        animation.put("idle", new Animation<>(.3f,
                bossAtlas.findRegion("BossIdle1"),
                bossAtlas.findRegion("BossIdle2"),
                bossAtlas.findRegion("BossIdle3"),
                bossAtlas.findRegion("BossIdle4")));

        animation.put("harden", new Animation<>(.5f,
                bossAtlas.findRegion("BossHarden1"),
                bossAtlas.findRegion("BossHarden2"),
                bossAtlas.findRegion("BossHarden3"),
                bossAtlas.findRegion("BossHarden4"),
                bossAtlas.findRegion("BossHarden5"),
                bossAtlas.findRegion("BossHarden6"),
                bossAtlas.findRegion("BossHarden7"),
                bossAtlas.findRegion("BossHarden8"),
                bossAtlas.findRegion("BossHarden9"),
                bossAtlas.findRegion("BossHarden10")));

        animation.put("attack", new Animation<>(.2f,
                bossAtlas.findRegion("BossAttack1"),
                bossAtlas.findRegion("BossAttack2"),
                bossAtlas.findRegion("BossAttack3"),
                bossAtlas.findRegion("BossAttack4"),
                bossAtlas.findRegion("BossAttack5"),
                bossAtlas.findRegion("BossAttack6"),
                bossAtlas.findRegion("BossAttack7"),
                bossAtlas.findRegion("BossAttack8"),
                bossAtlas.findRegion("BossAttack9")));

        animation.put("death", new Animation<>(.5f,
                bossAtlas.findRegion("BossDeath1"),
                bossAtlas.findRegion("BossDeath2"),
                bossAtlas.findRegion("BossDeath3"),
                bossAtlas.findRegion("BossDeath4"),
                bossAtlas.findRegion("BossDeath5"),
                bossAtlas.findRegion("BossDeath6"),
                bossAtlas.findRegion("BossDeath7"),
                bossAtlas.findRegion("BossDeath8"),
                bossAtlas.findRegion("BossDeath9"),
                bossAtlas.findRegion("BossDeath10"),
                bossAtlas.findRegion("BossDeath11"),
                bossAtlas.findRegion("BossDeath12"),
                bossAtlas.findRegion("BossDeath13"),
                bossAtlas.findRegion("BossDeath14")));
    }

    /**
     * Anima el Jefe Final dependiendo del estado
     * @param batch
     */
    public void draw(Batch batch) {
        timer += Gdx.graphics.getDeltaTime();
        switch (currentState) {
            case STARTING:
                actualFrame = animation.get("starting").getKeyFrame(timer, false);
                batch.draw(actualFrame, position.x * PPU - width - 250 * PPU / 2, position.y * PPU - height - 250 * PPU / 2, (width + 300) * PPU, (height + 300) * PPU);
                break;

            case IDLE:
                actualFrame = animation.get("idle").getKeyFrame(timer, true);
                batch.draw(actualFrame, position.x * PPU - width - 250 * PPU / 2, position.y * PPU - height - 250 * PPU / 2, (width + 300) * PPU, (height + 300) * PPU);
                break;

            case HARDEN:
                actualFrame = animation.get("harden").getKeyFrame(timer, false);
                batch.draw(actualFrame, position.x * PPU - width - 250 * PPU / 2, position.y * PPU - height - 250 * PPU / 2, (width + 300) * PPU, (height + 300) * PPU);
                break;

            case ATTACK:
                actualFrame = animation.get("attack").getKeyFrame(timer, false);
                batch.draw(actualFrame, position.x * PPU - width - 250 * PPU / 2, position.y * PPU - height - 250 * PPU / 2, (width + 300) * PPU, (height + 300) * PPU);
                break;

            case DEAD:
                actualFrame = animation.get("death").getKeyFrame(timer, false);
                batch.draw(actualFrame, position.x * PPU - width - 250 * PPU / 2, position.y * PPU - height - 250 * PPU / 2, (width + 300) * PPU, (height + 300) * PPU);
                break;
        }

        for (Bullets bullet : bullets) {
            Gdx.app.log("INFO", "bullet");
            batch.draw(bulletTexture, bullet.getBody().getWorldCenter().x * 32 * PPU - bullet.getWidth() * PPU / 2, bullet.getBody().getWorldCenter().y * 32 * PPU - bullet.getHeight() * PPU / 2,
                    bullet.getWidth() * PPU, bullet.getHeight() * PPU);
        }
    }

    /**
     * Actualiza el comportamiento segun su estado en la pelea
     * @param delta
     * @param player jugador
     */
    public void update(float delta, Player player) {
        timer += delta;

        switch (currentState) {
            case STARTING:
                startingBehaviour();
                break;

            case IDLE:
                idleBehaviour();
                break;

            case HARDEN:
                hardenBehaviour();
                break;

            case ATTACK:
                attackBehaviour(player);
                break;

            case DEAD:
                deadBehaviour();
                break;
        }
    }

    /**
     * Comportamiento al iniciar el jefe
     */
    public void startingBehaviour() {
        if (!startSound.isPlaying()) startSound.play();
        if (animation.get("starting").isAnimationFinished(timer)) {
            currentState = state.IDLE;
            timer = 0;
        }
    }

    /**
     * Comportamiento una vez iniciada la pelea
     */
    public void idleBehaviour() {
        armor = 1;
        Gdx.app.log("INFO", "hp: " + hp);
        int action = (int) (Math.random() * 100);
        if (timer > 4f) {
            timer = 0;
            if (action < 70) {
                currentState = state.ATTACK;
            }
            if (action >= 70) {
                currentState = state.HARDEN;
            }
//            if (action>=80) {
//                currentState = state.BEAM;
//            }
        }
    }

    /**
     * Comportamiento tras activar el endurecimiento
     */
    public void hardenBehaviour() {
        if (!hardenSound.isPlaying()) hardenSound.play();
        armor = 3;
        if (animation.get("harden").isAnimationFinished(timer)) {
            currentState = state.IDLE;
            timer = 0;
        }
    }

    /**
     * Comportamiento tras activar el ataque
     * @param player
     */
    public void attackBehaviour(Player player) {
        if (!attackSound.isPlaying()) attackSound.play();
        Vector2 bulletDir;
        if (animation.get("attack").isAnimationFinished(timer)) {
            bullets.add(new Bullets(world, new Vector2(position.x + 100, position.y + 75), 70, 30, 0, dmg, 15, category.ENEMY_NO_COLL.bits()));
            for (Bullets bullet : bullets) {
                if (bullet.isAlive()) {
                    bulletDir = new Vector2(player.getBody().getPosition().x * 32 - bullet.getPosition().x,
                            player.getBody().getPosition().y * 32 - bullet.getPosition().y).nor();

                    if (!bullet.isShot()) {
                        bullet.getBody().setLinearVelocity(new Vector2(bullet.getSpeed() * bulletDir.x,
                                bullet.getSpeed() * bulletDir.y));
                        bullet.setShot(true);
                    }
                }
            }

            currentState = state.IDLE;
            timer = 0;
        }
    }

    /**
     * Comportamiento una vez muerto
     */
    public void deadBehaviour() {
        if (!deadSound.isPlaying()) deadSound.play();
        if (animation.get("death").isAnimationFinished(timer)) {
            alive = false;
            timer = 0;
        }
    }

    @Override
    public void onHit(Object object) {
        if (object instanceof Float) {
            if (currentState != state.STARTING) {
                hitSound.play();
                hp -= (float) object / armor;
                if (hp <= 0) currentState = state.DEAD;
            }
        }
    }

    //region $Setters&Getters

    public float getDmg() {
        return dmg;
    }

    public void setDmg(float dmg) {
        this.dmg = dmg;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getAtkSpeed() {
        return atkSpeed;
    }

    public void setAtkSpeed(float atkSpeed) {
        this.atkSpeed = atkSpeed;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public state getCurrentState() {
        return currentState;
    }

    public void setCurrentState(state currentState) {
        this.currentState = currentState;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public ArrayList<Bullets> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullets> bullets) {
        this.bullets = bullets;
    }

    //endregion
}
