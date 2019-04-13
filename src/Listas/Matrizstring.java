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

        String pal="";
        String izq="";
        String der="";
        try{
        int letraini_largo=matrizs[r][c].length();
        for (int x=c; x<15;x++) {
            der+=matrizs[r][x];
            if(x+1==15 || matrizs[r][x+1]==null) {
                break;
            }
        }
        for (int  j=c; j>=0;j--) {
            if (matrizs[r][j].length()==2){izq+=reverse(matrizs[r][j]);}
            else { izq+=matrizs[r][j];}
            if(j-1==-1 || matrizs[r][j-1]==null) {
                break;
            }
        }
        if (reverse(izq).equals(der)){izq=""; der="";}
        if (izq.length()>letraini_largo){izq=reverse(izq.substring(letraini_largo));}
        else {izq="";}
        pal=izq+der;
        System.out.println(pal);
        return pal;
    }
        catch (NullPointerException e){
            System.out.println("donde ocurrio el error: "+matrizs[r][c]+"en "+r+","+c);
        }
        return pal;
    }
    private String recorrervertical(int r, int c){
        String pal;
        String izq="";
        String der="";
        int letraini_largo=matrizs[r][c].length();
        for (int y=r; y<15;y++) {
            der+=matrizs[y][c];
            if(y+1==15 || matrizs[y+1][c]==null) {
                break;
            }
        }
        for (int i=r; i>=0;i--) {
            if (matrizs[i][c].length()==2){izq+=reverse(matrizs[i][c]);}
            else { izq+=matrizs[i][c];}
            if(i-1==-1 || matrizs[i-1][c]==null) {
                break;
            }
        }
        if (reverse(izq).equals(der)){izq=""; der="";}
        if (izq.length()>letraini_largo){izq=reverse(izq.substring(letraini_largo));}
        else {izq="";}
        pal=izq+der;
        System.out.println(pal);
        return pal;
    }





    public ListaPalabras Verificar(Listapares pares){//recibe conjuntos de pares de posciones en la matriz y manda a crear las palabras nuevas que hizo el jugador
        System.out.println("voy a crear palabras");
        int cont=0;
        int r=pares.buscar(cont).getR();
        int c=pares.buscar(cont).getC();
        ListaPalabras palabras= new ListaPalabras();
        if (pares.getLargo()==1){
            String palhor=this.recorrerhorizontal(r,c);
            String palver=this.recorrervertical(r,c);
            if (palhor.length()>1){palabras.addFirst(palhor);}
            if (palver.length()>1){palabras.addFirst(palver);}
        }
        else if (pares.buscar(cont).getR()==pares.buscar(cont+1).getR()) {
            palabras.addFirst(this.recorrerhorizontal(r,c));
            while (cont<pares.largo){
                String palabra=this.recorrervertical(pares.buscar(cont).getR(),pares.buscar(cont).getC());
                if (palabra.length()>1){palabras.addFirst(palabra);}
                cont++;
            }
        }
        else if (pares.buscar(cont).getC()==pares.buscar(cont+1).getC()) {
            palabras.addFirst(this.recorrervertical(r,c));
            while (cont<pares.largo) {
                String palabra=this.recorrerhorizontal(pares.buscar(cont).getR(), pares.buscar(cont).getC());
                if (palabra.length()>1){palabras.addFirst(palabra);}
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
