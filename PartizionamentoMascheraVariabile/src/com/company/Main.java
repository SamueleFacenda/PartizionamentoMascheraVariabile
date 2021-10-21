package com.company;

/**
 * @author Samuele Facenda
 */
public class Main {

    public static void main(String[] args) {
        NetAddress address=new NetAddress("172.1.0.0");
        //System.out.println(address);
        NetAddress[] parti=address.partition(new int[]{70,30,11,21,20});
        for (NetAddress netAddress : parti) System.out.println("\n" + netAddress);
    }
}
