package pepse.world.trees;


import danogl.GameObject;
import danogl.util.Vector2;
import pepse.world.Block;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Random;

/**
 * The code Flora class creates a list representing the whole tree - including trunks leaves, and fruits.
 * It provides methods to generate and manage trees within a specified range based on the
 * game dimensions it gets.The trees generation is influenced by the ground height function that it holds.
 */
public class Flora {

    /**
     * The probability of creating a tree at each X coordinate.
     */
    private static final double PROBABILITY_OF_CREATING_A_TREE = 0.1;

    /**
     * The size of the foliage for trees.
     */
    private static final int FOLIAGE_SIZE = 6 * Block.SIZE;

    /**
     * The minimum height of the tree trunk.
     */
    private static final int MIN_TRUNK_HEIGHT = (int) (FOLIAGE_SIZE / 1.5f) + Block.SIZE;
    private static final double PROBABILITY_OF_FRUITS_CREATION = 0.05;

    /**
     * The lambda function providing ground height information.
     */
    private final Function<Float, Float> groundHeightFunc;

    /**
     * The supplier to detect when ever the avatar jumps.
     */
    private final Supplier<Boolean> getDetectAvatarJumps;

    /**
     * Constructs a new Flora instance with the specified ground height function and jump detection supplier.
     *
     * @param groundHeightFunc    The function providing ground height information.
     * @param getDetectAvatarJumps The supplier to detect avatar jumps.
     */
    public Flora(Function<Float, Float> groundHeightFunc, Supplier<Boolean> getDetectAvatarJumps) {
        this.groundHeightFunc = groundHeightFunc;
        this.getDetectAvatarJumps = getDetectAvatarJumps;
    }

    /**
     * Generates and returns an ArrayList of flora GameObjects within the given specified X coordinate range.
     *
     * @param minX The minimum X coordinate for flora creation.
     * @param maxX The maximum X coordinate for flora creation.
     * @return An ArrayList of flora GameObjects created within the specified X coordinate range.
     */
    public ArrayList<GameObject> createInRange(int minX, int maxX) {
        ArrayList<GameObject> treeParts = new ArrayList<>();
        int startX = (minX / Block.SIZE) * Block.SIZE;
        int endX = ((maxX + Block.SIZE - 1) / Block.SIZE) * Block.SIZE;
        Random random = new Random();

        // Generate trees based on probability
        for (int x = startX; x <= endX; x += Block.SIZE) {
            double randomNumber = random.nextDouble();
            if (randomNumber < PROBABILITY_OF_CREATING_A_TREE) {
                createTree(random, x, treeParts);
            }
        }
        return treeParts;
    }

    /**
     * Generates leaves and fruits around a tree location, adding them to the specified ArrayList of GameObjects.
     *
     * @param random           The Random instance for randomization.
     * @param treeLocationX    The X coordinate of the tree.
     * @param treeLocationY    The Y coordinate of the tree.
     * @param treeParts        The list of GameObjects to add the objects to.
     */
    private void createLeaves(Random random, int treeLocationX, float treeLocationY, ArrayList<GameObject> treeParts) {
        int leafXLocation = (int) (treeLocationX - FOLIAGE_SIZE / 2 + 0.5 * Block.SIZE);

        // Generate leaves and occasional fruits around the tree
        for (int x = leafXLocation; x < FOLIAGE_SIZE + leafXLocation; x += Block.SIZE) {
            for (float y = treeLocationY - FOLIAGE_SIZE / 2f; y < treeLocationY + FOLIAGE_SIZE / 2f; y += Block.SIZE) {
                if (random.nextBoolean()) {
                    treeParts.add(Leaf.create(getDetectAvatarJumps, new Vector2(x, y)));
                }
                if (random.nextDouble() < PROBABILITY_OF_FRUITS_CREATION) {
                    treeParts.add(new Fruit(new Vector2(x, y), getDetectAvatarJumps));
                }
            }
        }
    }

    /**
     * Generates a tree with a trunk and associated leaves, adding them to the specified ArrayList of GameObjects.
     *
     * @param random     The Random instance for random choosing the trunk height.
     * @param x          The X coordinate for tree creation.
     * @param treeParts  The list of GameObjects to add the tree parts to.
     */
    private void createTree(Random random, int x, ArrayList<GameObject> treeParts) {
        int height = MIN_TRUNK_HEIGHT + random.nextInt(3) * Block.SIZE;

        // Create a trunk and associated leaves
        Trunk tempTrunk = new Trunk(new Vector2(x, groundHeightFunc.apply((float) x) - height),
                new Vector2(Block.SIZE, height), getDetectAvatarJumps);
        treeParts.add(tempTrunk);
        createLeaves(random, x, groundHeightFunc.apply((float) x) - height, treeParts);
    }
}
