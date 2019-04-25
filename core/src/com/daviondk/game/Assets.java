package com.daviondk.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Davio on 07.03.2016.
 */
public class Assets {
    public static Texture menu;
    public static Texture start;
    public static Sound clickSound;
    public static Texture startAnimation;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load () {
        startAnimation = loadTexture("data/startsprites.png");
        menu = loadTexture("data/menu.png");
        clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
    }
    public static void playSound (Sound sound) {
        sound.play(1);
    }
}
