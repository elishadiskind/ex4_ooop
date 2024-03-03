package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;

public class PepseGameManager extends GameManager {

    private static final String GAME_MANAGER_NAME = "pepse";
    private static final float WINDOW_X = 500;
    private static final float WINDOW_Y = 500;
    public static final String SKY_TAG = "sky";
    private Vector2 windowDimensions;

    public PepseGameManager(String gameManagerName, Vector2 vector2) {
        super(gameManagerName, vector2);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        createSky(windowController);
        Terrain terrain = new Terrain(windowDimensions, 289);

        List<Block> blocks = terrain.createInRange(0, (int) windowDimensions.x());
        for(Block block :blocks){
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    private void createSky(WindowController windowController) {
        GameObject sky = pepse.world.Sky.create(windowController.getWindowDimensions());
        sky.setTag(SKY_TAG);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }

    public static void main(String[] args) {
        new PepseGameManager(GAME_MANAGER_NAME, new Vector2(WINDOW_X, WINDOW_Y)).run();
    }
}
