package ;
public class NodoArbol<t extends Comparable<t>>{
	public NodoArbol<t> left;
	public NodoArbol<t> right;
	public t element;
	
	public NodoArbol(t element){
		this.element=element;
		left=right=null;
	}
}
