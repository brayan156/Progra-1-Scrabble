package sample;


import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Ficha extends ImageView {
     public int posx,posy,valor;
     public String letra;

    public Ficha(int posx, int posy, String string) {
//        super(new Image("file:src/Media/"+letra+".JPG"));
//        System.out.println("si entra a clase ficha");
        if ("AEOISNLRUT".contains(string)){this.value=1;}
        else if ("DG".contains(string)){this.value=2;}
        else if ("CBMP".contains(string)){this.value=3;}
        else if ("HFVY".contains(string)){this.value=4;}
        else if ("CHQ".contains(string)){this.value=5;}
        else if ("JLL�RRX".contains(string)){this.value=8;}
        else if (string.equals("Z")){this.value=10;}
        else{this.value=0;}
        this.posx=posx;
        this.posy=posy;
        this.letra = string;
        this.setX(this.posx);
        this.setY(this.posy);
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }
}
