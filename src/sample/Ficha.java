package sample;


import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Ficha extends ImageView {
    int posx,posy,valor;
    String letra;

    public Ficha(int posx, int posy, String letra) {
        super(new Image("file:src/Media/"+letra+".JPG"));
        if ("AEOISNLRUT".contains(letra)){this.valor=1;}
        else if ("DG".contains(letra)){this.valor=2;}
        else if ("CBMP".contains(letra)){this.valor=3;}
        else if ("HFVY".contains(letra)){this.valor=4;}
        else if ("CHQ".contains(letra)){this.valor=5;}
        else if ("JLLÑRRX".contains(letra)){this.valor=8;}
        else if (letra.equals("Z")){this.valor=10;}
        else{this.valor=0;}
        this.posx=posx;
        this.posy=posy;
        this.letra=letra;
        this.setX(this.posx);
        this.setY(this.posy);
    }

}
