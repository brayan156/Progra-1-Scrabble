package Listas;

public class Node<T>{
    private T node;
    public Node next;

    public Node(T nodo){
        this.node= nodo;
        this.next= null;
    }

    public T getNode() {
        return node;
    }

    public void setNode(T node) {
        this.node = node;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
