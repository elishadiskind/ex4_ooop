package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

import static pepse.world.daynight.Sun.SUN_SIZE;

public class SunHalo{

    private static final String SUN_HALO_TAG = "sunHalo";

    public static GameObject create(GameObject sun){
        GameObject sunHalo = new GameObject(Vector2.ZERO,
                new Vector2(SUN_SIZE*1.5f,SUN_SIZE*1.5f),
                new OvalRenderable(new Color(255, 255, 0, 20)));
        sunHalo.setCenter(sun.getCenter());
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_HALO_TAG);
        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));
        return sunHalo;
    }
}
