package Listas;

import sample.Ficha;

import java.util.List;
import java.util.Arrays;
import java.io.IOException;

public class CircularListFichas<T> {
	private Node<T> head;
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
	
	//LISTA VACIA
	@SuppressWarnings("unused")
	private boolean isEmpty() {
		return (head == null);
	}
			
    //ULTIMO VALOR
	public Node<T> getLast() {
		return last;
	}
	
	//DISPLAY SIZE
	public int getSize() {
//		System.out.println("Size of list   "+this.size);
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
    
	//OBTENER LETRA EN EL NODO.
	public String nodeLetter(Node<T> ficha_encapsulada) {
		String letter = ((Ficha) ficha_encapsulada.getNode()).getLetter();
		return letter;
		}
	
	//NUMERO RANDOM
	public int getRandomInt() {
		this.random = this.size;
		return (int) Math.floor(Math.random() * Math.floor(random));	
	}

	//FICHA RANDOM
	public String returnRandomLetterNode(){
		System.out.println(".");
		if (this.head == null) {
			System.out.println(".");
			return null;
		}else {
			System.out.println(".");
			this.current = this.head;
			int contador = this.getRandomInt();
			while (contador  != 0) {
				System.out.println("random ---> "+contador);
				this.current = this.current.next;
				System.out.println(this.nodeLetter(this.current));
				contador--;
				}
			System.out.println("del current "+this.nodeLetter(current));
			return deleteNode(current);
			}
	}

	//INSERTAR AL FINAL
	public void insertionlast(String letter){
		
		Ficha ficha = new Ficha(letter, 0, 0);
//		System.out.println("Cualidades... | letra="+ficha.getLetter()+" |  valor="+ficha.getValue());	
		
//		Node<T> appending = new Node<T> (null);
//		appending.setNode(ficha);
//	    appending.setNext(head);
		
		Node<Ficha> appending = new Node<Ficha>(ficha);
	    
	    if(this.head == null){            
	        this.head=appending.getNode();
	        this.last=appending.getNode();
	        appending.setNext(this.head);
    
	    }else{
	    	this.last.setNext(appending);
	    	this.last.setNode((T) appending);
	    	this.last.setNext(this.head);
	    }this.size++;
	    this.getSize();
	}
	
	//DELETE
	public  String deleteNode(Node<T> del) {  
		Node<T> current = this.head;

		while (this.nodeLetter(del) != this.nodeLetter(current)) {
			System.out.println(".");
			System.out.println("Nodo head --> "+current.getNode());
			System.out.println("Nodo random --> "+del.getNode());
			if (current.next == this.head) {
				System.out.printf("\nGiven node is not found in the list!!!");  
				break;}
		}
		Node<T> tmp = current;
        current = (current.getNext());
        current.setNext(tmp.getNext().getNext());
        
        System.out.println(".....");
        
        String nodoborrado= this.nodeLetter(del);
        String nodocurrent=this.nodeLetter(current);
        String nodocurrentnext=this.nodeLetter(current.getNext());
        String nodocurrentnextnext=this.nodeLetter(current.getNext().getNext());
        
		System.out.println("del "+nodoborrado);
		System.out.println("current "+nodocurrent);
		System.out.println("current.next "+nodocurrentnext);
		System.out.println("current.next.next "+nodocurrentnextnext);
		return this.nodeLetter(current);
	}
      
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
    	
       	Banco1.insertionlast("K");
       	Banco1.insertionlast("A");
       	Banco1.insertionlast("B");
       	Banco1.insertionlast("C");
       	Banco1.insertionlast("Z");
       	Banco1.insertionlast("F");
       	Banco1.insertionlast("H");
       	Banco1.insertionlast("J");
       	Banco1.returnRandomLetterNode();
       	//    	Banco1.printList();
    }	
}