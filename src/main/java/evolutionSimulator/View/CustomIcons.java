package evolutionSimulator.View;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class CustomIcons {
    public ImagePattern generateImagePattern(String name){
        return new ImagePattern(new Image("icons/" + name + "/" + name + "40.png"));
    }
}
