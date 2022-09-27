package com.company;

/**
 * @author Samuele Facenda
 */
public class Main {

    public static void main(String[] args) {
        NetAddress address=new NetAddress("172.16.0.0");
        //System.out.println(address);
        NetAddress[] parti=address.partition(new int[]{300, 6, 6, 28, 28, 130});
        for (NetAddress netAddress : parti) System.out.println("\n" + netAddress);
    }
}
