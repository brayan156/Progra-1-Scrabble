package sample;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class UI_inicial extends Application{
	static public String nombre;
	public static void main(String[] args){
		// me inicia la aplicacion de la UI
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {	
		final StackPane layout1 = new StackPane();//metemos los elementos de la intefaz en un layout para meterlos de una al escenario
		Scene scene1=new Scene(layout1, 1000, 650);// el orden de ingreso, importa para la colocacion de los botones,labels...
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");
		
		final Image rutaIF = new Image(new FileInputStream("C:\\Users\\Aldo Cambronero\\Desktop\\fondo1.jpg"));
		ImageView imagen_Fondo = new ImageView(rutaIF);
		imagen_Fondo.setTranslateX(0);
		imagen_Fondo.setTranslateY(0);// lo referente a la imagen de fondoo
		imagen_Fondo.setFitHeight(650);
		imagen_Fondo.setFitWidth(1000);
		
		final Label crearP = new Label("Crear una nueva partida");
		crearP.setTextFill(Color.BLACK);
		crearP.setFont(new Font("Arial", 30));
		crearP.setTranslateX(-200);
		crearP.setTranslateY(-100);
		
		final Label unirseP = new Label("Unirse a una partida");
		unirseP.setFont(new Font("Arial", 30));
		unirseP.setTextFill(Color.BLACK);
		unirseP.setTranslateX(200);
		unirseP.setTranslateY(-100);
		
		final Image path = new Image(new FileInputStream("C:\\Users\\Aldo Cambronero\\Desktop\\Scrabble.png"));//OJO cambiar la ruta
		ImageView imageView = new ImageView(path);//cambiar la ruta de la imagen  
		imageView.setTranslateX(-250);
		imageView.setTranslateY(-270);
		
		final ChoiceBox Cjugadores = new ChoiceBox();
		Cjugadores.setTranslateX(-200);
		Cjugadores.setTranslateY(-50);//combobox para la contidad maxima de jugadores
		Cjugadores.getItems().add("Jugadores 2");
		Cjugadores.getItems().add("Jugadores 3");//cantidad de jugadores para la partida
		Cjugadores.getItems().add("Jugadores 4");
		Cjugadores.getSelectionModel().select(0);//los jugadores es el minimo para iniciar la partida
		
		final Button Bjugar = new Button("Jugar");
		Bjugar.setTranslateX(200);
		Bjugar.setTranslateY(0);
		Bjugar.setFont(new Font("Arial", 25));

		final Button Bcrear_P = new Button("Crear partida");
		Bcrear_P.setTranslateX(-200);
		Bcrear_P.setTranslateY(0);
		Bcrear_P.setFont(new Font("Arial", 25));
		
		final TextField caja_nombre = new TextField ();
		caja_nombre.setTranslateX(-200);
		caja_nombre.setTranslateY(200);
		caja_nombre.setMaxWidth(200);
		
		final Label Lpassword =new Label("Password:");
		Lpassword.setTranslateX(-360);
		Lpassword.setTranslateY(250);
		Lpassword.setTextFill(Color.BLACK);
		Lpassword.setFont(new Font("Arial", 25));
		
		final TextField password_textF = new TextField ();
		password_textF.setTranslateX(-200);
		password_textF.setTranslateY(250);
		password_textF.setMaxWidth(200);
		
		final Label txt_nombre =new Label("Nombre:");
		txt_nombre.setTranslateX(-350);
		txt_nombre.setTranslateY(200);
		txt_nombre.setTextFill(Color.BLACK);
		txt_nombre.setFont(new Font("Arial", 25));
		
		EventHandler<ActionEvent> eventoCrearP = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){// se mete para llamar el server con la cantidad de jugadores que pueden jugar una partida
				nombre=caja_nombre.getText();
            	Stage secondStage = new Stage();//se crea un evento que se asociara a un boton
            	Parent root;
				try {
					if (Filtro_palabra(nombre) && Filtro_palabra(password_textF.getText())) {
						root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
						secondStage.setTitle("Scrabble");
						secondStage.setScene(new Scene(root, 702, 522));//me crea una nuevo escenario y me carga todo lo del fxml
						secondStage.setResizable(false);
						secondStage.show();
						primaryStage.close();
						
					}else {
		            	JOptionPane.showMessageDialog(frame,"Nombre de usuario invalido");
					}
				} catch (IOException error1) {
					error1.printStackTrace();
				}
			}
		};
		Bcrear_P.setOnAction(eventoCrearP);//se establece la conexion entre el boton y el evento
		
		EventHandler<ActionEvent> eventoUnirseP = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e){
            	nombre=caja_nombre.getText();
            	Stage secondStage = new Stage();
            	Parent root;
				try {
					if (Filtro_palabra(nombre) && Filtro_palabra(password_textF.getText())) {
						root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
						secondStage.setTitle("Scrabble");// meter un filtro para la cantidad de jugadores que aguante el server
						secondStage.setScene(new Scene(root, 702, 522));//me crea una nuevo escenario y me carga todo lo del fxml
						secondStage.setResizable(false);
						secondStage.show();
		            	primaryStage.close();
					}else {
						JOptionPane.showMessageDialog(frame,"Nombre de usuario invalido");
					}
				} catch (IOException fallo) {
					fallo.printStackTrace();
				}
            }
        };
        Bjugar.setOnAction(eventoUnirseP);//le digo que metodo, esta asociada a Bjugar
		layout1.getChildren().addAll(imagen_Fondo,crearP,unirseP,imageView,Cjugadores,Bjugar,Bcrear_P,caja_nombre,txt_nombre,password_textF,Lpassword );
		
		primaryStage.setResizable(false);
		primaryStage.setTitle("Scrabble");//me crea un escenario para todo lo del UI inicial
		primaryStage.setScene(scene1);
		primaryStage.show();
		
	}
	public boolean Filtro_palabra(String dato) {
		if (dato.equals(null) || dato.equals("")) {//metodo para validar que haya ingresado una palabra, de nombre de usuario
			return false;
		} else {
			return true;
		}
	}
}
