package Circular_Letras;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import sample.Ficha;

public class Circular<T>{
	//Links
	private NodeLetter first, last;
	private int size;
	//GAME VARIABLES
	private List<Tuple> letterSet;
	
	//CONSTRUCTOR.
	public Circular() {
		this.first = null;
		this.last = null;
	}
	
	
	//LISTA VACIA.
	private boolean isEmpty() {
		return (this.getFirst() == null);
	}
	
	
	//DISPLAY CIRCULAR LIST.
    public void display() {  
    	NodeLetter currentprinting = this.getFirst();     	
        if(this.isEmpty()) {  
            System.out.println("List is empty");  
        }  
        else {  
        	System.out.print("Displaying...  ");
        	do{
        		//Prints each node by incrementing pointer.
        		System.out.print(" "+currentprinting.getNode());
        		currentprinting = currentprinting.getNext();
        	}while(currentprinting != this.getFirst());
        	System.out.println();  
			}  
    } 
    
    
    //SIZE OF THE CIRCULAR LIST
    public int getSize() {
    	System.out.println("Size | "+this.size);
    	return this.size;
    }

	
	//INSERT LAST
	public void insertlast(String string) {
		NodeLetter append = new NodeLetter(string);
		if (this.isEmpty()) {
			this.first = append;
			this.last = append;
			append = this.getFirst();
		}else {
			this.last.setNext(append);
			this.last = append;
			this.last.setNext(this.getFirst());
		}
		System.out.print(last.getNode()+ " ");
		this.size++;
	}
	
	//DELETE
	public Ficha delete(String string) {
		
		NodeLetter previous, actual;
		previous = null;
		actual = this.first;
		//create ficha.
		Ficha ficha = new Ficha(0, 0, "");
		
		if (!this.isEmpty()) {
			while((actual != null)&&(actual.getNode() != string)) {
//				System.out.println("\n  actual >>> "+actual.getNode());
				previous = actual;
				actual = actual.getNext();
//				System.out.println("  string >> "+string);
             if (actual == this.getFirst())
            	 break;
          }
			
          if ((actual == null)||(string != actual.getNode()))
          {
             System.out.println("\n ¡¡¡ Not exist \""+string+"\" !!!");        
          }    
          else
          {
        	  // Delete the only element case
        	  if(this.first.getNode() == string && 
        			  this.first.getNext().getNode() == this.first.getNode())
        	  {
        		  this.first.setNext(null);
        		  this.first = null;
//        		  System.out.println("head set to null.. "+this.first);
        	    }
        	  
        	  else if (actual == this.first)//caso 1: Elimina this.first 
             {
                this.first = this.first.getNext();
                this.last.setNext(this.first);
                actual.setNext(null);
                ficha.setLetra(string);
             }
             
             else if (actual == this.last)//caso 2: elimina this.last
             {
                previous.setNext(this.last.getNext());
                this.last = previous;
                actual.setNext(null);
                ficha.setLetra(string);
             }                        
             else  // caso 3: elimina en medio 
             {
                previous.setNext(actual.getNext());
                actual.setNext(null);
                ficha.setLetra(string);
             }    
          }this.size--;
       }
       else
       {
          System.out.print("\n¡¡¡ No elements !!!");
       }
//		System.out.println("Cualidades... | "+ "letra="
//			+ficha.getLetra()+" |  valor="
//			+ficha.getValue());
		this.display();
//		this.getSize();
    return ficha;
	}
    

//--------------METHODOS SOCKETS------------	
	//FUNCION PARA CREAR LA LISTA AUTOMATICAMENTE
	public List<Tuple> getLetterSet() {
		this.letterSets();
		return letterSet;
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
		return letterSet;
    }
	//INSERT LETTER BY LETTER (REQUIRED AMOUNTS).
	private void insertSetinCircularList() {
			for (int i=0; i<letterSet.size(); i++) {
				Tuple tupla = letterSet.get(i);
				int tupla_numero = tupla.gety();	
				while (tupla_numero != 0) {
	//            		System.out.println("Repeticiones... | contador="+tupla_numero+" |  current i="+ i);
					this.insertlast(tupla.getx());
					tupla_numero--;
	//        			System.out.println("Size of list   "+this.BancoFichas.getSize());
	//            		System.out.println(" ");
					continue;}
			}
    }
	
	//FICHA RANDOM
	public Ficha getRandomNode(){
		Ficha ficha=new Ficha(0,0,"");
		if (!this.isEmpty()) {
			NodeLetter currentrandomize = this.first;
			int random = new Random().nextInt(this.getSize());
//			System.out.println(random);
			while (random != 0) {
				currentrandomize = currentrandomize.next;
				random--;
			}
//			System.out.println(currentrandomize.getNode());
			ficha=this.delete(currentrandomize.getNode());

		}return ficha;
	}

	//SETS && GETS.
	public NodeLetter getFirst() {
		return this.first;
	}
	public NodeLetter getLast() {
		return this.last;
	}
	
	
	
	
}
