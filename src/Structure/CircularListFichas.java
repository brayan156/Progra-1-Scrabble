package Structure;

import sample.Node;
import sample.Ficha;

public class CircularListFichas<T> {
	Node<T> head;
	private Node<T> last, current;
	private int length, random;
	
	//CONSTRUCTOR
	public CircularListFichas(){
		this.head = null;
		this.length = 0;
		this.random = 0;
	}
	
	//ELIMINAR NODO
	public Node<T> deleteNode(Node<T> item) {
		Node<T> tmp = current;
		this.current.next=current.next.next;
		this.current=tmp.next;
		tmp.next=null;
		return tmp;
	}
	     
    //ULTIMO VALOR
	public Node<T> getLast() {
		return last;
	}
	
	//TAMAÑO
	public int getLength() {
		return length;
	}
	
	//NUMERO RANDOM
	public int getRandomInt() {
		return (int) Math.floor(Math.random() * Math.floor(random));	
	}
	
	//INSERTAR AL FINAL
	public void insertionlast(T ficha){
	    Node<T> appending = new Node<T>(ficha);    
	    appending.setNext(head);
	    if(this.head == null){            
	        head = appending;
	        appending.setNext(head);
	        last = head;
	        current = head;            
	    }else{
	        last.setNext(appending);
	        last = appending;            
	    }
	    length++;
	    random=length;
	}
	
	//LISTA VACIA
	private boolean isEmpty() {
		return (head == null);
	}
	
	//OBTENER LETRA EN EL NODO.
	public String nodeLetter(Node<T> ficha_encapsulada) {
		return ((Ficha) ficha_encapsulada.getNode()).getLetter();
	}
	
	//FICHA RANDOM
	public Node<T> returnRandomNode(){
		if (this.current == null) {
			return null;
		}else {
		int contador = this.getRandomInt();
		while (contador  != 0) {
			current = current.next;
		}
		return this.deleteNode(current);
		}
	}
	
	public void sortedInsertion(Node <T> ficha){
		Node<T> current = head;
		//SI ES VACIA SE REFIERE HEAD SE REFIERE A SÍ MISMO.
        if (this.isEmpty()) { 
            ficha.next = ficha; 
            this.head = ficha; 
        }
        //SI FICHA.DATO ES MENOR A CURRENT.DATO.
        else if ((this.nodeLetter(current).compareTo(this.nodeLetter(ficha))) < 0) {
            while (current.next != head) {
                this.current = current.next;
                
            current.next = ficha; 
            ficha.next = this.head;
            head = ficha;
            }
        }
        //SI FICHA.DATO ES MAYOR A CURRENT.DATO.
        else {
        	while (this.current.next != this.head  &&  
        			0 < (this.nodeLetter(current).compareTo(this.nodeLetter(ficha)))) {
        		this.current = this.current.next;
        		
        	ficha.next = current.next;
        	current.next = ficha;
        	}
        }
	}	
	
}