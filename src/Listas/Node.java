package Listas;

public class Node<T>{
    private T node;
    public Node<T> next;

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

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
