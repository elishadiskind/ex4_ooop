package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;


public class Avatar extends GameObject {
    public static final int AVATAR_HIGHT = 30;
    public static final int AVATAR_WIDTH = 30;
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 1000;
    public static final float ENERGY_LOSE_RIGHT_LEFT_KEY = 0.5f;
    private static final int ENERGY_LOSE_SPACE_KEY = 10;
    private static final float ENERGY_GAIN_NO_KEY_PRESSED = 0.1f;
    private final UserInputListener inputListener;
    private ImageReader imageReadr;
    public float energy = 100;
    String[] imagePathsRunning = {"assets/run_0.png","assets/run_1.png","assets/run_2.png","assets/run_3.png","assets/run_4.png","assets/run_5.png"};

    String[] imagePathsResting = {"assets/idle_0.png", "assets/idle_1.png", "assets/idle_2.png", "assets/idle_3.png"};

    String[] imagePathsJump = {"assets/jump_0.png", "assets/jump_1.png", "assets/jump_2.png", "assets/jump_3.png"};

    Renderable runningAnimation;
    Renderable restingAnimation;
    Renderable jumpingAnimation;


    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader){
        super(pos.subtract(new Vector2(AVATAR_WIDTH,-AVATAR_HIGHT))  // button right corner
                ,new Vector2(AVATAR_WIDTH,AVATAR_HIGHT)
                ,imageReader.readImage("assets/idle_0.png",true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.imageReadr = imageReader;
        this.inputListener = inputListener;

        this.runningAnimation = new AnimationRenderable(imagePathsRunning, imageReadr,true, 0.1);
        this.restingAnimation = new AnimationRenderable(imagePathsResting, imageReadr,true, 0.1);
        this.jumpingAnimation = new AnimationRenderable(imagePathsJump, imageReadr,true, 0.1);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
                (energy > ENERGY_LOSE_RIGHT_LEFT_KEY)) {
            xVel -= VELOCITY_X;
            energy -= ENERGY_LOSE_RIGHT_LEFT_KEY;
            renderer().setRenderable(runningAnimation);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) &&
                (energy > ENERGY_LOSE_RIGHT_LEFT_KEY)) {
            renderer().setRenderable(runningAnimation);
            xVel += VELOCITY_X;
            energy -= ENERGY_LOSE_RIGHT_LEFT_KEY;
        }
        transform().setVelocityX(xVel);  //updates the avatar to chosen side
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) &&
                getVelocity().y() == 0 && (energy > ENERGY_LOSE_SPACE_KEY)){
            transform().setVelocityY(VELOCITY_Y);
            energy -= ENERGY_LOSE_SPACE_KEY;
            renderer().setRenderable(jumpingAnimation);
        }
        else if (energy<=99 && getVelocity().y() == 0){
            energy = ENERGY_GAIN_NO_KEY_PRESSED;
            renderer().setRenderable(restingAnimation);
        }
        System.out.println(energy);
    }
}
