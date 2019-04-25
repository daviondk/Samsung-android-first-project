package com.daviondk.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

/**
 * Created by Davio on 11.03.2016.
 */
public class Level implements Screen {

    Game game;
    Texture end = new Texture(Gdx.files.internal("data/end.png"));
    static Stage stage;
    Vector3 touchPoint;
    OrthographicCamera camera;
    static Station station;
    static EnemyStation enemyStation;
    Space space;
    static ArrayList<IIActor> pactors;
    static ArrayList<IIActor> enemies;
    static ArrayList<Actor> shots;
    static Worker worker;
    Resource resource;
    public static Array<Asteroid> Asteroids = new Array<Asteroid>();
    public static Array<Vector2> AsPoses = new Array<Vector2>();
    BitmapFont End;
    SpriteBatch batch;
    Slider slider;
    static int resources=4;
    float dtsum;
    static Group group;
    static Group groupforshots;




    float aspectRatio = ((float)(Gdx.graphics.getHeight()*1920))/((float)(Gdx.graphics.getWidth()*1080));



    static final int x = Math.round(18 * Gdx.graphics.getWidth()/Gdx.graphics.getHeight());
    static final int y = 54;
    public static byte[][] Map = new byte[x][y];


    public Level(StartGame game){
        this.game = game;
        touchPoint = new Vector3();
    }

    public void update () {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        }
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        shots = new ArrayList<Actor>();
        

