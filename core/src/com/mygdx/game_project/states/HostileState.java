package com.mygdx.game_project.states;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Player;

import java.util.ArrayList;

public class HostileState extends BaseStateClass{
    public HostileState(states currentState) {
        super(currentState);
    }
    @Override
    public void stateBehavior(ArrayList<Enemy> enemies, Player player) {
        if (!enemies.isEmpty()) {
            for (Enemy enemy : enemies) {
                if (currentState == states.HOSTILE) {
                    if (enemy.isAlive()) {
                        Vector2 enemyDir = new Vector2((player.getBody().getPosition().x * 32 - enemy.getBody().getPosition().x * 32), (player.getBody().getPosition().y * 32 - enemy.getBody().getPosition().y * 32)).nor();

                        enemy.getBody().setLinearVelocity(new Vector2(enemy.getSpeed() * enemyDir.x, enemy.getSpeed() * enemyDir.y));
                    }
                }
            }
        }
    }
}
