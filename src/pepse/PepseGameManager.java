package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;

import pepse.world.Avatar;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import java.awt.*;

import pepse.world.Block;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;

public class PepseGameManager extends GameManager {

    private static final String GAME_MANAGER_NAME = "pepse";
    private static final float WINDOW_X = 800;
    private static final float WINDOW_Y = 800;
    public static final int CYCLE_LENGTH = 30;
    public static final float EARTH_HIGHT = 2/3f;
    private Vector2 windowDimensions;


    public PepseGameManager(String gameManagerName, Vector2 vector2) {
        super(gameManagerName, vector2);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        createSky(windowController);
        createNight(windowController);
        createSun(windowController);
        createAvatar(imageReader, inputListener);
        createTerrain();

    }

    private void createTerrain() {
        Terrain terrain = new Terrain(windowDimensions, 0);

        List<Block> blocks = terrain.createInRange(0, (int) windowDimensions.x());
        for(Block block :blocks){
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    private void createAvatar(ImageReader imageReader, UserInputListener inputListener) {
        Avatar avatar = new Avatar(new Vector2(90,90), inputListener, imageReader); //should be changed
        gameObjects().addGameObject(avatar);
    }

    private void createNight(WindowController windowController) {
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night,Layer.FOREGROUND);
    }
    private void createSun(WindowController windowController) {
        GameObject sun = Sun.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
        gameObjects().addGameObject(sun,Layer.BACKGROUND);
        createSunHalo(sun);
    }

    private void createSunHalo(GameObject sun) {
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo,Layer.BACKGROUND);
    }


    private void createSky(WindowController windowController) {
        GameObject sky = pepse.world.Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }

    public static void main(String[] args) {
        new PepseGameManager(GAME_MANAGER_NAME, new Vector2(WINDOW_X, WINDOW_Y)).run();
    }
}