        if ((float)(Gdx.graphics.getWidth() / Gdx.graphics.getHeight()) > (float)(16 / 9))
            camera = new OrthographicCamera(1920, 1080 * aspectRatio);
        else camera = new OrthographicCamera(1920 / aspectRatio, 1080);
        camera.position.set(new Vector3(960, 540, 0));
        End = new BitmapFont();
        End.setColor(Color.WHITE);




        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera));
        Gdx.input.setInputProcessor(stage);

        space = new Space();
        stage.addActor(space);

        groupforshots = new Group();
        stage.addActor(groupforshots);



        group = new Group();
        stage.addActor(group);





        stage.addListener(new InputListener(){

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (x < slider.getX()) {
                    if (worker.work == 1) worker.touched = false;
                    if (worker.touched) worker.b += 1;
                    else worker.b = 0;
                    if (worker.b >= 2 && worker.touched) {
                        worker.time = (float) ((Math.sqrt((x - worker.getX() - worker.getWidth() / 2) * (x - worker.getX() - worker.getWidth() / 2) + (y - worker.getY() - worker.getHeight() / 2) * (y - worker.getY() - worker.getHeight() / 2))) / (Gdx.graphics.getHeight() / 7));
                        if (x < slider.getX()) worker.clearActions();
                    }
                    if (worker.touched && worker.b >= 2 /*&& ((x != 0) && (y != 0))*/) {
                        worker.useworker = true;
                        worker.rot = (float) Math.toDegrees(Math.atan((y - worker.getHeight() / 2 - worker.getY()) / (x - worker.getWidth() / 2 - worker.getX()))) + (x < (worker.getX() + worker.getWidth() / 2) ? 90 : -90);
                        worker.setRotation(worker.rot);
                    }
                    if (((int) (x - 0.5 * worker.getWidth()) == (int) (worker.getX()) && (int) (y - 0.5 * worker.getHeight()) == (int) (worker.getY())) || !worker.touched)
                        worker.useworker = false;
                    if (worker.useworker)
                        worker.addAction(Actions.moveTo((float) (x - 0.5 * worker.getWidth()), (float) (y - 0.5 * worker.getHeight()), worker.time));
                }
                return true;
            }
        });







        enemyStation = new EnemyStation();
        enemyStation.setPosition(Gdx.graphics.getWidth()/2-(Gdx.graphics.getHeight()/2*EnemyStation.aspectRatio)/2, Gdx.graphics.getHeight()*3-Gdx.graphics.getHeight()/2);
        enemyStation.setSize((Gdx.graphics.getHeight()/2)*EnemyStation.aspectRatio, Gdx.graphics.getHeight()/2);

        station = new Station();
        station.setSize(Gdx.graphics.getHeight() * 0.38f, Gdx.graphics.getHeight() * 0.38f);
        station.setPosition(Gdx.graphics.getWidth()/2-station.getWidth()/2, 0);

        worker =  new Worker();
        worker.setSize(Gdx.graphics.getHeight() / 16, Gdx.graphics.getHeight() / 16);
        worker.setOriginX(worker.getWidth()/2);
        worker.setOriginY(worker.getHeight()/2);
        worker.setPosition(Gdx.graphics.getWidth()/2-station.getWidth(),Gdx.graphics.getHeight()/4);
        group.addActor(worker);

        pactors = new ArrayList<IIActor>();
        pactors.add(new Galley());
        pactors.get(0).setSize(Gdx.graphics.getHeight() / 9, Gdx.graphics.getHeight() / 9);
        pactors.get(0).setPosition(Station.xpos- pactors.get(0).getWidth()/2, pactors.get(0).getHeight()/2);
        pactors.get(0).setOriginX(pactors.get(0).getWidth()/2);
        pactors.get(0).setOriginY(pactors.get(0).getHeight()/2);
        group.addActor(pactors.get(0));
        enemies = new ArrayList<IIActor>();



        stage.addActor(station);
        stage.addActor(enemyStation);



        resource = new Resource();
        float sizez = (float)Rand(50,70)/10;
        resource.setSize(Gdx.graphics.getHeight() / sizez, Gdx.graphics.getHeight() / sizez);
        resource.collision = Gdx.graphics.getHeight() / sizez / 8;
        int widthrandom = (int) (Gdx.graphics.getWidth()-resource.getWidth());
        int random = (int) Rand(0,widthrandom);
        resource.setPosition(random, Gdx.graphics.getHeight()/2);
        stage.addActor(resource);






        for (int i = 0; i < 6 ; i++) {
            Asteroids.add(new Asteroid());
            float size = (float)Rand(50,70)/10;
            float rotation = (float)Rand(0,360);
            Asteroids.get(i).setSize(Gdx.graphics.getHeight() / size, Gdx.graphics.getHeight() / size);
            AsPoses.add(new Vector2(xyzasteroids( (int)(Gdx.graphics.getHeight() /  size)).x, xyzasteroids((int)(Gdx.graphics.getHeight() / size)).y));
            int dlinavectora = 0;

            if (i > 0) {
                while (true) {
                    int a = 0;


                    for (int j = 0; j < i; j++) {



                            int x1 = (int) (AsPoses.get(i).x + 0.5 * Asteroids.get(i).getWidth());
                            int y1 = (int) (AsPoses.get(i).y + 0.5 * Asteroids.get(i).getWidth());
                            int x2 = (int) (AsPoses.get(j).x + 0.5 * Asteroids.get(i).getWidth());
                            int y2 = (int) (AsPoses.get(j).y + 0.5 * Asteroids.get(i).getWidth());

                            dlinavectora = (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                            if (dlinavectora > Gdx.graphics.getHeight()/2)
                            { a+=1;}
                    }
                    int b=0;
                    if (a==i) {break;} else {
                        AsPoses.get(i).x = xyzasteroids((int)Asteroids.get(i).getWidth()).x;
                        AsPoses.get(i).y = xyzasteroids((int)Asteroids.get(i).getWidth()).y;
                    }
                }
            }
            Asteroids.get(i).setPosition(AsPoses.get(i).x, AsPoses.get(i).y);
            stage.addActor(Asteroids.get(i));
            Asteroids.get(i).setOrigin(Asteroids.get(i).getWidth()/2,Asteroids.get(i).getHeight()/2);
            Asteroids.get(i).setRotation(rotation);
        }
        slider = new Slider();
        slider.setSize(Gdx.graphics.getHeight()/2.25f, Gdx.graphics.getHeight());
        slider.setPosition(Gdx.graphics.getWidth()-0.25f*slider.getWidth(), camera.position.y);
        stage.addActor(slider);

        InputMultiplexer in = new InputMultiplexer();


        MyInputController controller = new MyInputController(camera, slider,worker);



        in.addProcessor(controller);
        in.addProcessor(stage);



        Gdx.input.setInputProcessor(in);
        FillMap(Map, Asteroids);
        dtsum = 0;

    }
    byte a = 0;

    public float getXfromMap(byte X){return (X+0.5f)*(Gdx.graphics.getWidth()/x);}
    public float getYfromMap(byte Y){return (Y+0.5f)*(Gdx.graphics.getHeight()*3/y);}
    public byte getXonMap(float X){return (byte)((int)X/((int)(Gdx.graphics.getWidth()/Level.x)));}
    public byte getYonMap(float Y) {return (byte)((int)Y/((int)((Gdx.graphics.getHeight()*3)/Level.y)));}

    public void FillMap(byte[][] Map, Array<Asteroid> Asteroids){
        for (int i = 0; i<Asteroids.size; i++){
            byte x1 = getXonMap(Asteroids.get(i).getX());
            byte y1 = getYonMap(Asteroids.get(i).getY());
            byte x2 = getXonMap(Asteroids.get(i).getX()+Asteroids.get(i).getWidth());
            byte y2 = getYonMap(Asteroids.get(i).getY()+Asteroids.get(i).getHeight());
            for (byte j = y1; j<=y2; j++){
                for(byte k = x1; k<=x2; k++){
                    if (k<x && j<y) Map[k][j]=1;
                }
            }
        }
        byte x1 = getXonMap(resource.getX());
        byte y1 = getYonMap(resource.getY());
        byte x2 = getXonMap(resource.getX()+resource.getWidth());
        byte y2 = getYonMap(resource.getY()+resource.getHeight());
        for (byte j = y1; j<=y2; j++){
            for(byte k = x1; k<=x2; k++){
                if (k<x && j<y) Map[k][j]=1;
            }
        }
    }
    public void VertilgerSpawn(){
        enemies.add(new Vertilger());
        enemies.get(enemies.size()-1).setSize(Gdx.graphics.getHeight() / 9, Gdx.graphics.getHeight() / 9);
        enemies.get(enemies.size()-1).setPosition(enemyStation.getX()+enemyStation.getWidth()/2-Gdx.graphics.getHeight() / 18, enemyStation.getY()+enemyStation.getWidth()/2-Gdx.graphics.getHeight() / 18);
        enemies.get(enemies.size()-1).setOriginX(Gdx.graphics.getHeight() / 18);
        enemies.get(enemies.size()-1).setOriginY(Gdx.graphics.getHeight() / 18);
        group.addActor(enemies.get(enemies.size()-1));



    }


    public static double Rand(int from,int to){
        return Math.floor((Math.random()*(to-from+1))+from);
    }
    public static Vector2 xyzasteroids(int width){
        Vector2 asteroidPos = new Vector2();
        float x = (float)Rand(0, Gdx.graphics.getWidth()-width);
        float y = (float)Rand(Gdx.graphics.getHeight()/3*2, Gdx.graphics.getHeight()/3*7);
        asteroidPos.set(x, y);
        return asteroidPos;
    }



    @Override
    public void render(float delta) {
        dtsum+=delta;
        if (dtsum>=8 && (Map[getXonMap(enemyStation.getX()+enemyStation.getWidth()/2)][getYonMap(enemyStation.getY()+enemyStation.getHeight()/2)]==0)) {dtsum=0; VertilgerSpawn();}



        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();
        slider.setY(camera.position.y-0.5f*Gdx.graphics.getHeight());

        batch.setProjectionMatrix(camera.combined);
        if ((station.hitpoints<=0) || (enemyStation.hitpoints<=0)) {
            group.addAction(Actions.removeActor());
            stage.clear();
            camera.position.set(new Vector3(960, 540, 0));
            batch.begin();
            batch.draw(end, camera.position.x-(Gdx.graphics.getHeight()*16/9)/2, camera.position.y-Gdx.graphics.getHeight()/2, Gdx.graphics.getHeight()*16/9, Gdx.graphics.getHeight());
            batch.end();
        }

        camera.update();
        update();

        stage.act(delta);
        if ((station.hitpoints>0) && (enemyStation.hitpoints>0))stage.draw();

//        actors.act(delta);
//        actors.draw();

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
