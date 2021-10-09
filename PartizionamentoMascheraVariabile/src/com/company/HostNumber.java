package com.company;
/**
*classe di utilità per la gestione degli host, in sola lettura
* salva alcuni valori per non diverli ricalcolare ogni volta
* i valori sono il numero di host, la potenza di due superiore e
* il numero di bit occupati da questa, aggiungendo anche i due indirizzi
* di rete e di broadcast
 * @author Samuele Facenda
 */
public class HostNumber {
    private final int val,base2,bitLen;

    /**
     * genera un hostnumber con valore num
     * @param num numero di host
     */
    public HostNumber(int num){
        if(num==0)
            val=base2=bitLen=0;
        else{
            val=num;
            int bitLe=0;
            num+=3;
            while(num!=0){
                num=num/2;
                bitLe++;
            }
            this.bitLen=bitLe;
            base2= (int) Math.pow(2,bitLen);
        }
    }

    /**
     * @return il numero di host
     */
    public int getVal(){
        return val;
    }

    /**
     * @return il numero di bit occupati dal valore+3 in base 2
     */
    public int getBitLen(){
        return bitLen;
    }
    /**
     * @return la potenza di 2 superiore, contando anche tre host per la rete,broadcast e def-gateway
     */
    public int getBase2(){
        return base2;
    }
    /**
     *bubble sort per array di hostNumber
     * @param in l'array da ordinare
     */
    public static void sort(HostNumber[] in){
        HostNumber swap;
        for(int i=0;i<in.length;i++){
            for(int e=0;e<in.length-1;e++){
                if(in[e].val<in[e+1].val){
                    swap=in[e];
                    in[e]=in[e+1];
                    in[e+1]=swap;
                }
            }
        }
    }
    /**
    *@return la dimensione delle sottoreti sommate di hostNumber in input
     * @param in l'array del quale si vuole la somma delle dimensoni
     */
    public static int getSize(HostNumber[] in){
        int sum=0;
        for(int i=0;i<in.length;i++)
            sum+=in[i].base2;
        return sum;
    }
    /**
    *@return  il numero di bit occupati da una sottorete contenente
    * tutti gli host inseriti in input
     * @param in l'array del quale si vuole sapere il numero di bit occupati come sottorete
     */
    public static int getBitSize(HostNumber[] in){
        int tot=getSize(in),len=0;
        while(tot!=0){
            tot/=2;
            len++;
        }
        return len;
    }
    /**
    *divide un array di HostNumber in sottogruppi di massimo
    * "size" dimensione
     * @return array diviso in sottogruppi di size dimensione
     * @param in l'array da dividere
     * @param size la dimesione di un sottogruppo
     */
    public static HostNumber[][] divideForSize(int size,HostNumber[] in){
        int num=0;
        for(HostNumber ho:in)
            num+=ho.base2;
        num=(int)Math.ceil((float)num/size);
        HostNumber[][] out=new HostNumber[num][];
        int i=0,sum,e;
        for(int h=0;h<num;h++){
            sum=0;
            e=0;
            while(i<in.length&&sum+in[i].base2<=size){
                sum+=in[i].base2;
                e++;
                i++;
            }
            out[h]=new HostNumber[e];
            if(sum>size) {
                e--;
                i--;
            }
            for(int j=0;j<e;j++)
                out[h][j]=in[j+i-e];
        }
        return out;

    }
    @Override
    public String toString(){
        return "val: "+val+", base2: "+base2+", bitLen: "+bitLen;
    }
}
