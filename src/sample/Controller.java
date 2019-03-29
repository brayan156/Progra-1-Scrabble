package sample;


import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Controller {
    @FXML private Pane juegopane;
    public static Logger log = LoggerFactory.getLogger(Controller.class);
    private Double orgSceneX;
    private Double orgSceneY;
    private Ficha matriz[][] = new Ficha[15][15];
    public TextField nombrefield;
    public Datos datos= new Datos();
    @FXML private Label labelturno;
    ObjectMapper objectMapper=new ObjectMapper();
    public TextField comprobacionfield= new TextField();

//    Menu de Inicio
    public TextField codigofield,nombref= new TextField();
    public Pane menupane= new Pane();



    public void espera()  {
            try {
                datos.setClient(nombrefield.getText());
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<String> future = executor.submit(new Turno(datos));
                if (future.isDone()) {
                    log.debug("se va a cambiar label");
                    labelturno.setText(future.get());
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
//        AnimationTimer timer= new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//            try {
//                datos.setClient(nombrefield.getText());
//                ExecutorService executor= Executors.newSingleThreadExecutor();
//                Future<String> future= executor.submit(new Turno(datos));
//                if (future.isDone()){
//                    labelturno.setText(future.get());
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            }
//        };
//        timer.start();

//        Platform.runLater(()->{
//            try {
//                labelturno.setText(future.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        });



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
                alert.setTitle("Server usado");
                alert.setContentText("el server esta siendo usado");
                alert.showAndWait();
            }

        }
    }

    public void unirse (){
        try {
            if (nombrefield.getText()=="" || codigofield.getText()==""){
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
                if (datosrecibidos.getRespueta().contains("partida_llena")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Server usado");
                    alert.setContentText("Partida llena");
                    alert.showAndWait();
                } else if (datosrecibidos.getRespueta().contains("Codigo_Erroneo")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Codigo Erroneo");
                    alert.setContentText("el codigo es incorrecto");
                    alert.showAndWait();
                } else if (datosrecibidos.getRespueta().contains("No hay partida")) {
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

    }


    public void clickon() {
        log.debug("si clickeaste compa");
        Ficha img = new Ficha(100,100,"Castillo1");
        img.setFitHeight(30);
        img.setFitWidth(30);
        img.setId("Imagen");
        String ID = img.getId();
        System.out.println(ID);
        juegopane.getChildren().add(img);
        img.setOnMousePressed(pressear);
        img.setOnMouseDragged(draggear);
        img.setOnMouseReleased(meter);
        System.out.println(img.letra);

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
                double x= 10-img.getFitWidth()/2;
                double y= 10-img.getFitHeight()/2;
                int contx=0;
                int conty=0;

                while (contx<15){
                    if (img.getX() >= x && img.getX() <=x+30 ){
                        while (conty<15){
                            if(img.getY() >= y && img.getY() <=y+30 ){
                                matriz[conty][contx] = img;
                                System.out.println(img.getY());
                                System.out.println(img.getX());
                                System.out.println(matriz[conty][contx].getId()+" guardada en "+contx+","+conty);
                                img.setY(y+img.getFitWidth()/2);
                                img.setX(x+img.getFitHeight()/2);
                                x=460;
                                y=460;
                                System.out.println(img.getImage().impl_getUrl());
                                System.out.println(img.letra);
                                System.out.println(img.valor);
                            }
                            else{
                                conty++;
                                y+=30;
                            }
                        }
                    }
                    else{
                        contx++;
                        x+=30;
                    }


                }
            };
}
