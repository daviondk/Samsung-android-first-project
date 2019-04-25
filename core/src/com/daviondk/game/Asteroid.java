package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Davio on 09.04.2016.
 */
public class Asteroid  extends Actor{
        Texture texture = new Texture(Gdx.files.internal("data/asteroids.png"));
        Array<TextureRegion> textureRegions = new Array<TextureRegion>();
        TextureRegion texReg = new TextureRegion(texture);
        public Asteroid(){
                for(int i = 0; i < 4; i++){
                        for(int j = 0; j < 4; j++){
                                if ((i == 3) && (j>=1)) break;
                                textureRegions.add(new TextureRegion(texture, j * 270 + 1, i * 270 + 1, 270, 270));
                        }
                }
                texReg = textureRegions.get((int)(Rand(0,12)));
                sprite = new Sprite(texReg);
                sprite.setOriginCenter();


        }



        public static double Rand(int from,int to){
                return Math.floor((Math.random()*(to-from+1))+from);
        }

        @Override
        public void draw(Batch batch, float parentAlpha){
                batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }

        @Override
        public void act(float delta){
                super.act(delta);

        }
        Sprite sprite;


}
