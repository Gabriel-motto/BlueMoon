package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game_project.entities.*;

public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // Gdx.app.log("CONTACT", "Contact");

        /**
         * Creación de las acciones tras colision de una bala
         */
        if (fixA.getUserData() instanceof Bullets || fixB.getUserData() instanceof Bullets) {
            Fixture bullet = fixA.getUserData() instanceof Bullets ? fixA : fixB;
            Fixture body2 = bullet == fixA ? fixB : fixA;

            // Con un enemigo
            if (body2.getUserData() != null && body2.getUserData() instanceof Enemy) {
                Gdx.app.log("INFO","bullet-enemy");
                ((Enemy) body2.getUserData()).onHit(((Bullets) bullet.getUserData()).getDmg());
                ((Bullets) bullet.getUserData()).onHit(0);
            }
            // Con la pared u objetos
            if (body2.getUserData() == null || body2.getUserData() instanceof TiledCollisions){
                Gdx.app.log("INFO", "object-wall");
                ((Bullets) bullet.getUserData()).onHit(0);
            }
            // Con el jefe final
            if (body2.getUserData() != null && body2.getUserData() instanceof RaidBoss
                    && ((Bullets) bullet.getUserData()).getGroup() != CreateHitbox.category.ENEMY_NO_COLL.bits()) {
                Gdx.app.log("INFO", "Boss hit");
                ((RaidBoss) body2.getUserData()).onHit(((Bullets) bullet.getUserData()).getDmg());
                ((Bullets) bullet.getUserData()).onHit(0);
            }
        }

        /**
         * Creación de las acciones tras colision del jugador
         */
        if (fixA.getUserData() instanceof Player || fixB.getUserData() instanceof Player) {
            Fixture player = fixA.getUserData() instanceof Player ? fixA : fixB;
            Fixture body2 = player == fixA ? fixB : fixA;

            Gdx.app.log("INFO", "userdata: " + body2.getUserData());

            if (body2 != null) {
                // Con un enemigo
                if (body2.getUserData() instanceof Enemy) {
                    Gdx.app.log("CONTACT","Enemy");
                    ((Player) player.getUserData()).onHit(((Enemy) body2.getUserData()).getDmg());
                    ((Enemy) body2.getUserData()).onHit(player.getUserData());
                }
                // Con una puerta
                if (body2.getUserData() instanceof TiledCollisions) {
                    Gdx.app.log("CONTACT", "Door");
                    ((Player) player.getUserData()).onHit("door");
                }
                // Con un objeto
                if (body2.getUserData() instanceof Objects) {
                    Gdx.app.log("CONTACT", "Chest");
                    ((Player) player.getUserData()).onHit(body2.getUserData());
                    ((Objects) body2.getUserData()).onHit(player.getUserData());
                }
                // Con las balas del Jefe final
                if (body2.getUserData() instanceof Bullets && ((Bullets) body2.getUserData()).getGroup() != CreateHitbox.category.PLAYER_NO_COLL.bits()) {
                    ((Player) player.getUserData()).onHit(((Bullets) body2.getUserData()).getDmg());
                    ((Bullets) body2.getUserData()).onHit(0);
                }
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
