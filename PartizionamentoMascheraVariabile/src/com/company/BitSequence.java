package com.company;
/**
*classe per la gestione di un array booleano
 * @author Samuele Facenda
 */
public class BitSequence {
    protected boolean[] arr;

    /**
     * bitsequnece da array booleano
     * @param arr array da trasformare in bitsequence
     */
    public BitSequence(boolean[] arr){
        this.arr=new boolean[arr.length];
        for(int i=0;i<arr.length;i++)
            this.arr[i]=arr[i];
    }

    /**
     * unisce i due array in una bitsequence
     * @param uno primo array da unire
     * @param due secondo array da unire
     */
    public BitSequence(boolean[] uno,boolean[] due){
        this.arr=new boolean[uno.length+due.length];
        int i;
        for(i=0;i<uno.length;i++)
            this.arr[i]=uno[i];
        for(i= uno.length;i<arr.length;i++)
            this.arr[i]=due[i- uno.length];
    }

    /**
     * divide la stringa in byte, divisa da punti, genera poi un array dai byte, portandoli in bit
     * @param in stringa da dividere con punti, ogni sezione max 255
     */
    public BitSequence(String in){
        String[] byt=in.split("\\.");
        arr=new boolean[0];
        for(int i=0;i< byt.length;i++)
            arr=usisci(arr,intToByte(Integer.parseInt(byt[i]),8));
    }

    /**
     * divide la stringa in byte, divisa da punti, genera poi un array dai byte, di lunghezza len, portandoli in bit
     * @param in stringa da dividere con punti, ogni sezione max 255
     * @param len lunghezza di bit massima
     */
    public BitSequence(String in,int len){
        String[] byt=in.split("\\.");
        arr=new boolean[0];
        for(int i=0;i< len;i++)
            arr=usisci(arr,intToByte(Integer.parseInt(byt[i]),8));
    }

    /**
     * ritorna un array booleano di len lunghezza che letto in binario é nu
     * @param nu il numero da convertire in binario
     * @param len la lunghezza massima
     * @return numero codificato in binario di len lunghezza
     */
    private boolean[] intToByte(int nu,int len){
        boolean[] out=new boolean[8];
        int i=out.length-1,num=nu;
        while(i>=0&&num>0){
            out[i]=num%2==1;
            i--;
            num/=2;
        }
        boolean[] out2=new boolean[len];
        for(i=0;i<len;i++)
            out2[i]=out[i];
        return out2;
    }

    /**
     * costruttore da hostnumber, converte il valore in binario, della lunghezza bitLen
     * @param in hostnumber da convertire in binario
     */
    public BitSequence(HostNumber in){
        arr=new boolean[in.getBitLen()];
        int i=arr.length-1,num=in.getVal();
        while(i>=0&&num>0){
            arr[i]=num%2==1;
            i--;
            num/=2;
        }
    }

    /**
     * costruttore da hostnumber, converte il valore in binario, della lunghezza massima len
     * @param len lunghezza massima
     * @param in hostnumber da convertire in binario
     */
    public BitSequence(int len,HostNumber in){
        arr=new boolean[len];
        int i=arr.length-1,num=in.getVal();
        while(i>=0&&num>0){
            arr[i]=num%2==1;
            i--;
            num*=0.5;
        }
    }

    /**
     * construttore che codifica il numero in in un array di lunghezza massima len
     * @param len lunghezza massima
     * @param in numero da codificare in binario
     */
    public BitSequence(int len,int in){
        arr=new boolean[len];
        int i=arr.length-1;
        while(i>=0&&in>0){
            arr[i]=in%2==1;
            i--;
            in/=2;
        }
    }
    public BitSequence(){}

    /**
     * costruttore che unisce due bitsequence
     * @param uno primo bistsequence da unire
     * @param due seconda bitsequence da unire
     */
    public BitSequence(BitSequence uno,BitSequence due){
        arr=usisci(uno.arr,due.arr);
    }
    private boolean[] usisci(boolean[] uno,boolean[] due){
        boolean[] out=new boolean[uno.length+due.length];
        int i=0;
        while(i<uno.length){
            out[i]=uno[i];
            i++;
        }
        while(i<out.length){
            out[i]=due[i-uno.length];
            i++;
        }
        return out;
    }
    /**
    *@return la dimesione dell'array
     */
    public int size(){
        return arr.length;
    }

    /**
     * @param i indice del boolean richiest
     * @return valore della cella di indice i
     */
    public boolean get(int i){
        return arr[i];
    }

    /**
     * set di una cella
     * @param i indice della cella da modificare
     * @param val valore a cui settare la cella
     */
    public void set(int i,boolean val){
        arr[i]=val;
    }
    /**
     * converte l'array in int che sono il valore binario in decimale dei byte dell'array
    *@return  l'array diviso in byte, da sinistra a destra
    * convertiti in int
     */
    public int[] toByte(){
        int[] out=new int[(int)Math.ceil((double)arr.length/8)];
        for(int i=arr.length-1;i>=0;i--){
            out[i/8]+=arr[i]?Math.pow(2,7-i%8):0;
        }
        return out;
    }
    @Override
    public String toString(){
        String out="";
        for(int num:toByte())
            out+=num+".";
        return out;
    }
    /**
     * metodo di utilitá per istanziare un array booleano tutto true
    *@return  un array booleano pieno della
    * lunghezza richiesta
     */
    public static boolean[] getFull(int len){
        boolean[] broad=new boolean[len];
        for(int i=0;i<broad.length;i++)
            broad[i]=true;
        return broad;
    }
    /**
    *restituisce l'array come stringa,
    * diviso in byte da punti
     * @return l'array in stringa diviso in byte
     */
    public String getStringBit(){
        String out="";
        for(int i=0;i<arr.length;i++){
            out+=arr[i]?'1':'0';
            if((i+1)%8==0)
                out+='.';
        }
        return out;
    }
    /**
     * metodo che ritorna l'indirizzo dell'array, non modificare
    *@return  l'indirizzo dell'array
     */
    public boolean[] getArr(){
        return arr;
    }
}
