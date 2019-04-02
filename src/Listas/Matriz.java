package Listas;

import javafx.scene.layout.Pane;
import sample.Ficha;

public class Matriz {
    public Ficha[][] matriz= new Ficha[15][15];
    public int width=30,height=30;
    double inix= 10-height/2;
    double iniy= 10-width/2;


    public void agregar(Ficha img){
        int contx=0, conty=0;
        double x= inix;
        double y= iniy;
        while (contx<15){
            if (img.getX() >= x && img.getX() <=x+30 ){
                while (conty<15){
                    if(img.getY() >= y && img.getY() <=y+30 ){
                        matriz[conty][contx] = img;
                        System.out.println(img.getY());
                        System.out.println(img.getX());
                        System.out.println(matriz[conty][contx].getId()+" guardada en "+contx+","+conty);
                        img.setY(y+img.getFitWidth()/2);
                        img.setX(x+img.getFitHeight()/2);
                        System.out.println(img.getImage().impl_getUrl());
                        System.out.println(img.letra);
                        System.out.println(img.valor);
                        break;
                    }
                    else{
                        conty++;
                        y+=30;

                    }
                }
                break;
            }
            else{
                contx++;
                x+=30;


            }


        }
    }
    public void agregar(String[][] matriz, Pane pane) {
        System.out.println("al menos entre aca");
        int contx = 0, conty = 0;
        while (contx < 15) {
            while (conty < 15) {
                if (matriz[conty][contx]!=null && this.matriz[conty][contx]==null) {
                    this.matriz[conty][contx] = new Ficha((int)inix+30*contx+height/2,(int)iniy+30*conty+width/2,matriz[conty][contx]);
                    this.matriz[conty][contx].setFitHeight(height);
                    this.matriz[conty][contx].setFitWidth(width);
                    System.out.println("estoy pintando");
                    pane.getChildren().add(this.matriz[conty][contx]);
                    conty++;
                } else {
                    conty++;

                }
            }
            contx++;
            conty=0;

        }
    }
    public String[][] convertir() {
        String[][] matrizstr = new String[15][15];
        int contx = 0, conty = 0;
        while (contx < 15) {
            while (conty < 15) {
                if (this.matriz[conty][contx] != null) {
                    matrizstr[conty][contx] = this.matriz[conty][contx].letra;
                    conty++;
                } else {
                    conty++;
                }
            }
            contx++;
            conty = 0;
        }
        return matrizstr;
    }
}
