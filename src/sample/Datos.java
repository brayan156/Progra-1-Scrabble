package sample;


public class Datos {
    private String accion,client,respueta = "";
    private String matriz[][]=new String[15][15];
    private ListaCliente listacliente=new ListaCliente();
    private ListaPalabras listapalabras= new ListaPalabras();
    private int jugadores,codigo=-1;



    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getRespueta() {
        return respueta;
    }

    public void setRespueta(String respueta) {
        this.respueta = respueta;
    }

    public String[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(String[][] matriz) {
        this.matriz = matriz;
    }

    public ListaCliente getListacliente() {
        return listacliente;
    }

    public void setListacliente(ListaCliente listacliente) {
        this.listacliente = listacliente;
    }

    public ListaPalabras getListapalabras() {
        return listapalabras;
    }

    public void setListapalabras(ListaPalabras listapalabras) {
        this.listapalabras = listapalabras;
    }

    public int getJugadores() {
        return jugadores;
    }

    public void setJugadores(int jugadores) {
        this.jugadores = jugadores;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
