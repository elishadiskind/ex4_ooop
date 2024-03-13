package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Supplier;

/**
 * The code Energy class represents an energy object in the game.
 * It provides a static method to create an energy GameObject with specific properties.
 */
public class Energy {

    /**
     * The tag associated with the energy object.
     */
    private static final String ENERGY_TAG = "energy";
    public static final Vector2 TEXT_SIZE_VECTOR = new Vector2(23, 23);
    public static final int ENERGY_PLACE = 50;
    public static final String INITIAL_ENERGY_VAL = "100";
    public static final String FONT = "Monospaced";
    public static final String PRECENT_CHAR = "%";

    /**
     * Creates a new energy GameObject with the specified window dimensions and a function to retrieve energy
     * value.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param getEnergyFunc    A supplier function providing the current energy value.
     * @return A GameObject representing the energy object.
     */
    public static GameObject create(Vector2 windowDimensions, Supplier<Float> getEnergyFunc) {
        // Create the energy GameObject with initial properties
        GameObject energy = new GameObject(
                new Vector2(ENERGY_PLACE, windowDimensions.y() - ENERGY_PLACE),
                TEXT_SIZE_VECTOR,
                new TextRenderable(INITIAL_ENERGY_VAL));

        // Set the coordinate space to CAMERA_COORDINATES
        energy.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        // Set the tag for the energy object
        energy.setTag(ENERGY_TAG);

        // Add a component to update the energy value dynamically
        energy.addComponent(deltaTime ->
                energy.renderer().setRenderable(
                        new TextRenderable(getEnergyFunc.get().intValue() + PRECENT_CHAR, FONT,
                                false,true)));

          return energy;
    }
}
