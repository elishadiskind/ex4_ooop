package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.util.function.Supplier;

public class Energy {
    private static final String ENERGY_TAG = "energy";


    public static GameObject create(Vector2 windowDimensions, Supplier<Float> getEnergyFunc){
        GameObject energy = new GameObject(new Vector2(50, windowDimensions.y() -50) ,
                new Vector2(20,20),
                new TextRenderable("100"));
        energy.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        energy.setTag(ENERGY_TAG);
        energy.addComponent(deltaTime ->
                energy.renderer().setRenderable(new TextRenderable(String.valueOf(getEnergyFunc.get()))));
        return energy;
    }
}

