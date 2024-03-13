package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * The Night class provides a static method to create a GameObject representing the night in a game.
 * It includes constants for night-related properties and a method to create the night GameObject.
 */
public class Night {

    /**
     * The tag associated with the night GameObject.
     */
    public static final String NIGHT_TAG = "night";

    /**
     * The opacity value representing daylight.
     */
    private static final float DAYLIGHT_OPACITY = 0f;

    /**
     * The opacity value representing midnight.
     */
    private static final Float MIDNIGHT_OPACITY = 0.5f;

    /**
     * The Sun class provides a static method to create a GameObject representing the sun in a game.
     * It includes constants for sun-related properties and a method to create the sun GameObject.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of a cycle for the transition.
     * @return The created night GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        // Creates the night GameObject with a black rectangle renderer
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);

        // Initiates a transition for opacity from daylight to midnight
        new Transition<>(night,           // the game object being changed
                night.renderer()::setOpaqueness,  // the method to call for opacity change
                DAYLIGHT_OPACITY,        // initial transition value
                MIDNIGHT_OPACITY,        // final transition value
                Transition.CUBIC_INTERPOLATOR_FLOAT,   // use a cubic interpolator
                cycleLength / 2,         // transition fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,  // choose appropriate ENUM value
                null);

        return night;
    }
}