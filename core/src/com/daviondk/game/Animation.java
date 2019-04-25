package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Davio on 11.03.2016.
 */
public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    public float cycleTime;
    private int frameCount;
    private int frame;


    public Animation(TextureRegion region, int frameCount, float cycleTime){
        if (Gdx.graphics.getHeight()>Gdx.graphics.getWidth()) {
            frames = new Array<TextureRegion>();
            int frameWidth = region.getRegionWidth() / frameCount;
            for (int i = 0; i < frameCount; i++) {
                frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
            }
            this.frameCount = frameCount;
            this.cycleTime = cycleTime;
            maxFrameTime = cycleTime / frameCount;
            frame = 0;
        }
        else {
            frames = new Array<TextureRegion>();
            int frameHeight = region.getRegionHeight() / frameCount;
            for (int i = 0; i < frameCount; i++) {
                frames.add(new TextureRegion(region, 0, i * frameHeight, region.getRegionWidth(), frameHeight));
            }
            this.frameCount = frameCount;
            this.cycleTime = cycleTime;
            maxFrameTime = cycleTime / frameCount;
            frame = 0;

        }
    }
    public void update(float dt){
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount)
            frame = 0;
    }
    public  TextureRegion getFrame(){
        return frames.get(frame);
    }
}