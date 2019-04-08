package sample;


import Circular_Letras.Circular;
import Listas.ListaCliente;
import Listas.ListaFichas;
import Listas.ListaPalabras;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    ObjectMapper objectMapper = new ObjectMapper();
    private String matriz[][] = new String[15][15];
    public ListaCliente clientesd= new ListaCliente();
    public String clientes[]=new String[2];
    String actcliente;
    String sigcliente, tmpcliente;
    int codigo=-1;
    int recorrido,cont, cantjugadores=-1;
    public static Logger log = LoggerFactory.getLogger(Server.class);
    Circular<Ficha> BancoFichas = new Circular<Ficha>();



    public static void main(String[] args) throws IOException {
        Server server=new Server();
    }


    private void cambiar_cliente(){
        if (tmpcliente.equals(actcliente)){
            recorrido=2;
        }
        actcliente=clientes[cont];
        if (cont+1==clientes.length){
            cont=0;
            sigcliente=clientes[cont];
        }
        else{
            cont++;
            sigcliente=clientes[cont];
        }
    }
    public ListaPalabras completarlista (ListaFichas fichas){
        while (fichas.getLargo()<7){
            if (BancoFichas.getSize()!=0) {
                fichas.addFirst(BancoFichas.getRandomNode());
                System.out.println(fichas.getLargo());
                System.out.println(fichas.buscar(0).letra);
            }
            else{break;}
        }
        return fichas.convertirstrings();
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
                    if (datos.getClient().equals(actcliente)){
                        System.out.println(recorrido);
                        if (recorrido==2) {
                            System.out.println("ha entrado el cliente correcto");
                            datos.setAccion("Tu_turno");
                            tmpcliente=actcliente;
                            DataOutputStream datosenvio = new DataOutputStream(misocket.getOutputStream());
                            log.debug("se creo abertura de datos");
                            datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                            log.debug("se logro enviar datos");
                            datosenvio.close();
                            misocket.close();
                        }
                        else {
                            System.out.println("cliente a actualizar");
                            datos.setAccion("Actualizacion");
                            DataOutputStream datosenvio = new DataOutputStream(misocket.getOutputStream());
                            log.debug("se creo abertura de datos");
                            datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                            log.debug("se logro enviar actualizar");
                            datosenvio.close();
                            misocket.close();
                        }
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
                else if (datos.getAccion().equals("Actualizar")){
                    datos.setAccion("Cambiar_matriz");
                    datos.setMatriz(this.matriz);
                    datos.setListafichas(this.completarlista(datos.getListafichas().convertirfichas()));
                    DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                    log.debug("se creo abertura de datos");
                    System.out.println(objectMapper.writeValueAsString(datos));
                    datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                    log.debug("se envio matriz");
                    this.cambiar_cliente();
                    datosenvio.close();
                    misocket.close();

                }
                else if (datos.getAccion().equals("Pasar")) {
                    cambiar_cliente();
                    tmpcliente=actcliente;
                    misocket.close();
                }
//                else if (datos.getAccion().equals("comprobar")){
//                    ListaPalabras lpal= this.comprobar(datos.getMatriz());
//                    ListaPalabras lpalerroneas= this.sacarerroneas(lpal);
//                    if (lpalerroneas.getLargo()==0){
//                        ListaPalabras lpalcorrectas= this.sacarcorrectas(lpal);
//                        clientesd.sumarpuntos(datos.getClient(), this.calcularpuntaje(lpalcorrectas));
//                        datos.setRespueta("jugada_correcta");
//                        datos.setListacliente(clientesd);
//                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
//                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
//                        this.cambiar_cliente();
//                        recorrido=1;
//                        datosenvio.close();
//                        misocket.close();
//                    }
//                    else{
//                        datos.setRespueta("jugada_incorrecta");
//                        datos.setListapalabras(lpalerroneas);
//                        DataOutputStream datosenvio = new DataOutputStream(misocket.getOutputStream());
//                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
//                        datosenvio.close();
//                        misocket.close();
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
                else if (datos.getAccion().equals("unirse")) {
                    if (codigo==-1) {
                        datos.setRespueta("No hay partida");
                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        datosenvio.close();
                        misocket.close();

                    }
                    else if (cantjugadores==clientesd.getLargo()){
                        datos.setRespueta("Partida_llena");
                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        datosenvio.close();
                        misocket.close();
                    }
                    else {
                        if (datos.getCodigo() == codigo) {
                            datos.setRespueta("Codigo Correcto");
                            DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                            datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                            datosenvio.close();
                            misocket.close();
                        } else {
                            datos.setRespueta("Codigo Erroneo");
                            DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                            datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                            datosenvio.close();
                            misocket.close();
                        }
                    }
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
        actcliente=clientes[0];
        tmpcliente=actcliente;
        sigcliente=clientes[1];
        cont=1;
        recorrido=1;
        matriz[13][12]="Castillo2";
        matriz[1][1]="Castillo2";
        BancoFichas.getLetterSet();
        File imagen = new File("src/Media/Castillo2.JPG");
        Thread hilo = new Thread(this);
        hilo.start();
    }

}
