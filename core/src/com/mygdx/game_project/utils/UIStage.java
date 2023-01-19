package com.mygdx.game_project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class UIStage extends Stage {
    private boolean visible = true;
    private Controller controller;
    public UIStage() {
        controller = new Controller(5);

        addActor(controller);
    }

    @Override
    public void draw() {
        act(Gdx.graphics.getDeltaTime());
        if (isVisible()) {
            super.draw();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
