package Listas;

public class ListaPalabras {
    int largo;
    Nodo<String> head= null;

    public void addFirst(String e) {
        Nodo n = new Nodo(e);
        n.next=this.head;
        this.head=n;
    }
    public void eliminar(String palabra){
        if (this.head.getNodo().equals(palabra)){
            this.head=this.head.next;
        }
        else{
            Nodo<String>tmp=this.head;
            while (tmp.next!=null){
                if (tmp.next.getNodo().equals(palabra)){
                    tmp.next=tmp.next.next;
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

    public Nodo getHead() {
        return head;
    }

    public void setHead(Nodo head) {
        this.head = head;
    }
}

