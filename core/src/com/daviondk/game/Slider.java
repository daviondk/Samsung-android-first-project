package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Davio on 18.05.2016.
 */
public class Slider extends Actor {

    Texture texture = new Texture(Gdx.files.internal("data/slider.png"));
    private BitmapFont res;
    private BitmapFont shp;
    private BitmapFont ehp;

    public Slider(){
        addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (x>=135f/480f*getWidth() && x<=270f/480f*getWidth() && y>=906f/1080f*getHeight() && y<=1041/1080f*getHeight()) { if ((Level.resources>=4) && (Level.Map[getXonMap(Station.xpos-Gdx.graphics.getHeight()/18)][getYonMap(Gdx.graphics.getHeight()/18)]==0)) {Level.resources-=4; GalleySpawn();}}
                return true;
            }

        });
        res = new BitmapFont();
        res.setColor(Color.WHITE);
        res.getData().setScale(2f);
        shp = new BitmapFont();
        shp.setColor(Color.GREEN);
        shp.getData().setScale(2f);
        ehp = new BitmapFont();
        ehp.setColor(Color.RED);
        ehp.getData().setScale(2f);
    }
    public void GalleySpawn(){
        Level.pactors.add(new Galley());
        Level.pactors.get(Level.pactors.size()-1).setSize(Gdx.graphics.getHeight() / 9, Gdx.graphics.getHeight() / 9);
        Level.pactors.get(Level.pactors.size()-1).setPosition(Station.xpos-Level.pactors.get(Level.pactors.size()-1).getWidth()/2, Level.pactors.get(Level.pactors.size()-1).getHeight()/2);
        Level.pactors.get(Level.pactors.size()-1).setOriginX(Level.pactors.get(Level.pactors.size()-1).getWidth()/2);
        Level.pactors.get(Level.pactors.size()-1).setOriginY(Level.pactors.get(Level.pactors.size()-1).getHeight()/2);
        Level.group.addActor(Level.pactors.get(Level.pactors.size()-1));

    }
    public byte getXonMap(float X){return (byte)((int)X/((int)(Gdx.graphics.getWidth()/Level.x)));}
    public byte getYonMap(float Y) {return (byte)((int)Y/((int)((Gdx.graphics.getHeight()*3)/Level.y)));}




    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        res.draw(batch, String.valueOf(Level.resources), getX()+getWidth()/20f, getY()+getHeight()*0.985f);
        res.draw(batch, String.valueOf(1), getX()+getWidth()/20f, getY()+getHeight()*0.05f);
        shp.draw(batch, String.valueOf(Level.station.hitpoints), getX()+getWidth()/20f, getY()+getHeight()*0.19f);
        ehp.draw(batch, String.valueOf(Level.enemyStation.hitpoints), getX()+getWidth()/20f, getY()+getHeight()*0.81f);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
