package Arboles_Diccionario;

import java.io.*;

public class Generador_Diccionario {
	
	ArbolBinario<String> Datos = new ArbolBinario<String>();
	
	public void Generador_lista_Diccionario() {
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
		try {
			archivo = new File("C:\\Users\\Aldo Cambronero\\Desktop\\xd.txt");//cambiar segun donde se emplee
			fr= new FileReader(archivo);
			br =  new BufferedReader(fr);
			String linea;
			while ((linea=br.readLine())!=null) {
				linea= eliminador_basura_palabra(linea);//me elimina trash values, para que entre limpio
				Datos.insert(linea.toLowerCase());//para que se meta a la lista como en minuscula
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
		final String txt = "C:\\Users\\Aldo Cambronero\\Desktop\\xd.txt";//cambiar ruta para el txt de lemario
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			File file = new File(txt);//clase para que el txt no se borre 
		
			fw = new FileWriter(file.getAbsoluteFile(),true);
			bw = new BufferedWriter(fw);
			
			bw.write("\n"+palabra.toLowerCase());//se mete al text
			Datos.insert(palabra.toLowerCase());//se mete a la lista simple
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
}
