package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;


/**
 * The {@code Sky} class represents the sky in the game.
 * It provides a static method to create a sky GameObject with specific properties.\
 */
public class Sky {

    /**
     * The basic color of the sky.
     */
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");

    /**
     * The tag associated with the sky GameObject.
     */
    public static final String SKY_TAG = "sky";

    /**
     * Creates a new sky GameObject with the specified window dimensions.
     *
     * @param windowDimensions The dimensions of the game window.
     * @return A GameObject representing the sky.
     */
    public static GameObject create(Vector2 windowDimensions) {
        // Create the sky GameObject with initial properties
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));

        // Set the coordinate space to CAMERA_COORDINATES
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // Set the tag for the sky GameObject
        sky.setTag(SKY_TAG);

        return sky;
    }
}