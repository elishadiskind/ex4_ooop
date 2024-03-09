package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;



/**
 * The Leaf class represents a leaf GameObject with specific properties and behaviors.
 * It provides methods for creating and managing leaf instances with swaying and acting properly when
 * the avatar is jumping.
 */
public class Leaf {

    /**
     * The base color of the leaf.
     */
    private static final Color BASE_LEAF_COLOR = new Color(50, 124, 30);

    /**
     * The tag associated with leaf instances.
     */
    public static final String LEAF_TAG = "leaf";
    private static final float LEAF_FINAL_ANGLE_ON_JUMP = 90f;
    private static final float LEAF_STARTING_ANGLE_ON_JUMP = 0f;
    private static final float LEAF_FLOW_ANGLE = 6f;
    private static final float TRANSITION_FLOW_TIME = 4f;
    private static final float TRANSITION_ON_JUMP_TIME = 1f;
    private static final float SWAYING_RESIZE_FACTOR = 0.9f;

    /**
     * Creates a new leaf GameObject with scheduled transitions.
     *
     * @param Detector a Supplier providing a boolean value indicating whether a jump is detected.
     * @param topLeftCorner the position of the top-left corner of the leaf.
     * @return the created leaf GameObject.
     */
    public static GameObject create(Supplier<Boolean> Detector, Vector2 topLeftCorner) {
        // Create a new leaf GameObject with the specified color
        GameObject leaf =
                new GameObject(topLeftCorner, new Vector2(Block.SIZE, Block.SIZE),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_LEAF_COLOR)));

        // Set the tag of the leaf
        leaf.setTag(LEAF_TAG);

        // Apply swaying and jumping transitions to the leaf
        leafSwayingTransition(leaf);
        leafWhileJumpTransition(Detector, leaf);

        // Return the created leaf GameObject
        return leaf;
    }

    /**
     * Adds a transition for leaf angle change during a jump when the jump detector is triggered.
     *
     * @param Detector a Supplier providing a boolean value indicating whether a jump is detected on
     * delta time.
     * @param leaf the leaf GameObject to which the transition is applied.
     */
    private static void leafWhileJumpTransition(Supplier<Boolean> Detector, GameObject leaf) {
        // Define a transition from starting angle to final angle during a jump
        Runnable angle90Transition = () -> new Transition<>(leaf,
                (Float angle) -> leaf.renderer().setRenderableAngle(angle),
                LEAF_STARTING_ANGLE_ON_JUMP,
                LEAF_FINAL_ANGLE_ON_JUMP,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                TRANSITION_ON_JUMP_TIME,
                Transition.TransitionType.TRANSITION_ONCE,
                null);

        // Add the transition to the leaf GameObject when the jump detector is triggered
        leaf.addComponent((deltaTime ->
        { if (Detector.get()) { angle90Transition.run(); } }));
    }

    /**
     * Adds swaying transitions for leaf angle and size back and forth.
     *
     * @param leaf the leaf GameObject to which the transitions are applied.
     */
    private static void leafSwayingTransition(GameObject leaf) {
        // Define a transition for swaying the leaf angle back and forth
        Runnable angleTransition = () -> new Transition<>(leaf,
                (Float angle) -> leaf.renderer().setRenderableAngle(angle),
                -LEAF_FLOW_ANGLE,
                LEAF_FLOW_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                TRANSITION_FLOW_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        // Define a transition for resizing the leaf back and forth
        Runnable sizeTransition = () -> new Transition<>(leaf,
                leaf::setDimensions,
                new Vector2(Block.SIZE * SWAYING_RESIZE_FACTOR, Block.SIZE),
                new Vector2(Block.SIZE * 1.1f, Block.SIZE),
                Transition.LINEAR_INTERPOLATOR_VECTOR,
                TRANSITION_FLOW_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        // applying both angle and size transitions in one runnable
        Runnable bothTransitions = () -> { sizeTransition.run(); angleTransition.run(); };

        // Introduce a random wait time before starting both transitions for a realistic leaves swaying
        Random random = new Random();
        double waitTime = random.nextDouble(2);
        new ScheduledTask(leaf, (float) waitTime, false, bothTransitions);
    }
}




