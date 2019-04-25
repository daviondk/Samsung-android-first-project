package com.daviondk.game;

/**
 * Created by Davio on 21.05.2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by Davio on 23.03.2016.
 */
public class IIActor extends Actor {
    Sprite textureregion = new Sprite(new Texture(Gdx.files.internal("data/galleysmall.png")));
    Array<Action> nullactions;
    byte[][] MapM;
    byte[] MovesX;
    byte[] MovesY;
    byte[][] Map;
    Parameters p;
    byte[][] DynamicMap;
    byte x1,x2,y1,y2;
    float v=1;
    float length;
    boolean attack;
    float radius = 1;
    int f=0, t = 0, a = 0;
    byte crossas;
    Vector2 crossdot;
    public static int hp;
    public static float px, py;


    public IIActor(){
        nullactions = new Array<Action>();
        DynamicMap = new byte[Level.x][Level.y];
        MapM = new byte[Level.x][Level.y];
        MovesX = new byte[Level.x*Level.y];
        MovesY = new byte[Level.x*Level.y];
        Map = new byte[Level.x][Level.y];
    }
    public void DynamicFill() {
        x1 = getXonMap(getX());
        y1 = getYonMap(getY());
        x2 = getXonMap(getX()+getWidth());
        y2 = getYonMap(getY()+getHeight());

        for (byte j = 0; j < Level.y; j++) {
            for (byte k = 0; k < Level.x; k++) {
                if (DynamicMap[k][j] == 1){
                    Level.Map[k][j]=0;
                    DynamicMap[k][j]=0;}
            }
        }
        for (byte j = y1; j <= y2; j++) {
            for (byte k = x1; k <= x2; k++) {
                if ((k<Level.x) && (j<Level.y)) if (Level.Map[k][j] != 1){
                    Level.Map[k][j]=1;
                    DynamicMap[k][j]=1;}

            }
        }
    }
    public Vector2 FindStation(){
        return new Vector2();
    }
    public void Next(Parameters p, byte[][] MapM){



        if ((p.x < Level.x-1) && ((MapM[p.x][p.y] - MapM[p.x + 1][p.y]) == 1)) {p.x+=1;  return;}

        if ((p.x > 0) && ((MapM[p.x][p.y] - MapM[p.x - 1][p.y]) == 1)) {p.x-=1;  return;}

        if ((p.y < Level.y-1) && ((MapM[p.x][p.y] - MapM[p.x][p.y + 1]) == 1)) {p.y+=1;  return;}

        if ((p.y > 0) && ((MapM[p.x][p.y] - MapM[p.x][p.y - 1]) == 1)) {p.y-=1;  return;}

        //if ((p.x < Level.x-1) && ((MapM[p.x][p.y] - MapM[p.x + 1][p.y + 1]) == 1) && (p.y < Level.y-1)) {p.x+=1; p.y+=1; return;}

        //if ((p.x < Level.x-1) && ((MapM[p.x][p.y] - MapM[p.x + 1][p.y-1]) == 1) && (p.y > 0)) {p.x+=1; p.y-=1;  return;}

        //if ((p.x > 0) && ((MapM[p.x][p.y] - MapM[p.x - 1][p.y+1]) == 1) && (p.y < Level.y-1)) {p.x-=1; p.y+=1;  return;}

        //if ((p.x > 0) && ((MapM[p.x][p.y] - MapM[p.x - 1][p.y-1]) == 1) && (p.y > 0)) {p.x-=1; p.y-=1; return;}






    }
    public void death(){};
    public void attack(IIActor enemy){};
    public Vector2 Search(byte XS, byte YS, byte XE, byte YE){


        for (byte j=0; j<Level.x; j++){
            {
                for (byte k=0;k<Level.y; k++){
                    Map[j][k]=Level.Map[j][k];
                    MapM[j][k]=0;
                }
            }
        }
        for (byte j=0; j<Level.x; j++){
            {
                for (byte k=0;k<Level.y; k++){
                    if (DynamicMap[j][k]==1) Map[j][k]=0;
                }
            }
        }

        for(int j=0; j<Level.x*Level.y; j++) {
            MovesX[j]=0;
            MovesY[j]=0;
        }
        MapM[XS][YS] = 1;
        byte i = 1;
        byte moves;
        p = new Parameters();
        do {
            i+=1;
            for (p.y=0; p.y<Level.y; p.y++){
                for (p.x=0; p.x<Level.x; p.x++){
                    if (MapM[p.x][p.y]==i-1) {

                        if ((p.y<(Level.y-1)) && (MapM[p.x][p.y+1]==0) && (Map[p.x][p.y+1]==0)) MapM[p.x][p.y+1]=i;
                        if ((p.y>0) && (MapM[p.x][p.y-1]==0) && (Map[p.x][p.y-1]==0)) MapM[p.x][p.y-1]=i;
                        if ((p.x<(Level.x-1)) && (MapM[p.x+1][p.y]==0) && (Map[p.x+1][p.y]==0)) MapM[p.x+1][p.y]=i;
                        if ((p.x>0) && (MapM[p.x-1][p.y]==0) && (Map[p.x-1][p.y]==0)) MapM[p.x-1][p.y]=i;
                        if (i>=Level.x*Level.y) { return null;}

                    }
                }
            }

        }while(!(MapM[XE][YE]>0));
        moves = (byte) (i-1);
        p.x = XE;
        p.y = YE;
        i = moves;
        Map[XE][YE] = 4;
        do {
            MovesX[i]=p.x;
            MovesY[i]=p.y;
            Next(p, MapM);
            Map[p.x][p.y]=3;
            i-=1;
        }while (!((p.x==XS) && (p.y==YS)));

        Map[XS][YS] = 2;
        if (MovesY[1]-YS==1 && MovesX[2]==MovesX[1]) return new Vector2(MovesX[2], MovesY[1]);
        else return new Vector2(MovesX[1],MovesY[1]);
    }
    class Parameters
    {
        public byte x=0;
        public byte y=0;
    }
    public static Vector2 Cross(float x1, float y1, float x2, float y2, float x0, float y0, float r){
        Vector2 deltaxy = new Vector2();
        float k = (y1 - y2) / (x1 - x2);
        float b = y2 - k * x2;
        float a1 = 1+k*k;
        float b1 = -2*x0 + 2*k*b - 2*k*y0;
        float c1 = b*b + x0*x0 - 2*b*y0 + y0*y0 - r*r;
        float D=b1*b1-4*a1*c1;
        if (D<0) {
            return null;
        } else if(D==0){
            float x11 = (-b1)/(2*a1);
            float y11 = k * x11 + b;
            deltaxy=new Vector2(x11,y11);
        } else if(D>0){
            float x11 = ((-b1)+(float)Math.sqrt(D))/(2*a1);
            float y11 = k * x11 + b;
            deltaxy=new Vector2(x11,y11);
        }
        return deltaxy;
    }

