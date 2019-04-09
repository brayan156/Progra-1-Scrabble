package sample;

import sample.Server;
import Listas.ListaFichas;
import Listas.ListaPalabras;
import Listas.Matriz;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.impl.PropertyValue;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class Controller {
    @FXML public  AnchorPane juegopane= new AnchorPane();
    public static Logger log = LoggerFactory.getLogger(Controller.class);
    private Double orgSceneX;
    private Double orgSceneY;
    public  Matriz matriz= new Matriz();
    public TextField nombrefield;
    public Datos datos= new Datos();
    @FXML private Label labelturno=new Label();
    ObjectMapper objectMapper=new ObjectMapper();
    public TextField comprobacionfield= new TextField();
    public ListaFichas listaFichas=new ListaFichas();
    
   

//    Menu de Inicio
    public TextField codigofield,nombref= new TextField();
    public ComboBox<Integer> jugadoresbox= new ComboBox<Integer>();
    public AnchorPane menupane= new AnchorPane();
    
    
    //HBOX ATRIBUTOS
    @FXML private HBox field_fichas = new HBox();
    private int cantidadfichas_HBox = 0;
    
    //HBOX Method
    public void shuffle() {
    	log.debug("Pidió una ficha más. ");
        if (cantidadfichas_HBox==7) {System.out.println("Pero ya tiene "+cantidadfichas_HBox+".");return;}
        else {
//        Ficha extra_ficha = Server.getBancoFichas().getRandomNode();
//      img.setPosx(0); img.setPosy(0);
        	
        //crear ficha.
        Ficha extra_ficha = new Ficha(0,0,"B");
        extra_ficha.crearimagen();
        extra_ficha.setFitHeight(41);
        extra_ficha.setFitWidth(41);
        //add to HBox the extraficha.
        field_fichas.getChildren().add(extra_ficha);
        //llamar a acciones
        extra_ficha.setOnMousePressed(pressear);
        extra_ficha.setOnMouseDragged(draggear);
        extra_ficha.setOnMouseReleased(meter);
        extra_ficha.setId(extra_ficha.getLetra()); 
        System.out.print(Server.getBancoFichas().getRandomNode().getLetra());
        System.out.println(extra_ficha.getLetra());
        cantidadfichas_HBox++;
        }
    }


    
    public void espera() {
        datos.setClient(nombrefield.getText());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Turno(datos));
        log.debug("aqui llega");
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                try {
                    if (future.isDone()) {
                    String resp= future.get();
                    log.debug("future terminÃ³");
                    if (resp.contains("Tu_turno")){
                        log.debug("turno del cliente");
                        log.debug("se va a cambiar label");
                        labelturno.setText(resp);
                    }
                    else{actualizar();
                    }
                    log.debug("se para animation timer");
                    stop();
                }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }

    public void actualizar(){
        try {
            log.debug("se llego a actualizar");
            datos.setAccion("Actualizar");
//            listaFichas.addFirst(new Ficha(0,0,"A"));
            datos.setListafichas(listaFichas.convertirstrings());
            Socket client = new Socket(InetAddress.getLocalHost(), 9500);
            log.debug("se conecto");
            DataOutputStream datosenvio = new DataOutputStream(client.getOutputStream());
            datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
            log.debug("se envio objeto");
            DataInputStream datosentrada = new DataInputStream(client.getInputStream());
            log.debug("entrada se conecto");
            Datos datosrecibidos = objectMapper.readValue(datosentrada.readUTF(), Datos.class);
            log.debug("se creo objeto");
            matriz.agregar(datosrecibidos.getMatriz(), juegopane);
            System.out.println(objectMapper.writeValueAsString(datosrecibidos));
            this.pintarfichas(datosrecibidos.getListafichas().convertirfichas());
            labelturno.setText("si lo logrÃ©");
            datosenvio.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void pasar_turno(){

        try {
            datos.setAccion("Pasar");
            Socket client = new Socket(InetAddress.getLocalHost(), 9500);
            log.debug("se conecto");
            DataOutputStream datosenvio= new DataOutputStream(client.getOutputStream());
            datosenvio.writeUTF(objectMapper.writeValueAsString(this.datos));
            log.debug("se envio objeto");
            this.espera();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void iniciar(){
        if (nombref.getText().equals("")){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacio en blanco");
            alert.setContentText("debe escrbir un nombre de jugador para iniciar una partida");
            alert.showAndWait();
        }
        else{
            try {
                this.datos.setClient(nombref.getText());
                this.datos.setJugadores(jugadoresbox.getValue());
                Socket client = new Socket(InetAddress.getLocalHost(), 9500);
                log.debug("iniciar");
                DataOutputStream datosenvio= new DataOutputStream(client.getOutputStream());
                datosenvio.writeUTF(objectMapper.writeValueAsString(this.datos));
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
                    alert.setContentText("El codigo de entrada es"+datosrecibidos.getCodigo());
                    alert.showAndWait();
                    datosenvio.close();
                    client.close();
                }

            } catch (IOException e) {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error inesperado");
                alert.setContentText("ocurriÃ³ un error inesperado");
                alert.showAndWait();
            }

        }
    }

    public void unirse (){
        try {
            if (nombrefield.getText().equals("") || codigofield.getText().equals("")){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Espacio en blanco");
                alert.setContentText("debe escrbir el nombre de jugador y el codigo para unirse a una partida");
                alert.showAndWait();
            }
            else {
                datos.setClient(nombrefield.getText());
                datos.setCodigo(Integer.parseInt(codigofield.getText()));
                datos.setAccion("unirse");
                Socket client = new Socket(InetAddress.getLocalHost(), 9500);
                log.debug("unirse");
                DataOutputStream datosenvio = new DataOutputStream(client.getOutputStream());
                datosenvio.writeUTF(objectMapper.writeValueAsString(this.datos));
                DataInputStream datosentrada = new DataInputStream(client.getInputStream());
                log.debug("entrada se conecto");
                Datos datosrecibidos = objectMapper.readValue(datosentrada.readUTF(), Datos.class);
                log.debug("se creo objeto");
                if (datosrecibidos.getRespueta().equals("Partida_llena")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Server usado");
                    alert.setContentText("Partida llena");
                    alert.showAndWait();
                } else if (datosrecibidos.getRespueta().equals("Codigo_Erroneo")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Codigo Erroneo");
                    alert.setContentText("el codigo es incorrecto");
                    alert.showAndWait();
                } else if (datosrecibidos.getRespueta().equals("No hay partida")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Server sin uso");
                    alert.setContentText("No existe partida creada: inicie una o espere");
                    alert.showAndWait();
                } else{}
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
    public void comprobar(){
        try {
            if (listaFichas.getLargo()==7){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setContentText("no se ha realizado ninguna jugada");
                alert.showAndWait();
            }
            else {
                datos.setAccion("comprobar");
                datos.setMatriz(matriz.convertir());
                datos.setListacliente(datos.getListacliente());
                datos.setListafichas(listaFichas.convertirstrings());
                Socket client = null;
                client = new Socket(InetAddress.getLocalHost(), 9500);
                log.debug("unirse");
                DataOutputStream datosenvio = new DataOutputStream(client.getOutputStream());
                datosenvio.writeUTF(objectMapper.writeValueAsString(this.datos));
                DataInputStream datosentrada = new DataInputStream(client.getInputStream());
                log.debug("entrada se conecto");
                Datos datosrecibidos = objectMapper.readValue(datosentrada.readUTF(), Datos.class);
                log.debug("se creo objeto");
                if (datosrecibidos.getRespueta().equals("jugada_correcta")) {
                    datosrecibidos.setListacliente(datosrecibidos.getListacliente());
                    this.pintarfichas(datosrecibidos.getListafichas().convertirfichas());

                    //aqui va la actualizacion del puntaje
                    labelturno.setText("En espera");
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Palbras incorrectas");
//                    String palabraserrones=datosrecibidos.getListapalabras().obtenerstring();
//                    alert.setContentText("las siguientes palabras con incorrectas"+palabraserrones);
                    alert.showAndWait();
                }
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void preguntar(){

    }

    public void pintarfichas(ListaFichas fichas){
        int cont=0,conlf=this.listaFichas.getLargo()-1;
        int posx=100,posy=480;
        while (conlf>=0){
            log.debug("voy a quitar fichas");
            juegopane.getChildren().removeAll(this.listaFichas.buscar(conlf));
            conlf--;
        }
        while (cont<7){
            log.debug("voy a pintar ficha");
            Ficha fichatmp= fichas.buscar(cont);
            fichatmp.crearimagen();
            fichatmp.setFitHeight(41);
            fichatmp.setFitWidth(41);
            fichatmp.setPosx(posx);
            fichatmp.setPosy(posy);
            fichatmp.setX(posx);
            fichatmp.setY(posy);
            fichatmp.setOnMousePressed(pressear);
            fichatmp.setOnMouseDragged(draggear);
            fichatmp.setOnMouseReleased(meter);
            juegopane.getChildren().addAll(fichatmp);
            posx+=41;
            cont++;
        }
        this.listaFichas=fichas;
    }



    
    

    EventHandler<MouseEvent> pressear =
            t -> {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
            };

    EventHandler<MouseEvent> draggear =
            t -> {
                ImageView img= (ImageView)(t.getSource());
                double offsetX = orgSceneX - t.getSceneX();
                double offsetY = orgSceneY - t.getSceneY();
                double newTranslateX = orgSceneX - offsetX -img.getFitWidth()/2;
                double newTranslateY = orgSceneY - offsetY -img.getFitHeight()/2;
                img.setX(newTranslateX);
                img.setY(newTranslateY);

            };
    EventHandler<MouseEvent> meter =
            t -> {
                Ficha img= (Ficha) (t.getSource());
                matriz.agregar(img);
            };
}
