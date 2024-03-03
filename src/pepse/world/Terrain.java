package pepse.world;

import danogl.util.Vector2;

public class Terrain {
    private final float groundHeightAtX0;
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.y() * ((float) 2 /3);
    }

    public float groundHeightAt(float x) { return groundHeightAtX0; }

}
