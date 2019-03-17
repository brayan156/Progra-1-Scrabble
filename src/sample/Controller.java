package sample;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class Controller {
    @FXML
    private Pane hola;
    public static imagen player;
    public static Logger log = LoggerFactory.getLogger(Controller.class);
    @FXML
    private ImageView castillo2;
    private Double orgSceneX;
    private Double orgSceneY;
    private ImageView matriz[][] = new ImageView[15][15];



    public void clickon() {
        log.debug("si clickeaste compa");
        Image imagen = new Image("file:src/Media/Castillo1.JPG");
        ImageView img = new ImageView(imagen);
        img.setX(100);
        img.setY(100);
        img.setFitHeight(30);
        img.setFitWidth(30);
        img.setId("Imagen");
        String ID = img.getId();
        System.out.println(ID);
        hola.getChildren().add(img);
        img.setOnMousePressed(pressear);
        img.setOnMouseDragged(draggear);
        img.setOnMouseReleased(meter);

    }

    EventHandler<MouseEvent> pressear =
            t -> {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
            };

    EventHandler<MouseEvent> draggear =
            t -> {
                ImageView img= (ImageView)(t.getSource());
                double offsetX = orgSceneX - t.getSceneX();
                double offsetY = orgSceneY - t.getSceneY();
                double newTranslateX = orgSceneX - offsetX -img.getFitWidth()/2;
                double newTranslateY = orgSceneY - offsetY -img.getFitHeight()/2;
                img.setX(newTranslateX);
                img.setY(newTranslateY);

            };
    EventHandler<MouseEvent> meter =
            t -> {
                ImageView img= (ImageView)(t.getSource());
                double x= 10-img.getFitWidth()/2;
                double y= 10-img.getFitHeight()/2;
                int contx=0;
                int conty=0;

                while (contx<15){
                    if (img.getX() >= x && img.getX() <=x+30 ){
                        while (conty<15){
                            if(img.getY() >= y && img.getY() <=y+30 ){
                                matriz[conty][contx] = img;
                                System.out.println(img.getY());
                                System.out.println(img.getX());
                                System.out.println(matriz[conty][contx].getId()+" guardada en "+contx+","+conty);
                                x=460;
                                y=460;
                            }
                            else{
                                conty++;
                                y+=30;
                            }
                        }
                    }
                    else{
                        contx++;
                        x+=30;
                    }


                }
            };
}
