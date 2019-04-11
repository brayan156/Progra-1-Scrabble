package Arboles_Diccionario;
import Listas.ListaPalabras;

import java.io.*;

public class Generador_Diccionario {
	
	public ArbolBinario<String> Datos = new ArbolBinario<String>();
	
	public void Generador_lista_Diccionario() {
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
		try {
			System.out.println("voy a crear diccionario");
			archivo = new File("src\\Arboles_Diccionario\\lemario.txt");//cambiar segun donde se emplee
			fr= new FileReader(archivo);
			br =  new BufferedReader(fr);
			String linea;

			while ((linea=br.readLine())!=null) {
				linea= eliminador_basura_palabra(linea);//me elimina trash values, para que entre limpio
				System.out.println(linea);
				Datos.insert(linea);//para que se meta a la lista como en minuscula
			}//En mi compu dura como 2 mint
		}catch(Exception e){
	         e.printStackTrace();
	    }finally{
	         try{                    
	            if( null != fr ){//para que el txt siempre se cierre   
	               fr.close();     
	            }                  
	         }catch (Exception num2){ 
	            num2.printStackTrace();
	         }
	      }
	}
	
	public String eliminador_basura_palabra(String str) {
	    if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
	        str = str.substring(0, str.length() - 1);
	    }
	    return str;
	}
	public void metertxt(String palabra) {
		final String txt = "src\\Arboles_Diccionario\\lemario.txt";//cambiar ruta para el txt de lemario
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			File file = new File(txt);//clase para que el txt no se borre 
		
			fw = new FileWriter(file.getAbsoluteFile(),true);
			bw = new BufferedWriter(fw);
			
			bw.write("\n"+palabra.toLowerCase());//se mete al text
			Datos.insert(palabra);//se mete a la lista simple
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)//para que el txt siempre se cierre
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	public ListaPalabras ListaIncorrecta_P(ListaPalabras datosL) {
		int cont=0;
		ListaPalabras palabras_incorrectas = new ListaPalabras();//la lista con las palabras incorrectas

		while(datosL.getLargo()>cont) {//cambie por su variable del largo de la lista
			String contenedor = datosL.buscar(cont);
			//el metodo para cambiar _ por cada letra iria justo aqui
			//tiene que encontrar si existe Datos.buscarDato(contenedor)=true y si no agrega palabras_incorrectas.addFirst(contenedor);
			if (!Datos.buscarDato(contenedor)){//filtro para las palabras que no estan en el arbol
				palabras_incorrectas.addFirst(contenedor);
				System.out.println(palabras_incorrectas.buscar(0));
			}
			cont++;
		}return palabras_incorrectas;
	}
	public ListaPalabras ListaCorrecta_P(ListaPalabras datosL) {
		int cont=0;
		ListaPalabras palabras_correctas = new ListaPalabras();//la lista con las palabras incorrectas

		while(datosL.getLargo()>cont) {//cambie por su variable del largo de la lista
			String contenedor = datosL.buscar(cont);
			if (Datos.buscarDato(contenedor)){//filtro para las palabras que no estan en el arbol
				palabras_correctas.addFirst(contenedor);
				System.out.println(palabras_correctas.buscar(0));
			}cont++;
		}return palabras_correctas;
	}
}
