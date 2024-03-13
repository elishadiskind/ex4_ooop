package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;
import pepse.world.Block;

import java.awt.*;
import java.util.function.Supplier;

/**
 * The Fruit class represents a fruit GameObject in the game.
 * It extends the GameObject. when ever the avatar is jumping the fruit changes its color
 * and when ever the avatar touches the fruit (collide with) it disappear for a whole game cycle.
 */
public class Fruit extends GameObject {

    /**
     * The tag associated with the fruit GameObject.
     */
    public static final String FRUIT_TAG = "Fruit";

    /**
     * The base color of the fruit.
     */
    private static final Color BASE_FRUIT_COLOR = new Color(199, 18, 18);

    /**
     * The tag set when the fruit is not edible - not in the game.
     */
    private static final String FRUIT_IS_NOT_EDIBLE_TAG = "Fruit is not edible";
    /**
     * The color delta - influenced the change in the color while jumping.
     */
    public static final int COLOR_DELTA = 70;

    /**
     * The supplier for setting new color when ever the avatar jumps.
     */
    private final Supplier<Boolean> setColorDetector;

    /**
     * Constructs a new Fruit instance with the specified position and color-changing supplier.
     *
     * @param topLeftCorner Position of the fruit, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param setColorOnJump The supplier for setting color on avatar jumps.
     */
    public Fruit(Vector2 topLeftCorner, Supplier<Boolean> setColorOnJump) {
        super(topLeftCorner,
                Vector2.ONES.mult((float) Block.SIZE),
                new OvalRenderable(ColorSupplier.approximateColor(BASE_FRUIT_COLOR)));
        this.setColorDetector = setColorOnJump;
        this.setTag(FRUIT_TAG);
    }

    /**
     * Updates the fruit's color based on avatar jumps.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (setColorDetector.get()) {
            this.renderer().setRenderable(
                    new OvalRenderable(ColorSupplier.approximateColor(BASE_FRUIT_COLOR, COLOR_DELTA)));
        }
    }

    /**
     * Handles actions when a collision occurs with another GameObject.
     *
     * @param other     The other GameObject involved in the collision.
     * @param collision The information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(Avatar.AVATAR_TAG)) {
            this.setTag(FRUIT_IS_NOT_EDIBLE_TAG);
            this.renderer().fadeOut(0);
            Runnable bringThemBackHome = () -> {
                this.setTag(FRUIT_TAG);
                this.renderer().fadeIn(0);
            };
            new ScheduledTask(this,
                    PepseGameManager.CYCLE_LENGTH,
                    false, bringThemBackHome);
        }
    }
}