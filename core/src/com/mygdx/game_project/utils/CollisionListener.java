package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Bullets;
import com.mygdx.game_project.entities.Player;

public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // Gdx.app.log("CONTACT", "Contact");

        if (fixA.getUserData() instanceof Bullets || fixB.getUserData() instanceof Bullets) {
            Fixture bullet = fixA.getUserData() instanceof Bullets ? fixA : fixB;
            Fixture body2 = bullet == fixA ? fixB : fixA;

            if (body2.getUserData() != null && body2.getUserData() instanceof Enemy) {
                Gdx.app.log("INFO","bullet-enemy");
                ((CreateHitbox) body2.getUserData()).onHit(((CreateHitbox) bullet.getUserData()).dmg);
                ((CreateHitbox) bullet.getUserData()).onHit(0);
            }
            if (body2.getUserData() == null){
                Gdx.app.log("INFO", "object-wall");
                ((CreateHitbox) bullet.getUserData()).onHit(0);
            }
        }

        if (fixA.getUserData() instanceof Player || fixB.getUserData() instanceof Player) {
            Fixture player = fixA.getUserData() instanceof Player ? fixA : fixB;
            Fixture body2 = player == fixA ? fixB : fixA;

            Gdx.app.log("INFO", "userdata: " + body2.getUserData());

            if (body2 != null && body2.getUserData() instanceof Enemy) {
                Gdx.app.log("CONTACT","Enemy");
                ((CreateHitbox) player.getUserData()).onHit(((CreateHitbox) body2.getUserData()).dmg);
            }
            if (body2 != null && body2.getUserData() instanceof TiledCollisions) {
                Gdx.app.log("CONTACT", "Door");
                ((CreateHitbox) player.getUserData()).onHit(0);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
