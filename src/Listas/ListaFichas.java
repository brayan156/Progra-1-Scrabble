package Listas;

import sample.Ficha;

public class ListaFichas {
    int largo=0;
    Nodo<Ficha> head= null;


    public void addLast (Ficha e){
        if (this.head==null){
            this.head= new Nodo<Ficha>(e);
        }
        else {
            Nodo<Ficha> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo<Ficha>(e);
        }
    }
    public void addFirst(Ficha e) {
        Nodo<Ficha> n = new Nodo<Ficha>(e);
        n.next=this.head;
        head=n;
        largo+=1;
    }
    public void eliminar (int posx){
        if (this.head.getNodo().getPosx() ==posx){
            this.head=this.head.next;
            largo-=1;
        }
        else{
            Nodo<Ficha>tmp=this.head;
            while (tmp.next!=null){
                if (tmp.next.getNodo().getPosx() ==posx){
                    tmp.next=tmp.next.next;
                    largo-=1;
                    break;
                }
                else { tmp=tmp.next; }
            }
        }
    }
    public Ficha buscar( int n){
        Nodo<Ficha>tmp=this.head;
        while (n>0){
            tmp=tmp.next;
            n--;
        }
        return tmp.getNodo();

    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo<Ficha> getHead() {
        return head;
    }

    public void setHead(Nodo<Ficha> head) {
        this.head = head;
    }
    public ListaPalabras convertirstrings(){
        int n=0;
        ListaPalabras listastrings = new ListaPalabras();
        if (this.head!=null) {
            System.out.println("voy a convertir");
            while (n < this.largo) {
                listastrings.addFirst(this.buscar(n).getLetra());
                System.out.println(listastrings.buscar(0));
                n++;
            }
        }
        return listastrings;
    }
}

