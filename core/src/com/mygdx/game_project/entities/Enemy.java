package com.mygdx.game_project.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game_project.utils.CreateHitbox;
import java.util.HashMap;
import static com.mygdx.game_project.constants.Constant.PPU;

public class Enemy extends CreateHitbox {
    Vector2 position;
    private float width, height, speed, dmg, armor, hp, maxHp;
    private Body body;
    private boolean alive = true;
    private boolean focusable = true;
    private boolean attacking = false;
    private TextureAtlas goblinAtlas = new TextureAtlas("Enemies\\Goblin\\Goblin.atlas");
    private TextureAtlas wraithAtlas = new TextureAtlas("Enemies\\Wraith\\Wraith.atlas");
    private TextureAtlas mummyAtlas = new TextureAtlas("Enemies\\Mummy\\Mummy.atlas");
    private HashMap<String, Animation<TextureAtlas.AtlasRegion>> animation;
    private TextureRegion actualFrame;
    private float time, delay;
    public enum states {
        SLEEP,
        HOSTILE,
        AVOID;
    }
    private states currentState;
    public Enemy(World world, Vector2 position, int width, int height, float speed, float dmg, float armor, float hp) {
        super(world, position, width, height, 10f, false, true, false, category.COLLISION.bits(), dmg);
        fixture.setUserData(this);
        this.position = position;
        this.width = width;
        this.height = height;
        this.body = super.body;
        int rand = (int)Math.floor(Math.random() * 3);
        switch (rand) {
            case 0:
                this.speed = speed;
                this.dmg = dmg + 1;
                this.armor = armor;
                this.hp = hp - 4;
                this.maxHp = hp;
                this.currentState = states.SLEEP;
                break;

            case 1:
                this.speed = speed;
                this.dmg = dmg;
                this.armor = armor + 2;
                this.hp = hp - 4;
                this.maxHp = hp;
                this.currentState = states.HOSTILE;
                break;

            case 2:
                this.speed = speed;
                this.dmg = dmg;
                this.armor = armor;
                this.hp = hp;
                this.maxHp = hp;
                this.currentState = states.AVOID;
                break;
        }

        animate();
    }
    public void animate() {
        animation = new HashMap<>();
        switch (currentState) {
            case HOSTILE:
                animation.put("idle", new Animation<>(.3f,
                        goblinAtlas.findRegion("goblin-idle(1)"),
                        goblinAtlas.findRegion("goblin-idle(2)"),
                        goblinAtlas.findRegion("goblin-idle(3)"),
                        goblinAtlas.findRegion("goblin-idle(4)")));
                animation.put("attackRight", new Animation<>(.5f,
                        goblinAtlas.findRegion("goblin-attack(1)"),
                        goblinAtlas.findRegion("goblin-attack(2)"),
                        goblinAtlas.findRegion("goblin-attack(3)"),
                        goblinAtlas.findRegion("goblin-attack(4)")));
                animation.put("attackLeft", new Animation<>(.5f,
                        goblinAtlas.findRegion("goblin-attack(5)"),
                        goblinAtlas.findRegion("goblin-attack(6)"),
                        goblinAtlas.findRegion("goblin-attack(7)"),
                        goblinAtlas.findRegion("goblin-attack(8)")));
                animation.put("runRight", new Animation<>(.3f,
                        goblinAtlas.findRegion("goblin-run(1)"),
                        goblinAtlas.findRegion("goblin-run(2)"),
                        goblinAtlas.findRegion("goblin-run(3)"),
                        goblinAtlas.findRegion("goblin-run(4)")));
                animation.put("runLeft", new Animation<>(.3f,
                        goblinAtlas.findRegion("goblin-run(5)"),
                        goblinAtlas.findRegion("goblin-run(6)"),
                        goblinAtlas.findRegion("goblin-run(7)"),
                        goblinAtlas.findRegion("goblin-run(8)")));
                break;

            case AVOID:
                animation.put("idle", new Animation<>(.3f,
                        mummyAtlas.findRegion("mummy-idle(1)"),
                        mummyAtlas.findRegion("mummy-idle(2)"),
                        mummyAtlas.findRegion("mummy-idle(3)"),
                        mummyAtlas.findRegion("mummy-idle(4)")));
                animation.put("attackRight", new Animation<>(.7f,
                        mummyAtlas.findRegion("mummy-attack(1)"),
                        mummyAtlas.findRegion("mummy-attack(2)"),
                        mummyAtlas.findRegion("mummy-attack(3)"),
                        mummyAtlas.findRegion("mummy-attack(4)")));
                animation.put("attackLeft", new Animation<>(.7f,
                        mummyAtlas.findRegion("mummy-attack(5)"),
                        mummyAtlas.findRegion("mummy-attack(6)"),
                        mummyAtlas.findRegion("mummy-attack(7)"),
                        mummyAtlas.findRegion("mummy-attack(8)")));
                animation.put("runRight", new Animation<>(.3f,
                        mummyAtlas.findRegion("mummy-run(1)"),
                        mummyAtlas.findRegion("mummy-run(2)"),
                        mummyAtlas.findRegion("mummy-run(3)"),
                        mummyAtlas.findRegion("mummy-run(4)")));
                animation.put("runLeft", new Animation<>(.3f,
                        mummyAtlas.findRegion("mummy-run(5)"),
                        mummyAtlas.findRegion("mummy-run(6)"),
                        mummyAtlas.findRegion("mummy-run(7)"),
                        mummyAtlas.findRegion("mummy-run(8)")));
                break;

            case SLEEP:
                animation.put("idle", new Animation<>(.3f,
                        wraithAtlas.findRegion("wraith-idle(1)"),
                        wraithAtlas.findRegion("wraith-idle(2)"),
                        wraithAtlas.findRegion("wraith-idle(3)"),
                        wraithAtlas.findRegion("wraith-idle(4)")));
                animation.put("attackRight", new Animation<>(.7f,
                        wraithAtlas.findRegion("wraith-attack(1)"),
                        wraithAtlas.findRegion("wraith-attack(2)"),
                        wraithAtlas.findRegion("wraith-attack(3)"),
                        wraithAtlas.findRegion("wraith-attack(4)")));
                animation.put("attackLeft", new Animation<>(.7f,
                        wraithAtlas.findRegion("wraith-attack(5)"),
                        wraithAtlas.findRegion("wraith-attack(6)"),
                        wraithAtlas.findRegion("wraith-attack(7)"),
                        wraithAtlas.findRegion("wraith-attack(8)")));
                animation.put("runRight", new Animation<>(.3f,
                        wraithAtlas.findRegion("wraith-run(1)"),
                        wraithAtlas.findRegion("wraith-run(2)"),
                        wraithAtlas.findRegion("wraith-run(3)"),
                        wraithAtlas.findRegion("wraith-run(4)")));
                animation.put("runLeft", new Animation<>(.3f,
                        wraithAtlas.findRegion("wraith-run(5)"),
                        wraithAtlas.findRegion("wraith-run(6)"),
                        wraithAtlas.findRegion("wraith-run(7)"),
                        wraithAtlas.findRegion("wraith-run(8)")));
                break;
        }
    }
    public void draw(Batch batch) {
        time += Gdx.graphics.getDeltaTime();

        if (body.getLinearVelocity().x < 0) {
            if (isAttacking()) {
                delay += Gdx.graphics.getDeltaTime();
                body.setLinearVelocity(0,0);
                actualFrame = animation.get("attackLeft").getKeyFrame(time, false);
                batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*1.25f*PPU, height*1.25f*PPU);
                if (delay > 1f) {
                    setAttacking(false);
                    delay -= 1f;
                }
            } else {
                actualFrame = animation.get("runLeft").getKeyFrame(time, true);
                batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*1.25f*PPU, height*1.25f*PPU);
            }
        }
        if (body.getLinearVelocity().x > 0) {
            if (isAttacking()) {
                delay += Gdx.graphics.getDeltaTime();
                body.setLinearVelocity(0,0);
                actualFrame = animation.get("attackRight").getKeyFrame(time, false);
                batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*1.25f*PPU, height*1.25f*PPU);
                if (delay > 1f) {
                    setAttacking(false);
                    delay -= 1f;
                }
            } else {
                actualFrame = animation.get("runRight").getKeyFrame(time, true);
                batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*1.25f*PPU, height*1.25f*PPU);
            }
        }
        if (body.getLinearVelocity().x == 0 && !isAttacking()) {
            actualFrame = animation.get("idle").getKeyFrame(time, true);
            batch.draw(actualFrame, position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*1.25f*PPU, height*1.25f*PPU);
        }
    }
    @Override
    public void onHit(Object object) {
        if (object instanceof Bullets) {
            hp -= (float) object /armor;
            if (currentState == states.SLEEP) currentState = states.HOSTILE;
            Gdx.app.log("INFO", "Enemy hp: " + hp);
            if (hp <= 0) setAlive(false);
        }
        if (object instanceof Player) {
            setAttacking(true);
        }
    }

    float randDirX = (float) (Math.random() * 2) - 1;
    float randDirY = (float) (Math.random() * 2) - 1;
    public void updateBehavior(float delta, Player player) {
        if (isAlive()) {
            Vector2 enemyDir;
            float playerDistanceX;
            float playerDistanceY;
            switch (currentState) {
                case HOSTILE:
                    enemyDir  = new Vector2((player.getBody().getPosition().x * 32 - body.getPosition().x * 32), (player.getBody().getPosition().y * 32 - body.getPosition().y * 32)).nor();
                    body.setLinearVelocity(new Vector2(speed * enemyDir.x, speed * enemyDir.y));
                    break;

                case AVOID:
                    enemyDir = new Vector2((player.getBody().getPosition().x * 32 - body.getPosition().x * 32), (player.getBody().getPosition().y * 32 - body.getPosition().y * 32)).nor();
                    playerDistanceX = (player.getBody().getPosition().x - body.getPosition().x) > 0 ? (player.getBody().getPosition().x - body.getPosition().x) : -(player.getBody().getPosition().x - body.getPosition().x);
                    playerDistanceY = (player.getBody().getPosition().y - body.getPosition().y) > 0 ? (player.getBody().getPosition().y - body.getPosition().y) : -(player.getBody().getPosition().y - body.getPosition().y);

                    if (playerDistanceX + playerDistanceY < 7) {
                        body.setLinearVelocity(new Vector2(speed * -enemyDir.x, speed * -enemyDir.y));
                        randDirX = (float) (Math.random() * 2) - 1;
                        randDirY = (float) (Math.random() * 2) - 1;
                    } else {
                        body.setLinearVelocity(new Vector2(speed * randDirX, speed * randDirY));
                    }
                    break;

                case SLEEP:
                    this.speed += 0.005f;
                    playerDistanceX = (player.getBody().getPosition().x - body.getPosition().x) > 0 ? (player.getBody().getPosition().x - body.getPosition().x) : -(player.getBody().getPosition().x - body.getPosition().x);
                    playerDistanceY = (player.getBody().getPosition().y - body.getPosition().y) > 0 ? (player.getBody().getPosition().y - body.getPosition().y) : -(player.getBody().getPosition().y - body.getPosition().y);

                    if (playerDistanceX + playerDistanceY < 2) currentState = states.HOSTILE;
                    break;

                default:
                    break;
            }
            position = new Vector2(body.getPosition().x * 32, body.getPosition().y * 32);
        }
    }

    //region $setters&getters

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDmg() {
        return dmg;
    }

    public void setDmg(float dmg) {
        this.dmg = dmg;
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isFocusable() {
        return focusable;
    }

    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    //endregion
}
