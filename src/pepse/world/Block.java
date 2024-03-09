package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Block class represents a block in the terrain (pixel), extending the GameObject class.
 * It is a basic element with a defined size, tag, and physics properties.
 */
public class Block extends GameObject {

    /**
     * The size of the block.
     */
    public static final int SIZE = 30;

    /**
     * The tag associated with the block.
     */
    public static final String BLOCK_TAG = "block";

    /**
     * Constructs a new  Block instance with the specified top-left corner position
     * and a renderable object.
     *
     * @param topLeftCorner The top-left corner position of the block.
     * @param renderable    The renderable object associated with the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);

        // Set up physics properties
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);

        // Set the tag for the block
        this.setTag(BLOCK_TAG);
    }
}