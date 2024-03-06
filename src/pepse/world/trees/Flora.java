package pepse.world.trees;


import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Random;
public class Flora {

    private static final double PROBABILITY_OF_CREATING_A_TREE = 0.1;
    private static final int FOLIAGE_SIZE = 8 * Block.SIZE;
    private static final int MIN_TRUNK_HEIGHT = FOLIAGE_SIZE / 2 + Block.SIZE;
    private static final Color BASE_TRUNK_COLOR = new Color(100, 50, 20);
    private final Function<Float, Float> groundHeightFunc;
    private final Supplier<Boolean> getDetectAvatarJumps;


    public Flora(Function<Float,Float> groundHeightFunc, Supplier<Boolean> getDetectAvatarJumps) {
        this.groundHeightFunc = groundHeightFunc;
        this.getDetectAvatarJumps =getDetectAvatarJumps;
    }

    public ArrayList<GameObject> createInRange(int minX, int maxX){
        ArrayList<GameObject> treeParts = new ArrayList<GameObject>();
        int startX = (minX / Block.SIZE) * Block.SIZE;
        int endX = ((maxX + Block.SIZE - 1) / Block.SIZE) * Block.SIZE;
        Random random = new Random();
        for (int x = startX; x <= endX; x += Block.SIZE) {
            double randomNumber = random.nextDouble();
            if (randomNumber < PROBABILITY_OF_CREATING_A_TREE) {
                createTree(random, x, treeParts);
            }
        }
        return treeParts;
    }

    private void createLeaves(Random random, int treeLocationX, float treeLocationY, ArrayList<GameObject> treeParts) {
        int leafXLocation = (int) (treeLocationX - FOLIAGE_SIZE / 2 + 0.5 * Block.SIZE);
        for (int x = leafXLocation; x < FOLIAGE_SIZE + leafXLocation; x += Block.SIZE) {
            for (float y = treeLocationY - FOLIAGE_SIZE / 2f; y < treeLocationY + FOLIAGE_SIZE / 2f; y += Block.SIZE) {
                if (random.nextBoolean()) {
                    treeParts.add(Leaf.create(getDetectAvatarJumps, new Vector2(x, y)));
                }
            }
        }
    }

    private void createTree(Random random, int x, ArrayList<GameObject> treeParts) {
        int height = MIN_TRUNK_HEIGHT + random.nextInt(3) * Block.SIZE;
        Trunk tempTrunk = new Trunk(new Vector2(x, groundHeightFunc.apply((float) x) - height) ,
                new Vector2(Block.SIZE, height), new RectangleRenderable(ColorSupplier.approximateColor(BASE_TRUNK_COLOR)));
        treeParts.add(tempTrunk);
        createLeaves(random, x,groundHeightFunc.apply((float) x) - height, treeParts);
    }
}
