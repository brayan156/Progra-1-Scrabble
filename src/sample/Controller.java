package sample;

import Listas.ListaCliente;
import Listas.ListaFichas;
import Listas.Listapares;
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
    public Datos datos= new Datos();
    @FXML private Label labelturno=new Label();
    ObjectMapper objectMapper=new ObjectMapper();
    public TextField comprobacionfield= new TextField();
    public ListaFichas listaFichas=new ListaFichas();
    public Listapares listapares=new Listapares();


    //BOX Estadisticas Puntajes
    @FXML public Label cliente1= new Label(),cliente2= new Label(),cliente3= new Label(),cliente4= new Label();
    @FXML public Label puntaje1= new Label(),puntaje2= new Label(),puntaje3= new Label(),puntaje4 = new Label();


//    //BOX Suministrar ATRIBUTOS
//    private List<Tuple> listaPosicionamiento;
//    private int cantidadfichas_HBox = 0;
//    private int currStr=0;
//    private static int dimension = 41;
//





    public void enviar_propiedades(String nombre) {
        System.out.println(nombre);
        // aqui llamar a la clase UI_inicial, para sacar el atributo del nombre del jugador
        datos.setClient(nombre);//aqui lo pone
    }// en el setText me pone el texto del TextField de la pantalla


//    //HBOX Method
//    public void suministrar() throws Exception {
//        try{
//            log.debug("Pidio una ficha mas. ");
//
//            //variable para almacenar la posicion para actualizar.
//            int posNueva = 0;
//            if (cantidadfichas_HBox==7) {
//                System.out.println("Pero ya tiene "+cantidadfichas_HBox+".");
//                return;}
//            //si son 0 fichas, llamar acomodar todo en no.
//            else if (this.cantidadfichas_HBox==0) {
//                this.acomodarlistaPosicionamiento();
//            }
//            //obtener posicion "no", mas proxima.
//            posNueva = this.listaPosicionamiento.get(this.indicePosVacia()).getY();
//
//            //convertir listaFichas (fichas) en listaPalabras (str).
//            ListaPalabras entLetra = this.listaFichas.convertirstrings();
//            Nodo<String> cabezal = entLetra.getHead();
//            if (cabezal!=null){
//                if (cabezal.equals(entLetra.getHead())) {
//                    return;
//                }
//            }
////////////
//            ///////// para probar la entrada de las fichas de servidor.
//            entLetra.addLast("A"); entLetra.addLast("S"); entLetra.addLast("D");
//            entLetra.addLast("T"); entLetra.addLast("R"); entLetra.addLast("L");
//            entLetra.addLast("C");
//            ///////// para probar la entrada de las fichas de servidor.
////////////
//            System.out.println(entLetra.getHead());
//            //crear ficha.
//            Ficha extra_ficha = new Ficha(posNueva,701, entLetra.getNodeinPos(this.currStr));
//            extra_ficha.crearimagen();
//            extra_ficha.setFitHeight(Controller.dimension);
//            extra_ficha.setFitWidth(Controller.dimension);
//            if (this.currStr==entLetra.getLargo()-1) {
//                this.currStr=0; //si ya es igual al tamano de la lista, se setea a 0.
//                System.out.println("Entra, currStr = 0");
//            }
//            //add to pane the extraficha.
//            juegopane.getChildren().add(extra_ficha);
//            //llamar a acciones.
//            extra_ficha.setOnMousePressed(pressear);
//            extra_ficha.setOnMouseDragged(draggear);
//            extra_ficha.setOnMouseReleased(quitarclick);
//            extra_ficha.setId(extra_ficha.getLetra());
//            this.cantidadfichas_HBox++;
//            this.currStr++;
//        }
//        catch(Exception e) {
//            System.out.println("Esperando jugadores para empezar.");
//        }
//    }
//    //BOX_LISTA.POSICION METODO
//    private void acomodarlistaPosicionamiento() {
//        this.listaPosicionamiento = Arrays.asList(
//                new Tuple("no", 12),
//                new Tuple("no", 55),
//                new Tuple("no", 98),
//                new Tuple("no", 141),
//                new Tuple("no", 184),
//                new Tuple("no", 227),
//                new Tuple("no", 270));
//    }
//    //acomoda el espacio que queda vacio en "no", despues de soltar el click, mediante el retorno de la posicion anterior
//    private void acomodarespacioPosicionamiento(int posx) {
//        for (int e=0; e < this.listaPosicionamiento.size(); e++) {
//            Tuple tupla = this.listaPosicionamiento.get(e);
//            if (tupla.getY() == posx) {
//                tupla.setX("no");
//                System.out.print("cambiado");
//                return;}}
//    }
//    //retorna el indice de la tupla con la posicion que no tiene una ficha asignada.
//    private Integer indicePosVacia() {
//        int index = 0;
//        for (int e=0; e < this.listaPosicionamiento.size(); e++) {
//            Tuple tupla = this.listaPosicionamiento.get(e);
//            if (tupla.getX() == "no") {
//                tupla.setX("si");
//                index = e;
//                break;}}
//        return index;
//    }







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
            //aqui va la actualizacion del puntaje.
            int indice = 0;
            int puntaje=0;
            ListaCliente clienteypuntaje = datosrecibidos.getListacliente();
            //ordenar por puntaje
            System.out.println(objectMapper.writeValueAsString(clienteypuntaje));
            while (indice != clienteypuntaje.getLargo()) {
                log.debug("procede a cambiar labels");
                if (indice==0) {
                    System.out.println("escribiendo label 1");
                    puntaje = clienteypuntaje.buscar(indice).getPuntaje();
                    log.debug("saco el puntaje");
                    this.cliente1.setText(clienteypuntaje.buscar(indice).getNombre());
                    this.puntaje1.setText(Integer.toString(puntaje));
                }
                else if(indice==1) {
                    System.out.println("escribiendo label 2");
                    puntaje = clienteypuntaje.buscar(indice).getPuntaje();
                    this.cliente2.setText(clienteypuntaje.buscar(indice).getNombre());
                    this.puntaje2.setText(Integer.toString(puntaje));
                }
                else if(indice==2) {
                    System.out.println("escribiendo label 3");
                    puntaje = clienteypuntaje.buscar(indice).getPuntaje();
                    this.cliente3.setText(clienteypuntaje.buscar(indice).getNombre());
                    this.puntaje3.setText(Integer.toString(puntaje));
                }
                else if(indice==3) {
                    System.out.println("escribiendo label 4");
                    puntaje = clienteypuntaje.buscar(indice).getPuntaje();
                    this.cliente4.setText(clienteypuntaje.buscar(indice).getNombre());
                    this.puntaje4.setText(Integer.toString(puntaje));
                } indice++;
            }
            labelturno.setText("On Hold");
            datosenvio.close();
            client.close();
            this.espera();
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
                client.close();
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
                    datos.setListapares(listapares);
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
                        listapares.limpiar();
                        labelturno.setText("On Hold");
                        datosenvio.close();
                        client.close();
                        this.espera();
                    } else {
                        datosenvio.close();
                        client.close();
                        log.debug("se ha insertado palabras incorrectas");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Palbras incorrectas");
                        String palabraserrones=datosrecibidos.getListapalabras().concatenarpalabras();
                        alert.setContentText("las siguientes palabras con incorrectas"+palabraserrones);
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
                        int y=(int) ((img.getY() - (77 - img.getFitHeight() / 2)) / img.getFitHeight());
                        int x=(int) ((img.getX() - (5 - img.getFitWidth() / 2)) / img.getFitWidth());
                        matriz.matriz[y][x] = null;
                        listapares.eliminar(y,x);
                        System.out.println("se elimino " +y+"," +x);
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
                        matriz.agregar(img,listapares,listaFichas);
                    else {
                        img.setY(img.getPosy());
                        img.setX(img.getPosx());
                        listaFichas.addFirst(img);
                    }
                }
            };


}
