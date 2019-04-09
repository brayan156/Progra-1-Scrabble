package Listas;

import javafx.scene.layout.AnchorPane;
import sample.Ficha;

public class Matriz {
    public Ficha[][] matriz= new Ficha[15][15];
    public int width=41,height=41;
    double inix= 5-height/2;
    double iniy= 77-width/2;


    public void agregar(Ficha img){
        int contx=0, conty=0;
        double x= inix;
        double y= iniy;
        while (contx<15){
            if (img.getX() >= x && img.getX() <=x+41 ){
                while (conty<15){
                    if(img.getY() >= y && img.getY() <=y+41 && (matriz[conty][contx] ==null || matriz[conty][contx]==img)){
                        matriz[conty][contx] = img;
                        System.out.println(img.getY());
                        System.out.println(img.getX());
                        System.out.println(matriz[conty][contx].getId()+" guardada en "+conty+","+contx);
                        img.setY(y+img.getFitWidth()/2);
                        img.setX(x+img.getFitHeight()/2);
                        System.out.println(img.getImage().impl_getUrl());
                        System.out.println(img.getLetra());
                        System.out.println(img.getValor());
                        break;
                    }
                    else if(img.getY() >= y && img.getY() <=y+41 && matriz[conty][contx] !=null){
                        img.setY(img.getPosy());
                        img.setX(img.getPosx());
                        break;
                    }
                    else{
                        conty++;
                        y+=41;

                    }
                }
                break;
            }
            else{
                contx++;
                x+=41;
            }
        }
    }
    public void agregar(String[][] matriz, AnchorPane pane) {
        System.out.println("al menos entre aca");
        int contx = 0, conty = 0;
        while (contx < 15) {
            while (conty < 15) {
                if (matriz[conty][contx]!=null && this.matriz[conty][contx]==null) {
                    this.matriz[conty][contx] = new Ficha((int)inix+41*contx+height/2,(int)iniy+41*conty+width/2,matriz[conty][contx]);
                    this.matriz[conty][contx].setFitHeight(height);
                    this.matriz[conty][contx].setFitWidth(width);
                    this.matriz[conty][contx].crearimagen();
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
                    matrizstr[conty][contx] = this.matriz[conty][contx].getLetra();
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
