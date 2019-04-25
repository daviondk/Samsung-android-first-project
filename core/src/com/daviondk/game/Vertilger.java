package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by Davio on 21.05.2016.
 */
public class Vertilger extends IIActor{
    Sprite textureregion = new Sprite(new Texture(Gdx.files.internal("data/vertilger.png")));
    float deltasum;
    int hitpoints = 15;
    int k,l;
    ParticleEffect effect;
    float len;
    public Vertilger(){
        v=Gdx.graphics.getHeight()/5;
        radius = Gdx.graphics.getHeight()/2.5f;

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("effects/enginefire1.p"), Gdx.files.internal("effects"));
        effect.getEmitters().first().setPosition( getX(), getY() );
        effect.scaleEffect(0.5f);
        effect.start();

    }
    public Vector2 FindStation(){
        length = 150;
        byte j = 0;
        for (byte i=0; i<Station.col.length; i++){
            if (((float) Math.sqrt((Station.col[i].x-getXonMap(getX()))*(Station.col[i].x-getXonMap(getX()))+(Station.col[i].y-getYonMap(getY()))*(Station.col[i].y-getYonMap(getY())))<length) && (Level.Map[(int)Station.col[i].x][(int)Station.col[i].y]!=1)) {
                length = (float) Math.sqrt((Station.col[i].x - getXonMap(getX())) * (Station.col[i].x - getXonMap(getX())) + (Station.col[i].y - getYonMap(getY())) * (Station.col[i].y - getYonMap(getY())));
                j=i;
            }
        }
        return new Vector2(Station.col[j]);
    }
    public void setHp(int dt){
        hitpoints+=dt;
    }
    public void baseAttack(){
        deltasum+=Gdx.graphics.getDeltaTime();
        rot = (float) Math.toDegrees(Math.atan((Level.station.getY()+Level.station.getHeight()/2 - getY()) / (Level.station.getX()+Level.station.getWidth()/2 - getX()))) + (Level.station.getX()+Level.station.getWidth()/2 < (getX()) ? 90 : -90);
        setRotation(rot);
        if (deltasum>=1/4f) {Level.station.setHp(-2);
            spawnBullets(Level.station.getX()+Level.station.getWidth()/2,Level.station.getY()+Level.station.getHeight()/2,Level.station.getWidth(),Level.station.getHeight());
            deltasum=0;
        }
    }
    public void attack(IIActor enemy){
        len = (float) Math.sqrt((enemy.getX() + enemy.getWidth()/2 - getX() - getWidth() / 2) * (enemy.getX() + enemy.getWidth()/2 - getX() - getWidth() / 2) + (enemy.getY() + enemy.getHeight()/2 - getY() - getHeight() / 2) * (enemy.getY() + enemy.getHeight()/2 - getY() - getHeight() / 2));
        deltasum+=Gdx.graphics.getDeltaTime();
        rot = (float) Math.toDegrees(Math.atan((enemy.getY() - getY()) / (enemy.getX() - getX()))) + (enemy.getX() < (getX()) ? 90 : -90);
        setRotation(rot);
        if (deltasum>=1/4f) {enemy.setHp(-2);
            spawnBullets(enemy.getX(),enemy.getY(),enemy.getWidth(),enemy.getHeight());
            deltasum=0;
        }
    }
    public void death(){
        clearMap();
        Level.enemies.remove(this);
        setVisible(false);
        spawnExplosion();
        addAction(Actions.removeActor());
    }
    public void spawnExplosion(){
        Level.shots.add(new Explosion());
        Level.shots.get(Level.shots.size()-1).setSize(getWidth(), getHeight());
        Level.shots.get(Level.shots.size()-1).setPosition(getX(), getY());
        Level.group.addActor(Level.shots.get(Level.shots.size()-1));

    }
    public void spawnBullets(float targetx, float targety,float targetwidth, float targetheight){
        Level.shots.add(new Shot(1));
        k=1;
        Level.shots.get(Level.shots.size()-1).setSize(getHeight(), getWidth());
        Level.shots.get(Level.shots.size()-1).setPosition(getX(), getY());
        Level.shots.get(Level.shots.size()-1).setOriginX(getWidth()/2);
        Level.shots.get(Level.shots.size()-1).setOriginY(getHeight()/2);
        Level.shots.get(Level.shots.size()-1).setRotation(getRotation());
        Level.groupforshots.addActor(Level.shots.get(Level.shots.size()-1));
        float timeforshot = (float) (Math.sqrt((targetx + targetwidth/2 - getX() + getWidth() / 2) * (targetx + targetwidth/2 - getX() + getWidth() / 2) + (targety + targetheight/2 - getY() + getHeight() / 2) * (targety + targetheight/2 - getY() + getHeight() / 2))/(Gdx.graphics.getHeight()*3));
        if (k<2) Level.shots.get(Level.shots.size()-1).addAction(Actions.moveTo(targetx,targety,timeforshot));

    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        effect.draw(batch);
        batch.draw(textureregion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    public void act(float delta) {
        super.act(delta);
        for (int j = k; j<l; j++){
            Level.shots.get(j).setRotation(getRotation());
        }
        if (!attack) {
            if (getActions().size == 0 && ((float) Math.sqrt((Level.station.getX()+Level.station.getWidth()/2 - getX()-getWidth()/2) * (Level.station.getX()+Level.station.getWidth()/2 - getX()-getWidth()/2) + (Level.station.getY()+Level.station.getHeight()/2-getY()-getHeight()/2)*(Level.station.getY()+Level.station.getHeight()/2-getY()-getHeight()/2)))>Gdx.graphics.getHeight()/2) {
                DynamicFill();
                xy = Search(getXonMap(getX() + getWidth() / 2), getYonMap(getY() + getHeight() / 2), (byte) FindStation().x, (byte) FindStation().y);
                if (xy != null) {
                    x = getXfromMap(xy.x);
                    y = getYfromMap(xy.y);
                    time = (float) ((Math.sqrt((x - getX() - getWidth() / 2) * (x - getX() - getWidth() / 2) + (y - getY() - getHeight() / 2) * (y - getY() - getHeight() / 2))) / v);
                    addAction(Actions.moveTo(x, y, time));
                    rot = (float) Math.toDegrees(Math.atan((y - getY()) / (x - getX()))) + (x < (getX()) ? -90 : 90)+180;
                    addAction(Actions.rotateTo(rot, (Math.abs(rot - getRotation())) / 360));
                }

            }
            else if ((Math.sqrt((Level.station.getX()+Level.station.getWidth()/2 - getX() - getWidth() / 2) * (Level.station.getX()+Level.station.getWidth()/2 - getX() - getWidth() / 2) + (Level.station.getY()+Level.station.getHeight()/2 - getY() - getHeight() / 2) * (Level.station.getY()+Level.station.getHeight()/2 - getY() - getHeight() / 2)))<=Gdx.graphics.getHeight()/2) baseAttack();
        }


        for (int i = 0; i < effect.getEmitters().size; i++) { //get the list of emitters - things that emit particles
            effect.getEmitters().get(i).getAngle().setLow(getRotation() + 270); //low is the minimum rotation
            effect.getEmitters().get(i).setPosition((float) ((getX() + getWidth() / 2)), (float) ((getY()+getHeight()/2)));
            effect.getEmitters().get(i).getAngle().setHigh(getRotation() + 270); //high is the max rotation
        }
        effect.update(delta);
        effect.start();

            if ((getActions().size == 0) && (length < 2)) {
                clearMap();
                death();
            }
        enemySearch(Level.pactors);
        if(hitpoints<=0) death();
        if (attack=false) deltasum=1/3f;
    }
}
