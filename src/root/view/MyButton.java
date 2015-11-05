package root.view;

import javafx.scene.control.Button;

/**
 * Created by andrew on 10/28/15.
 */
public class MyButton extends MyItem {
    private final Button button;

    public MyButton(final String path, final Button button) {
        super(path);
        this.button = button;
    }
}
