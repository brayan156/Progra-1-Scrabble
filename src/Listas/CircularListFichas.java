package Listas;

import sample.Ficha;

import java.util.List;
import java.util.Arrays;
import java.io.IOException;

public class CircularListFichas<T> {
	Node<T> head;
	private Node<T> last, current;
	private int size, random;
	private List<Tuple> letterSet;
	private CircularListFichas<T> Banco;
	
	//CONSTRUCTOR
	public CircularListFichas(){
		this.head = null;
		this.size = 0;
		this.random = 0;
	}
	
//	//ELIMINAR NODO
//	public void deleteNode(String string) {
//		@SuppressWarnings("unchecked")
//		Node<T> del = (Node<T>) new Node<String>(string);
//		this.deleteNode(del);
//	}private Node<T> deleteNode(Node<T> item) {
//		Node<T> tmp = current;
//		this.current.next=current.next.next;
//		this.current=tmp.next;
//		tmp.next=null;
//		this.size--;
//		return tmp;
//	}
	
	
	/* Function to delete a given node from the list */
	public  Node<T> deleteNode(String string)  
	{  
	    if (this.head == null)  
	        return null;  
	  
	    // Find the required node  
	    @SuppressWarnings("unchecked")
		Node<T> current = this.head, previous= new Node<T>((T) string);  
	    while (this.nodeLetter(current).equals(string)) {  
	        if (current.next == this.head){  
	            System.out.printf("\nGiven node is not found"+ 
	                " in the list!!!");  
	            break;  
	        } 
	        previous = current;  
	        current = current.next;  
	    }   
	    // Check if node is only node  
	    if (current.next == this.head) {  
	    	this.head = null; 
	    	System.out.println("Size of list  (D) "+this.getSize());
	        return this.head;  
	    }  
	    // If more than one node, check if  
	    // it is first node  
	    if (current == this.head){  
	        previous = this.head;  
	        while (previous.next != this.head)  
	            previous = previous.next;  
	        this.head = current.next;  
	        previous.next = this.head; 
	        System.out.println("Size of list  (D) "+this.Banco.getSize());
	    }  
	  
	    // check if node is last node  
	    else if (current . next == this.head)  
	    {  
	        previous.next = this.head;
	        System.out.println("Size of list  (D) "+this.Banco.getSize());
	    }  
	    else
	    {  
	        previous.next = current.next;  
	        System.out.println("Size of list  (D) "+this.Banco.getSize());
	    }  
	    this.size--;
	    System.out.println("Size of list  (D) "+this.Banco.getSize());
	    return this.head; 
	} 
	     
    //ULTIMO VALOR
	public Node<T> getLast() {
		return last;
	}
	
	//DISPLAY SIZE
	public int getSize() {System.out.println("Size of list   "+this.size);
	return this.size;}
	
	//DISPLAY CIRCULAR LIST
    public void printList() {
    	    Node<T> temp = head;  
    	    if (head != null)  
    	    {  
    	        do
    	        {  
    	        	String letra =  this.nodeLetter(temp);
    	            System.out.println(letra);  
    	            temp = temp.next;  
    	        }  
    	        while (temp != head);  
    	    }  
    	}  
	
	//NUMERO RANDOM
	public int getRandomInt() {
		this.random = this.size;
		return (int) Math.floor(Math.random() * Math.floor(random));	
	}
	
	//LISTA VACIA
	@SuppressWarnings("unused")
	private boolean isEmpty() {
		return (head == null);
	}
	
	//OBTENER LETRA EN EL NODO.
	public String nodeLetter(Node<T> ficha_encapsulada) {
		String letter = ((Ficha) ficha_encapsulada.getNode()).getLetter();
		return letter;
		}
	
	//FICHA RANDOM
	public Node<T> returnRandomLetterNode(){
		if (this.current == null) {
			return null;
		}else {
		int contador = this.getRandomInt();
		while (contador  != 0) {
			current = current.next;
		}
		String encapsulado = (((Ficha) current.getNode()).getLetter());
		return this.deleteNode(encapsulado);
		}
	}

