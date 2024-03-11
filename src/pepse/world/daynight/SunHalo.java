package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * The SunHalo class provides a static method to create a GameObject representing the halo around the sun in a game.
 */
public class SunHalo {

    /**
     * The tag associated with the sun halo GameObject.
     */
    private static final String SUN_HALO_TAG = "sunHalo";
    public static final float HALO_SIZE_FACTOR = 2f;
    public static final Color HALO_COLOR = new Color(255, 255, 0, 20);

    /**
     * Creates a sun halo GameObject around the specified sun GameObject with a translucent oval renderable.
     * The sun halo follows the center position of the sun with the "add component" method.
     *
     * @param sun The sun GameObject around which the halo is created.
     * @return The created sun halo GameObject.
     */
    public static GameObject create(GameObject sun) {
        // Creates the sun halo GameObject with a translucent oval renderable
        GameObject sunHalo = new GameObject(Vector2.ZERO,
                new Vector2(Sun.SUN_SIZE * HALO_SIZE_FACTOR, Sun.SUN_SIZE * HALO_SIZE_FACTOR),
                new OvalRenderable(HALO_COLOR));
        sunHalo.setCenter(sun.getCenter());
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_HALO_TAG);

        // Adds a component to update the sun halo position based on the sun's center
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));

        return sunHalo;
    }
}