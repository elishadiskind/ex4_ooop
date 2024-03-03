package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123,74);
    private static final int TERRAIN_DEPTH = 20;
    private static final String GROUND_TAG = "ground";
    private static RectangleRenderable rectangleRenderable =
            new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
    private final Vector2 windowDimensions;
    private final NoiseGenerator noiseGenerator;
    private final int groundHeightAtX0;

    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = (int) (windowDimensions.y() * ((float) 2 /3));
        this.windowDimensions = windowDimensions;
        noiseGenerator = new NoiseGenerator(seed, groundHeightAtX0);
    }

    public float groundHeightAt(float x) {
                float noise = (float) noiseGenerator.noise(x, Block.SIZE*7);
                return groundHeightAtX0 + noise;
            }

    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blockList = new ArrayList<>();

        // Determine the starting and ending X coordinates for block creation
        int startX = (minX / Block.SIZE) * Block.SIZE;
        int endX = ((maxX + Block.SIZE - 1) / Block.SIZE) * Block.SIZE;

        // Iterate over the range of X coordinates
        for (int x = startX; x <= endX; x += Block.SIZE) {
            // Calculate Y coordinate for ground height, rounded to nearest Block.SIZE
            int y = (int) (Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE);
            for (int i = 0; i < TERRAIN_DEPTH; i++) {
                // Create a block at calculated coordinates
                Block block = new Block(new Vector2(x, y + i*Block.SIZE), rectangleRenderable);
                block.setTag(GROUND_TAG);
                blockList.add(block);
            }
        }

        return blockList;
    }

}
