package sample;

public class NodoList <T> {
    int largo;
    Nodo head=null;

    public void addLast (T e){
        if (this.head==null){
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
    public void addFirst(T e) {
        Nodo n = new Nodo(e);
        n.next=this.head;
        head=n;
    }
}
