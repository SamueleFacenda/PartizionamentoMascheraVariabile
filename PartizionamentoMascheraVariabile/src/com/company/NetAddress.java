package com.company;
/**
*La classe NetAddress è una classe che contiene un prefisso e una
* sottorete, fornendo solo i metodi di stampa e partizionamento,
* metodi che semplicemente richiamano metodi delle clessi Subnet
* e BitSequence
 * @author Samuele Facenda
*/
public class NetAddress {
    private final BitSequence net;
    private final Subnet host;
    /**
    *Viene fornita in ingresso una stringa di numeri(max 255),
    * divisi da punti, massimo due numeri. Viene poi calcolata la classe
    * dell'indirizzo e instanziati gli attributi di conseguenza
     * @param in stringa da dividere in byte con i punti, ogni numero deve essere massimo 255
     */
    public NetAddress(String in){
        int i=0;
        boolean finded=false;
        for(boolean bit:new BitSequence(in.split("\\.")[0]).getArr()){
            if(!finded){
                if(bit)
                    i++;
                else
                    finded=true;
            }
        }

        net=new BitSequence(in,i+1);
        host=new Subnet(32-(i+1)*8,0);
    }
    /**
    *costruttore senza istanziazione, usare solo in metodi instanziati ad hoc
     */
    public NetAddress(BitSequence net,Subnet host){
        this.net=net;
        this.host=host;
    }
    /**
    *restituisce una array di NetAddress con sottoreti partizionate
    * per gruppi di host, questo metodo non fa altro che copiare il risultato del
    * metdo partition della classe Subnet
     * @return indirizzo partizionato
     * @param num numero di host di ogni sottorete
     */
    public NetAddress[] partition(int[] num){
        NetAddress[] out=new NetAddress[num.length];
        HostNumber[] lista=new HostNumber[num.length];
        for(int i=0;i<num.length;i++)
            lista[i]=new HostNumber(num[i]);
        HostNumber.sort(lista);
        Subnet[] sub=Subnet.partition(lista, host.size());
        for(int i=0;i<num.length;i++)
            out[i]=new NetAddress(net,sub[i]);
        return out;
    }
    @Override
    public String toString(){
        return "rete: "+net+host.getNet().toStringLast()+"/"+(32-host.getNumberOfBitForHost())+
                "\nbroadcast: "+net+host.getBroad().toStringLast()+
                "\nprimo utilizzato: "+net+host.getFirst().toStringLast()+
                "\nultimo utilizzato: "+net+host.getLastUsed().toStringLast()+
                "\nsubnet mask: "+new BitSequence(new BitSequence(BitSequence.getFull(net.size())),host.getSubnetMask()).toStringLast()+
                "\ndefault gateway:"+net+host.getDefaultGateway().toStringLast()+
                "\nn host: "+host.getHN().getVal()+
                "\n"+host.getStringBit();
    }
}