	//INSERTAR AL FINAL
	public void insertionlast(String letra){
		Ficha ficha = new Ficha(letra, 0, 0);
		System.out.println("Cualidades... | letra="+ficha.getLetter()+" |  valor="+ficha.getValue());
		@SuppressWarnings("unchecked")
		Node<T> appending = (Node<T>) new Node<Ficha>(ficha);
	    appending.setNext(head);
	    if(this.head == null){            
	        this.head = appending;
	        appending.setNext(head);
	        this.last = head;
	        this.current = head; 
	    }else{
	    	this.last.setNext(appending);
	    	this.last = appending;   
	    	
	    }this.size++;
	    this.getSize();
	}
	
//	//INSERTAR DE FORMA ORDENADA.
//	public void sortedInsertion(String letra){
//		Ficha ficha = new Ficha(letra, 0, 0);
//		System.out.println("FICHA CREADA >>>   Letra>"+ficha.getLetter()+" Valor>"+ ficha.getValue());
//		Node<T> node_ficha = (Node<T>) new Node<Ficha>(ficha);
//		Node<T> current = head;
//		//SI ES VACIA SE REFIERE HEAD SE REFIERE A SÍ MISMO	
//	         /* Special case for head node */
//	         if (this.isEmpty() || (this.nodeLetter(head).compareTo(this.nodeLetter(node_ficha)) > 0)){
//	        	 head = node_ficha;
//	        	 node_ficha.next = head; 
//	             
//	         } 
//	         else { 
//	            /* Locate the node before point of insertion. */	  
//	            while (current.next != null && 
//	            		(this.nodeLetter(head).compareTo(this.nodeLetter(node_ficha)) < 0)) {
//	                  current = current.next; 
//	  
//	            node_ficha.next = current.next; 
//	            current.next = node_ficha; 
//	         } 
//	         }
//	     }
      
    //CREAR LISTA CON LETRA Y CANTIDAD CORRESPONDIENTE.
    private  List<Tuple> letterSets() {
		letterSet = Arrays.asList(
			new Tuple("_", 2),
    		new Tuple("A", 12), new Tuple("E", 12), new Tuple("O", 9),
    		new Tuple("I", 6), new Tuple("S", 6), new Tuple("N", 5),
    		new Tuple("L", 4), new Tuple("R", 5), new Tuple("U", 5),
    		new Tuple("T", 4),
    		
    		new Tuple("D", 5), new Tuple("G", 2),
    		
    		
    		new Tuple("C", 4), new Tuple("B", 2), new Tuple("M", 2),
    		new Tuple("P", 2),
    		
    		new Tuple("H", 2), new Tuple("F", 1), new Tuple("V", 1),
    		new Tuple("Y", 1),
    		
    		new Tuple("CH", 1), new Tuple("Q", 1), 
    		
    		new Tuple("J", 1), new Tuple("LL", 1), new Tuple("Ñ", 1),
    		new Tuple("RR", 1), new Tuple("X", 1),
    		
    		new Tuple("Z", 1));
		
		this.insertSetinCircularList();
		return letterSet;}
    
    //FUNCION PARA INSERTAR EN LA LISTA CIRCULAR LOS SETS DE LAS LETRAS CON SUS REPETICIONES RESPECTIVAS.
    private void insertSetinCircularList() {
    	this.Banco = new CircularListFichas<T>();
    		for (int i=0; i<letterSet.size(); i++) {
    			Tuple tupla = letterSet.get(i);
    			int tupla_numero = tupla.gety();	
    			while (tupla_numero != 0) {
//            		System.out.println("Repeticiones... | contador="+tupla_numero+" |  current i="+ i);
    				this.Banco.insertionlast(tupla.getx());
    				tupla_numero--;
//        			System.out.println("Size of list   "+this.Banco.getSize());
//            		System.out.println(" ");
    				continue;
    			}
    		}
    }
    
    //FUNCION PARA CREAR LA LISTA AUTOMATICAMENTE
	public List<Tuple> getLetterSet() {
		this.letterSets();
		return letterSet;
	}

    public static void main(String[] args) throws IOException {
    	CircularListFichas<Ficha> Banco1 = new CircularListFichas<Ficha>();
    	Banco1.getLetterSet();
       	Banco1.insertionlast("S");
       	Banco1.insertionlast("S");
       	Banco1.insertionlast("S");
       	Banco1.insertionlast("S");
        Banco1.deleteNode("A");
//    	Banco1.printList();
    }	
}