    public void enemySearch(ArrayList<IIActor> enemies){
        for (byte i = 0; i<enemies.size(); i++) {
            for(byte j = 0; j<Level.Asteroids.size; j++){
                crossdot = Cross(enemies.get(i).getX()+enemies.get(i).getWidth()/2, enemies.get(i).getY()+enemies.get(i).getHeight()/2, getX()+getWidth()/2, getY()+getHeight()/2, Level.Asteroids.get(j).getX()+getWidth()/2,Level.Asteroids.get(j).getY()+getHeight()/2, Level.Asteroids.get(j).getWidth());
                if ((crossdot!=null) && (
                        ((getX()+getWidth()/2<=crossdot.x) &&
                                (crossdot.x<=(enemies.get(i).getX()+enemies.get(i).getWidth()/2)) && (getY()+getHeight()/2<=crossdot.y) && (crossdot.y<=(enemies.get(i).getY()+enemies.get(i).getHeight()/2)))
                                || ((getX()+getWidth()/2>=crossdot.x) && (crossdot.x>=(enemies.get(i).getX()+enemies.get(i).getWidth()/2)) && (getY()+getHeight()/2>=crossdot.y) && (crossdot.y>=(enemies.get(i).getY()+enemies.get(i).getHeight()/2)))
                                || ((getX()+getWidth()/2>=crossdot.x) && (crossdot.x>=(enemies.get(i).getX()+enemies.get(i).getWidth()/2)) && (getY()+getHeight()/2<=crossdot.y) && (crossdot.y<=(enemies.get(i).getY()+enemies.get(i).getHeight()/2)))
                                || ((getX()+getWidth()/2<=crossdot.x) && (crossdot.x<=(enemies.get(i).getX()+enemies.get(i).getWidth()/2)) && (getY()+getHeight()/2>=crossdot.y) && (crossdot.y>=(enemies.get(i).getY()+enemies.get(i).getHeight()/2))))){crossas=(byte) (i+1);}
            }
            if ((Math.sqrt((enemies.get(i).getX()-getX())*(enemies.get(i).getX()-getX())+(enemies.get(i).getY()-getY())*(enemies.get(i).getY()-getY()))<radius) && crossas==0) {
                attack = true;
                if (a!=crossas-1) attack(enemies.get(a));
                f=0;
                t++;
                if (t<1){a=i;}}
            else {f++;}
            if (f==enemies.size())
                attack=false;
            crossas=0;
        }
        if (enemies.size()==0) attack=false;

    }
    public void setHp(int dt){
        hp+=dt;
    }
    public void clearMap(){
        for (byte j = 0; j < Level.y; j++) {
            for (byte k = 0; k < Level.x; k++) {
                if (DynamicMap[k][j] == 1) {
                    Level.Map[k][j] = 0;
                    DynamicMap[k][j] = 0;
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(textureregion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    float rot;
    float time;
    float x,y;
    Vector2 xy;
    public byte getXonMap(float X){return (byte)((int)X/((int)(Gdx.graphics.getWidth()/Level.x)));}
    public byte getYonMap(float Y) {return (byte)((int)Y/((int)((Gdx.graphics.getHeight()*3)/Level.y)));}
    public float getXfromMap(float X){return (X-0.5f)*((Gdx.graphics.getWidth()/Level.x));}
    public float getYfromMap(float Y){return (Y-0.5f)*((Gdx.graphics.getHeight()*3/Level.y));}
    @Override
    public void act(float delta) {
        super.act(delta);
    }
}