package Pruebas;
import java.io.*;

public class Main_pruebas {
	public static void main(String [] arg) throws FileNotFoundException {
		Generador_Diccionario ejem = new Generador_Diccionario();
		ejem.Generador_lista_Diccionario();//meto el txt en la lista
		System.out.print(ejem.Datos.buscarDato("mundo"));// me rotorna un true o false si la palabra esta o no
	}
}
