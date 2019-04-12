package Arboles_Diccionario;

public class mainDiccionario {

	public static void main(String[] args) {
		Generador_Diccionario ejem = new Generador_Diccionario();
		ejem.Generador_lista_Diccionario();//meto el txt en la lista		
		
		System.out.println("Palabra  "+"z_zo  "+ejem.Datos.buscarDato("z_zo"));
		System.out.println("Palabra  "+"bara_o  "+ejem.Datos.buscarDato("bara_o"));
		System.out.println("Palabra  "+"barati_o  "+ejem.Datos.buscarDato("barati_o"));
		System.out.println("Palabra  "+"_mpiedoso  "+ejem.Datos.buscarDato("_mpiedoso"));
		System.out.println("Palabra  "+"espa_ol  "+ejem.Datos.buscarDato("espa_ol"));
		System.out.println("Palabra  "+"soco_er  "+ejem.Datos.buscarDato("soco_er"));
		
	}

}
