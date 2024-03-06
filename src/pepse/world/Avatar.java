package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;


public class Avatar extends GameObject {
    public static final int AVATAR_HIGHT = 30;
    public static final int AVATAR_WIDTH = 30;
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 1000;
    public static final float ENERGY_LOSE_RIGHT_LEFT_KEY = 0.5f;
    private static final int ENERGY_LOSE_SPACE_KEY = 10;
    private static final int ENERGY_GAIN_NO_KEY_PRESSED = 1;
    public static final String INITIAL_AVATAR_ASSET = "assets/idle_0.png";
    public static final double TIME_BETWEEN_ANIMATIONS = 0.1;
    private final UserInputListener inputListener;
    public float energy = 100;
    private boolean isAvatarFaceToRight = true;
    private boolean isAvatarJamped = false;
    String[] imagePathsRunning = {"assets/run_0.png","assets/run_1.png","assets/run_2.png","assets/run_3.png","assets/run_4.png","assets/run_5.png"};

    String[] imagePathsResting = {"assets/idle_0.png", "assets/idle_1.png", "assets/idle_2.png", "assets/idle_3.png"};

    String[] imagePathsJump = {"assets/jump_0.png", "assets/jump_1.png", "assets/jump_2.png", "assets/jump_3.png"};
    Renderable runningAnimation;
    Renderable restingAnimation;
    Renderable jumpingAnimation;


    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader){
        super(pos.subtract(new Vector2(AVATAR_WIDTH,-AVATAR_HIGHT))  // button right corner
                ,new Vector2(AVATAR_WIDTH,AVATAR_HIGHT)
                ,imageReader.readImage(INITIAL_AVATAR_ASSET,true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        this.runningAnimation =
                new AnimationRenderable(imagePathsRunning, imageReader,true, TIME_BETWEEN_ANIMATIONS);
        this.restingAnimation =
                new AnimationRenderable(imagePathsResting, imageReader,true, TIME_BETWEEN_ANIMATIONS);
        this.jumpingAnimation =
                new AnimationRenderable(imagePathsJump, imageReader,true, TIME_BETWEEN_ANIMATIONS);

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals(Block.BLOCK_TAG)){
            transform().setVelocityY(0);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        this.isAvatarJamped = false;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
                (energy > ENERGY_LOSE_RIGHT_LEFT_KEY)) {
            if(isAvatarFaceToRight) {
                isAvatarFaceToRight = false;}
            xVel -= VELOCITY_X;
            energy -= ENERGY_LOSE_RIGHT_LEFT_KEY;
            renderer().setRenderable(runningAnimation);
            renderer().setIsFlippedHorizontally(!isAvatarFaceToRight);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) &&
                (energy > ENERGY_LOSE_RIGHT_LEFT_KEY)) {
            isAvatarFaceToRight = true;
            renderer().setRenderable(runningAnimation);
            xVel += VELOCITY_X;
            energy -= ENERGY_LOSE_RIGHT_LEFT_KEY;
            renderer().setIsFlippedHorizontally(!isAvatarFaceToRight);
        }
        transform().setVelocityX(xVel);  //updates the avatar to move to chosen side
         if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) &&
                getVelocity().y() == 0 && (energy >= ENERGY_LOSE_SPACE_KEY)){
             this.isAvatarJamped = true;
            transform().setVelocityY(VELOCITY_Y);
            energy -= ENERGY_LOSE_SPACE_KEY;
            renderer().setRenderable(jumpingAnimation);
        }
        else if(energy<100 && getVelocity().y() == 0 && xVel == 0){
            energy = Math.min(100, energy + ENERGY_GAIN_NO_KEY_PRESSED);
            renderer().setRenderable(restingAnimation);
        }
    }


    public  Supplier<Float> getEnergyFunction = () -> energy;
    public Supplier<Boolean> getDetectAvatarJumps = () -> isAvatarJamped;

}
