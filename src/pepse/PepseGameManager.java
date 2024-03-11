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
import pepse.world.Energy;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Leaf;

import pepse.world.Block;
import pepse.world.Terrain;
import pepse.world.trees.Flora;
import pepse.world.trees.Trunk;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The PepseGameManager class extends the GameManager class and represents the game manager
 * specifically designed for the "pepse" game. It includes one public method to initialize the game.
 *
 * @author Shira Gelbstein and Elisha Diskind
 */
public class PepseGameManager extends GameManager {

    /**
     * The name of the game manager.
     */
    private static final String GAME_MANAGER_NAME = "pepse";

    /**
     * The default window dimensions for the game.
     */
    private static final float WINDOW_X = 1000;
    private static final float WINDOW_Y = 800;

    /**
     * The length of a cycle in the game.
     */
    public static final int CYCLE_LENGTH = 30;

    /**
     * The height of the earth relative to the window dimensions.
     */
    public static final float EARTH_HEIGHT = 2 / 3f;
    public static final int LEAF_LAYER = -30;

    /**
     * The dimensions of the game window.
     */
    private Vector2 windowDimensions;

    /**
     * Supplier function to detect when avatar jump has been made.
     */
    Supplier<Boolean> getDetectAvatarJumps;

    /**
     * Constructs a PepseGameManager.
     *
     * @param gameManagerName The name of the game manager.
     * @param vector2         The window dimensions as a Vector2.
     */
    public PepseGameManager(String gameManagerName, Vector2 vector2) {
        super(gameManagerName, vector2);
    }

    /**
     * Initializes the game by setting up the necessary elements such as sky, night, sun, avatar, and terrain.
     *
     * @param imageReader      The image reader for loading images.
     * @param soundReader      The sound reader for loading sounds.
     * @param inputListener    The user input listener for handling user inputs.
     * @param windowController The window controller for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        createSky(windowController);
        createNight(windowController);
        createSun(windowController);
        createAvatar(imageReader, inputListener);
        createTerrain();
    }

    //Private methods

    /**
     * Creates a tree with flora (leaves and trunk) and adds them to the game objects list.
     *
     * @param terrain The terrain on which the tree is created.
     */
    private void createTreeWithFlora(Terrain terrain) {
        Flora flora = new Flora(terrain.groundHeightFunc, this.getDetectAvatarJumps);
        ArrayList<GameObject> arrayList = flora.createInRange(31, (int) windowDimensions.x());
        for (GameObject object : arrayList) {
            if (object.getTag().equals(Leaf.LEAF_TAG)) {
                // Adds leaves to the game objects list with -30 layer
                gameObjects().addGameObject(object, LEAF_LAYER);
            } else if (object.getTag().equals(Trunk.TRUNK_TAG)) {
                // Adds trunk to the game objects list with STATIC_OBJECTS layer
                gameObjects().addGameObject(object, Layer.STATIC_OBJECTS);
            } else {
                // Adds other parts of the tree to the game objects list
                gameObjects().addGameObject(object);
            }
        }
    }

    /**
     * Creates the terrain and adds it to the game objects list.
     */
    private void createTerrain() {
        Terrain terrain = new Terrain(windowDimensions, 0);
        List<Block> blocks = terrain.createInRange(0, (int) windowDimensions.x());
        for (Block block : blocks) {
            // Adds blocks to the game objects list with STATIC_OBJECTS layer
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
        createTreeWithFlora(terrain);
    }

    /**
     * Creates the avatar and adds it to the game objects list along with its energy.
     *
     * @param imageReader   The image reader for loading avatar images.
     * @param inputListener The user input listener for handling avatar inputs.
     */
    private void createAvatar(ImageReader imageReader, UserInputListener inputListener) {
        Avatar avatar = new Avatar(new Vector2(30, (int) windowDimensions.y() - (windowDimensions.y() * EARTH_HEIGHT)),
                inputListener, imageReader);
        // Adds avatar to the game objects list
        gameObjects().addGameObject(avatar);
        this.getDetectAvatarJumps = avatar.getDetectAvatarJumps;
        createEnergy(avatar, windowDimensions);
    }

    /**
     * Creates the energy and adds it to the game objects list.
     *
     * @param avatar           The avatar for which energy is created.
     * @param windowDimensions The dimensions of the game window.
     */
    private void createEnergy(Avatar avatar, Vector2 windowDimensions) {
        // Creates energy GameObject
        GameObject energy = Energy.create(windowDimensions, avatar.getEnergyFunction);
        // Adds energy GameObject to the game objects list
        gameObjects().addGameObject(energy);
    }

    /**
     * Creates the night GameObject and adds it to the game objects list.
     *
     * @param windowController The window controller for obtaining window dimensions.
     */
    private void createNight(WindowController windowController) {
        // Creates night GameObject
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        // Adds night GameObject to the game objects list with FOREGROUND layer
        gameObjects().addGameObject(night, Layer.FOREGROUND);
    }

    /**
     * Creates the sun GameObject, its halo, and adds them to the game objects list.
     *
     * @param windowController The window controller for obtaining window dimensions.
     */
    private void createSun(WindowController windowController) {
        // Creates sun GameObject
        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        // Adds sun GameObject to the game objects list with BACKGROUND layer
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        // Creates and adds the sun halo to the game objects list with BACKGROUND layer
        createSunHalo(sun);
    }

    /**
     * Creates the sun halo GameObject and adds it to the game objects list.
     *
     * @param sun The sun GameObject for which the halo is created.
     */
    private void createSunHalo(GameObject sun) {
        // Creates sun halo GameObject
        GameObject sunHalo = SunHalo.create(sun);
        // Adds sun halo GameObject to the game objects list with BACKGROUND layer
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
    }

    /**
     * Creates and adds the sky GameObject to the game objects list.
     *
     * @param windowController The window controller for obtaining window dimensions.
     */
    private void createSky(WindowController windowController) {
        // Creates sky GameObject
        GameObject sky = pepse.world.Sky.create(windowController.getWindowDimensions());
        // Adds sky GameObject to the game objects list with BACKGROUND layer
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }

    /**
     * The main method to launch the PepseGameManager and start the game.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        new PepseGameManager(GAME_MANAGER_NAME, new Vector2(WINDOW_X, WINDOW_Y)).run();
    }
}


