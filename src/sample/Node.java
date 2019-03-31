package sample;

public class Node <T>{
    public T Node;
    public Node<T> next;

    public Node(T Node){
        this.Node= Node;
        this.next= null;
    }

    public T getNode() {
        return this.Node;
    }

    public void setNode(T Node) {
        this.Node = Node;
    }

    public Node<T> getNext() {
        return this.next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
