package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by Davio on 23.03.2016.
 */
public class Galley extends IIActor {
    Sprite textureregion = new Sprite(new Texture(Gdx.files.internal("data/galleysmall.png")));
    private ParticleEffect effect;
    float dtsumm;
    int hitpoints = 15;
    float summm = 0;


    public Galley(){
        v=Gdx.graphics.getHeight()/5;

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("effects/enginefire.p"), Gdx.files.internal("effects"));
        effect.getEmitters().first().setPosition( getX(), getY() );
        effect.scaleEffect(0.5f);
        effect.start();
        radius = Gdx.graphics.getHeight()/3;
    }
    public void death(){
        if (summm<3)summm++;

        clearMap();
        Level.pactors.remove(this);
        setVisible(false);
        if (summm<2)spawnExplosion();
        clearActions();
        addAction(Actions.removeActor(this));
    }
    public void spawnExplosion(){
        Level.shots.add(new Explosion());
        Level.shots.get(Level.shots.size()-1).setSize(getWidth(), getHeight());
        Level.shots.get(Level.shots.size()-1).setPosition(getX(), getY());
        Level.group.addActor(Level.shots.get(Level.shots.size()-1));

    }

    public void setHp(int dt){
        hitpoints+=dt;
    }
    public void attack(IIActor enemy){
        rot = (float) Math.toDegrees(Math.atan((enemy.getY() - getY()) / (enemy.getX() - getX()))) + (enemy.getX() < (getX()) ? 90 : -90);
        setRotation(rot);
        dtsumm+=Gdx.graphics.getDeltaTime();
        if (dtsumm>=0.1f) {clearActions();
            time = (float) ((Math.sqrt((enemy.getX()+enemy.getWidth()/2 - getX() - getWidth() / 2) * (enemy.getX()+enemy.getWidth()/2 - getX() - getWidth() / 2) + (enemy.getY()+enemy.getHeight()/2 - getY() - getHeight() / 2) * (enemy.getY()+enemy.getHeight()/2 - getY() - getHeight() / 2))) / (Gdx.graphics.getHeight() / 5));
            addAction(Actions.moveTo(enemy.getX()+enemy.getWidth()/2,enemy.getY()+enemy.getHeight()/2, time));
            dtsumm=0;
        }
        if(Math.sqrt((enemy.getX()+enemy.getWidth()/2 - getX() - getWidth() / 2) * (enemy.getX()+enemy.getWidth()/2 - getX() - getWidth() / 2) + (enemy.getY()+enemy.getHeight()/2 - getY() - getHeight() / 2) * (enemy.getY()+enemy.getHeight()/2 - getY() - getHeight() / 2))<Gdx.graphics.getHeight()/9){enemy.setHp(-30); death(); }}

   public Vector2 FindStation(){
        length = 150;
        byte j = 0;
        for (byte i=0; i<EnemyStation.col.length; i++){

            if (((float) Math.sqrt((EnemyStation.col[i].x-getXonMap(getX()))*(EnemyStation.col[i].x-getXonMap(getX()))+(EnemyStation.col[i].y-getYonMap(getY()))*(EnemyStation.col[i].y-getYonMap(getY())))<length) &&
                    (Level.Map[(int)EnemyStation.col[i].x][(int)EnemyStation.col[i].y]!=1)) {
                length = (float) Math.sqrt((EnemyStation.col[i].x - getXonMap(getX())) * (EnemyStation.col[i].x - getXonMap(getX())) + (EnemyStation.col[i].y - getYonMap(getY())) * (EnemyStation.col[i].y - getYonMap(getY())));
                j=i;
            }
        }
        return new Vector2(EnemyStation.col[j]);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        effect.draw(batch);
        batch.draw(textureregion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    @Override
    public void act(float delta) {
        if (hitpoints>0) {
            super.act(delta);
            length = (float) Math.sqrt((FindStation().x - getXonMap(getX())) * (FindStation().x - getXonMap(getX())) + (FindStation().y - getXonMap(getY())) * (FindStation().y - getXonMap(getY())));
            if (!attack) {
                if (getActions().size == 0 && (FindStation().y - getYonMap(getY() + getHeight() / 2) != 0)) {
                    DynamicFill();
                    xy = Search(getXonMap(getX() + getWidth() / 2), getYonMap(getY() + getHeight() / 2), (byte) FindStation().x, (byte) FindStation().y);
                    if (xy != null) {
                        x = getXfromMap(xy.x);
                        y = getYfromMap(xy.y);
                        time = (float) ((Math.sqrt((x - getX() - getWidth() / 2) * (x - getX() - getWidth() / 2) + (y - getY() - getHeight() / 2) * (y - getY() - getHeight() / 2))) / v);
                        addAction(Actions.moveTo(x, y, time));
                        rot = (float) Math.toDegrees(Math.atan((y - getY()) / (x - getX()))) + (x < (getX()) ? 90 : -90);
                        if (rot > 180) rot += 180;
                        addAction(Actions.rotateTo(rot, (Math.abs(rot - getRotation())) / 360));
                    }

                }

            }
            if ((length < 2)) {
                Level.enemyStation.hitpoints -= 30;
                death();
            }
            for (int i = 0; i < effect.getEmitters().size; i++) {
                effect.getEmitters().get(i).getAngle().setLow(getRotation() - 90);
                effect.getEmitters().get(i).setPosition((float) ((getX() + getWidth() / 2)), (float) ((getY() + getHeight() / 2)));
                effect.getEmitters().get(i).getAngle().setHigh(getRotation() - 90);
            }
            effect.update(delta);
            effect.start();
            px=getX()+getWidth()/2;
            py=getY()+getHeight()/2;

            enemySearch(Level.enemies);
        }
    if (hitpoints<=0) {death();}
   }

}
