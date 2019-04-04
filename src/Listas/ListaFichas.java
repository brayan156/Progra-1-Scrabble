package Listas;

import sample.Ficha;

public class ListaFichas {
    int largo;
    Nodo<Ficha> head= null;


    public void addLast (String e){
        if (this.head.getNodo()==null){
            this.head= new Nodo(e);
        }
        else {
            Nodo tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo(e);
        }
    }
    public void addFirst(String e) {
        Nodo n = new Nodo(e);
        n.next=this.head;
        head=n;
    }
    public void eliminar (int posx){
        if (this.head.getNodo().posx ==posx){
            this.head=this.head.next;
        }
        else{
            Nodo<Ficha>tmp=this.head;
            while (tmp.next!=null){
                if (tmp.next.getNodo().posx ==posx){
                    tmp.next=tmp.next.next;
                    break;
                }
                else {
                    tmp=tmp.next;
                }
            }
        }
    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo getHead() {
        return head;
    }

    public void setHead(Nodo head) {
        this.head = head;
    }
}

