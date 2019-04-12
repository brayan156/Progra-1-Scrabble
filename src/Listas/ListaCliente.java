package Listas;

import sample.Cliente;

public class ListaCliente{
    int largo=0;
    Nodo<Cliente> head= null;


    public void addLast (Cliente e){
        if (this.head==null){
            this.head= new Nodo<Cliente>(e);
            largo++;
        }
        else {
            Nodo tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo<Cliente>(e);
            largo++;
        }
    }
    public Cliente buscar (int n){
        Nodo<Cliente>tmp=this.head;
        while (n>0){
            tmp=tmp.next;
            n--;
        }
        return tmp.getNodo();
    }

    public void bubbleSort() {
        if (this.largo > 1) {
            boolean wasChanged;

            do {
                Nodo<Cliente> current = this.head;
                Nodo<Cliente> previous = null;
                Nodo<Cliente> siguiente = this.head.getNext();
                wasChanged = false;

                while ( siguiente != null ) {
                    if (current.getNodo().getPuntaje() > siguiente.getNodo().getPuntaje()) {
                        wasChanged = true;
                        if ( previous != null ) {
                            Nodo<Cliente> sig = siguiente.getNext().getNext();

                            previous.setNext(siguiente);
                            siguiente.setNext(current);
                            current.setNext(sig);
                        } else {
                            Nodo<Cliente> sigsig = siguiente.getNext();
                            head = siguiente;
                            siguiente.setNext(current);
                            current.setNext(sigsig);
                        }
                        previous = siguiente;
                        siguiente = current.getNext();
                    } else {
                        previous = current;
                        current = siguiente;
                        siguiente = siguiente.getNext();
                    }
                }
            } while( wasChanged );
        }
    }

    public void sumarpuntos(String cliente, int puntos){
        int n=0;
        while(n<this.largo){
            Cliente client= this.buscar(n);
            if (client.getNombre().equals(cliente)){
                client.setPuntaje(client.getPuntaje()+puntos);
                break;
            }
            else {n++;}
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
