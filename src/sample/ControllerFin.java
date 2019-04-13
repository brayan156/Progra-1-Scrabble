package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerFin {


    @FXML public Label ganadorlabel=new Label();

    public void insertar_ganador(String Cliente){
        ganadorlabel.setText("Cliente Ganador: "+Cliente);
    }

}
