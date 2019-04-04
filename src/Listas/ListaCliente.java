package Listas;

import sample.Cliente;

public class ListaCliente{
    int largo;
    Nodo<Cliente> head= null;


    public void addLast (Cliente e){
        if (this.head.getNodo()==null){
            this.head= new Nodo<Cliente>(e);
        }
        else {
            Nodo<Cliente> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.getNext();
            }
            tmp.setNext(new Nodo<Cliente>(e));
        }
    }
    public void addFirst(Cliente e) {
        new Nodo<Cliente>(e).setNext(this.head);
        head=new Nodo<Cliente>(e);
    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo<Cliente> getHead() {
        return head;
    }

    public void setHead(Nodo<Cliente> head) {
        this.head = head;
    }
}
