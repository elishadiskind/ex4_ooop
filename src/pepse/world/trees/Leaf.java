package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;



public class Leaf {
    private static final Color BASE_LEAF_COLOR = new Color(50, 124, 40);
    public static final String LEAF_TAG = "leaf";
    public static GameObject create(Supplier<Boolean> Detector, Vector2 topLeftCorner){
        GameObject leaf =
                new GameObject(topLeftCorner, new Vector2(Block.SIZE,Block.SIZE),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_LEAF_COLOR)));
        leaf.setTag(LEAF_TAG);
        Runnable angleTransition = () -> new Transition<>(leaf,
                (Float angle) -> leaf.renderer().setRenderableAngle(angle),
                5f,
                15f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                1f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        Runnable sizeTransition = () -> new Transition<>(leaf,
                leaf::setDimensions,
                new Vector2(Block.SIZE,Block.SIZE),
                new Vector2(Block.SIZE - 0.2f,Block.SIZE-0.2f),
                Transition.LINEAR_INTERPOLATOR_VECTOR,
                4f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        Runnable angle90Transition = () -> new Transition<>(leaf,
                (Float angle) -> leaf.renderer().setRenderableAngle(angle),
                0f,
                90f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                1f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        Runnable bothTransitions = ()-> {sizeTransition.run(); angleTransition.run();};
        Random random = new Random();
        double waitTime = random.nextDouble();
        new ScheduledTask(leaf,(float)waitTime,true,bothTransitions);

        leaf.addComponent((deltaTime -> {if(Detector.get()){angle90Transition.run();}}));
        return leaf;
    }
}
