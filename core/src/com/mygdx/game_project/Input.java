package com.mygdx.game_project;

import com.badlogic.gdx.Gdx;

public class Input {

    private int horizontalForce = 0;
    private int verticalForce = 0;

    public Input(float delta) {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.J)) {
            horizontalForce--;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.L)) {
            horizontalForce++;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.I)) {
            verticalForce++;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.K)) {
            verticalForce--;
        }
    }

    public int getHorizontalForce() {
        return horizontalForce;
    }

    public void setHorizontalForce(int horizontalForce) {
        this.horizontalForce = horizontalForce;
    }

    public int getVerticalForce() {
        return verticalForce;
    }

    public void setVerticalForce(int verticalForce) {
        this.verticalForce = verticalForce;
    }
}
