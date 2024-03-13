package pepse.world;

import danogl.gui.ImageReader;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * The Terrain class represents the terrain in the game. It generates and manages the ground
 * with specific properties, including colors, depth, and tags for the ground blocks.
 */
public class Terrain {

    /**
     * The base color of the ground.
     */
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);

    /**
     * The depth of the terrain, representing the number of layers of ground blocks.
     */
    private static final int TERRAIN_DEPTH = 20;

    /**
     * The tag associated with the ground blocks.
     */
    private static final String GROUND_TAG = "ground";

    /**
     * The noise generator used to create variations in ground height.
     */
    private final NoiseGenerator noiseGenerator;

    /**
     * The ground height at the initial X coordinate (x=0).
     */
    private final int groundHeightAtX0;

    /**
     * Constructs a new {@code Terrain} instance with the specified window dimensions and seed for the noise
     * generator.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param seed             The seed for the noise generator.
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        groundHeightAtX0 = (int) (windowDimensions.y() * (PepseGameManager.EARTH_HEIGHT));
        noiseGenerator = new NoiseGenerator(seed, groundHeightAtX0);
    }

    /**
     * Calculates the ground height at a given X coordinate using noise generation.
     *
     * @param x The X coordinate for which to calculate the ground height.
     * @return The calculated ground height at the specified X coordinate.
     */
    private float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * 7);
        return groundHeightAtX0 + noise;
    }

    /**
     * Function representing the ground height calculation at a given X coordinate.
     */
    public Function<Float, Float> groundHeightFunc = this::groundHeightAt;

    /**
     * Creates a list of ground blocks within the specified X coordinate range.
     *
     * @param minX The minimum X coordinate for block creation.
     * @param maxX The maximum X coordinate for block creation.
     * @return A list of ground blocks within the specified X coordinate range.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blockList = new ArrayList<>();

        // Determine the starting and ending X coordinates for block creation
        int startX = (minX / Block.SIZE) * Block.SIZE;
        int endX = ((maxX + Block.SIZE - 1) / Block.SIZE) * Block.SIZE;

        // Iterate over the range of X coordinates
        for (int x = startX; x <= endX; x += Block.SIZE) {
            // Calculate Y coordinate for ground height, rounded to nearest Block.SIZE
            int y = (int) (Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE);

            // Create blocks within the specified depth
            for (int i = 0; i < TERRAIN_DEPTH; i++) {
                // Create a block at calculated coordinates
                Block block = new Block(new Vector2(x, y + i * Block.SIZE),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                block.setTag(GROUND_TAG);
                blockList.add(block);
            }
        }
        return blockList;
    }
}
