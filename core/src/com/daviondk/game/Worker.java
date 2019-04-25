package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Davio on 23.03.2016.
 */
public class Worker extends Actor {
    float collision = Gdx.graphics.getHeight() / 32;
    byte work = 0;
    boolean touched = false;
    int b = 0;
    boolean useworker = false;
    float time;
    float rot;
    float dtsum;

    Texture texture = new Texture(Gdx.files.internal("data/worker.png"));
    TextureRegion textureregion = new TextureRegion(new Texture(Gdx.files.internal("data/worker.png")));

    public Worker() {
        addListener(new InputListener(){
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (work != 1) {
                if (touched) {
                    b = 0;
                    touched = false;
                    textureregion = new TextureRegion(new Texture(Gdx.files.internal("data/worker.png")));
                } else {
                    b = 0;
                    touched = true;
                    textureregion = new TextureRegion(new Texture(Gdx.files.internal("data/workerselected.png")));
                }
            }
            return true;
        }

    });


    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureregion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    }

    public void work(){
        setVisible(false);
        work = 1;
    };

    @Override
    public void act(float delta) {
        super.act(delta);
        dtsum+=delta;
        if (work==1) {clearActions(); if (dtsum>=1f) {Level.resources++; dtsum=0f;}}

    }

}