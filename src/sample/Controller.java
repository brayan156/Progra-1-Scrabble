package sample;

import Listas.ListaFichas;
import Listas.Matriz;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.util.concurrent.*;

public class Controller {
    @FXML public  AnchorPane juegopane= new AnchorPane();
    public static Logger log = LoggerFactory.getLogger(Controller.class);
    private Double orgSceneX;
    private Double orgSceneY;
    public  Matriz matriz= new Matriz();
    @FXML public TextField nombrefield;
    public Datos datos= new Datos();
    @FXML private Label labelturno=new Label();
    ObjectMapper objectMapper=new ObjectMapper();
    public TextField comprobacionfield= new TextField();
    public ListaFichas listaFichas=new ListaFichas();





    public void poner_nombre(String nombre) {
        System.out.println(nombre);
        // aqui llamar a la clase UI_inicial, para sacar el atributo del nombre del jugador
        datos.setClient(nombre);//aqui lo pone
    }// en el setText me pone el texto del TextField de la pantalla

    
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
        Ficha extra_ficha = new Ficha(100,100,"B");
        extra_ficha.crearimagen();
        extra_ficha.setFitHeight(41);
        extra_ficha.setFitWidth(41);
        //add to HBox the extraficha.
        juegopane.getChildren().add(extra_ficha);
        //llamar a acciones
        extra_ficha.setOnMousePressed(pressear);
        extra_ficha.setOnMouseDragged(draggear);
        extra_ficha.setOnMouseReleased(quitarclick);
        extra_ficha.setId(extra_ficha.getLetra());
        System.out.println(extra_ficha.getLetra());
        cantidadfichas_HBox++;
        }
    }


    
    public void espera() {
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
            labelturno.setText("On Hold");
            datosenvio.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void pasar_turno(){

        try {
            if (!labelturno.getText().equals("On Hold")) {
                datos.setAccion("Pasar");
                this.matriz.reordenar(listaFichas);
                Socket client = new Socket(InetAddress.getLocalHost(), 9500);
                log.debug("se conecto");
                DataOutputStream datosenvio = new DataOutputStream(client.getOutputStream());
                datosenvio.writeUTF(objectMapper.writeValueAsString(this.datos));
                log.debug("se envio objeto");
                labelturno.setText("On Hold");
                this.espera();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void comprobar(){
        try {
            if (!labelturno.getText().equals("On Hold")) {
                if (listaFichas.getLargo() == 7) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Advertencia");
                    alert.setContentText("no se ha realizado ninguna jugada");
                    alert.showAndWait();
                } else {
                    datos.setAccion("comprobar");
                    datos.setMatriz(matriz.convertir());
                    datos.setListacliente(datos.getListacliente());
                    datos.setListafichas(listaFichas.convertirstrings());//se envia la lista de fichas que no han sido usadas
                    Socket client = new Socket(InetAddress.getLocalHost(), 9500);
                    log.debug("unirse");
                    DataOutputStream datosenvio = new DataOutputStream(client.getOutputStream());
                    datosenvio.writeUTF(objectMapper.writeValueAsString(this.datos));
                    DataInputStream datosentrada = new DataInputStream(client.getInputStream());
                    log.debug("entrada se conecto");
                    Datos datosrecibidos = objectMapper.readValue(datosentrada.readUTF(), Datos.class);
                    log.debug("se creo objeto");
                    if (datosrecibidos.getRespueta().equals("jugada_correcta")) {
                        datosrecibidos.setListacliente(datosrecibidos.getListacliente());
                        this.pintarfichas(datosrecibidos.getListafichas().convertirfichas());//se manda a pintar la lista de fichas que envia el server ya completa

                        //aqui va la actualizacion del puntaje
                        labelturno.setText("On Hold");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Palbras incorrectas");
//                    String palabraserrones=datosrecibidos.getListapalabras().obtenerstring();
//                    alert.setContentText("las siguientes palabras con incorrectas"+palabraserrones);
                        alert.showAndWait();
                    }
                }
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void preguntar(){

    }

    public void pintarfichas(ListaFichas fichas){ //recibe la lista que envia el server de las fichas que el cliente no uso
        int cont=0,conlf=this.listaFichas.getLargo()-1; // y las nuevas
        int posx=5,posy=707;
        while (conlf>=0){ //aqui se eliminan la imagenes viejas
            log.debug("voy a quitar fichas");
            juegopane.getChildren().remove(this.listaFichas.buscar(conlf));
            conlf--;
        }
        while (cont<7){ //aqui se pintan las imagenes nuevas
            log.debug("voy a pintar ficha");
            Ficha fichatmp= fichas.buscar(cont);
            fichatmp.crearimagen();
            fichatmp.setFitHeight(41);
            fichatmp.setFitWidth(41);
            fichatmp.setPosx(posx);
            fichatmp.setPosy(posy);
            fichatmp.setOnMousePressed(pressear);
            fichatmp.setOnMouseDragged(draggear);
            fichatmp.setOnMouseReleased(quitarclick);
            juegopane.getChildren().add(fichatmp);
            posx+=41;
            cont++;
        }
        this.listaFichas=fichas;
    }

    public void clickon() {
        log.debug("si clickeaste compa");
        Ficha img = new Ficha(100,480,"Castillo1");
        img.crearimagen();
        img.setFitHeight(30);
        img.setFitWidth(30);
        img.setId("Imagen");
        String ID = img.getId();
        System.out.println(ID);
        this.juegopane.getChildren().add(img);
        img.setOnMousePressed(pressear);
        img.setOnMouseDragged(draggear);
        img.setOnMouseReleased(quitarclick);
        System.out.println(img.getLetra());
    }


    
    

    EventHandler<MouseEvent> pressear =
            t -> {
                if (!labelturno.getText().equals("On Hold")) {
                    Ficha img = (Ficha) (t.getSource());
                    if (img.getX() >= 5 - img.getFitWidth() / 2 && img.getX() <= 5 - img.getFitWidth() / 2 + 15 * img.getFitWidth() && img.getY() >= 77 - img.getFitHeight() / 2 && img.getY() <= 77 - img.getFitHeight() / 2 + 15 * img.getFitHeight()) {
                        matriz.matriz[(int) ((img.getY() - (77 - img.getFitHeight() / 2)) / img.getFitHeight())][(int) ((img.getX() - (5 - img.getFitWidth() / 2)) / img.getFitWidth())] = null;
                        System.out.println((int) ((img.getY() - (77 - img.getFitHeight() / 2)) / img.getFitHeight()) + "," + (int) ((img.getX() - (5 - img.getFitWidth() / 2)) / img.getFitWidth()));
                    } else if (img.getX() == img.getPosx() && img.getY() == img.getPosy()) {
                        listaFichas.eliminar(img.getPosx());
                    }
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                }
            };

    EventHandler<MouseEvent> draggear =
            t -> {
                if (!labelturno.getText().equals("On Hold")) {
                    Ficha img = (Ficha) (t.getSource());
                    double offsetX = orgSceneX - t.getSceneX();
                    double offsetY = orgSceneY - t.getSceneY();
                    double newTranslateX = orgSceneX - offsetX - img.getFitWidth() / 2;
                    double newTranslateY = orgSceneY - offsetY - img.getFitHeight() / 2;
                    img.setX(newTranslateX);
                    img.setY(newTranslateY);
                }
            };
    EventHandler<MouseEvent> quitarclick =
            t -> {
                if (!labelturno.getText().equals("On Hold")) {
                    Ficha img = (Ficha) (t.getSource());
                    if (img.getX() >= 5 - img.getFitWidth() / 2 && img.getX() <= 5 - img.getFitWidth() / 2 + 15 * img.getFitWidth() && img.getY() >= 77 - img.getFitHeight() / 2 && img.getY() <= 77 - img.getFitHeight() / 2 + 15 * img.getFitHeight())
                        matriz.agregar(img);
                    else {
                        img.setY(img.getPosy());
                        img.setX(img.getPosx());
                        listaFichas.addLast(img);
                    }
                }
            };


}
