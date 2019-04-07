package Circular_Letras;

import sample.Ficha;

public class maincircular {
public static void main(String[] args) {
	Circular<Ficha> BancoFichas1 = new Circular<Ficha>();
	BancoFichas1.getLetterSet();
   	
   	System.out.println("");
//   	System.out.println("  this.first >>> "+BancoFichas1.getFirst().getNode());
   	
   	System.out.print("SET  ------->   ");
   	BancoFichas1.display();
   	BancoFichas1.getSize();
   	BancoFichas1.delete("Z");
   	BancoFichas1.getRandomNode();
   	BancoFichas1.getRandomNode();
   	BancoFichas1.getRandomNode();
   	BancoFichas1.getRandomNode();
   	BancoFichas1.getRandomNode();
   	BancoFichas1.getRandomNode();
   	BancoFichas1.getRandomNode();
   	BancoFichas1.getRandomNode();	

   	}
}

