package sample;


import Arboles_Diccionario.Generador_Diccionario;
import Circular_Letras.Circular;
import Listas.ListaCliente;
import Listas.ListaFichas;
import Listas.ListaPalabras;
import Listas.Matrizstring;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    ObjectMapper objectMapper = new ObjectMapper();
    public ListaCliente clientes = new ListaCliente();
    String actcliente;
    String sigcliente, tmpcliente;//tmp cliente sirve como temporal para el recorrido 1 que es donde se actualiza los labels y matrices de todos
    int codigo=-1;
    int recorrido,cont, cantjugadores=-1;
    public static Logger log = LoggerFactory.getLogger(Server.class);
    private static Circular<Ficha> BancoFichas = new Circular<Ficha>();
    Generador_Diccionario diccionario= new Generador_Diccionario();
    Matrizstring matriz=new Matrizstring();
    int pases=0;//lleva la cuenta de cuantos pass se realizan para saber cuando terminar el juego



    public static void main(String[] args) throws IOException {
        Server server=new Server();
    }


    private void cambiar_cliente(){//cambia al siguiente cliente y ve cuando termina el recorrido 1
        if (tmpcliente.equals(actcliente)){
            recorrido=2;
        }
        actcliente= clientes.buscar(cont).getNombre();
        if (cont+1== clientes.getLargo()){
            cont=0;
            sigcliente= clientes.buscar(cont).getNombre();
        }
        else{
            cont++;
            sigcliente= clientes.buscar(cont).getNombre();
        }
    }




    @Override
    public void run() {
        try {
            ServerSocket server= new ServerSocket(9500);
            System.out.println("hola");

            while (true) {
                Socket misocket = server.accept();//se pone el server a la escucha
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
                    if (datos.getClient().equals(actcliente)){// se usa para lograr terminar la partida al pasar
                        System.out.println(recorrido);
                        if (pases==cantjugadores){
                            log.debug("cliente a actualizar");
                            datos.setAccion("Actualizacion");
                            DataOutputStream datosenvio = new DataOutputStream(misocket.getOutputStream());
                            log.debug("se creo abertura de datos");
                            datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                            log.debug("se logro enviar actualizar");
                            datosenvio.close();
                            misocket.close();
                        }
                        else if (recorrido==2) {//recorrido 2 es para cuando es el turno real de un jugador
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
                            log.debug("cliente a actualizar");//se usa en el recorrido uno para mandar a actualizar
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
                        datos.setAccion("Sigue_esperando");// si no es el turno mantiene en esperando a cada jugador
                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                        log.debug("se creo abertura de datos");
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        log.debug("se logro enviar datos");
                        datosenvio.close();
                        misocket.close();
                        log.debug("se logro terminar y en espera");
                    }
                }
                else if (datos.getAccion().equals("Actualizar")){//envia los datos de actualizacion
                    log.debug("se entro a actualizar");
                    datos.setRespueta("Cambiar_matriz");
                    if (pases==cantjugadores){datos.setRespueta("fin del juego");}
                    datos.setMatriz(this.matriz.getMatrizs());
                    datos.setListacliente(clientes);
                    DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                    log.debug("se creo abertura de datos");
                    System.out.println(objectMapper.writeValueAsString(datos));
                    datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                    log.debug("se envio matriz");
                    this.cambiar_cliente();
                    datosenvio.close();
                    misocket.close();

                }
                else if (datos.getAccion().equals("Pasar")) {// metodo simple para pasar al siguiente jugador cuando no se realiza jugadas
                    log.debug("pasar");
                    cambiar_cliente();
                    recorrido=2;
                    pases+=1;
                    misocket.close();
                }
                else if (datos.getAccion().equals("comprobar")){// se llama cuando se desea comprobar que todas las palabras son correctas y si asi es añade el puntaje obtenido al cliente
                    log.debug("entre a comprobar");
                    matriz.setMatrizs(datos.getMatriz());
                    ListaPalabras lpal= matriz.Verificar(datos.getListapares());
                    ListaPalabras lpalerroneas= this.diccionario.ListaIncorrecta_P(lpal);
                    if (lpalerroneas.getLargo()==0){
                        log.debug("se hizo una jugada correcta");
                        pases=0;
                        datos.setListafichas(BancoFichas.completarlista(datos.getListafichas().convertirfichas()));
                        ListaPalabras lpalcorrectas= this.diccionario.ListaCorrecta_P(lpal);
                        clientes.sumarpuntos(datos.getClient(), lpalcorrectas.sacarpuntaje());
                        datos.setRespueta("jugada_correcta");
                        System.out.println(objectMapper.writeValueAsString(clientes));
                        log.debug("se envia al cliente su jugada correcta");
                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        this.cambiar_cliente();
                        recorrido=1;
                        datosenvio.close();
                        misocket.close();
                    }
                    else{
                        log.debug("se hizo una jugada incorrecta");
                        datos.setRespueta("jugada_incorrecta");
                        datos.setListapalabras(lpalerroneas);
                        DataOutputStream datosenvio = new DataOutputStream(misocket.getOutputStream());
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        datosenvio.close();
                        misocket.close();
                    }
                }
                else if (datos.getAccion().equals("iniciar")){// para iniciar una partida tambien genera el codigo de sala
                    log.debug("se entro a iniciar");
                if (codigo==-1){
                    codigo = (int) Math.floor(Math.random()*1000000);
                    datos.setRespueta("codigo_enviado");
                    datos.setCodigo(codigo);
                    datos.setListafichas(BancoFichas.completarlista(new ListaFichas()));
                    cantjugadores= Integer.parseInt(datos.getJugadores().substring(datos.getJugadores().length()-1));
                    clientes.addLast(new Cliente(datos.getClient()));
                    System.out.println("cantidad de jugadores:"+cantjugadores);
                    System.out.println("cliente añadido:"+ clientes.buscar(clientes.getLargo()-1).getNombre());
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
                else if (datos.getAccion().equals("unirse")) {//se debe usar el codigo para unirse a la sala
                    log.debug("entra a unirse");
                    if (codigo==-1) {
                        datos.setRespueta("No hay partida");
                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        datosenvio.close();
                        misocket.close();

                    }
                    else if (cantjugadores== clientes.getLargo()){
                        datos.setRespueta("Partida_llena");
                        DataOutputStream datosenvio= new DataOutputStream(misocket.getOutputStream());
                        datosenvio.writeUTF(objectMapper.writeValueAsString(datos));
                        datosenvio.close();
                        misocket.close();
                    }
                    else {
                        if (datos.getCodigo() == codigo) {
                            datos.setRespueta("Codigo Correcto");
                            clientes.addLast(new Cliente(datos.getClient()));
                            datos.setListafichas(BancoFichas.completarlista(new ListaFichas()));
                            if (clientes.getLargo()==cantjugadores){
                                actcliente= clientes.buscar(0).getNombre();
                                System.out.println(actcliente);
                                sigcliente= clientes.buscar(1).getNombre();
                                System.out.println(sigcliente);
                                cont=1;
                                recorrido=2;
                            }
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
                else{log.debug("no hice nada");}


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Server() throws IOException {
        BancoFichas.getLetterSet();
        diccionario.Generador_lista_Diccionario();
        Thread hilo = new Thread(this);
        hilo.start();
    }


}
