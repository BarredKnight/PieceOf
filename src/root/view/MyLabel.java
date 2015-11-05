package root.view;

import javafx.scene.control.Label;

/**
 * Created by andrew on 10/28/15.
 */
public class MyLabel extends MyItem{
    public final Label label;

    public MyLabel(final String path, final Label label) {
        super(path);
        this.label = label;
    }
}
