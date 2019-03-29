package sample;


import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    ObjectMapper objectMapper = new ObjectMapper();
    private ImageView matriz[][] = new ImageView[3][3];
    public String clientes[]=new String[2];
    String matrizJson,clientantJson, clientJson;
    String sigcliente, tmpcliente;
    int codigo=-1;

    int recorrido,cont;
    public static Logger log = LoggerFactory.getLogger(Server.class);


    public static void main(String[] args) throws IOException {
        Server server=new Server();
    }


    private void cambiar_cliente(){
        sigcliente=clientes[cont];
        if (cont+1==clientes.length){cont=0;}
        else{cont++;}
    }



    @Override
    public void run() {
        try {
            ServerSocket server= new ServerSocket(9500);
            System.out.println("hola");

            while (true) {
                Socket misocket = server.accept();
                log.debug("se acepto cliente");
                DataInputStream flujo_entrada=new DataInputStream(misocket.getInputStream());
                String entrada= flujo_entrada.readUTF();
                System.out.println(entrada);
                log.debug("se recibio objeto");
                Datos datos=objectMapper.readValue(entrada, Datos.class);
                log.debug("se creo objeto");
                System.out.println(datos.getAccion().length());
                if (datos.getAccion().equals("buscar_turno")){
                    log.debug("se entro");
                    if (datos.getClient().equals(sigcliente)){
                        System.out.println("ha entrado el cliente correcto");
                        datos.setAccion("Tu_turno");
                        this.cambiar_cliente();
                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                        log.debug("se creo abertura de datos");
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        log.debug("se logro enviar datos");
                        datosenvio.close();
                        misocket.close();


                    }
                    else{
                        datos.setAccion("Sigue_esperando");
                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                        log.debug("se creo abertura de datos");
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        log.debug("se logro enviar datos");
                        datosenvio.close();
                        misocket.close();
                        log.debug("se logro terminar y en espera");
                    }
                }
                else if (datos.getAccion().equals("Pasar")) {
                    if (cont+1==clientes.length){cont=0;}
                    else{cont++;}
                    misocket.close();
                }
//                else if (datos.getAccion().equals("comprobar")){
//                    ListaPalabras lpalerroneas= this.comprobar(datos.getMatriz());
//                    if (lpalerroneas.largo==0){
//                        datos.setRespueta("jugada_correcta");
//                        datos.setListacliente(listacliente);
//                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
//                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
//                        this.cambiar_cliente();
//                        datosenvio.close();
//                        misocket.close();
//                    }
//                    else{
//                        datos.setRespueta("jugada_incorrecta");
//                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
//                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
//                        datosenvio.close();
//                        misocket.close();
//
//                    }
//                }
                else if (datos.getAccion().equals("iniciar")){
                if (codigo==-1){
                    codigo = (int) Math.floor(Math.random()*1000000);
                    datos.setRespueta("codigo_enviado");
                    DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                    datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                    datosenvio.close();
                    misocket.close();
                }
                else{
                    datos.setRespueta("server_usado");
                    DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                    datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                    datosenvio.close();
                    misocket.close();

                }

                }
                else if (datos.getAccion().equals("unirse")){
                    if (datos.getCodigo()==codigo);
                    datos.setRespueta("");
                }
                else if (datos.getAccion().equals("preguntar")){

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


//    {
//        try {
//            car = objectMapper.readValue(carJson, Car.class);
//            carJson = objectMapper.writeValueAsString(matriz);
//            objectMapper.readValue();
//
//            System.out.println(carJson);
//            System.out.println("car brand = " + matriz[1][1]);
//            System.out.println("car doors = " + matriz[1][2]);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public Server() throws IOException {
        clientes[0]="Paco";
        clientes[1]="Juan";
        sigcliente=clientes[0];
        tmpcliente=sigcliente;
        cont=1;
        String abc= "abcdf";
        boolean a="baacd".contains("aa");
        System.out.println(a);
        File imagen = new File("src/Media/Castillo2.JPG");
        System.out.println(imagen.exists());
        System.out.println("b".compareTo("a"));


        Thread hilo = new Thread(this);
        hilo.start();
    }

}
