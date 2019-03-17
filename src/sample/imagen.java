package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


public class imagen extends Rectangle {
    private int posx;
    private int posy;
    private Image image;
    private ImagePattern imagePattern;


    public imagen (int posx, int posy, int width, int height, String imgPath) {
        super(width, height, Color.BISQUE);
        this.setTranslateX(posx);
        this.setTranslateY(posy);
        this.image = new Image(imgPath);
        this.imagePattern = new ImagePattern(this.image);
        this.setFill(imagePattern);
        this.posx = posx;
        this.posy = posy;

    }

}
