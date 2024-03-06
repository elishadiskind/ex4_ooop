package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;

import java.awt.*;

public class Sun {

    public static final String SUN_TAG = "sun";
    public static final int SUN_SIZE = 30;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        GameObject sun = new GameObject(Vector2.ZERO,
                new Vector2(SUN_SIZE,SUN_SIZE),
                new OvalRenderable(Color.YELLOW));
        sun.setCenter(windowDimensions.mult(0.5f));
        Vector2 initialSunCenter = windowDimensions.mult(0.5f).subtract(new Vector2(SUN_SIZE,SUN_SIZE));
        Vector2 cycleCenter = new Vector2(windowDimensions.mult(0.5f).x(),windowDimensions.mult(PepseGameManager.EARTH_HEIGHT).y());
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);

        new Transition<>(sun,
                (Float angle)-> sun.setCenter(initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
        0f,
        360f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);
        return sun;
    }
}
