package sample;

public class Nodo <T>{
    private T nodo;
    public Nodo next;

    public Nodo(T nodo){
        this.nodo= nodo;
        this.next= null;
    }

    public T getNodo() {
        return nodo;
    }

    public void setNodo(T nodo) {
        this.nodo = nodo;
    }

    public Nodo getNext() {
        return next;
    }

    public void setNext(Nodo next) {
        this.next = next;
    }
}
