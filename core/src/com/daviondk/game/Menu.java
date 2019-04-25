package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Davio on 07.03.2016.
 */
public class Menu implements Screen {
    OrthographicCamera camera;
    StartGame game;
    Rectangle startBounds;
    Rectangle ratingBounds;
    Rectangle settingsBounds;
    Rectangle exitBounds;
    Vector3 touchPoint;
    Texture doors;
    TextureRegion leftdoor;
    TextureRegion rightdoor;
    float leftdoorPosx;
    float leftdoorPosy;
    float rightdoorPosx;
    float rightdoorPosy;
    float timeisit = 0;
    float width;
    float wh;
    boolean pressing = false;
    boolean close=false;
    boolean open=true;

    private Animation startAnimation;
    private float dtsum = 0;
    private float deltasum = 0;
    private boolean tex1,tex2,tex3,tex4 = false;

    float aspectRatio = ((float)(Gdx.graphics.getHeight()*1920))/((float)(Gdx.graphics.getWidth()*1080));



    public Menu(StartGame game){
        this.game = game;
        startBounds = new Rectangle(708, 699, 496, 99);
        ratingBounds = new Rectangle(704, 561, 502, 100);
        settingsBounds = new Rectangle(701, 417, 506, 104);
        exitBounds = new Rectangle(698, 287, 510, 83);
        touchPoint = new Vector3();

    }

public void update () {
    if (Gdx.input.justTouched() && (!tex1) && (!tex2) && (!tex3) && (!tex4) && (pressing)) {
        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

        if (startBounds.contains(touchPoint.x, touchPoint.y)) {
            startAnimation = new Animation(new TextureRegion(Assets.startAnimation), 12, 0.5f);
            Assets.playSound(Assets.clickSound);
            tex1 = true;
            timeisit = 0;
            pressing = false;
            close = true;

            return;
        }
    }
}

    public TextureRegion getStart(Animation anim) {
        return anim.getFrame();
    }

    @Override
    public void show() {



        if ((float)(Gdx.graphics.getWidth() / Gdx.graphics.getHeight()) > (float)(16 / 9)) {
            camera = new OrthographicCamera(1920, 1080 * aspectRatio);
            width = 1920;
        }
        else {
            camera = new OrthographicCamera(1920 / aspectRatio, 1080);
            width = 1920/aspectRatio;
        }
        wh = camera.viewportWidth/camera.viewportHeight;
        width=1920;

        if (wh < 1.55) doors = new Texture(Gdx.files.internal("data/doors43.png"));
        else doors = new Texture(Gdx.files.internal("data/doors169.png"));
        leftdoor = new TextureRegion(doors, 0, 0, doors.getWidth()/2, doors.getHeight());
        rightdoor = new TextureRegion(doors, doors.getWidth()/2, 0, doors.getWidth()/2, doors.getHeight());


        camera.position.set(new Vector3(960, 540, 0));
        leftdoorPosx = 960-leftdoor.getRegionWidth();
        leftdoorPosy = 540-leftdoor.getRegionHeight()/2;
        rightdoorPosx = 960;
        rightdoorPosy = 540-leftdoor.getRegionHeight()/2;
    }
    int counta = 0;
    public void doors(float delta, float width,float time, boolean open) {
        deltasum += delta;

        int count = (int) time * 30;
        float a = width / count;
            if (timeisit > 1.5 && counta < count && (open)) {
                if (deltasum >= (1f / 30f)) {
                    leftdoorPosx -= a;
                    rightdoorPosx += a;
                    deltasum = 0;
                    counta++;
                }
            }

        if (counta<count && (!open)) {
            if (deltasum > (1f / 30f)) {
                leftdoorPosx += a;
                rightdoorPosx -= a;
                deltasum = 0;
                counta++;
            }
        }
        game.batch.draw(leftdoor, leftdoorPosx, leftdoorPosy);
        game.batch.draw(rightdoor, rightdoorPosx, leftdoorPosy);
        if (timeisit<time+1.5) pressing=false;
        else {pressing=true; counta=0; this.open=false;leftdoorPosx=960-leftdoor.getRegionWidth()-width; rightdoorPosx = 960+width;}
        timeisit += delta;
    }
    public void buttonAnimation(float delta, Animation butAnim, int x, int y){

            butAnim.update(delta);
            game.batch.draw(getStart(butAnim), x, y);
            dtsum+=delta;
            if (dtsum > butAnim.cycleTime)  {
            }
                if (dtsum > 3f) {
                    tex1 = false; tex2 = false; tex3 = false; tex4 = false;
                    dtsum = 0;
                    game.setScreen(new Level(game));
                }
    }

    @Override
    public void render(float delta) {

        update();


        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Assets.menu, 0, 0);
        if (tex1) buttonAnimation(delta, startAnimation,708, 699);
        if (tex2) buttonAnimation(delta, startAnimation,704, 561);
        if (tex3) buttonAnimation(delta, startAnimation,701, 417);
        if (tex4) buttonAnimation(delta, startAnimation, 698, 287);
        if (close) doors(delta, width, 2f, false);
        if (open)doors(delta, width, 2f, true);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
