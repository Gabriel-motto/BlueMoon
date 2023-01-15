package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Objects;

public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // Gdx.app.log("CONTACT", "Contact");

        if (Objects.class.isAssignableFrom(fixA.getUserData().getClass()) || Objects.class.isAssignableFrom(fixB.getUserData().getClass())) {
            Fixture bullet = Objects.class.isAssignableFrom(fixA.getUserData().getClass()) ? fixA : fixB;
            Fixture enemy = bullet == fixA ? fixB : fixA;

            if (enemy.getUserData() != null && CreateHitbox.class.isAssignableFrom(enemy.getUserData().getClass())) {
                Gdx.app.log("INFO","bullet-enemy");
                ((CreateHitbox) enemy.getUserData()).onHit();
                //((CreateHitbox) bullet.getUserData()).onHit();
            }
            else {
                Gdx.app.log("INFO", "object-wall");
                //((CreateHitbox) bullet.getUserData()).onHit();
            }
        }

//        if (fixA.getUserData() == "player" || fixB.getUserData() == "player") {
//            if (fixA.getUserData() == "enemy" || fixB.getUserData() == "enemy") Gdx.app.log("CONTACT","Enemy");
//            if (fixA.getUserData() == "object" || fixB.getUserData() == "object") Gdx.app.log("CONTACT","Object");
//        }
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
