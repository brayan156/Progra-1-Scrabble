package sample;


import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Ficha extends ImageView {
    int posx,posy,value;
    private String letter;

    public Ficha(String letter,int posx, int posy) {
//        super(new Image("file:src/Media/"+letter+".JPG"));
//        System.out.println("si entra a clase ficha");
        if ("AEOISNLRUT".contains(letter)){this.value=1;}
        else if ("DG".contains(letter)){this.value=2;}
        else if ("CBMP".contains(letter)){this.value=3;}
        else if ("HFVY".contains(letter)){this.value=4;}
        else if ("CHQ".contains(letter)){this.value=5;}
        else if ("JLLÑRRX".contains(letter)){this.value=8;}
        else if (letter.equals("Z")){this.value=10;}
        else{this.value=0;}
        this.posx=posx;
        this.posy=posy;
        this.letter = letter;
        this.setX(this.posx);
        this.setY(this.posy);
    }

	public int getValue() {
		return value;
	}

	public String getLetter() {
		return this.letter;
	}

}
