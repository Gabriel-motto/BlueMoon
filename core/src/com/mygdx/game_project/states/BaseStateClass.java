package com.mygdx.game_project.states;

import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Player;

import java.util.ArrayList;

public abstract class BaseStateClass {
    protected enum states {
        SLEEP,
        HOSTILE,
        AVOID;
    }
    protected states currentState;
    public BaseStateClass(states currentState) {
        this.currentState = currentState;
    }

    public abstract void stateBehavior(ArrayList<Enemy> enemies, Player player);
}
