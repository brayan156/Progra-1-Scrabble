package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

public class Turno implements Callable<String> {//este callable sirve para que pueda retornar algo cuando el hilo termina y asi se sepa cuando se recibió respuesta del server en el juego
    public static Logger log = LoggerFactory.getLogger(Turno.class);
    private boolean espera=true;
    private Datos datos;
    private ObjectMapper objectMapper = new ObjectMapper();
    public Turno(Datos datos) {
        this.datos = datos;
        datos.setAccion("buscar_turno");
    }

    @Override
    public String call() throws Exception {
        String respuesta="";
        try{
            while (espera) {
                Socket client = new Socket(InetAddress.getLocalHost(), 9500);
                log.debug("se conecto");
                DataOutputStream datosenvio= new DataOutputStream(client.getOutputStream());
                datosenvio.writeUTF(objectMapper.writeValueAsString(this.datos));
                log.debug("se envio objeto");
                DataInputStream datosentrada= new DataInputStream(client.getInputStream());
                log.debug("entrada se conecto");
                Datos datos=objectMapper.readValue(datosentrada.readUTF(), Datos.class);
                log.debug("se creo objeto");
                if (datos.getAccion().equals("Tu_turno")){
                    log.debug("se recibio turno");
                    respuesta="Tu_turno";
                    espera=false;
                    datosenvio.close();
                    client.close();
                }
                else if(datos.getAccion().equals("Actualizacion")){
                    log.debug("se debe actualizar");
                    respuesta="Actualizacion";
                    espera=false;
                    datosenvio.close();
                    client.close();
                }
                else{
                    log.debug("esperando");
                    datosenvio.close();
                    client.close();
                    Thread.sleep(2000);


                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        log.debug("se envio respuesta");
        return respuesta;
    }
}