package com.company;

/**
 * @author Samuele Facenda
 */
public class Main {

    public static void main(String[] args) {
        NetAddress address=new NetAddress("172.2.0.0");
        //System.out.println(address);
        NetAddress[] parti=address.partition(new int[]{580,230,120,50,30});
        for (NetAddress netAddress : parti) System.out.println("\n" + netAddress);
    }
}
