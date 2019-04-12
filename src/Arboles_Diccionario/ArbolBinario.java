package Arboles_Diccionario;

import java.util.Arrays;
import java.util.List;

import Circular_Letras.Tuple;

public class ArbolBinario<t extends Comparable<t>>{
	private NodoArbol<t> root=null;
	public int tamano=0;
//	private int indexComodin = 0;
	private List<Character> arrayLetras = null;
	private List<String> arrayStringLetras = null;
	
	public boolean isEmpty() {
		return root==null;
	}
	public void insert(t dato) {
		this.root=this.insert(dato, this.root);
	}
	//metodo para insertar datos en el Arbol Binario
	private NodoArbol<t> insert(t dato, NodoArbol<t> current){
		if (current==null) {
			tamano++;
			return new NodoArbol<t>(dato);
		}if (dato.compareTo(current.element)< 0) {
			current.left =insert(dato,current.left);
		}else if (dato.compareTo(current.element)> 0){
			current.right =insert(dato,current.right);
		}return current;
	}
	//Recorre el arbol de manera raiz,izquierda y derecha 
	public void preOrden() {
		preOrden(root);
	}
	private void preOrden(NodoArbol<t> temp){
		if (temp != null) {
			System.out.print(temp.element+"\n");
			preOrden(temp.left);
			preOrden(temp.right);
		}
	}
	//Recorrer el arbol de manera izquierda, raiz y derecha
	public void inOrden() {
		inOrden1(root);
	}
	private void inOrden1(NodoArbol<t> temp) {
		if (temp != null) {
			inOrden1(temp.left);
			System.out.print(temp.element+",");
			inOrden1(temp.right);
		}
	}
	//recorre el arbol de manera izquierda,derecha y raiz
	public void postOrden() {
		postOrden(root);
	}
	private void postOrden(NodoArbol<t> temp) {
		if (temp != null) {
			postOrden(temp.left);
			postOrden(temp.right);
			System.out.print(temp.element+"\n");
		}
	}
	// Metodo para buscar un dato en el arbol
	public boolean buscarDato(String dato) {
		if (this.isComodin(dato)) {return isComodin_aux(dato);}
		return buscarDato_aux(dato.toUpperCase(),root);
	}	
	
	private boolean buscarDato_aux(String dato, NodoArbol<t> temp) {
		if (temp.element.equals(dato)) {
			return true;
		}else if (dato.compareTo((String) temp.element)< 0) {
			if (temp.left==null) {
				return false;
			}else {
				return buscarDato_aux(dato,temp.left);
			}
		}else if (dato.compareTo((String) temp.element)> 0) {
			if (temp.right==null) {
				return false;
			}else {
				return buscarDato_aux(dato,temp.right);
			}
		}return false;
	}
	
	private boolean isComodin(String dato) {
		int index=0;
		while (index != ((String) dato).length()) {
			if (((String) dato).charAt(index) == '_') {
//				this.indexComodin = index;
				return true;}
			index++;
			continue;
		}return false;
	}
	
	private boolean isComodin_aux(String dato) {
		if (this.arrayLetras==null) this.setarraysLetras();
		for (int i=0; i < this.arrayLetras.size(); i++) { 
			String incognita = ((String) dato).replace('_', this.arrayLetras.get(i));
//			System.out.println("Palabra  "+incognita);
			if (this.buscarDato(incognita))
				return true;
		} for (int i=0; i < this.arrayStringLetras.size(); i++) {
			String incognita = ((String) dato).replace("_", this.arrayStringLetras.get(i));
//			System.out.println("Palabra  "+incognita);
			if (this.buscarDato(incognita))
				return true;
		}return false;
	}

	private void setarraysLetras() { 
    	this.arrayLetras = Arrays.asList(
    			'a','b','c','d','e','f','g',
    			'h','i','j','k','l','m','n','ñ',
    			'o','p','q','r','s','t','u',
    			'v','w','x','y','z');
    	this.arrayStringLetras = Arrays.asList(
    			"ch","ll","rr");
    } 
}
	
