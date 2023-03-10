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

import static com.mygdx.game_project.constants.Constant.PPU;

public class Objects extends CreateHitbox {
    // region &Vars

    private Vector2 position;
    private float width, height;
    private Body body;
    public enum Rareness {
        COMMON,
        RARE,
        EXOTIC;
    }
    private Rareness rareness;
    private TextureAtlas chestTextureAtlas = new TextureAtlas("Chests/chests.atlas");
    private Animation<TextureAtlas.AtlasRegion> chestOpen;
    private TextureRegion actualFrame;
    private float time;
    private boolean openned = false;
    private boolean alive = true;

    // endregion
    public Objects(World world, Vector2 position, float width, float height) {
        super(world, position, width, height, 0, false, true, false, category.ENEMY_NO_COLL.bits(), 0, false);
        fixture.setUserData(this);
        this.position = position;
        this.width = width;
        this.height = height;
        this.body = super.body;

        int rand = (int) (Math.random() * 100);
        if (rand >= 0 && rand <= 60) rareness = Rareness.COMMON;
        if (rand > 60 && rand <= 95) rareness = Rareness.RARE;
        if (rand > 95) rareness = Rareness.EXOTIC;
    }

    /**
     * Dibuja el objeto
     * @param batch
     */
    public void draw(Batch batch) {
        time += Gdx.graphics.getDeltaTime();
        if (!isOpenned()) {
            batch.draw(chestTextureAtlas.findRegion("chests(1)"),position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*PPU, height*PPU);
        } else {
            batch.draw(chestTextureAtlas.findRegion("chests(2)"), position.x*PPU - width*PPU/2, position.y*PPU - height*PPU/2, width*PPU, height*PPU);
            setAlive(false);
        }
    }

    @Override
    public void onHit(Object object) {
        Gdx.app.log("CHEST", "Cat: " + rareness);
        setOpenned(true);
    }

    // region &Setters/Getters

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

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Rareness getRareness() {
        return rareness;
    }

    public void setRareness(Rareness rareness) {
        this.rareness = rareness;
    }

    public boolean isOpenned() {
        return openned;
    }

    public void setOpenned(boolean openned) {
        this.openned = openned;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    // endregion
}
