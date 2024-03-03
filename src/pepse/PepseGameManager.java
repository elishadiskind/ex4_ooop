package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import java.awt.*;

public class PepseGameManager extends GameManager {

    private static final String GAME_MANAGER_NAME = "pepse";
    private static final float WINDOW_X = 500;
    private static final float WINDOW_Y = 500;
    public static final int CYCLE_LENGTH = 30;


    public PepseGameManager(String gameManagerName, Vector2 vector2) {
        super(gameManagerName, vector2);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        createSky(windowController);
        createNight(windowController);
        createSun(windowController);
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
