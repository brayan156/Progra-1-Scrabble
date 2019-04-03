package sample;

import Listas.Circular;

public class mainprueba {
public static void main(String[] args) {
	Circular<Ficha> BancoFichas1 = new Circular<Ficha>();
//	BancoFichas1.getLetterSet();
   	BancoFichas1.insertlast("A");
   	BancoFichas1.insertlast("B");
   	BancoFichas1.insertlast("C");
   	BancoFichas1.insertlast("D");
   	BancoFichas1.insertlast("F");
   	BancoFichas1.insertlast("G");
   	BancoFichas1.insertlast("H");
   	BancoFichas1.insertlast("I");
   	
   	System.out.println("");
//   	System.out.println("  this.first >>> "+BancoFichas1.getFirst().getNode());
   	
   	System.out.print("SET  ------->   ");
   	BancoFichas1.display();
   	BancoFichas1.getSize();
   	BancoFichas1.delete("F");
   	BancoFichas1.delete("B");
   	BancoFichas1.delete("C");
   	BancoFichas1.delete("D");
   	BancoFichas1.getRandomNode();
   	BancoFichas1.delete("G");
   	BancoFichas1.delete("A");
   	BancoFichas1.delete("H");
   	BancoFichas1.delete("I");
   	}
}

