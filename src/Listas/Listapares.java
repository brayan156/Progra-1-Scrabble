package Listas;

public class Listapares {
    int largo=0;
    Nodo<Pares> head= null;

    public Listapares() {
        this.largo = 0;
    }

    public void limpiar(){
        this.head=null;
        this.largo=0;
    }

    public void addFirst(Pares e) {
        Nodo<Pares> n = new Nodo<Pares>(e);
        n.next=this.head;
        head=n;
        largo++;
    }
    public Pares buscar (int n){
        Nodo<Pares>tmp=this.head;
        while (n>0){
            tmp=tmp.next;
            n--;
        }
        return tmp.getNodo();
    }

    public void eliminar(int r, int c){
        if (this.head.getNodo().getR()==r && this.head.getNodo().getC()==c){
            this.head=this.head.next;
            System.out.println("se elimino " +r+"," +c);
            largo-=1;
        }
        else{
            Nodo<Pares>tmp=this.head;
            while (tmp.next!=null){
                if (tmp.next.getNodo().getR()==r && tmp.next.getNodo().getC()==c){
                    tmp.next=tmp.next.next;
                    System.out.println("se elimino " +r+"," +c);
                    largo-=1;
                    break;
                }
                else {
                    tmp=tmp.next;
                }
            }
        }
    }

    public boolean verificarlinea(){
        int cont=1;
        Boolean nolinea=false;
        Boolean nolineah=false;
        Boolean nolineav=false;
        if (this.head!=null) {
            int y=this.head.getNodo().getR();
            int x=this.head.getNodo().getC();
            while (cont < largo) {
                if (this.buscar(cont).getR()!=y){ nolineav=true; }
                y=this.buscar(cont).getR();
                cont++;
            }
            cont=1;
            while (cont < largo) {
                if (this.buscar(cont).getC() != x) { nolineah = true; }
                x = this.buscar(cont).getC();
                cont++;
            }
            if (nolineav && nolineah){nolinea=true;}
        }
        return nolinea;

    }


    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public Nodo<Pares> getHead() {
        return head;
    }

    public void setHead(Nodo <Pares> head) {
        this.head = head;
    }

}
