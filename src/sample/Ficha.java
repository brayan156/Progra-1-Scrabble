package sample;


import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Ficha extends ImageView {
     private int posx,posy,valor;
     private String letra,url;

    public Ficha(int posx, int posy, String letra) {
        super();
        this.setUrl("file:src/Media/"+letra+".JPG");
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
    public int getPosx() { return posx; }

    public void setPosx(int posx) {
        this.posx = posx;
        this.setX(this.posx);
    }

    public int getPosy() {
        return posy;

    }

    public void setPosy(int posy) {
        this.posy = posy;
        this.setY(this.posy);
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

    public void crearimagen(){
        this.setImage(new Image("file:src/Media/"+letra+".JPG"));
    }
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
