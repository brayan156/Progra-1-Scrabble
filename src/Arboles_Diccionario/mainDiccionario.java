package Arboles_Diccionario;

public class mainDiccionario {

	public static void main(String[] args) {
		ArbolBinario<String> Datos = new ArbolBinario<String>();
		Datos.insert("hola");
		Datos.insert("adios");
		Datos.insert("perro");
		Datos.insert("calle");
		Datos.insert("chao");
		Datos.insert("español");
		Datos.insert("llave");
		Datos.insert("carro");
		Datos.insert("ayer");
		
		System.out.println("Palabra  "+"ca_o  "+Datos.buscarDato("ca_o"));
		System.out.println("Palabra  "+"pe_o  "+Datos.buscarDato("pe_o"));
		System.out.println("Palabra  "+"_ave  "+Datos.buscarDato("_ave"));
		System.out.println("Palabra  "+"ca_e  "+Datos.buscarDato("ca_e"));
		System.out.println("Palabra  "+"espa_ol  "+Datos.buscarDato("espa_ol"));
		System.out.println("Palabra  "+"h_la  "+Datos.buscarDato("h_la"));
		System.out.println("Palabra  "+"_ao  "+Datos.buscarDato("_ao"));
		System.out.println("Palabra  "+"a_ios  "+Datos.buscarDato("a_ios"));
		System.out.println("Palabra  "+"ayer_  "+Datos.buscarDato("ayer_"));
		
	}

}
