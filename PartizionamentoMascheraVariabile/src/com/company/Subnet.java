package com.company;
/**
* estesione della classe BitSequence,
* aggiunge un hostNumber, è una sottorete,
* quindi si possono ottenere indirizzi di broadcast,rete...
* ha inoltre il metodo partiziona, che opera un partizionamento a maschera variabile sulla
* rete
 * @author Samuele Facenda
 * @version 1.0
 */
public class Subnet extends BitSequence{
    private HostNumber val;
    /**
     * costruisce una subnet di lunghezza e valore specificati
     * @param len lunghezza della sottorete
     * @param in valore poi convertito in binario della sottorete
     */
    public Subnet(int len,HostNumber in){
        super(len,in);
        val=in;
    }
    public Subnet(){ }
    /**
     * costruisce una sottorete da una bitsequence, con valore 0
     * @param in bitsequence da copiare
     */
    public Subnet(BitSequence in){
        arr=in.arr;
        val=new HostNumber(0);
    }

    /**
     * costruttore da hostnumber con lunghezza lenBit
     * @param in hostnumber da convertire in binario
     */
    public Subnet(HostNumber in){
        super(in);
        val=in;
    }

    /**
     * costruttore che unisce due bitsequebce, valore 0
     * @param uno prima bitsequence da unire
     * @param due seconda bitsequence da unire
     */
    public Subnet(BitSequence uno,BitSequence due){
        super(uno,due);
        val=new HostNumber(0);
    }

    /**
     * crea una subnet aggiungendo a una esistente una bitsequence davanti
     * @param uno bitsequence da aggiungere davanti
     * @param due subnet da estendere
     */
    public Subnet(BitSequence uno,Subnet due){
        super(uno, due);
        val= due.val;
    }

    /**
     * ritorna una subnet di dimensione len, copiando questa
     * @param len dimensione della subnet da ritornare
     * @return subnet con dimensione cambiata
     */
    public Subnet resize(int len){
        boolean[] out=new boolean[len];
        for(int i=len-1;i>=len-arr.length&&i>=0;i--)
            out[i]=arr[i+(arr.length-len)];
        Subnet aut=new Subnet();
        aut.arr=out;
        aut.val=val;
        return aut;
    }

    /**
     * costruttore con valore e lunghezza
     * @param len lunghezza dell'array
     * @param num valore da convertire in binario
     */
    public Subnet(int len,int num){
        super(len,num);
        val=new HostNumber(num);
    }

    /**
     * @return rappresentazone in stringa della subnet divisa in byte, scritti in int
     */
    public String toStringNumber(){
        return super.toString();
    }
    /**
    *@return indirizzo di broadcast della sottorete
     */
    public BitSequence getBroad(){
        boolean[] broad=BitSequence.getFull(val.getBitLen()),rete=new boolean[arr.length-val.getBitLen()];
        for(int i=0;i<rete.length;i++)
            rete[i]=arr[i];
        return new BitSequence(rete,broad);
    }
    /**
    *@return subnet mask della sottorete
     */
    public BitSequence getSubnetMask(){
        return new BitSequence(new BitSequence(getFull(arr.length- val.getBitLen())),new BitSequence(val.getBitLen(),0));
    }
    /**
    *@return indirizzo di rete della sottorete
     */
    public BitSequence getNet(){
        boolean[] broad=new boolean[arr.length-val.getBitLen()],rete=new boolean[val.getBitLen()];
        for(int i=0;i<broad.length;i++)
            broad[i]=arr[i];
        return new BitSequence(broad,rete);
    }
    /**
    *@return primo indirizzo utilizzato/utilizzabile nella sottorete
     */
    public BitSequence getFirst(){
        boolean[] broad=new boolean[arr.length-val.getBitLen()],rete=new boolean[val.getBitLen()];
        rete[rete.length-1]=true;
        for(int i=0;i<broad.length;i++)
            broad[i]=arr[i];
        return new BitSequence(broad,rete);
    }

    /**
     * @return default gateway della sottorete(ultimo indirizzo utilizzabile)
     */
    public BitSequence getDefaultGateway(){
        boolean[] rete=new boolean[arr.length-val.getBitLen()],def=BitSequence.getFull(val.getBitLen());
        for(int i=0;i< rete.length;i++)
            rete[i]=arr[i];
        def[def.length-1]=false;
        return new BitSequence(rete,def);
    }
    /**
    *@return ultimo indirizzo utilizzato, è l'array booleano
     */
    public BitSequence getLastUsed(){
        return new BitSequence(arr);
    }
    /**
    *metodo ricorsivo per il partizionameto a maschera variabile, in input ha un array
    * di hostNumber e la lunghezza totale richiesta
    * l'array deve essere ordinato
     * @return la subnet partizonata in sottoreti di lunghezza len in base all'array parametro
     * @param in hostnumber di ogni sottorete da partizionare
     * @param len lunghezza delle sottoreti da ritornare
     */
    public static Subnet[] partition(HostNumber[] in,int len){
        //uscita dalla ricorione: se l'array inseriro ha un solo elemento, lo ritorna come sottorete
        if(in.length==1)
            return new Subnet[]{new Subnet(in[0])};
        else{
            //divido l'array in input in sottogruppi della dimensione del maggiore(sottoreti della rete)
            HostNumber[][] hostList=HostNumber.divideForSize(in[0].getBase2(),in);
            Subnet[] out=new Subnet[in.length];
            //i è la dimL dell'array di output, lenSubPart è la dimensione richiesta per ogni sottorete di questa
            int i=0,lenSubPart=in[0].getBitLen();
            Subnet[] partitioned;
            //per ogni sottogruppo dell'array input, lo ripartiziono aggiungendogli davanti il numero di sottorete
            for(int subGroup=0;subGroup<hostList.length;subGroup++){
                partitioned=partition(hostList[subGroup],lenSubPart);
                for(Subnet sub:partitioned){
                    //per ogni indirizzo aggiungo all'output una sottorete(di lunghezza len)composta dal numero della sottorete e dal pezzo partizionato di sottorete
                    out[i]=new Subnet(new BitSequence(len-lenSubPart,subGroup),sub.resize(lenSubPart));
                    i++;
                }
            }
            return out;
        }
    }
    /**
     * {@inheritDoc}
    *@return la sottorete come sequenza di bit,
    * con i byte divisi da punti e una barra tra
    * la parte host e quella di sottoreti
     */
    @Override
    public String getStringBit() {
        String out="";
        for(int i=0;i<arr.length;i++){
            out+=arr[i]?'1':'0';
            if((i+1)%8==0)
                out+='.';
            if(i+1==arr.length-val.getBitLen())
                out+='|';
        }
        return out.substring(0,out.length()-1);
    }
    @Override
    public String toString(){
        return super.toString()+val.toString();
    }
    public HostNumber getHN(){
        return val;
    }
}
