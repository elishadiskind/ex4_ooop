package pepse;

import danogl.GameManager;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;

public class PepseGameManager extends GameManager {

    private static final String GAME_MANAGER_NAME = "whatever";
    private static final float WINDOW_X = 300;
    private static final float WINDOW_Y = 300;

    public PepseGameManager(String gameManagerName, Vector2 vector2) {
        super(gameManagerName, vector2);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
    }

    public static void main(String[] args) {
        new PepseGameManager(GAME_MANAGER_NAME, new Vector2(WINDOW_X, WINDOW_Y)).run();
    }
}
