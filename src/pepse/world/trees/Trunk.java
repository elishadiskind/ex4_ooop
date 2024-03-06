package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.function.Supplier;

public class Trunk extends GameObject {
    private static final Color BASE_TRUNK_COLOR = new Color(100, 50, 20);
    private boolean colorIsFliped = false;
    private final Supplier<Boolean> getDetectAvatarJumps;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner        Position of the object, in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param dimensions           Width and height in window coordinates.
     * @param getDetectAvatarJumps
     */
    public Trunk(Vector2 topLeftCorner, Vector2 dimensions, Supplier<Boolean> getDetectAvatarJumps) {
        super(topLeftCorner, dimensions, new RectangleRenderable(ColorSupplier.approximateColor(BASE_TRUNK_COLOR)));
        this.getDetectAvatarJumps = getDetectAvatarJumps;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(getDetectAvatarJumps.get()){
            this.renderer().setRenderable(new RectangleRenderable(ColorSupplier.approximateColor(BASE_TRUNK_COLOR,
                    20)));
        };
    }
}
