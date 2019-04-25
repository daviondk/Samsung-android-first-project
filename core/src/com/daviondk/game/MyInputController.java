package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by Davio on 09.04.2016.
 */
public class MyInputController implements InputProcessor {

    private Worker worker;
    private int dtx;
    private int dty;
    private int dtys;
    private OrthographicCamera camera;
    private Slider slider;
    private byte move;
    static float left,right;

    public MyInputController(OrthographicCamera camera, Slider slider , Worker worker){
        this.camera=camera;
        this.worker=worker;
        this.slider=slider;
        left = Gdx.graphics.getWidth()-slider.getWidth();
        right = Gdx.graphics.getWidth()-0.25f*slider.getWidth();
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {



        dtx = screenX;
        dty = screenY;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if ((move==-1) && (slider.getActions().size==0)) {slider.addAction(Actions.moveBy(left-slider.getX(),0,(slider.getX()-left)/slider.getWidth()));}
        if ((move==1) && (slider.getActions().size==0)) {slider.addAction(Actions.moveBy(right-slider.getX(),0,(right-slider.getX())/slider.getWidth()));}
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
            dtx -= screenX;
            dtx = -dtx;
            dty -= screenY;
            dty = -dty;
            dtys += dty;
            int a=0;

            if ((slider.getX()>=left) && (screenX>=slider.getX()) && (screenX<=slider.getX()+slider.getWidth()/4) && (slider.getX()<=right) && (slider.getActions().size==0)){
                if ((slider.getX()+dtx>=left) && (slider.getX()+dtx<=right)){ slider.setX(slider.getX()+dtx); if (dtx<0) move = -1; else if (dtx>0) move = 1;}
                if (slider.getX()+dtx<left) slider.setX(left);
                if(slider.getX()>right) slider.setX(right);
            }
        if ((Level.station.hitpoints>0) && (Level.enemyStation.hitpoints>0)) {
            if ((camera.position.y == Gdx.graphics.getHeight() / 2) && (dty > 0)) a = 1;
            else if ((camera.position.y == Gdx.graphics.getHeight() / 2) && (dty < 0)) a = 2;
            else if ((camera.position.y == Gdx.graphics.getHeight() * 2.5f) && (dty < 0)) a = 3;
            else if ((camera.position.y == Gdx.graphics.getHeight() * 2.5f) && (dty > 0)) a = 4;
            else if ((camera.position.y < Gdx.graphics.getHeight() * 2.5f) && (camera.position.y > Gdx.graphics.getHeight() / 2))
                a = 5;
            switch (a) {
                case 1:
                    camera.position.add(0, dty, 0);
                    break;
                case 2:
                    camera.position.add(0, 0, 0);
                    break;
                case 3:
                    camera.position.add(0, dty, 0);
                    break;
                case 4:
                    camera.position.add(0, 0, 0);
                    break;
                case 5:
                    if (camera.position.y + dty >= Gdx.graphics.getHeight() * 2.5f)
                        camera.position.set(camera.position.x, Gdx.graphics.getHeight() * 2.5f, camera.position.z);
                    if (camera.position.y + dty >= Gdx.graphics.getHeight() * 2.5f)
                        camera.position.set(camera.position.x, Gdx.graphics.getHeight() * 2.5f, camera.position.z);
                    else if (camera.position.y + dty <= Gdx.graphics.getHeight() / 2)
                        camera.position.set(camera.position.x, Gdx.graphics.getHeight() / 2, camera.position.z);
                    else camera.position.add(0, dty, 0);
                    break;
            }
            dtx = screenX;
            dty = screenY;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
