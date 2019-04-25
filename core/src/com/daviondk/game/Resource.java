package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Davio on 23.03.2016.
 */
public class Resource extends Actor {
    float collision;
    Texture texture = new Texture(Gdx.files.internal("data/res.png"));

    public Resource(){

    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        Worker worker = Level.worker;
        if(checkWorker(worker)==1){
            worker.work();
        }
    }
    public byte checkWorker(Worker worker){
        float distance = (float)Math.sqrt(
                (getX()+getWidth()/2-worker.getX()-worker.getWidth()/2)*(getX()+getWidth()/2-worker.getX()-worker.getWidth()/2)+
                (getY()+getHeight()/2-worker.getY()-worker.getHeight()/2)*(getY()+getHeight()/2-worker.getY()-worker.getHeight()/2));
        if (distance<collision+worker.collision){
            return 1;
        }else{
            return 0;
        }
    }
}