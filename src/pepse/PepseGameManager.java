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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PepseGameManager extends GameManager {

    private static final String GAME_MANAGER_NAME = "pepse";
    private static final float WINDOW_X = 1000;
    private static final float WINDOW_Y = 800;
    public static final int CYCLE_LENGTH = 30;
    public static final float EARTH_HEIGHT = 2/3f;
    private Vector2 windowDimensions;
    Supplier<Boolean> getDetectAvatarJumps;


    public PepseGameManager(String gameManagerName, Vector2 vector2) {
        super(gameManagerName, vector2);
    }

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

    private void createTreeWithFlora(Terrain terrain) {
        Flora flora = new Flora(terrain.groundHeightFunc,this.getDetectAvatarJumps);
        ArrayList<GameObject> arrayList =
                flora.createInRange(0, (int)windowDimensions.x());
        for(GameObject object: arrayList){
            if(object.getTag().equals(Leaf.LEAF_TAG)){
            gameObjects().addGameObject(object,10);
        }
        else{
            gameObjects().addGameObject(object,-110);
        }
    }
    }

    private void createTerrain() {
        Terrain terrain = new Terrain(windowDimensions, 0);
        List<Block> blocks = terrain.createInRange(0, (int) windowDimensions.x());
        for(Block block :blocks){
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
        createTreeWithFlora(terrain);
    }

    private void createAvatar(ImageReader imageReader, UserInputListener inputListener) {
        Avatar avatar =
                new Avatar(new Vector2(70,(windowDimensions.y() * EARTH_HEIGHT)), inputListener, imageReader);
        gameObjects().addGameObject(avatar);
        this.getDetectAvatarJumps = avatar.getDetectAvatarJumps;
        createEnergy(avatar,windowDimensions);
    }

    private void createEnergy(Avatar avatar, Vector2 windowDimensions) {
        GameObject energy = Energy.create(windowDimensions, avatar.getEnergyFunction);
        gameObjects().addGameObject(energy);
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







//Energy bar code
//private void createEnergy(Avatar avatar, Vector2 windowDimensions,ImageReader imageReader) {
//        Renderable backGroundRenderer = imageReader.readImage("assets/energyBar/BarBackground.png",true);
//        GameObject backGround = new GameObject(new Vector2(30, windowDimensions.y() -90),new Vector2(200,100),backGroundRenderer);
//        gameObjects().addGameObject(backGround,Layer.STATIC_OBJECTS + 1);
//
//        Renderable barGlassRenderer = imageReader.readImage("assets/energyBar/BarGlass.png",true);
//        GameObject barGlass = new GameObject(new Vector2(30, windowDimensions.y() -90),new Vector2(200,100),barGlassRenderer);
//        gameObjects().addGameObject(barGlass,Layer.STATIC_OBJECTS + 3);
//
//        Renderable redBarRenderer = imageReader.readImage("assets/energyBar/GreenBar.png",true);
//        GameObject redBar = new GameObject(new Vector2(30, windowDimensions.y() -90),new Vector2(200,100),redBarRenderer);
//        redBar.addComponent(deltaTime -> redBar.setDimensions(new Vector2(0 + 2*avatar.getEnergy(),100)));
//        redBar.addComponent(deltaTime -> redBar.setTopLeftCorner(new Vector2(30, windowDimensions.y() - 90)));
//        gameObjects().addGameObject(redBar,Layer.STATIC_OBJECTS + 2);