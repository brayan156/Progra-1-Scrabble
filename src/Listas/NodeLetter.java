package Listas;

public class NodeLetter{
    private String Node;
    public NodeLetter next;

    public NodeLetter(String string){
        this.Node= string;
        this.next= null;
    }

    public String getNode() {
        return this.Node;
    }

    public void setNode(String Node) {
        this.Node = Node;
    }

    public NodeLetter getNext() {
        return this.next;
    }

    public void setNext(NodeLetter next) {
        this.next = next;
    }
}
