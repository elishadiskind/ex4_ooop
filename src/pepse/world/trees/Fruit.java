package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.PepseGameManager;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;
import pepse.world.Block;

import java.awt.*;
import java.util.function.Supplier;

public class Fruit extends GameObject {
    public static final String FRUIT_TAG = "Fruit";
    private static final Color BASE_FRUIT_COLOR = new Color(199, 18, 18);
    private static final String FRUIT_IS_NOT_EDIBLE_TAG = "Fruit is not edible";
    private final Supplier<Boolean> setColorOnJump;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     */
    public Fruit(Vector2 topLeftCorner, Supplier<Boolean> setColorOnJump) {
        super(topLeftCorner,
                Vector2.ONES.mult((float) Block.SIZE),
                new OvalRenderable(ColorSupplier.approximateColor(BASE_FRUIT_COLOR)));
        this.setColorOnJump = setColorOnJump;
        this.setTag(FRUIT_TAG);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(setColorOnJump.get()){
            this.renderer().setRenderable(new OvalRenderable(ColorSupplier.approximateColor(BASE_FRUIT_COLOR,
                    70)));
        };
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals(Avatar.AVATAR_TAG)){
            this.setTag(FRUIT_IS_NOT_EDIBLE_TAG);
            this.renderer().fadeOut(0);
            Runnable bringThemBackHome = () -> {
                this.setTag(FRUIT_TAG);
                this.renderer().fadeIn(0);
            };
           new ScheduledTask(this, PepseGameManager.CYCLE_LENGTH, false, bringThemBackHome);
        }
    }
}
