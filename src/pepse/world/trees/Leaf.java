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
    public static GameObject create(Supplier<Boolean> Detector, Vector2 topLeftCorner){
        GameObject leaf =
                new GameObject(topLeftCorner, new Vector2(Block.SIZE,Block.SIZE),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_LEAF_COLOR)));
        Runnable angleTransition = () ->  new Transition<>(leaf,
                (Float angle) -> leaf.renderer().setRenderableAngle(angle),
                0f,
                10f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                1.5f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        Runnable sizeTransition = () -> new Transition<>(leaf,
                (Float dim)-> leaf.setDimensions(new Vector2(dim,dim)),
                (float)Block.SIZE - 2,
                (float)Block.SIZE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                1.5f,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        Runnable bothTransitions = ()-> {sizeTransition.run(); angleTransition.run();};
        Random random = new Random();
        float waitTime = random.nextInt(100)/100f;
        new ScheduledTask(leaf,waitTime,true,bothTransitions);
        return leaf;
    }
}

//Supplier<Transition<Float>> transitionFunc =
