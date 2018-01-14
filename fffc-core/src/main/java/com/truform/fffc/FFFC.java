package com.truform.fffc;

/**
 * Hello world!
 */
public class FFFC {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("argument missing!");
        }
        System.out.println("Hello " + args[0] + "!");
    }
}
