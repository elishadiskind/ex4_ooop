package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.awt.*;

public class Night {

    public static final String NIGHT_TAG = "night";
    public static final float DAYLIGHT_OPACITY = 0f;
    private static final Float MIDNIGHT_OPACITY = 0.5f;

    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        GameObject night = new GameObject( Vector2.ZERO, windowDimensions, new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);
        new Transition<>(night,//the game object being changed
                night.renderer()::setOpaqueness,// the method to call
                DAYLIGHT_OPACITY, //initial transition value
                MIDNIGHT_OPACITY, //final transition value
                Transition.CUBIC_INTERPOLATOR_FLOAT,  //use acubic interpolator
                cycleLength/2, //transition fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,//Choose appropriate ENUM value
                null);

        return night;
    }
}
