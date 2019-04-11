package sample; 
 
public class Cliente { 
    private String nombre; 
    private int puntaje; 
 
    public Cliente(String nombre) { 
        this.nombre = nombre; 
        puntaje=0; 
    } 
 
    public Cliente() { 
    } 
 
    public String getNombre() { 
        return nombre; 
    } 
 
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    } 
 
    public int getPuntaje() { 
        return puntaje; 
    } 
 
    public void setPuntaje(int puntaje) { 
        this.puntaje = puntaje; 
    } 
} 
