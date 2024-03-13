package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.trees.Fruit;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;

/**
 * The Avatar class represents the character controlled by the player.
 * It extends the GameObject class and includes methods to handle keyboard input and update the
 * avatar's state due to collisions with other game objects and keyboard.
 */
public class Avatar extends GameObject {

    /**
     * The height of the avatar.
     */
    private static final int AVATAR_HEIGHT = 30;

    /**
     * The width of the avatar.
     */
    private static final int AVATAR_WIDTH = 30;

    /**
     * The initial X velocity of the avatar.
     */
    private static final float VELOCITY_X = 400;

    /**
     * The initial Y velocity of the avatar (jumping velocity).
     */
    private static final float VELOCITY_Y = -800;

    /**
     * The gravity acting on the avatar.
     */
    private static final float GRAVITY = 1000;

    /**
     * Energy cost for moving left or right.
     */
    private static final float ENERGY_LOSE_RIGHT_LEFT_KEY = 0.5f;

    /**
     * Energy cost for using the space key to jump.
     */
    private static final int ENERGY_LOSE_SPACE_KEY = 10;

    /**
     * Energy gain when no keys are pressed.
     */
    private static final int ENERGY_GAIN_NO_KEY_PRESSED = 1;

    /**
     * The asset path for the initial avatar image.
     */
    public static final String INITIAL_AVATAR_ASSET = "assets/idle_0.png";

    /**
     * Time between animation frames.
     */
    private static final double TIME_BETWEEN_ANIMATIONS = 0.1;

    /**
     * The tag associated with the avatar GameObject.
     */
    public static final String AVATAR_TAG = "Avatar";

    /**
     * Energy gain when colliding with a fruit.
     */
    private static final int ENERGY_GAIN_BY_FRUIT = 10;

    /**
     * The UserInputListener used to handle user input for the avatar.
     */
    private final UserInputListener inputListener;

    /**
     * The current energy level of the avatar.
     */
    private float energy = 100;

    /**
     * Flag indicating whether the avatar is facing right.
     */
    private boolean isAvatarFaceToRight = true;

    /**
     * Flag indicating whether the avatar is in a jumping state.
     */
    private boolean isAvatarJumped = false;

    /**
     * Array of image paths for running animation.
     */
    private final String[] imagePathsRunning = {"assets/run_0.png", "assets/run_1.png", "assets/run_2.png",
            "assets/run_3.png", "assets/run_4.png", "assets/run_5.png"};

    /**
     * Array of image paths for resting animation.
     */
    private final String[] imagePathsResting = {"assets/idle_0.png", "assets/idle_1.png",
            "assets/idle_2.png", "assets/idle_3.png"};

    /**
     * Array of image paths for jumping animation.
     */
    private final String[] imagePathsJump = {"assets/jump_0.png", "assets/jump_1.png",
            "assets/jump_2.png", "assets/jump_3.png"};

    /**
     * Renderable for the running animation.
     */
    private final Renderable runningAnimation;

    /**
     * Renderable for the resting animation.
     */
    private final Renderable restingAnimation;

    /**
     * Renderable for the jumping animation.
     */
    private final Renderable jumpingAnimation;

    /**
     * Constructs an Avatar with the specified position, UserInputListener, and ImageReader.
     * and initialise animation pictures
     *
     * @param pos           The initial position of the avatar.
     * @param inputListener The UserInputListener for handling user input.
     * @param imageReader   The ImageReader for loading avatar images.
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos.subtract(new Vector2(AVATAR_WIDTH, -AVATAR_HEIGHT)),
                new Vector2(AVATAR_WIDTH, AVATAR_HEIGHT),
                imageReader.readImage(INITIAL_AVATAR_ASSET, true));
        this.setTag(AVATAR_TAG);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        this.runningAnimation =
                new AnimationRenderable(imagePathsRunning, imageReader, true,
                        TIME_BETWEEN_ANIMATIONS);
        this.restingAnimation =
                new AnimationRenderable(imagePathsResting, imageReader, true,
                        TIME_BETWEEN_ANIMATIONS);
        this.jumpingAnimation =
                new AnimationRenderable(imagePathsJump, imageReader, true,
                        TIME_BETWEEN_ANIMATIONS);
    }

    /**
     * Handles collision events with block - sets velocity Y to 0, and by colliding
     * with a fruit increases the avatars energy
     *
     * @param other     The GameObject collided with.
     * @param collision The details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(Block.BLOCK_TAG)) {
            // Stops the vertical movement when colliding with a block
            transform().setVelocityY(0);
        }
        if (other.getTag().equals(Fruit.FRUIT_TAG)) {
            // Increases energy when colliding with a fruit
            energy = Math.min(100, energy + ENERGY_GAIN_BY_FRUIT);
        }
    }

    /**
     * Updates the avatar's movement and animation based on user input and the avatar's energy.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        this.isAvatarJumped = false;

        // Handling left key input
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && (energy > ENERGY_LOSE_RIGHT_LEFT_KEY)) {
            if (isAvatarFaceToRight) {
                isAvatarFaceToRight = false;
            }
            xVel -= VELOCITY_X;
            energy -= ENERGY_LOSE_RIGHT_LEFT_KEY;
            renderer().setRenderable(runningAnimation);
            renderer().setIsFlippedHorizontally(!isAvatarFaceToRight);
        }

        // Handling right key input
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && (energy > ENERGY_LOSE_RIGHT_LEFT_KEY)) {
            isAvatarFaceToRight = true;
            renderer().setRenderable(runningAnimation);
            xVel += VELOCITY_X;
            energy -= ENERGY_LOSE_RIGHT_LEFT_KEY;
            renderer().setIsFlippedHorizontally(!isAvatarFaceToRight);
        }

        transform().setVelocityX(xVel);  // Updates the avatar to move to the chosen side

        // Handling space key input for jumping
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 &&
                (energy >= ENERGY_LOSE_SPACE_KEY)) {
            this.isAvatarJumped = true;
            transform().setVelocityY(VELOCITY_Y);
            energy -= ENERGY_LOSE_SPACE_KEY;
            renderer().setRenderable(jumpingAnimation);
        } else if (energy < 100 && getVelocity().y() == 0 && xVel == 0) {
            // Gain energy when no keys are pressed and avatar is not moving
            energy = Math.min(100, energy + ENERGY_GAIN_NO_KEY_PRESSED);
            renderer().setRenderable(restingAnimation);
        }
    }

    /**
     * Gets the current energy level of the avatar.
     */
    public Supplier<Float> getEnergyFunction = () -> energy;

    /**
     * Gets a boolean indicating whether the avatar has jumped in the current delta time.
     */
    public Supplier<Boolean> getDetectAvatarJumps = () -> isAvatarJumped;
}