package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Davio on 27.04.2016.
 */
public class Space extends Actor {
    Texture space = new Texture(Gdx.files.internal("data/space.png"));

    public Space(){
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(space, (Gdx.graphics.getHeight()*16/9-Gdx.graphics.getWidth())/(-2), 0, Gdx.graphics.getHeight()*16/9, Gdx.graphics.getHeight()*3);
    }

    @Override
    public void act(float delta){

    }
}
