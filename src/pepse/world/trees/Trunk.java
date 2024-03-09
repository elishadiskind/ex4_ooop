package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.function.Supplier;

/**
 * The Trunk class represents the trunk of a tree GameObject in the game.
 * It extends the GameObject class. The trunk is a static, immovable GameObject with a rectangular shape,
 * and it changes its appearance when avatar jumps are detected.
 *
 */
public class Trunk extends GameObject {

    /**
     * The tag associated with the trunk GameObject.
     */
    public static final String TRUNK_TAG = "trunk";

    /**
     * The base color of the trunk.
     */
    private static final Color BASE_TRUNK_COLOR = new Color(100, 50, 20);

    /**
     * The supplier for detecting avatar jumps.
     */
    private final Supplier<Boolean> getDetectAvatarJumps;

    /**
     * Constructs a new Trunk instance with the specified position, dimensions, and jump detection supplier.
     *
     * @param topLeftCorner        Position of the trunk, in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param dimensions           Width and height in window coordinates.
     * @param getDetectAvatarJumps The supplier for detecting avatar jumps.
     */
    public Trunk(Vector2 topLeftCorner, Vector2 dimensions, Supplier<Boolean> getDetectAvatarJumps) {
        super(topLeftCorner,
                dimensions,
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_TRUNK_COLOR)));
        this.getDetectAvatarJumps = getDetectAvatarJumps;

        // Configure physics properties
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);

        // Set the tag for the trunk GameObject
        this.setTag(TRUNK_TAG);
    }

    /**
     * Updates the trunk's appearance based on avatar jumps.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Change trunk color when avatar jumps are detected
        if (getDetectAvatarJumps.get()) {
            this.renderer().setRenderable(
                    new RectangleRenderable(ColorSupplier.approximateColor(BASE_TRUNK_COLOR, 20)));
        }
    }
}
