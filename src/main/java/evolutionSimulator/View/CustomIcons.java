package evolutionSimulator.View;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class CustomIcons {
    ImagePattern iconLion = new ImagePattern(new Image("icons/Lion/Lion40.png"));
    ImagePattern iconGrass = new ImagePattern(new Image("icons/Grass/icons8-grass-40.png"));

    public ImagePattern getIconLion() {
        return iconLion;
    }
    public ImagePattern getIconGrass() {
        return iconGrass;
    }

    public ImagePattern generateImagePattern(String name){
        return new ImagePattern(new Image("icons/" + name + "/" + name + "40.png"));
    }
}
