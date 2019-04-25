package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Davio on 23.03.2016.
 */
public class Station extends Actor{
    Texture texture = new Texture(Gdx.files.internal("data/basestation.png"));
    public static float xpos;
    static Vector2[] col;
    public static int hitpoints = 500;

    public Station(){
        xpos = Gdx.graphics.getWidth()/2-getWidth()/2;
        byte x1 = (byte) (getXonMap(xpos));
        byte y1 = (byte) (getYonMap(0));
        byte x2 = (byte) (getXonMap(Gdx.graphics.getWidth()/2+Gdx.graphics.getHeight() * 0.38f/2f));
        byte y2 = (byte) (getYonMap(Gdx.graphics.getHeight() * 0.38f/2f));
        byte quan = (byte) ((y2-y1)*2+(x2-x1)-1);
        col = new Vector2[quan];

        byte n=0;
        for (int j = x1+1; j<x2;j++){
            col[n] = new Vector2(j,y2);
            n++;
        }
        for (int j = y1; j<y2;j++){
            col[n] = new Vector2(x2,j);
            n++;
        }
        for (int j = y1; j<y2;j++){
            col[n] = new Vector2(x1,j);
            n++;
        }
    }
    public byte getXonMap(float X){return (byte)((int)X/((int)(Gdx.graphics.getWidth()/Level.x)));}
    public byte getYonMap(float Y) {return (byte)((int)Y/((int)((Gdx.graphics.getHeight()*3)/Level.y)));}
    public void setHp(int a){hitpoints+=a;}

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta){

    }
}
