package sample;
import java.io.*;

import java.net.InetAddress;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UI_inicial extends Application{
	static public String nombre;
	private TextField caja_nombre, caja_codigo;
	private ChoiceBox Cjugadores;
	ObjectMapper objectMapper=new ObjectMapper();
	public static Logger log = LoggerFactory.getLogger(UI_inicial.class);
	public static void main(String[] args){
		// me inicia la aplicacion de la UI
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {	
		final StackPane layout1 = new StackPane();//metemos los elementos de la intefaz en un layout para meterlos de una al escenario
		Scene scene1=new Scene(layout1, 1000, 650);// el orden de ingreso, importa para la colocacion de los botones,labels...
		
		final Image rutaIF = new Image("file:src/Media/fondo1.jpg");
		ImageView imagen_Fondo = new ImageView(rutaIF);
		imagen_Fondo.setTranslateX(0);
		imagen_Fondo.setTranslateY(0);
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
		
		final Image path = new Image("file:src/Media/scrabble1.png");//OJO cambiar la ruta
		ImageView imageView = new ImageView(path);//cambiar la ruta de la imagen  
		imageView.setTranslateX(0);
		imageView.setTranslateY(-215);
		
		Cjugadores = new ChoiceBox();
		Cjugadores.setTranslateX(-200);
		Cjugadores.setTranslateY(-50);
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
		
		caja_nombre = new TextField ();
		caja_nombre.setTranslateX(-200);
		caja_nombre.setTranslateY(200);
		caja_nombre.setMaxWidth(200);


		final Label Lpassword =new Label("Password:");
		Lpassword.setTranslateX(-360);
		Lpassword.setTranslateY(250);
		Lpassword.setTextFill(Color.BLACK);
		Lpassword.setFont(new Font("Arial", 25));

		caja_codigo = new TextField ();
		caja_codigo.setTranslateX(-200);
		caja_codigo.setTranslateY(250);
		caja_codigo.setMaxWidth(200);
		

		final Label txt_nombre =new Label("Nombre:");
		txt_nombre.setTranslateX(-350);
		txt_nombre.setTranslateY(200);
		txt_nombre.setTextFill(Color.BLACK);
		txt_nombre.setFont(new Font("Arial", 25));
		
		EventHandler<ActionEvent> eventop = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){// se mete para llamar el server con la cantidad de jugadores que pueden jugar una partida
				iniciar(primaryStage);
			}
		};
		Bcrear_P.setOnAction(eventop);
		EventHandler<ActionEvent> evento = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e){
            	unirse(primaryStage);
            }
        };
        Bjugar.setOnAction(evento);//le digo que metodo, esta asociada a Bjugar
		layout1.getChildren().addAll(imagen_Fondo,crearP,unirseP,imageView,Cjugadores,Bjugar,Bcrear_P,caja_nombre,txt_nombre, this.caja_codigo, Lpassword);
		
		primaryStage.setResizable(false);
		primaryStage.setTitle("Scrabble");//me crea un escenario para todo lo del UI inicial
		primaryStage.setScene(scene1);
		primaryStage.show();
	}
	public boolean Filtro_palabra(String dato) {
		if (dato!=null && dato!="") {//metodo para validar que haya ingresado una palabra, de nombre de usuario
			return true;
		}else {
			return false;
		}
	}

	public void unirse (Stage primaryStage){
		try {
			Datos datos=new Datos();
			datos.setAccion("unirse");
			if (caja_nombre.getText().equals("") || caja_codigo.getText().equals("")){
				Alert alert=new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Espacio en blanco");
				alert.setContentText("debe escrbir el nombre de jugador y el codigo para unirse a una partida");
				alert.showAndWait();

			}
			else {
				datos.setClient(caja_nombre.getText());
				datos.setCodigo(Integer.parseInt(caja_codigo.getText()));
				datos.setAccion("unirse");
				Socket client = new Socket(InetAddress.getLocalHost(), 9500);
				log.debug("unirse");
				DataOutputStream datosenvio = new DataOutputStream(client.getOutputStream());
				datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
				DataInputStream datosentrada = new DataInputStream(client.getInputStream());
				log.debug("entrada se conecto");
				Datos datosrecibidos = objectMapper.readValue(datosentrada.readUTF(), Datos.class);
				log.debug("se creo objeto");
				if (datosrecibidos.getRespueta().equals("Partida_llena")) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Server usado");
					alert.setContentText("Partida llena");
					alert.showAndWait();
				} else if (datosrecibidos.getRespueta().equals("Codigo Erroneo")) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Codigo Erroneo");
					alert.setContentText("el codigo es incorrecto");
					alert.showAndWait();
				} else if (datosrecibidos.getRespueta().equals("No hay partida")) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Server sin uso");
					alert.setContentText("No existe partida creada: inicie una o espere");
					alert.showAndWait();
				} else{
					nombre=caja_nombre.getText();
					Stage secondStage = new Stage();
					Parent root;
					FXMLLoader loader;
					loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
					root=loader.load();
					Controller controller= loader.getController();
					controller.enviar_propiedades(nombre);
					controller.pintarfichas(datosrecibidos.getListafichas().convertirfichas());
					controller.espera();
					secondStage.setTitle("Scrabble");
					secondStage.setScene(new Scene(root, 867, 754));//me crea una nuevo escenario y me carga todo lo del fxml
					secondStage.setResizable(false);
					secondStage.show();
					primaryStage.close();
				}
			}
		}
		catch (NumberFormatException e){
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Codigo Erroneo");
			alert.setContentText("el codigo esta compuesto de numeros");
			alert.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void iniciar(Stage primaryStage){
		Datos datos=new Datos();
		datos.setAccion("iniciar");
		if (caja_nombre.getText().equals("")){
			Alert alert=new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Espacio en blanco");
			alert.setContentText("debe escrbir un nombre de jugador para iniciar una partida");
			alert.showAndWait();
		}
		else{
			try {
				datos.setClient(caja_nombre.getText());
				datos.setJugadores(Cjugadores.getValue().toString());
				System.out.println(datos.getJugadores());
				Socket client = new Socket(InetAddress.getLocalHost(), 9500);
				log.debug("iniciar");
				DataOutputStream datosenvio= new DataOutputStream(client.getOutputStream());
				datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
				DataInputStream datosentrada= new DataInputStream(client.getInputStream());
				log.debug("entrada se conecto");
				Datos datosrecibidos=objectMapper.readValue(datosentrada.readUTF(), Datos.class);
				log.debug("se creo objeto");
				if (datosrecibidos.getRespueta().equals("server_usado")){
					Alert alert=new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Server usado");
					alert.setContentText("el server esta siendo usado");
					alert.showAndWait();
					datosenvio.close();
					client.close();
				}
				else{
					Alert alert=new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Codigo");
					alert.setContentText("El codigo de entrada es "+datosrecibidos.getCodigo());
					alert.showAndWait();
					datosenvio.close();
					client.close();
					nombre=caja_nombre.getText();
					Stage secondStage = new Stage();
					Parent root;
					FXMLLoader loader;
					loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
					root=loader.load();
					Controller controller= loader.getController();
					controller.enviar_propiedades(nombre);
					controller.pintarfichas(datosrecibidos.getListafichas().convertirfichas());
					controller.espera();
					secondStage.setTitle("Scrabble");
					secondStage.setScene(new Scene(root, 867, 754));//me crea una nuevo escenario y me carga todo lo del fxml
					secondStage.setResizable(false);
					secondStage.show();
					primaryStage.close();
				}

			} catch (IOException e) {
				Alert alert=new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error inesperado");
				alert.setContentText("ocurriÃ³ un error inesperado");
				alert.showAndWait();
			}

		}
	}
}
