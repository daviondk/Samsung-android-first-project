package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by Davio on 23.05.2016.
 */
public class Shot extends Actor {
    TextureRegion shot;
    float dt;
    public Shot(int e){
        if (e == 1) {shot = new TextureRegion(new Texture(Gdx.files.internal("data/enemyshot.png")));} else {shot = new TextureRegion(new Texture(Gdx.files.internal("data/playershot.png")));}
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(shot, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta){
        dt+=delta;
        super.act(delta);
        if ((dt>0.1) && (getActions().size==0)){addAction(Actions.removeActor());}
    }

}
