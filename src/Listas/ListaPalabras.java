package Listas;

public class ListaPalabras {
    int largo;
    Nodo<String> head= null;


    public void addLast (String e){
        if (this.head.getNodo()==null){
            this.head= new Nodo<String>(e);
        }
        else {
            Nodo<String> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Nodo<String>(e);
        }
    }
    public void addFirst(String e) {
        Nodo<String> n = new Nodo<String>(e);
        n.next=this.head;
        head=n;
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
    

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo<String> getHead() {
        return head;
    }

    public void setHead(Nodo<String> head) {
        this.head = head;
    }
}

