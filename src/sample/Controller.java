package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import Circular_Letras.Tuple;
import Listas.ListaFichas;
import Listas.ListaPalabras;
import Listas.Matriz;
import Listas.Nodo;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    
    //BOX Suministrar ATRIBUTOS 
    private List<Tuple> listaPosicionamiento; 
    private int cantidadfichas_HBox = 0; 
	private int currStr=0;
	private static int dimension = 41; 
 
	
/*Methods*/ 
    public void poner_nombre(String nombre) {
        System.out.println(nombre);
        // aqui llamar a la clase UI_inicial, para sacar el atributo del nombre del jugador
        datos.setClient(nombre);//aqui lo pone
    }// en el setText me pone el texto del TextField de la pantalla
    
	public void shuffle() throws Exception { 
		//pendiente 
	} 
	
    //HBOX Method 
    public void suministrar() throws Exception { 
    	try{ 
    		log.debug("Pidio una ficha mas. "); 
    	 
    	//variable para almacenar la posicion para actualizar. 
    	int posNueva = 0; 
    	if (cantidadfichas_HBox==7) { 
			System.out.println("Pero ya tiene "+cantidadfichas_HBox+"."); 
			return;} 
    	//si son 0 fichas, llamar acomodar todo en no. 
    	else if (this.cantidadfichas_HBox==0) { 
    		this.acomodarlistaPosicionamiento(); 
    	} 
    	//obtener posicion "no", mas proxima. 
    	posNueva = this.listaPosicionamiento.get(this.indicePosVacia()).getY(); 
    	 
		//convertir listaFichas (fichas) en listaPalabras (str). 
    	ListaPalabras entLetra = this.listaFichas.convertirstrings(); 
    	Nodo<String> cabezal = entLetra.getHead(); 
    	if (cabezal!=null){ 
    		if (cabezal.equals(entLetra.getHead())) { 
    			return; 
    		} 
    	} 
////////// 
    	///////// para probar la entrada de las fichas de servidor. 
    	entLetra.addLast("A"); entLetra.addLast("S"); entLetra.addLast("D"); 
    	entLetra.addLast("T"); entLetra.addLast("R"); entLetra.addLast("L"); 
    	entLetra.addLast("C"); 
    	///////// para probar la entrada de las fichas de servidor.    
////////// 
    	System.out.println(entLetra.getHead()); 
    	//crear ficha. 
		Ficha extra_ficha = new Ficha(posNueva,705, entLetra.getNodeinPos(this.currStr)); 
		extra_ficha.crearimagen(); 
		extra_ficha.setFitHeight(Controller.dimension); 
		extra_ficha.setFitWidth(Controller.dimension); 
		if (this.currStr==entLetra.getLargo()-1) { 
			this.currStr=0; //si ya es igual al tamano de la lista, se setea a 0. 
			System.out.println("Entra, currStr = 0"); 
		}		 
		//add to pane the extraficha. 
		juegopane.getChildren().add(extra_ficha); 
		//llamar a acciones. 
		extra_ficha.setOnMousePressed(pressear); 
		extra_ficha.setOnMouseDragged(draggear); 
		extra_ficha.setOnMouseReleased(quitarclick); 
		extra_ficha.setId(extra_ficha.getLetra());  
		this.cantidadfichas_HBox++; 
		this.currStr++; 
    	} 
    	catch(Exception e) { 
    		System.out.println("Esperando jugadores para empezar."); 
    		} 
    } 
    //BOX_LISTA.POSICION METODO 
    private void acomodarlistaPosicionamiento() { 
    	this.listaPosicionamiento = Arrays.asList( 
    			new Tuple("no", 5), 
        		new Tuple("no", 49),  
        		new Tuple("no", 93),  
        		new Tuple("no", 137), 
        		new Tuple("no", 181), 
        		new Tuple("no", 225),  
        		new Tuple("no", 269)); 
    } 
    //acomoda el espacio que queda vacio en "no", despues de soltar el click, mediante el retorno de la posicion anterior 
	private void acomodarespacioPosicionamiento(int posx) { 
		for (int e=0; e < this.listaPosicionamiento.size(); e++) { 
			Tuple tupla = this.listaPosicionamiento.get(e); 
			if (tupla.getY() == posx) { 
				tupla.setX("no"); 
				System.out.print("cambiado"); 
				return;}} 
	} 
    //retorna el indice de la tupla con la posicion que no tiene una ficha asignada. 
    private Integer indicePosVacia() { 
    	int index = 0; 
    	for (int e=0; e < this.listaPosicionamiento.size(); e++) { 
    		Tuple tupla = this.listaPosicionamiento.get(e); 
    		if (tupla.getX() == "no") { 
    			tupla.setX("si"); 
    			index = e; 
    			break;}} 
    	return index; 
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
                    log.debug("future terminó");
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
            labelturno.setText("si lo logré");
            datosenvio.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void pasar_turno(){

        try {
            datos.setAccion("Pasar");
            this.matriz.reordenar(listaFichas);
            Socket client = new Socket(InetAddress.getLocalHost(), 9500);
            log.debug("se conecto");
            DataOutputStream datosenvio= new DataOutputStream(client.getOutputStream());
            datosenvio.writeUTF(objectMapper.writeValueAsString(this.datos));
            log.debug("se envio objeto");
            labelturno.setText("Espera");
            this.espera();
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
            fichatmp.setFitHeight(Controller.dimension);
            fichatmp.setFitWidth(Controller.dimension);
            fichatmp.setPosx(posx);
            fichatmp.setPosy(posy);
            fichatmp.setOnMousePressed(pressear);
            fichatmp.setOnMouseDragged(draggear);
            fichatmp.setOnMouseReleased(quitarclick);
            juegopane.getChildren().add(fichatmp);
            posx+=Controller.dimension;
            cont++;
        }
        this.listaFichas=fichas;
    }

    public void clickon() {
        log.debug("si clickeaste compa");
        Ficha img = new Ficha(100,480,"Castillo1");
        img.crearimagen();
        img.setFitHeight(Controller.dimension);
        img.setFitWidth(Controller.dimension);
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
//                Ficha img= (Ficha) (t.getSource());
//                if (img.getX()>=5-img.getFitWidth()/2 && img.getX()<=5-img.getFitWidth()/2+15*img.getFitWidth() && img.getY()>=77-img.getFitHeight()/2 && img.getY()<=77-img.getFitHeight()/2+15*img.getFitHeight()){
//                    matriz.matriz[(int)((img.getY()-(77-img.getFitHeight()/2))/img.getFitHeight())][(int)((img.getX()-(5-img.getFitWidth()/2))/img.getFitWidth())]=null;
//                    System.out.println((int)((img.getY()-(77-img.getFitHeight()/2))/img.getFitHeight())+","+(int)((img.getX()-(5-img.getFitWidth()/2))/img.getFitWidth()));
//                }
//                else if (img.getX()==img.getPosx() && img.getY()==img.getPosy()){
//                    listaFichas.eliminar(img.getPosx());
//                }
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
            };

    EventHandler<MouseEvent> draggear =
            t -> {
                Ficha img= (Ficha) (t.getSource());
                double offsetX = orgSceneX - t.getSceneX();
                double offsetY = orgSceneY - t.getSceneY();
                double newTranslateX = orgSceneX - offsetX -img.getFitWidth()/2;
                double newTranslateY = orgSceneY - offsetY -img.getFitHeight()/2;
                img.setX(newTranslateX);
                img.setY(newTranslateY);

            };
    EventHandler<MouseEvent> quitarclick =
            t -> {
                Ficha img= (Ficha) (t.getSource());
                if (img.getX()>=5-img.getFitWidth()/2 && 
                		img.getX()<=5-img.getFitWidth()/2+15*img.getFitWidth() && 
                		img.getY()>=77-img.getFitHeight()/2 && 
                		img.getY()<=77-img.getFitHeight()/2+15*img.getFitHeight()) {
                matriz.agregar(img);
                this.cantidadfichas_HBox-=1; 
                this.acomodarespacioPosicionamiento(img.getPosx()); 
                }else{
                    img.setY(img.getPosy());
                    img.setX(img.getPosx());
                    listaFichas.addLast(img);
                }
            };


}
