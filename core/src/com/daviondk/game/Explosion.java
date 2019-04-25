package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by Davio on 24.05.2016.
 */
public class Explosion extends Actor {
    ParticleEffect effect;
    float dt;
    public Explosion(){

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("effects/explosion.p"), Gdx.files.internal("effects"));
        effect.getEmitters().first().setPosition( getX(), getY() );
        effect.scaleEffect(0.5f);
        effect.start();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        effect.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (int i = 0; i < effect.getEmitters().size; i++) {
            effect.getEmitters().get(i).setPosition((float) ((getX() + getWidth() / 2)), (float) ((getY() + getHeight() / 2)));
        }
        effect.update(delta);
        effect.start();
        dt+=delta;
        if (dt>1.5) {setVisible(false); addAction(Actions.removeActor());}
    }
}
