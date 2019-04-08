package Listas;

import sample.Ficha;

public class ListaPalabras {
    int largo;
    Nodo<String> head;

    public ListaPalabras() {
        largo=0;
        head=null;
    }

    public void addFirst(String e) {
        Nodo<String> n = new Nodo<String>(e);
        n.next=this.head;
        this.head=n;
        largo+=1;
    }
    public void eliminar(String palabra){
        if (this.head.getNodo().equals(palabra)){
            this.head=this.head.next;
            largo-=1;
        }
        else{
            Nodo<String>tmp=this.head;
            while (tmp.next!=null){
                if (tmp.next.getNodo().equals(palabra)){
                    tmp.next=tmp.next.next;
                    largo-=1;
                    break;
                }
                else {
                    tmp=tmp.next;
                }
            }
        }
    }
    public String buscar (int n){
        Nodo<String>tmp=this.head;
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

    public Nodo <String> getHead() {
        return head;
    }

    public void setHead(Nodo <String> head) {
        this.head = head;
    }
    public ListaFichas convertirfichas(){
        int n=0;
        ListaFichas listaFichas= new ListaFichas();
        if (this.head!=null) {
            System.out.println("voy a convertir a ficha");
            while (n < this.largo) {
                listaFichas.addFirst(new Ficha(0, 0, this.buscar(n)));
                System.out.println(listaFichas.buscar(0).letra);
                n++;
            }
        }
        return listaFichas;
    }
}

