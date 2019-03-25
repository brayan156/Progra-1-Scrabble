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
    int recorrido,cont;
    public static Logger log = LoggerFactory.getLogger(Server.class);


    public static void main(String[] args) throws IOException {
        Server server=new Server();
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
                String str="buscar_turno";
                System.out.println(str.length());
                if (datos.getAccion().equals(str)){
                    log.debug("se entro");
                    DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                    log.debug("se creo abertura de datos");
                    if (datos.getClient().equals(sigcliente)){
                        System.out.println("ha entrado el cliente correcto");
                        datos.setAccion("Tu_turno");
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        log.debug("se logro terminar con exito");
                        datosenvio.close();
                        misocket.close();
                        sigcliente=clientes[cont];
                        if (cont+1==clientes.length){cont=0;}
                        else{cont++;}

                    }
                    else{
                        datos.setAccion("Sigue_esperando");
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        datosenvio.close();
                        misocket.close();
                        log.debug("se logro terminar con fallo");
                    }
                }
                else {System.out.println("no llegue");}


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

        Thread hilo = new Thread(this);
        hilo.start();
    }

}
