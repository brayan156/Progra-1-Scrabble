
public class ArbolBinario<t extends Comparable<t>>{
	private NodoArbol<t> root=null;
	public int tamano=0;
	
	public boolean isEmpty() {
		return root==null;
	}
	public void insert(t dato) {
		this.root=this.insert(dato, this.root);
	}
	
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
	public boolean buscarDato(t dato) {
		return buscarDato_aux(dato,root);
	}
	private boolean buscarDato_aux(t dato, NodoArbol<t> temp) {
		if (temp.element.equals(dato)) {
			return true;
		}else if (dato.compareTo(temp.element)< 0) {
			if (temp.left==null) {
				return false;
			}else {
				return buscarDato_aux(dato,temp.left);
			}
		}else if (dato.compareTo(temp.element)> 0) {
			if (temp.right==null) {
				return false;
			}else {
				return buscarDato_aux(dato,temp.right);
			}
		}return false;
	}
}
