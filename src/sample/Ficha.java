package sample;


import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Ficha extends ImageView {
    int posx,posy,value;
    private String letter;

    public Ficha(String string,int posx, int posy) {
//        super(new Image("file:src/Media/"+letter+".JPG"));
//        System.out.println("si entra a clase ficha");
        if ("AEOISNLRUT".contains(string)){this.value=1;}
        else if ("DG".contains(string)){this.value=2;}
        else if ("CBMP".contains(string)){this.value=3;}
        else if ("HFVY".contains(string)){this.value=4;}
        else if ("CHQ".contains(string)){this.value=5;}
        else if ("JLLÑRRX".contains(string)){this.value=8;}
        else if (string.equals("Z")){this.value=10;}
        else{this.value=0;}
        this.posx=posx;
        this.posy=posy;
        this.letter = string;
        this.setX(this.posx);
        this.setY(this.posy);
    }

	public int getValue() {
		return value;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getLetter() {
		return this.letter;
	}

}
