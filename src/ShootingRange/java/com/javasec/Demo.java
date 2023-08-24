package com.javasec;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Demo {
    @Test
    public void test() throws IOException {
        InputStream inputStream = new URL("file:///E:\\1.txt").openConnection().getInputStream();
        for(int a=1;a<10;a++){
            int read = inputStream.read();
            System.out.println(read);
        }
    }
}
