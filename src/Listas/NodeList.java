package Listas;

public class NodeList <T> {
    int largo;
    Node<T> head=null;

    public void addLast (final T e){
        if (this.head==null){
            this.head= new Node<T>(e);
        }
        else {
            Node<T> tmp= this.head;
            while (tmp.next!= null) {
                tmp = tmp.next;
            }
            tmp.next=new Node<T>(e);
        }
    }
    public void addFirst(final T e) {
        final Node<T> n = new Node<T>(e);
        n.setNext(this.head);
        head=n;
    }
    public boolean Buscar_dato(String dato) {
		Node<T> temp=head;
		int cont=0;
		while (cont<largo) {
			if (temp.getNode().equals(dato)){
				return true;
			}else {
				cont++;
				temp=temp.next;
			}
		}
		return false;
	}
}
