package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;

import java.awt.*;
import java.util.function.Supplier;

/**
 * The Sun class provides a static method to create a GameObject representing the sun in a game.
 * It includes constants for sun-related properties and a method to create the sun GameObject.
 */
public class Sun {

    /**
     * The tag associated with the sun GameObject.
     */
    public static final String SUN_TAG = "sun";

    /**
     * The size of the sun GameObject.
     */
    public static final int SUN_SIZE = 50;
    private static final float HALF = 0.5f;
    private static final float INIT_SUN_ANGLE = 0f;
    private static final float FINAL_SUN_ANGLE = 360f;

    /**
     * Creates a sun GameObject with a yellow oval renderable, transitioning its position in a circular path
     * over a specified cycle length.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of a cycle (e.g., a day) for the transition.
     * @return The created sun GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        // Creates the sun GameObject with a yellow oval renderable
        GameObject sun = new GameObject(Vector2.ZERO,
                new Vector2(SUN_SIZE, SUN_SIZE),
                new OvalRenderable(Color.YELLOW));
        sun.setCenter(windowDimensions.mult(HALF));
        Vector2 initialSunCenter = windowDimensions.mult(HALF).subtract(new Vector2(SUN_SIZE, SUN_SIZE));
        Vector2 cycleCenter = new Vector2(windowDimensions.mult(HALF).x(),
                windowDimensions.mult(PepseGameManager.EARTH_HEIGHT).y());
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);

        // Initiates a transition for the sun's circular motion
        new Transition<>(sun,
                (Float angle) ->
                     sun.setCenter(initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
                INIT_SUN_ANGLE,
                FINAL_SUN_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);

        return sun;
    }

}