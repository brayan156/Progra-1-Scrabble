package Listas;

public class Matrizstring {
    String[][] matrizs=new String[15][15];


    public void Ingresar (int row, int column, String Letra) {
        matrizs[row][column]=Letra;
    }

    public String reverse(String palabra) {
        if (palabra.length() == 1) {
            return palabra;
        }
        else {
            return reverse(palabra.substring(1))+palabra.charAt(0);
        }
    }


    private String recorrerhorizontal(int r,int c){
        String pal;
        String izq="";
        String der="";
        for (int x=c; x<15;x++) {
            der+=matrizs[r][x];
            if(x+1==15 || matrizs[r][x+1]==null) {
                break;
            }
        }
        for (int  j=c; j>=0;j--) {
            izq+=matrizs[r][j];
            if(j-1==-1 || matrizs[r][j-1]==null) {
                break;
            }
        }
        if (izq.length()>1){izq=reverse(izq.substring(1));}
        else {izq="";}
        pal=izq+der;
        System.out.println(pal);
        return pal;
    }
    private String recorrervertical(int r, int c){
        String pal;
        String izq="";
        String der="";

        for (int y=r; y<15;y++) {
            der+=matrizs[y][c];
            if(y+1==15 || matrizs[y+1][c]==null) {
                break;
            }
        }
        for (int i=r; i>=0;i--) {
            izq+=matrizs[i][c];
            if(i-1==-1 || matrizs[i-1][c]==null) {
                break;
            }
        }
        if (izq.length()>1){izq=reverse(izq.substring(1));}
        else {izq="";}
        pal=izq+der;
        System.out.println(pal);
        return pal;
    }





    public ListaPalabras Verificar(Listapares pares){
        System.out.println("voy a crear palabras");
        int cont=0;
        int r=pares.buscar(cont).getR();
        int c=pares.buscar(cont).getC();
        ListaPalabras palabras= new ListaPalabras();
        if (pares.largo==1){
            String palhor=this.recorrerhorizontal(r,c);
            String palver=this.recorrervertical(r,c);
            if (palhor.length()!=1){palabras.addFirst(palhor);}
            if (palver.length()!=1){palabras.addFirst(palver);}
        }
        else if (r==pares.buscar(cont+1).getR()) {
            palabras.addFirst(this.recorrerhorizontal(r,c));
            while (cont<pares.largo){
                String palabra=this.recorrervertical(r,pares.buscar(cont).getC());
                if (palabra.length()!=1){palabras.addFirst(palabra);}
                cont++;
            }
        }
        else {
            palabras.addFirst(this.recorrervertical(r,c));
            while (cont<pares.largo) {
                String palabra=this.recorrerhorizontal(pares.buscar(cont).getR(), c);
                if (palabra.length()!=1){palabras.addFirst(palabra);}
                cont++;
            }
        }
        System.out.println("termina de crear palabras");
        return palabras;
    }


    public String[][] getMatrizs() {
        return matrizs;
    }

    public void setMatrizs(String[][] matrizs) {
        this.matrizs = matrizs;
    }
}
