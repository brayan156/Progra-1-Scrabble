package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Scrabble");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    public static void main(String[] args) {
    	launch(args);

    }
    

    public void renovar_fichas_en_pantalla(ListaFichas fichas){ //recibe la lista que envia el server de las fichas que el cliente no uso
        elminar_fichas_en_pantalla();
        pintar_nuevas_fichas_en_pantalla(fichas,14,701);
        this.listaFichas=fichas;
    }

    public void elminar_fichas_en_pantalla() {
        int largo_lista_fichas_en_pantalla = this.listaFichas.getLargo();
        for (int i = 0; i < largo_lista_fichas_en_pantalla; i++) {//aqui se eliminan la imagenes viejas
            juegopane.getChildren().remove(this.listaFichas.buscar(i));
        }
    }

    public void pintar_nuevas_fichas_en_pantalla(ListaFichas listaNuevasFichas,int pocisionRelativaX, int pocisionRelativaY) {
        int cantidadFichas= listaNuevasFichas.getLargo();
        distancia_entre_fichas=41;
        int posicionX= pocisionRelativaX, pocisionY = pocisionRelativaY;
        for (int i=0; i<cantidadFichas; i++) {//aqui se pintan las imagenes nuevas
            Ficha fichatmp= listaNuevasFichas.buscar(i);
            configurar_parametros_ficha(fichatmp,posicionX,pocisionY);
            juegopane.getChildren().add(fichatmp);
            posicionX+=distancia_entre_fichas;
        }
    }

    public void configurar_parametros_ficha(Ficha ficha, int posicion_x, int posicion_y){
        double altura_ficha=41 , ancho_ficha=41;
        ficha.crearimagen();
        ficha.setFitHeight(altura_ficha);
        ficha.setFitWidth(ancho_ficha);
        ficha.setPosx(posicion_x);
        ficha.setPosy(posicion_y);
        ficha.setOnMousePressed(pressear);
        ficha.setOnMouseDragged(draggear);
        ficha.setOnMouseReleased(quitarclick);
    }   
    
 
    

}

